package com.example.loanprovisioning.metric;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.management.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Component
@Slf4j
public class PublicMetricConfig {
    private final long timestamp;

    public PublicMetricConfig() {
        this.timestamp = System.currentTimeMillis();

    }

    public Set<Metric> metrics() {
        LinkedHashSet<Metric> result = new LinkedHashSet<>();
        addBasicMetrics(result);
        addManagementMetrics(result);
        return result;
    }

    private void addBasicMetrics(LinkedHashSet<Metric> result) {
        Runtime runtime = Runtime.getRuntime();
        result.add(newMemoryMetric("mem", runtime.totalMemory() + getTotalNonHeapMemoryIfPossible()));
        result.add(newMemoryMetric("mem.free", runtime.freeMemory()));
        result.add(new Metric("processors", (double) runtime.availableProcessors()));
        result.add(new Metric("instance.uptime", (double) System.currentTimeMillis() - this.timestamp));

    }

    private long getTotalNonHeapMemoryIfPossible() {
        try {
            return ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed();
        } catch (Exception ex) {
            log.error("Error while getting Non Heap Memory Usage");
            return 0;
        }
    }

    private void addManagementMetrics(LinkedHashSet<Metric> result) {
        try {
            // JVM in ms
            result.add(new Metric("uptime", (double) ManagementFactory.getRuntimeMXBean().getUptime()));
            result.add(new Metric("systemload.average",
                    ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage()));
            addHeapMetrics(result);
            addNonHeapMetrics(result);
            addThreadMetrics(result);
            addClassLoadingMetrics(result);
            addGarbageCollectionMetrics(result);
        } catch (Exception ex) {
            // Expected on Google App Engine
            log.error("Error while getting System Load Average and Runtime MX Bean");
        }
    }

    private void addHeapMetrics(LinkedHashSet<Metric> result) {
        MemoryUsage memoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        result.add(newMemoryMetric("heap.committed", memoryUsage.getCommitted()));
        result.add(newMemoryMetric("heap.init", memoryUsage.getInit()));
        result.add(newMemoryMetric("heap.used", memoryUsage.getUsed()));
        result.add(newMemoryMetric("heap", memoryUsage.getMax()));
    }

    private void addNonHeapMetrics(LinkedHashSet<Metric> result) {
        MemoryUsage memoryUsage = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
        result.add(newMemoryMetric("nonheap.committed", memoryUsage.getCommitted()));
        result.add(newMemoryMetric("nonheap.init", memoryUsage.getInit()));
        result.add(newMemoryMetric("nonheap.used", memoryUsage.getUsed()));
        result.add(newMemoryMetric("nonheap", memoryUsage.getMax()));
    }

    private Metric newMemoryMetric(String name, long bytes) {
        return new Metric(name, (double) bytes / 1024);
    }

    private void addThreadMetrics(LinkedHashSet<Metric> result) {
        ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();
        result.add(new Metric("threads.peak", (double) threadMxBean.getPeakThreadCount()));
        result.add(new Metric("threads.daemon", (double) threadMxBean.getDaemonThreadCount()));
        result.add(new Metric("threads.totalStarted", (double) threadMxBean.getTotalStartedThreadCount()));
        result.add(new Metric("threads", (double) threadMxBean.getThreadCount()));
    }

    private void addClassLoadingMetrics(LinkedHashSet<Metric> result) {
        ClassLoadingMXBean classLoadingMxBean = ManagementFactory.getClassLoadingMXBean();
        result.add(new Metric("classes", (double) classLoadingMxBean.getLoadedClassCount()));
        result.add(new Metric("classes.loaded", (double) classLoadingMxBean.getTotalLoadedClassCount()));
        result.add(new Metric("classes.unloaded", (double) classLoadingMxBean.getUnloadedClassCount()));
    }

    private void addGarbageCollectionMetrics(LinkedHashSet<Metric> result) {
        List<GarbageCollectorMXBean> garbageCollectorMxBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMxBeans) {
            String name = beautifyGcName(garbageCollectorMXBean.getName());
            result.add(new Metric("gc." + name + ".count", (double) garbageCollectorMXBean.getCollectionCount()));
            result.add(new Metric("gc." + name + ".time", (double) garbageCollectorMXBean.getCollectionTime()));
        }
    }

    private String beautifyGcName(String name) {
        return StringUtils.replace(name, " ", "_").toLowerCase(Locale.ENGLISH);
    }
}

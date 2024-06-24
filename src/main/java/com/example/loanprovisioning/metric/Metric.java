package com.example.loanprovisioning.metric;

import lombok.Data;
import org.springframework.util.Assert;

import java.util.Date;

@Data
public class Metric {

    private final String name;

    private final Double value;

    private final Date timestamp;

    public Metric(String name, Double value) {
        this(name, value, new Date());
    }

    public Metric(String name, Double value, Date timestamp) {
        Assert.notNull(name, "Name must not be null");
        this.name = name;
        this.value = value;
        this.timestamp = timestamp;
    }

    public Metric increment(Double amount) {
        return new Metric(this.getName(), this.getValue() + amount);
    }

    public Metric set(Double value) {
        return new Metric(this.getName(), value);
    }

    @Override
    public String toString() {
        return "Metric [name=" + this.name + ", value=" + this.value + ", timestamp=" + this.timestamp + "]";
    }
}
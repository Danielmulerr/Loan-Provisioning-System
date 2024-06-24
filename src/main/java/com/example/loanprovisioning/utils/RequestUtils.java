package com.example.loanprovisioning.utils;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;

@Slf4j
public class RequestUtils {
    public static final String SORT_BY_REGEX = "\\.";
    public static final String ASC = "asc";
    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"
    };

    private static boolean isValidIp(String ip) {
        return StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip);
    }

    private static HttpServletRequest getHttpServletRequest() {
        val requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static String getRemoteAddr(HttpServletRequest request) {
        return request.getRemoteAddr();
    }


    public static String getIpAddressFromHeader(HttpServletRequest request) {
        if (request == null) {
            request = getHttpServletRequest();
            if (request == null) return null;
        }
        for (String ipHeader : IP_HEADER_CANDIDATES) {
            String headerValue = Collections.list(request.getHeaders(ipHeader)).stream()
                    .filter(RequestUtils::isValidIp)
                    .findFirst()
                    .orElse(null);

            if (headerValue != null) {
                return headerValue;
            }
        }
        return getRemoteAddr(request);
    }
}

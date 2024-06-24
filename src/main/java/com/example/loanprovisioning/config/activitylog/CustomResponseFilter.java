package com.example.loanprovisioning.config.activitylog;

import com.example.loanprovisioning.dto.ActionType;
import com.example.loanprovisioning.entity.AuditTrailLog;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;

import static com.example.loanprovisioning.config.AppConstants.LOG_PREFIX;


@Component
@Slf4j
public class CustomResponseFilter implements Filter {

    private final AuditService auditService;


    public CustomResponseFilter(AuditService auditService) {
        this.auditService = auditService;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Response interceptor");
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;

        try {
            filterChain.doFilter(httpRequest, httpResponse);
            String method = httpRequest.getMethod();
            String path = httpRequest.getRequestURI();
            ActionType actionType = mapMethodToActionType(method);
            if (actionType.equals(ActionType.OTHER)) {
                log.info("Skipping Activity");
                return;
            }

            if (httpResponse.getStatus() == HttpServletResponse.SC_OK
                    || httpResponse.getStatus() == HttpServletResponse.SC_CREATED) {
                AuditTrailLog auditTrailEntry = getAuditTrailEntry(httpRequest, actionType);
                auditTrailEntry.setPagePath(path);
                auditService.processAuditLog(httpRequest, auditTrailEntry);
            }
        } catch (Exception e) {
            log.error(LOG_PREFIX, "Error at response filter", "", e);
        }
    }

    private ActionType mapMethodToActionType(String method) {
        return switch (method.toUpperCase(Locale.ROOT)) {
            case "POST" -> ActionType.CREATE;
            case "PATCH", "PUT" -> ActionType.UPDATE;
            case "DELETE" -> ActionType.DELETE;
            default -> ActionType.OTHER;
        };
    }

    private AuditTrailLog getAuditTrailEntry(HttpServletRequest request, ActionType actionType) {
        AuditTrailLog auditTrailEntry = new AuditTrailLog();
        auditTrailEntry.setActionType(actionType);
        return auditTrailEntry;
    }
}

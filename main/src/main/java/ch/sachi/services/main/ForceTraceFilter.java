package ch.sachi.services.main;

import io.opentelemetry.api.trace.Span;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ForceTraceFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String forceTrace = req.getHeader("X-Force-Trace");
        if (forceTrace != null) {
            Span.current().setAttribute("force_trace", forceTrace);
            LoggerFactory.getLogger(ForceTraceFilter.class).debug("Forcing trace");
        }
        chain.doFilter(req, res);
    }
}

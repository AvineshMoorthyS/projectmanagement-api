package com.projectmanagement.config;

import com.projectmanagement.controller.OrganizationController;
import com.projectmanagement.helper.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

@Component
public class FilterConfig extends OncePerRequestFilter {

    @Autowired
    private User user;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private CacheManager cacheManager;

    public void printCacheContents() {
        Cache cache = cacheManager.getCache("organizationCache");
        if (cache instanceof ConcurrentMapCache) {
            ConcurrentMap<?, ?> nativeCache = ((ConcurrentMapCache) cache).getNativeCache();
            nativeCache.forEach((k, v) -> logger.info("Key: " + k + ", Value: " + v));
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String apiName = request.getMethod() + " " + request.getRequestURI();
            MDC.put("apiName", apiName);


            String userId = user.getUserId();// Adjust as needed
            if (userId != null && !userId.isEmpty()) {
                MDC.put("userId", userId);
            }

            filterChain.doFilter(request, response);
        } finally {
            MDC.remove("apiName");
            MDC.remove("userId");
        }
    }
}
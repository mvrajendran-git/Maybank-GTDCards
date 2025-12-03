package com.maybank.interceptor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {
  private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    long start = System.currentTimeMillis();

    String method = request.getMethod();
    String uri = request.getRequestURI();
    String query = request.getQueryString();

    log.info("REQUEST: method={}, uri={}, query={}, remoteAddr={}",
        method, uri, query, request.getRemoteAddr());

    filterChain.doFilter(request, response);

    long durationMs = System.currentTimeMillis() - start;

    log.info("RESPONSE: status={}, method={}, uri={}, durationMs={}",
        response.getStatus(), method, uri, durationMs);
  }
}
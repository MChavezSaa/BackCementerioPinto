package com.backendcementeriode.pinto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class SimpleCORSFilter implements Filter {

  private static  final Logger LOGGER = LoggerFactory.getLogger(SimpleCORSFilter.class);
  private static final List<String> ALLOWED_ORIGINS = Arrays.asList("http://localhost:4200");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("Initializin CORSFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest requestToUse = (HttpServletRequest) request;
        HttpServletResponse responseToUse = (HttpServletResponse) response;

            responseToUse.setHeader("Access-Control-Allow-Origin", requestToUse.getHeader("Origin"));
            responseToUse.setHeader("Access-Control-Allow-Credentials", "true");
            responseToUse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            responseToUse.setHeader("Access-Control-Max-Age", "3600");
            responseToUse.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-Width, remember-me");
            chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}

package com.saz.se.goat.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestControllerLoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerLoggerAspect.class);

    @Value("${response.log.limit:2000}")  // Limit response log size
    private int logLimit;

    @Value("${client.log.response.summary:false}")  // Log response summary flag
    private boolean logResponseSummary;

    @Around("@annotation(org.springframework.web.bind.annotation.CrossOrigin) && execution(public * *(..)) && args(.., @org.springframework.web.bind.annotation.RequestHeader headers)")
    public Object logRequestResponse(ProceedingJoinPoint proceedingJoinPoint, HttpHeaders headers) throws Throwable {
        long start = System.currentTimeMillis();
        Object[] args = proceedingJoinPoint.getArgs();

        // Logging request
        if (args.length > 0) {
            Object requestBody = args[0];
            logger.info("Request: " + (requestBody != null ? requestBody.toString() : "No request body"));
        }

        Object response = null;
        try {
            response = proceedingJoinPoint.proceed(args); // Proceed to actual method call

            // Logging response
            if (response != null) {
                String responseStr = response.toString();
                if (responseStr.length() > logLimit) {
                    responseStr = responseStr.substring(0, logLimit) + " (" + responseStr.length() + " bytes)";
                }
                logger.info("Response: " + responseStr);
            }

            long duration = System.currentTimeMillis() - start;
            logger.info("Request handled in {} ms", duration);

        } catch (Exception ex) {
            logger.error("Error processing request: " + ex.getMessage(), ex);
            throw ex;  // Re-throw the exception to ensure proper handling
        }

        return response;
    }
}


/**
 * 
 */
package com.saclient.saclientapi.services.aspects;

import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Krishna
 *
 */
public class ClientApiAspect {

	Logger logger = LoggerFactory.getLogger(ClientApiAspect.class);
	
	/**
	 * This method creates footprint of request how it travelled through the
	 * application. By logging it To use this advice method needs to be annotated
	 * with @LoggingPointCut
	 */
	@Around("@annotation(LoggingPointCut)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
		if (logger.isDebugEnabled()) {
			logger.debug("excecution started for method {} of class {} at {}", methodName, className,
					LocalDateTime.now());
		}
		long start = System.currentTimeMillis();
		joinPoint.getSignature();
		Object proceed = joinPoint.proceed();

		long executionTime = System.currentTimeMillis() - start;
		if (logger.isDebugEnabled()) {
			logger.debug("Excecution ends for method {} of class {} at {} took {} ms to complete", methodName,
					className, LocalDateTime.now(), executionTime);
		}
		return proceed;
	}
	
}

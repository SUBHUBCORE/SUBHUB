

package io.subHub.common.aspect;

import io.subHub.common.exception.ErrorCode;
import io.subHub.common.exception.RenException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Redis Section processing class
 *
 * @author By
 */
@Aspect
@Component
public class RedisAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * Is Redis caching enabled  true=open   false=close
     */

    @Around("execution(* io.subHub.common.redis.RedisUtils.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
            try{
                result = point.proceed();
            }catch (Exception e){
                logger.error("redis error", e);
                throw new RenException(ErrorCode.REDIS_ERROR);
            }
        return result;
    }
}

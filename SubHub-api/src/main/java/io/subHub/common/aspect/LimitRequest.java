package io.subHub.common.aspect;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD) // This annotation can only be placed on the method
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitRequest {
    long time() default 6000; //Limit time unit: milliseconds
    int count() default 1; // Number of requests allowed
}

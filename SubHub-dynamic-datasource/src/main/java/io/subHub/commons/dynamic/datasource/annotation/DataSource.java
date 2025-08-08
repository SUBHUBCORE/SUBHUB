package io.subHub.commons.dynamic.datasource.annotation;

import java.lang.annotation.*;

/**
 * Multiple data source annotations
 *
 * @author By
 * @since 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
    String value() default "";
}

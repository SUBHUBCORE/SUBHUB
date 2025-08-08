package io.subHub.common.annotation;

import java.lang.annotation.*;

/**
 * @author : by
 * @date:  2022/11/12  12:25
 * @description: Permission annotation
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    /**
     * routeUrl
     * @return
     */
    String routeUrl();
}

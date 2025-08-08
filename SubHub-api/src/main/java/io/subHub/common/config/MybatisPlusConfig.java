
package io.subHub.common.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import io.subHub.common.interceptor.IdTableNameHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus Config
 *
 * @author By
 * @since 1.0.0
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //Dynamic Table Name
        mybatisPlusInterceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor());
        // Paging plugin
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // optimistic lock
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // Prevent full table updates and deletions
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());


        return mybatisPlusInterceptor;
    }
    /**
     * Register dynamic table name interceptor
     *
     * @return Dynamic Table Name Interceptor
     */
    private DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor() {
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor =
                new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler(
                new IdTableNameHandler()
        );
        return dynamicTableNameInnerInterceptor;
    }

}

package com.coral.mini.program.common.config.mybatisplus;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author coral
 */
@Configuration
@MapperScan({"com.coral.mini.program.*.*.*.mapper","com.coral.mini.program.rcs.cff.data.consumer.db.mapper","com.coral.mini.program.*.mapper"})
public class MybatisPlusConfig {

    /**
     * 分页插件，自动识别数据库类型
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}

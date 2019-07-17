package cn.imooc.ad.delivery.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author buchengyin
 * @create 2019/7/6 10:11
 * @describe
 */
@Configuration
@MapperScan(basePackages = "cn.imooc.ad.delivery.dao")
public class MybatisPlusConfig {

    //添加分页插件拦截
    @Bean
    @SuppressWarnings("all")
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

}
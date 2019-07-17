package cn.imooc.ad.common.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author ：yinchong
 * @create ：2019/7/16 9:16
 * @description：
 * @modified By：
 * @version:
 */
@Configuration
public class ServerWebConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
           converters.clear();
           converters.add(new FastJsonHttpMessageConverter());
    }
}

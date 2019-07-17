package cn.imooc.ad.common.advice;

import cn.imooc.ad.common.annotation.IgnoreAdvice;
import cn.imooc.ad.common.vo.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author ：yinchong
 * @create ：2019/7/16 9:05
 * @description：
 * @modified By：
 * @version:
 */
@Slf4j
@ControllerAdvice
public class ServerResponseAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreAdvice.class)) {
            return false;
        }
        if (methodParameter.hasMethodAnnotation(IgnoreAdvice.class)) {
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o == null) {
            return ServerResponse.success(null);
        }
        if (o instanceof ServerResponse) {
            return o;
        }
        return ServerResponse.success(o);
    }
}

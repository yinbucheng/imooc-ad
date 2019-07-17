package cn.imooc.ad.common.exception;

import cn.imooc.ad.common.constant.ServerStatus;
import cn.imooc.ad.common.vo.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：yinchong
 * @create ：2019/7/16 9:11
 * @description：
 * @modified By：
 * @version:
 */
@Slf4j
@ControllerAdvice
public class ServerExceptionHandler {

    @ExceptionHandler({Exception.class, Throwable.class})
    @ResponseBody
    public Object handleException(HttpServletRequest req, HttpServletResponse resp, Object e) {
        log.error("server happen error,cause:" + e);
        return new ServerResponse(ServerStatus.ERROR.getCode(), ServerStatus.ERROR.getDescribe(), e.toString());
    }
}

package cn.imooc.ad.common.vo;

import cn.imooc.ad.common.constant.ServerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：yinchong
 * @create ：2019/7/16 8:58
 * @description：
 * @modified By：
 * @version:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerResponse {
    private Integer code;
    private String message;
    private Object data;

    public static ServerResponse success(Object data) {
        return new ServerResponse(ServerStatus.SUCCESS.getCode(), ServerStatus.SUCCESS.getDescribe(), data);
    }

    public static ServerResponse fail(String message) {
        return new ServerResponse(ServerStatus.FAIL.getCode(), ServerStatus.FAIL.getDescribe(), null);
    }
}

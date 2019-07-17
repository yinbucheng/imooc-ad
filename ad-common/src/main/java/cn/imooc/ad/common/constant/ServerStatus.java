package cn.imooc.ad.common.constant;

import lombok.Getter;

@Getter
public enum ServerStatus {
    SUCCESS(0,"执行成功"),
    FAIL(1,"执行错误"),
    ERROR(-1,"服务器异常");
    private Integer code;
    private String describe;

    ServerStatus(Integer code, String describe) {
        this.code = code;
        this.describe = describe;
    }
}

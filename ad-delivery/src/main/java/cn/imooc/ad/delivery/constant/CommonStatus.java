package cn.imooc.ad.delivery.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：yinchong
 * @create ：2019/7/16 9:35
 * @description：
 * @modified By：
 * @version:
 */
@Getter
@AllArgsConstructor
public enum CommonStatus {
    VALID(1, "有效"),
    INVALID(0, "无效");
    private Integer code;
    private String message;
}

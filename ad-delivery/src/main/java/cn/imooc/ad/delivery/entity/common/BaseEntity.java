package cn.imooc.ad.delivery.entity.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ：yinchong
 * @create ：2019/7/16 9:25
 * @description：
 * @modified By：
 * @version:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    private Date createTime;

    private Date updateTime;

    private String remark;

}

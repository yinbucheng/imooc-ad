package cn.imooc.ad.delivery.entity;

import cn.imooc.ad.delivery.constant.CommonStatus;
import cn.imooc.ad.delivery.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Target;
import java.util.Date;

/**
 * @author ：yinchong
 * @create ：2019/7/16 9:22
 * @description：
 * @modified By：
 * @version:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_add")
public class AdUser extends BaseEntity {

    private Long id;

    private String username;

    private String password;

    private Integer userStatus;

    public AdUser(String username, String password) {

        this.username = username;
        this.password = password;
        this.userStatus = CommonStatus.VALID.getCode();
        this.setCreateTime(new Date());
        this.setUpdateTime(new Date());
    }


}

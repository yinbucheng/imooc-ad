package cn.imooc.ad.delivery.entity;

import cn.imooc.ad.delivery.constant.CommonStatus;
import cn.imooc.ad.delivery.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ：yinchong
 * @create ：2019/7/16 9:23
 * @description：推广计划
 * @modified By：
 * @version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlan extends BaseEntity {

    private Long id;

    private Long userId;

    private String planName;

    private Integer planStatus;

    private Date startDate;

    private Date endDate;

    public AdPlan(Long userId, String planName,
                  Date startDate, Date endDate) {

        this.userId = userId;
        this.planName = planName;
        this.planStatus = CommonStatus.VALID.getCode();
        this.startDate = startDate;
        this.endDate = endDate;
        this.setCreateTime(new  Date());
        this.setUpdateTime(new Date());
    }


}

package cn.imooc.ad.delivery.entity;

import cn.imooc.ad.delivery.constant.CommonStatus;
import cn.imooc.ad.delivery.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ：yinchong
 * @create ：2019/7/16 9:22
 * @description：推广单元
 * @modified By：
 * @version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnit extends BaseEntity {
    private Long id;
    private Long planId;
    private String unitName;
    private Integer unitStatus;
    private Integer positionType;
    private Long budget;

    public AdUnit(Long planId, String unitName,
                  Integer positionType, Long budget) {
        this.planId = planId;
        this.unitName = unitName;
        this.unitStatus = CommonStatus.VALID.getCode();
        this.positionType = positionType;
        this.budget = budget;
        this.setCreateTime(new Date());
        this.setUpdateTime(new Date());
    }
}

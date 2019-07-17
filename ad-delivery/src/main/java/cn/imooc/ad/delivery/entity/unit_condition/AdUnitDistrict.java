package cn.imooc.ad.delivery.entity.unit_condition;

import cn.imooc.ad.delivery.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：yinchong
 * @create ：2019/7/16 9:23
 * @description：
 * @modified By：
 * @version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitDistrict extends BaseEntity {
    private Long id;
    private Long unitId;
    private String province;
    private String city;

    public AdUnitDistrict(Long unitId, String province, String city) {
        this.unitId = unitId;
        this.province = province;
        this.city = city;
    }
}

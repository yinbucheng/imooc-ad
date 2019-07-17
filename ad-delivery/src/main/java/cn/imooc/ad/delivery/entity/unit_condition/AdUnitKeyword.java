package cn.imooc.ad.delivery.entity.unit_condition;

import cn.imooc.ad.delivery.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：yinchong
 * @create ：2019/7/16 9:24
 * @description：
 * @modified By：
 * @version:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitKeyword extends BaseEntity {
    private Long id;
    private Long unitId;
    private String keyword;

    public AdUnitKeyword(Long unitId, String keyword) {
        this.unitId = unitId;
        this.keyword = keyword;
    }
}

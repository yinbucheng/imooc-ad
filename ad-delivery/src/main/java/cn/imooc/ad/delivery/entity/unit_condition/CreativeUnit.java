package cn.imooc.ad.delivery.entity.unit_condition;

import cn.imooc.ad.delivery.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
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
@AllArgsConstructor
@NoArgsConstructor
@TableName("creative_unit")
public class CreativeUnit extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long creativeId;

    private Long unitId;

    public CreativeUnit(Long creativeId, Long unitId) {
        this.creativeId = creativeId;
        this.unitId = unitId;
    }

}

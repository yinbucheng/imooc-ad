package cn.imooc.ad.delivery.entity;

import cn.imooc.ad.delivery.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：yinchong
 * @create ：2019/7/16 9:23
 * @description：创意表
 * @modified By：
 * @version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ad_creative")
public class Creative extends BaseEntity {

    private Long id;

    private String name;
    //
    private Integer type;
    //物料类型比如图片可以是bmp,jpg等等
    private Integer materialType;

    private Integer height;

    private Integer width;

    private Long size;

    private Integer duration;

    private Integer auditStatus;

    private Long userId;

    private String url;

}

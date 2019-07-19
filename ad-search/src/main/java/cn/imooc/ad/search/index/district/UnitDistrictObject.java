package cn.imooc.ad.search.index.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by Qinyi.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "unit_district",type = "unit_district")
public class UnitDistrictObject {
    @Id
    private Long unitId;
    private String province;
    private String city;

}

package cn.imooc.ad.search.index.interest;

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
@Document(indexName = "unit_it",type = "unit_it")
public class UnitItObject {
   @Id
    private Long unitId;
    private String itTag;
}

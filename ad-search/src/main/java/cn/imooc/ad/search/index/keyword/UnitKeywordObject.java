package cn.imooc.ad.search.index.keyword;

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
@Document(indexName = "unit_keyword",type = "unit_keyword")
public class UnitKeywordObject {
    @Id
    private Long unitId;
    private String keyword;
}

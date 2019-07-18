package cn.imooc.ad.search.index.adplan;

import cn.imooc.ad.search.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * Created by Qinyi.
 */
@Slf4j
@Component
public class AdPlanIndex implements IndexAware<Long, AdPlanObject> {

    @Autowired
    private ElasticsearchTemplate esTemplate;


    @Override
    public AdPlanObject get(Long key) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withFilter(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("id", key)))
                .build();
        List<AdPlanObject> adPlanObjects = esTemplate.queryForList(searchQuery, AdPlanObject.class);
        if (CollectionUtils.isEmpty(adPlanObjects)) {
            return null;
        }
        return adPlanObjects.get(0);
    }

    @Override
    public void add(Long key, AdPlanObject value) {
        IndexQuery indexQuery = new IndexQueryBuilder().withIndexName("ad_plan")
                .withType("ad_plan").withId(key + "").withObject(value).build();
        esTemplate.index(indexQuery);
    }

    @Override
    public void update(Long key, AdPlanObject value) {
        IndexQuery indexQuery = new IndexQueryBuilder().withIndexName("ad_plan")
                .withType("ad_plan").withId(key + "").withObject(value).build();
        esTemplate.index(indexQuery);
    }

    @Override
    public void delete(Long key, AdPlanObject value) {
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setQuery(QueryBuilders.termQuery("id", key));
        esTemplate.delete(deleteQuery);
    }
}

package cn.imooc.ad.search.index.adunit;

import cn.imooc.ad.search.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Qinyi.
 */
@Slf4j
@Component
public class AdUnitIndex implements IndexAware<Long, AdUnitObject> {

    @Autowired
    private ElasticsearchTemplate esTemplate;



    public Set<Long> match(Integer positionType) {
        SearchQuery query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("position_type",positionType)).build();
        List<AdUnitObject> adUnitObjects = esTemplate.queryForList(query, AdUnitObject.class);
        if(CollectionUtils.isEmpty(adUnitObjects))
            return null;
        return adUnitObjects.stream().map(AdUnitObject::getUnitId).collect(Collectors.toSet());
    }


    @Override
    public AdUnitObject get(Long key) {
        SearchQuery query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("id",key))).build();
        List<AdUnitObject> adUnitObjects = esTemplate.queryForList(query, AdUnitObject.class);
        if(CollectionUtils.isEmpty(adUnitObjects))
            return null;
        return adUnitObjects.get(0);
    }

    @Override
    public void add(Long key, AdUnitObject value) {

        IndexQuery indexQuery = new IndexQueryBuilder().withIndexName("ad_unit")
                .withType("ad_unit").withId(key + "").withObject(value).build();
        esTemplate.index(indexQuery);
    }

    @Override
    public void update(Long key, AdUnitObject value) {

        IndexQuery indexQuery = new IndexQueryBuilder().withIndexName("ad_unit")
                .withType("ad_unit").withId(key + "").withObject(value).build();
        esTemplate.index(indexQuery);
    }

    @Override
    public void delete(Long key, AdUnitObject value) {
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setQuery(QueryBuilders.termQuery("id", key));
        esTemplate.delete(deleteQuery,AdUnitObject.class);
    }
}

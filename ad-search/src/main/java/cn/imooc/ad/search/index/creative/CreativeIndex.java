package cn.imooc.ad.search.index.creative;

import cn.imooc.ad.search.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Qinyi.
 */
@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long, CreativeObject> {

    @Autowired
    private ElasticsearchTemplate template;


    public List<CreativeObject> fetch(Collection<Long> adIds) {

        if (CollectionUtils.isEmpty(adIds)) {
            return Collections.emptyList();
        }

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.termQuery("id", adIds)).build();
        List<CreativeObject> creativeObjects = template.queryForList(searchQuery, CreativeObject.class);
        return creativeObjects;
    }

    @Override
    public CreativeObject get(Long key) {
        GetQuery query = new GetQuery();
        query.setId(key + "");
        return template.queryForObject(query, CreativeObject.class);
    }

    @Override
    public void add(Long key, CreativeObject value) {
        IndexQuery query = new IndexQueryBuilder().withId(key + "").withObject(value).build();
        template.index(query);
    }

    @Override
    public void update(Long key, CreativeObject value) {
        IndexQuery query = new IndexQueryBuilder().withId(key + "").withObject(value).build();
        template.index(query);
    }

    @Override
    public void delete(Long key, CreativeObject value) {
        DeleteQuery query = new DeleteQuery();
        query.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("id", 1)));
        template.delete(query, CreativeObject.class);
    }
}

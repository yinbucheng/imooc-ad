package cn.imooc.ad.delivery.commad;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.CannotReadScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ：yinchong
 * @create ：2019/7/17 16:58
 * @description：
 * @modified By：
 * @version:
 */
@Component
@Order(-Integer.MAX_VALUE)
public class SqlLoadRunner implements CommandLineRunner, EnvironmentAware, BeanFactoryAware, ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(SqlLoadRunner.class);
    private static String LOAD_NAME = "spring.datasource.intellif.data";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Environment environment;

    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        loadSql();
    }

    private void loadSql() {
        String sqlPaths = environment.getProperty(LOAD_NAME);
        String[] paths = sqlPaths.split(",");
        if (paths != null && paths.length > 0) {
            for (String path : paths) {
                loadSqlAndInit(path);
            }
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private void loadSqlAndInit(String path) {
        if (Strings.isBlank(path)) {
            return;
        }
        List<String> sqls = resolveSqlFromFile(path);
        if(CollectionUtils.isEmpty(sqls)){
            return;
        }
        SqlLoadRunner bean = this.beanFactory.getBean(SqlLoadRunner.class);
        int size = sqls.size();
        if (size > 10000) {
            int middle = size / 2;
            Thread firstJob = new Thread(() ->
                    bean.executeSql(sqls.subList(0, middle))
            );

            Thread secondJob = new Thread(() ->
                    bean.executeSql(sqls.subList(middle, size)
                    ));
            firstJob.start();
            secondJob.start();

        } else {
            bean.executeSql(sqls);
        }
    }


    @Transactional
    public void executeSql(List<String> sqls) {
        if (CollectionUtils.isEmpty(sqls))
            return;
        long startTime = System.currentTimeMillis();
        for (String sql : sqls) {
            jdbcTemplate.execute(sql);
        }
        long endTime = System.currentTimeMillis();
        logger.info(" load sql use " + (endTime - startTime) + " ms");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    private static String readScript(EncodedResource resource, String commentPrefix, String separator,String blockCommentEndDelimiter)
            throws IOException {

        LineNumberReader lnr = new LineNumberReader(resource.getReader());
        try {
            return ScriptUtils.readScript(lnr, commentPrefix, separator,blockCommentEndDelimiter);
        } finally {
            lnr.close();
        }
    }

    private List<String> extractSql(Resource tempresouce) {
        EncodedResource resource = new EncodedResource(tempresouce);
        String script;
        String separator = ScriptUtils.DEFAULT_STATEMENT_SEPARATOR;
        String commentPrefix = ScriptUtils.DEFAULT_COMMENT_PREFIX;
        String blockCommentStartDelimiter = ScriptUtils.DEFAULT_BLOCK_COMMENT_START_DELIMITER;
        String blockCommentEndDelimiter = ScriptUtils.DEFAULT_BLOCK_COMMENT_END_DELIMITER;
        try {
            script = readScript(resource, commentPrefix, separator,blockCommentEndDelimiter);
        } catch (IOException ex) {
            throw new CannotReadScriptException(resource, ex);
        }
        if (separator == null) {
            separator = ScriptUtils.DEFAULT_STATEMENT_SEPARATOR;
        }
        if (!ScriptUtils.EOF_STATEMENT_SEPARATOR.equals(separator) && !ScriptUtils.containsSqlScriptDelimiters(script, separator)) {
            separator = ScriptUtils.FALLBACK_STATEMENT_SEPARATOR;
        }

        List<String> statements = new LinkedList<String>();
        ScriptUtils.splitSqlScript(resource, script, separator, commentPrefix, blockCommentStartDelimiter,
                blockCommentEndDelimiter, statements);
        return statements;
    }


    private Resource[] doGetResources(String location) {
        try {
            SortedResourcesFactoryBean factory = new SortedResourcesFactoryBean(
                    this.applicationContext, Collections.singletonList(location));
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to load resources from " + location,
                    ex);
        }
    }

    public List<String> resolveSqlFromFile(String path){
        if(!path.startsWith("classpath:")){
            path="classpath:"+path;
        }
        List<Resource> resources = getResources(LOAD_NAME, Collections.singletonList(path), false);
        if(CollectionUtils.isEmpty(resources)){
            return null;
        }
        return extractSql(resources.get(0));
    }

    private List<Resource> getResources(String propertyName, List<String> locations,
                                        boolean validate) {
        List<Resource> resources = new ArrayList<Resource>();
        for (String location : locations) {
            for (Resource resource : doGetResources(location)) {
                if (resource.exists()) {
                    resources.add(resource);
                } else if (validate) {
                    throw new RuntimeException(propertyName + "no find");
                }
            }
        }
        return resources;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

package cn.imooc.ad.search.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.net.InetSocketAddress;

/**
 * @author ：yinchong
 * @create ：2019/7/19 18:27
 * @description：
 * @modified By：
 * @version:
 */
@Configuration
public class Config {


    @Bean
    @Primary
    public TransportClient transportClient() {
        TransportAddress node = new TransportAddress(
                new InetSocketAddress("localhost", 9300)
        );
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(node);
        return client;
    }

}

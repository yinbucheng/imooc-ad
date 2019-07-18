package cn.imooc.ad.search.sender.kafka;

import cn.imooc.ad.search.mysql.dto.MySqlRowData;
import cn.imooc.ad.search.sender.ISender;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by Qinyi.
 */
@Component("kafkaSender")
@Slf4j
public class KafkaSender implements ISender {

    @Value("${adconf.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sender(MySqlRowData rowData) {

        String content = JSON.toJSONString(rowData);
        log.info("begin send data to kafka topic " + topic + " content:" + content);
        kafkaTemplate.send(
                topic, content
        );
    }

    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
    public void processMysqlRowData(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            MySqlRowData rowData = JSON.parseObject(
                    message.toString(),
                    MySqlRowData.class
            );
            log.info("kafka topic:ad-search-mysql-data  groupId:ad-search processMysqlRowData: " +
                    JSON.toJSONString(rowData));
        }
    }
}

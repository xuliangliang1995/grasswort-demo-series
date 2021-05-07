package demo;

import constants.KafkaServerConstants;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/7
 */
public class Demo01 {

    public static void main(String[] args) {
        new Thread(Demo01::consumer).start();

        new Thread(() -> {
            try {
                producer();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public static void producer() throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaServerConstants.SERVER);
        // kafka 持久化数据的 MQ， 不会加工数据， 双方需要约定编解码
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "0");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        final String topic = "topic";

        Random random = new Random();
        while (true) {
            TimeUnit.SECONDS.sleep(random.nextInt(3));
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    String key = "item" + j;
                    String val = "value" + i;
                    Future<RecordMetadata> send = producer.send(new ProducerRecord<>(topic, key, val));
                    RecordMetadata metadata = send.get();
                    int partition = metadata.partition();
                    long offset = metadata.offset();
                    //System.out.println("key:" + key + ", value:" + val + ", partition:" + partition + ", offset:" + offset);
                }
            }
        }
    }

    public static void consumer() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaServerConstants.SERVER);
        // kafka 持久化数据的 MQ， 不会加工数据， 双方需要约定编解码
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group_001");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // latest
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"); //自动提交时，异步提交(Consumer 挂了)，很容易丢数据和重复消费
        // properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, ""); 5s
        // properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList("topic"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5)); // 0 ~ 多条
            // 1. 按每条记录（最傻）
            // 2. 按分区粒度（每个分区有自己对应的 offset， 可以多线程处理）
            // 3. 按当前 poll 批次同步提交（复杂）

            System.out.println("poll " + records.count() + " 条数据。");
            // 以下代码优化很重要
            Iterator<ConsumerRecord<String, String>> iterator = records.iterator();

            while (iterator.hasNext()) {
                ConsumerRecord<String, String> record = iterator.next();
                int partition = record.partition();
                long offset = record.offset();
                System.out.println("key:" + record.key() + ", value:" + record.value() + ", partition:" + partition + ", offset:" + offset);
            }
            // 按批次提交
            consumer.commitAsync();
        }

    }
}

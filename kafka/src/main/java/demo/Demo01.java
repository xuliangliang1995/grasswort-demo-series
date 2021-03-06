package demo;

import constants.KafkaServerConstants;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.*;
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
        //createTopic();
        new Thread(Demo01::consumer).start();

        new Thread(() -> {
            try {
                producer();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void createTopic() {
        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaServerConstants.SERVER);
        AdminClient client = KafkaAdminClient.create(properties);
        NewTopic topic = new NewTopic("newTopic", 2, (short) 2);
        Map<String, String> topicCfg = new HashMap<>();
        topicCfg.put(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, "2");
        client.createTopics(Arrays.asList(topic));
        client.close();
    }


    public static void producer() throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaServerConstants.SERVER);
        // kafka ?????????????????? MQ??? ????????????????????? ???????????????????????????
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
        // kafka ?????????????????? MQ??? ????????????????????? ???????????????????????????
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group_001");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // latest
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"); //??????????????????????????????(Consumer ??????)????????????????????????????????????
        // properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, ""); 5s
        // properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList("topic"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5)); // 0 ~ ??????
            // 1. ???????????????????????????
            // 2. ???????????????????????????????????????????????? offset??? ????????????????????????
            // 3. ????????? poll ??????????????????????????????

            System.out.println("poll " + records.count() + " ????????????");
            // ???????????????????????????
            Iterator<ConsumerRecord<String, String>> iterator = records.iterator();

            while (iterator.hasNext()) {
                ConsumerRecord<String, String> record = iterator.next();
                int partition = record.partition();
                long offset = record.offset();
                System.out.println("key:" + record.key() + ", value:" + record.value() + ", partition:" + partition + ", offset:" + offset);
            }
            // ???????????????
            consumer.commitAsync();
        }

    }
}

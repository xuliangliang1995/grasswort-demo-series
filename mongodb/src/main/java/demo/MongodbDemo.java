package demo;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/4/28
 */
public class MongodbDemo {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("192.168.111.40", 27017);
        // boolean auth = db.authenticate(myUserName, myPassword);
        MongoClientOptions.Builder options = new MongoClientOptions.Builder();
        options.cursorFinalizerEnabled(true);
        // options.autoConnectRetry(true);// 自动重连true
        // options.maxAutoConnectRetryTime(10); // the maximum auto connect retry time
        options.connectionsPerHost(10);// 连接池设置为300个连接,默认为100
        options.connectTimeout(30000);// 连接超时，推荐>3000毫秒
        options.maxWaitTime(5000); //
        options.socketTimeout(0);// 套接字超时时间，0无限制
        options.writeConcern(WriteConcern.ACKNOWLEDGED);//
        options.build();

        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> shops = database.getCollection("shop");

        FindIterable<Document> iterable = shops.find();
        MongoCursor<Document> cursor = iterable.cursor();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }

        mongoClient.close();
    }
}

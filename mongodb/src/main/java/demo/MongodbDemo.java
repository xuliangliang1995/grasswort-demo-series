package demo;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadConcern;
import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

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
        options.connectionsPerHost(10);// 连接池设置为300个连接,默认为100
        options.connectTimeout(30000);// 连接超时，推荐>3000毫秒
        options.maxWaitTime(5000); //
        options.socketTimeout(3000);// 套接字超时时间，0无限制
        options.readConcern(ReadConcern.MAJORITY);
        options.writeConcern(WriteConcern.MAJORITY.withJournal(true));
        options.build();

        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> shops = database.getCollection("shop");

        /// 创建 id 为唯一索引
        /*IndexOptions indexOptions = new IndexOptions();
        indexOptions.unique(true);
        shops.createIndex(Indexes.ascending("id"), indexOptions);*/

        FindIterable<Document> iterable = shops.find();
        MongoCursor<Document> cursor = iterable.cursor();

        while (cursor.hasNext()) {
            Document shopDoc = cursor.next();
            System.out.println(shopDoc.toJson());
            /*shopDoc.put("extra_info", "请你思念我，回想我。");
            Bson filter = Filters.eq("_id", shopDoc.getObjectId("_id"));
            UpdateResult updateResult = shops.replaceOne(filter, shopDoc);
            System.out.println(updateResult.wasAcknowledged());*/
        }

        mongoClient.close();
    }
}

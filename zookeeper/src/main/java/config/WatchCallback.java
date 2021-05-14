package config;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/5/14
 */
public class WatchCallback implements Watcher, AsyncCallback.StatCallback, AsyncCallback.DataCallback {

    private ZooKeeper zk;

    private ConfigContext configuration;

    private final String path = "/config1";

    private CountDownLatch latch = new CountDownLatch(1);


    public void aWait() {
        System.out.println("waiting config data .");
        zk.exists(path, this, this, "extraContent");
        try {
            latch.await();
            System.out.println("get config data .");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event.toString());
        Event.EventType type = event.getType();
        switch (type) {
            case None:
                break;
            case NodeCreated:
                zk.getData(path, this, this, "NodeCreated");
                break;
            case NodeDeleted:
                System.out.println("config lose ...");
                configuration.setContent("");
                latch = new CountDownLatch(1);
                break;
            case NodeDataChanged:
                zk.getData(path, this, this, "NodeDataChanged");
                break;
            case NodeChildrenChanged:
                break;
            case DataWatchRemoved:
                break;
            case ChildWatchRemoved:
                break;
            case PersistentWatchRemoved:
                break;
            default:
                break;
        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        String s = new String(data);
        System.out.println("data callback :" + s);
        configuration.setContent(s);
        latch.countDown();
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        System.out.println("stat callback :" + stat);
        if (stat != null) {
            zk.getData(path, this, this, "watcher");
        }
    }


    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public void setConfiguration(ConfigContext configuration) {
        this.configuration = configuration;
    }
}

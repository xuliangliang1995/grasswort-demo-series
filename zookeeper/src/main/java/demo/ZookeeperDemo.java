package demo;

import constants.ZookeeperServerConstants;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/14
 */
public class ZookeeperDemo {

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zookeeper = new ZooKeeper(ZookeeperServerConstants.SERVER_URL, 3000, watcher);

        latch.await();

        String pathName = zookeeper.create("/xxoo", "oldData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(pathName);

        pathName = zookeeper.create("/ooxx", "oldData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(pathName);

        Stat stat = new Stat();
        byte[] node = zookeeper.getData("/xxoo", new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("getData Watcher : " + watchedEvent);
            }
        }, stat);
        System.out.println(new String(node));

        System.out.println(stat);

        Stat stat1 = zookeeper.setData("/xxoo", "newData".getBytes(), stat.getVersion());
        System.out.println(stat1);

        Stat stat2 = zookeeper.setData("/xxoo", "newData".getBytes(), stat1.getVersion());
        System.out.println(stat2);

        zookeeper.getData("/ooxx", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
                System.out.println("async getData :" + new String(bytes) + ",ctx : " + o);
            }
        }, "ctx");

        LockSupport.park();
    }


    public static Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            Event.KeeperState state = event.getState();
            Event.EventType type = event.getType();
            String path = event.getPath();

            System.out.println(event);
            System.out.println("state : " + state);
            System.out.println("path : " + path);

            switch (state) {
                case Unknown:
                    break;
                case Disconnected:
                    break;
                case NoSyncConnected:
                    break;
                case SyncConnected:
                    System.out.println("connected .");
                    latch.countDown();
                    break;
                case AuthFailed:
                    break;
                case ConnectedReadOnly:
                    break;
                case SaslAuthenticated:
                    break;
                case Expired:
                    break;
                case Closed:
                    break;
            }

            switch (type) {
                case None:
                    break;
                case NodeCreated:
                    break;
                case NodeDeleted:
                    break;
                case NodeDataChanged:
                    break;
                case NodeChildrenChanged:
                    break;
                case DataWatchRemoved:
                    break;
                case ChildWatchRemoved:
                    break;
                case PersistentWatchRemoved:
                    break;
            }
        }
    };
}

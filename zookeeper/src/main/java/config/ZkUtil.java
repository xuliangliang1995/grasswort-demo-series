package config;

import constants.ZookeeperServerConstants;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/5/14
 */
public class ZkUtil {

    public static ZooKeeper getZk() {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            ZooKeeper zooKeeper = new ZooKeeper(ZookeeperServerConstants.SERVER_URL, 3000,
                    new DefaultWatch(latch));
            latch.await();
            return zooKeeper;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static class DefaultWatch implements Watcher {

        private final CountDownLatch latch;

        public DefaultWatch(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void process(WatchedEvent watchedEvent) {

            Event.KeeperState state = watchedEvent.getState();

            switch (state) {
                case Unknown:
                    break;
                case Disconnected:
                    break;
                case NoSyncConnected:
                    break;
                case SyncConnected:
                    System.out.println("Zk Connected ...");
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
                default:
                    break;
            }
        }
    }
}

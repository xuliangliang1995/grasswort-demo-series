package lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/5/16
 */
public class ZkDistributedLock implements Watcher, AsyncCallback.StringCallback, AsyncCallback.Children2Callback, AsyncCallback.StatCallback {

    private final String path;

    private ZooKeeper zk;

    private String pathName;

    private CountDownLatch latch = new CountDownLatch(1);

    public ZkDistributedLock(String path) {
        this.path = path;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public void tryLock() {
        zk.create(path, String.valueOf(Thread.currentThread().getName()).getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,this,"abc");
        try {
            latch.await();
            System.out.println(pathName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("lock Success .");
    }

    public void unlock() {
        try {
            zk.delete(pathName, -1);
            //System.out.println(Thread.currentThread().getName() + "over work. ");
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        //System.out.println("Children Callback : " + children.size());

        for (int i = 0; i < children.size(); i++) {
            String childPath = "/" + children.get(i);
            //System.out.println(childPath);

            if (childPath.equals(pathName)) {
                if (i == 0) {
                    latch.countDown();
                    break;
                }
                zk.exists("/" + children.get(i - 1), this, this, "exists");
            }
        }

    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        System.out.println("Stat Callback : " + stat);
    }

    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        boolean createSuccess = rc == KeeperException.Code.OK.intValue();
        if (createSuccess) {
            //System.out.println("String Callback : " + name);
            pathName = name;
            System.out.println(pathName + " created .");
            zk.getChildren("/", this, this, "getChildren");
        } else {
            System.out.println(rc);
            System.out.println("path create fail : " + rc);
        }

    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);

        Event.EventType type = event.getType();
        switch (type) {
            case None:
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                zk.getChildren("/", this, this, "getChildren");
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
            default:
                break;
        }
    }


}

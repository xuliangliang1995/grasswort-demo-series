package config;
import config.ConfigContext;


import org.apache.zookeeper.ZooKeeper;
import sun.security.krb5.Config;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/5/14
 */
public class Test001 {

    /**
     * 模拟客户端从 Zookeeper 配置中心拿数据,并监控数据变化
     * [通过 zkCli.sh 手动改变节点内容,触发程序控制台输出内容变更]
     * @param args
     */
    public static void main(String[] args) {
        ConfigContext cfg = new ConfigContext();

        ZooKeeper zk = ZkUtil.getZk();

        WatchCallback watchCallback = new WatchCallback();
        watchCallback.setZk(zk);
        watchCallback.setConfiguration(cfg);

        watchCallback.aWait();

        while (true) {
            if (cfg.getContent().equals("")) {
                watchCallback.aWait();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(cfg.getContent());
        }


    }
}

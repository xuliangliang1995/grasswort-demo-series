package hash.consistenthash;

import hash.consistenthash.exception.BugException;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/13
 */
public class ConsistentHashTest {
    /**
     * 数据存储服务列表
     */
    private static final Machine[] machines = {
            new Machine(1, "192.168.0.10", 1000),
            new Machine(2, "192.168.0.11", 1000),
            new Machine(3, "192.168.0.12", 1000),
            new Machine(4, "192.168.0.13", 1000)
    };
    /**
     * 自增 ID
     */
    private static final AtomicLong autoIncreaseId = new AtomicLong();

    /**
     * 一致性哈希(分布式数据存储模拟测试)
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        MachineProxy bookStorageServer = new MachineProxy();
        // 启动集群
        startCluster(bookStorageServer);

        // 添加 100 条数据
        insertData(bookStorageServer, 10);
        testSearch(bookStorageServer);
        printMachineStatus();

        Random random = new Random();
        while (true) {
            // 每三秒, DOWN / UP 一台服务器
            TimeUnit.SECONDS.sleep(3);

            while (true) {
                long upCount = Arrays.stream(machines)
                        .filter(m -> m.getStatus() == Machine.MachineStatus.UP)
                        .count();
                boolean canDown = upCount > 1;

                Machine randomMachine = machines[random.nextInt(machines.length)];
                if (! canDown && Machine.MachineStatus.UP == randomMachine.getStatus()) {
                    continue;
                }
                if (Machine.MachineStatus.UP == randomMachine.getStatus()) {
                    bookStorageServer.down(randomMachine);
                } else {
                    bookStorageServer.up(randomMachine);
                }
                break;
            }
            // 添加 10 条数据
            insertData(bookStorageServer, 10);

            // 查询测试
            testSearch(bookStorageServer);

            // 打印集群状态
            printMachineStatus();
        }
    }





    /**
     * 配置集群
     * @param cluster
     */
    private static void startCluster(MachineCluster cluster) {
        for (Machine machine : machines) {
            cluster.up(machine);
        }
    }

    /**
     * 打印机器状态
     */
    private static void printMachineStatus() {
        for (Machine machine : machines) {
            System.out.printf("【%s】machine [%d] books size : %d \n",
                    machine.getStatus(), machine.getId(), machine.bookSize());
        }
    }

    /**
     * 添加数据
     * @param storage
     * @param count
     */
    private static void insertData(BookStorage storage, int count) {
        for (int i = 0; i < count; i++) {
            storage.addBook(new Book(autoIncreaseId.incrementAndGet(),
                    RandomStringUtils.randomAlphabetic(10)));
        }
    }

    /**
     * 测试数据查询
     * @param storage
     */
    private static void testSearch(BookStorage storage) {
        long maxId = autoIncreaseId.get();
        for (int id = 1; id <= maxId; id++) {
            Book book = storage.searchBook(id);
            if (book == null) {
                throw new BugException();
            }
        }
    }
}

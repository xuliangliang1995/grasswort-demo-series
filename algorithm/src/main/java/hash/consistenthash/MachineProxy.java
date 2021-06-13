package hash.consistenthash;

import hash.Hash;
import hash.consistenthash.exception.ServiceUnavailableException;
import hash.hashes.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/13
 */
public class MachineProxy implements BookStorage, MachineCluster {
    /**
     * 哈希算法
     */
    private static final Hash HASH = new SelfHash(1_0000);

    private HashRing hashRing = new HashRing();

    private Map<Integer, Machine> machines = new HashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void addBook(Book book) {
        lock.readLock().lock();
        try {
            long hash = HASH.hash(book.getId().toString());
            int machineId = hashRing.loadBalance(hash);
            if (machineId == -1) {
                throw new ServiceUnavailableException();
            }
            Machine machine = machines.get(machineId);
            assert machine != null;
            machine.addBook(book);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Book searchBook(long id) {
        lock.readLock().lock();
        try {
            long hash = HASH.hash(String.valueOf(id));
            int machineId = hashRing.loadBalance(hash);
            if (machineId == -1) {
                throw new ServiceUnavailableException();
            }
            Machine machine = machines.get(machineId);
            assert machine != null;
            return machine.searchBook(id);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void up(Machine machine) {
        lock.writeLock().lock();
        try {
            assert Machine.MachineStatus.DOWN == machine.getStatus();
            int virNodeCount = machine.getVirNodeCount();
            for (int i = 0; i < virNodeCount; i++) {
                long hash = HASH.hash(machine.toString() + LocalDateTime.now()
                        + RandomStringUtils.randomAlphabetic(10));
                HashRing.VirtualNode[] lastAndNextNode = hashRing.addVirtualNode(machine.getId(), hash);
                if (lastAndNextNode != null) {
                    // 记录虚拟节点
                    machine.recordVirNode(hash);

                    HashRing.VirtualNode lastNode = lastAndNextNode[0];
                    HashRing.VirtualNode nextNode = lastAndNextNode[1];

                    // 机器号不匹配,进行数据传输
                    if (nextNode.getMachineId() != machine.getId()) {
                        Predicate<Book> transferPredicate = b -> false;
                        long lastHashValue = lastNode.getHash();
                        if (hash > lastHashValue) {
                            transferPredicate = b -> {
                                long bookHash = HASH.hash(b.getId().toString());
                                return bookHash > lastHashValue && bookHash <= hash;
                            };
                        } else {
                            transferPredicate = b -> {
                                long bookHash = HASH.hash(b.getId().toString());
                                return bookHash > lastHashValue || bookHash <= hash;
                            };
                        }
                        // transfer data ..
                        machines.get(nextNode.getMachineId())
                                .transferDataTo(machine, transferPredicate);
                    }
                } else {
                    i--;
                }
            }
            machines.put(machine.getId(), machine);
            machine.setStatus(Machine.MachineStatus.UP);
        } finally {
            System.out.println();
            System.out.printf("@@ machine-%d @@ 已上线\n", machine.getId());
            lock.writeLock().unlock();
        }
    }

    @Override
    public void down(Machine machine) {
        lock.writeLock().lock();
        try {
            for (Long hash : machine.getVirNodes()) {
                HashRing.VirtualNode[] lastAndNextNode = hashRing.neighborNode(hash);
                if (lastAndNextNode != null) {

                    HashRing.VirtualNode lastNode = lastAndNextNode[0];
                    HashRing.VirtualNode nextNode = lastAndNextNode[1];

                    // 机器号不匹配,进行数据传输
                    if (nextNode.getMachineId() != machine.getId()) {
                        Predicate<Book> transferPredicate = b -> false;
                        long lastHashValue = lastNode.getHash();
                        if (hash > lastHashValue) {
                            transferPredicate = b -> {
                                long bookHash = HASH.hash(b.getId().toString());
                                return bookHash > lastHashValue && bookHash <= hash;
                            };
                        } else {
                            transferPredicate = b -> {
                                long bookHash = HASH.hash(b.getId().toString());
                                return bookHash > lastHashValue || bookHash <= hash;
                            };
                        }
                        // transfer data ..
                        machine.transferDataTo(machines.get(nextNode.getMachineId()), transferPredicate);
                    }
                }
                hashRing.removeVirtualNode(hash);
            }
            machines.remove(machine.getId());
            machine.down();
        } finally {
            lock.writeLock().unlock();
            System.out.println();
            System.out.printf("@@ machine-%d @@ 已下线\n", machine.getId());
        }
    }

    @Override
    public Collection<Machine> machines() {
        return machines.values();
    }

}

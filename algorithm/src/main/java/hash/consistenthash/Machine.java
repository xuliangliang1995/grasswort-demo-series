package hash.consistenthash;

import java.util.*;
import java.util.function.Predicate;

/**
 * @author 🌺xuliangliang🌺
 * @Description 模拟机器
 * @Date 2021/6/13
 */
public class Machine implements BookStorage {

    private final int id;

    private final String ip;

    private final int virNodeCount;

    private final List<Book> books = new LinkedList<>();

    private final Set<Long> virNodes = new HashSet<>();

    private MachineStatus status = MachineStatus.DOWN;

    public Machine(int id, String ip, int virNodeCount) {
        this.id = id;
        this.ip = ip;
        this.virNodeCount = virNodeCount;
    }

    /**
     * 传输
     * @param machine
     * @param transferPredicate
     */
    public void transferDataTo(Machine machine, Predicate<Book> transferPredicate) {
        if (this.equals(machine)) {
            // ignore
            return;
        }
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (transferPredicate.test(book)) {
                iterator.remove();
                machine.books.add(book);
                System.out.printf("move Book[%s],  Machine[%d] ==> [%d] \n", book, this.id, machine.id);
            }
        }
    }

    /**
     * 重置
     */
    public void down() {
        status = MachineStatus.DOWN;
        virNodes.removeIf(v -> true);
        books.removeIf(b -> true);
    }

    /**
     * 记录虚拟节点
     * @param hash
     */
    public void recordVirNode(long hash) {
        virNodes.add(hash);
    }

    public Set<Long> getVirNodes() {
        return virNodes;
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
        System.out.printf("save %s to machine %s \n", book, this);
    }

    @Override
    public Book searchBook(long id) {
        Book book = books.stream().filter(b -> b.getId().equals(id))
                .findFirst().orElse(null);
        System.out.printf("search book[%s] from machine %s , result : %s \n",
                id, this, book);
        return book;
    }

    public int getId() {
        return id;
    }

    public MachineStatus getStatus() {
        return status;
    }

    public void setStatus(MachineStatus status) {
        this.status = status;
    }

    public int getVirNodeCount() {
        return virNodeCount;
    }

    public int bookSize() {
        return books.size();
    }

    /**
     * 机器状态
     */
    public enum MachineStatus {
        /**
         * 上线
         */
        UP,
        /**
         * 下线(默认状态)
         */
        DOWN,
        /**
         * 暂停服务
         */
        OUT_OF_SERVICE;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Machine) {
            return ((Machine)obj).id == this.id;
        }
        return false;
    }
}

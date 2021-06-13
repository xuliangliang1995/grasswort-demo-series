package hash.consistenthash;

import java.util.Collection;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/13
 */
public interface MachineCluster {

    /**
     * 机器上线
     * @param machine
     */
    void up(Machine machine);

    /**
     * 机器下线
     * @param machine
     */
    void down(Machine machine);

    /**
     * 机器列表
     * @return
     */
    Collection<Machine> machines();
}

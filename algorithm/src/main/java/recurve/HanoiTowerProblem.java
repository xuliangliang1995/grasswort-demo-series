package recurve;

/**
 * @author xuliangliang
 * @Description 汉诺塔问题
 * @Date 2021/6/3
 */
public class HanoiTowerProblem {

    public static void main(String[] args) {
        move(4, "from", "to", "other");
    }

    private static void move(int size, String from, String to, String other) {
        if (size == 1) {
            doMove(size, from, to);
            return;
        }
        move(size - 1, from, other, to);
        doMove(size, from, to);
        move(size - 1, other, to, from);
    }


    private static void doMove(int level, String from, String to) {
        System.out.printf("Move %s  %s ==> %s . \n", level, from, to);
    }
}

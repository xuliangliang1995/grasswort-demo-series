package reservoir;

import java.util.Random;

/**
 * @author 🌺xuliangliang🌺
 * @Description 蓄水池算法
 * @Date 2021/6/12
 */
public class Reservoir {

    public static void main(String[] args) {
        int numberCount = 200;
        int[] numbers = new int[numberCount + 1];
        for (int i = 0; i < 500000; i++) {
            Reservoir reservoir = new Reservoir(10);
            for (int j = 1; j <= numberCount; j++) {
                reservoir.via(j);
            }
            int[] bag = reservoir.bag();
            for (int num : bag) {
                numbers[num] = numbers[num] + 1;
            }
        }
        for (int i = 0; i < numbers.length; i++) {
            System.out.printf("%d : %d\n", i + 1, numbers[i]);
        }
    }

    private final int capacity;

    private int[] bag;

    private int size = 0;

    private Random random = new Random();

    public Reservoir(int capacity) {
        this.capacity = capacity;
        this.bag = new int[capacity];
    }


    /**
     * num 应该是一个递增序列,应按升序顺序调用
     * 例如 : via(1), via(2), via(3)
     * @param num
     */
    public void via(int num) {
        if (size < capacity) {
            bag[size++] = num;
            return;
        }
        boolean toBag = (random.nextInt(num) + 1) <= capacity;
        if (toBag) {
            bag[random.nextInt(capacity)] = num;
        }
    }


    public int[] bag() {
        return bag;
    }


}

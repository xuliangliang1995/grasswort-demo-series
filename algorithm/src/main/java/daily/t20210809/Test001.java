package daily.t20210809;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/8/12
 */
public class Test001 {

    //jkokkkacccpee
    public static void main(String[] args) {
        print("jkokkkacccpee");
    }

    public static void print(String str) {
        char[] arr = str.toCharArray();
        insertionSort(arr);
        char cursorChar = arr[0];
        int charCount = 1;
        for (int i = 1; i < arr.length; i++) {
            char nextChar = arr[i];
            if (nextChar == cursorChar) {
                charCount++;
            } else {
                System.out.print(cursorChar + "(" + charCount + ")");
                cursorChar = nextChar;
                charCount = 1;
            }
        }
        if (charCount > 0) {
            System.out.print(cursorChar + "(" + charCount + ")");
        }
    }

    public static void insertionSort(char[] arr) {
        for (int i = 1; i < arr.length; i++) {
            char key = arr[i];
            for (int j = i - 1; j >= 0 ; j--) {
                char lastChar = arr[j];
                if (lastChar > key) {
                    swapChar(arr, j, j + 1);
                } else {
                    break;
                }
            }
        }
    }


    public static void swapChar(char[] arr, int a, int b) {
        if (a == b) {
            return;
        }
        char tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}

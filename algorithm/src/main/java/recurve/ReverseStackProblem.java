package recurve;

import java.util.Stack;

/**
 * @author 🌺xuliangliang🌺
 * @Description 不声明新的数据结构来反转一个链表
 * @Date 2021/6/3
 */
public class ReverseStackProblem {


    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 1; i <= 10; i++) {
            stack.push(i);
        }
        printStack(stack);
        reverse(stack);
        printStack(stack);
    }

    /**
     * 栈反转
     * @param stack
     */
    private static void reverse(Stack<Integer> stack) {
        if (stack == null || stack.isEmpty()) {
            return;
        }
        int bottom = popBottom(stack);
        reverse(stack);
        stack.push(bottom);
    }

    /**
     * 釜底抽薪
     * @param stack
     * @return
     */
    private static Integer popBottom(Stack<Integer> stack) {
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        Integer popVal = stack.pop();
        if (stack.isEmpty()) {
            return popVal;
        }
        Integer bottom = popBottom(stack);
        stack.push(popVal);
        return bottom;
    }

    /**
     * 打印栈
     * @param stack
     */
    private static void printStack(Stack<Integer> stack) {
        for (Integer integer : stack) {
            System.out.print(integer + ",");
        }
        System.out.print("\n");
    }
}

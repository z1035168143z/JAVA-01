package com.zrzhao.mysql.configrution;

import java.util.Stack;
import java.util.function.Supplier;

/**
 * @author zrzhao
 * @date 2021/3/6
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<Stack<String>> CONTEXT_HOLDER = ThreadLocal.withInitial(() -> {
        Stack<String> stack = new Stack<>();
        stack.push("write");
        return stack;
    });

    public static void setDataSourceKey(String key) {
        CONTEXT_HOLDER.get().push(key);
    }

    public static String getDataSourceKey() {
        return CONTEXT_HOLDER.get().peek();
    }

    public static void clearDataSourceKey() {
        Stack<String> stack = CONTEXT_HOLDER.get();
        stack.pop();

        if (stack.isEmpty()) {
            CONTEXT_HOLDER.remove();
        }
    }
}

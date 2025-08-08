package io.subHub.commons.dynamic.datasource.config;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Multiple data source context
 *
 * @author By
 */
public class DynamicContextHolder {
    @SuppressWarnings("unchecked")
    private static final ThreadLocal<Deque<String>> CONTEXT_HOLDER = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return new ArrayDeque();
        }
    };

    /**
     * Obtain the current thread data source
     *
     * @return Data source name
     */
    public static String peek() {
        return CONTEXT_HOLDER.get().peek();
    }

    /**
     * Set the current thread data source
     *
     * @param dataSource Data source name
     */
    public static void push(String dataSource) {
        CONTEXT_HOLDER.get().push(dataSource);
    }

    /**
     * Clear the current thread data source
     */
    public static void poll() {
        Deque<String> deque = CONTEXT_HOLDER.get();
        deque.poll();
        if (deque.isEmpty()) {
            CONTEXT_HOLDER.remove();
        }
    }

}

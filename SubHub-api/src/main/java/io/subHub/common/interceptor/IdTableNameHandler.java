package io.subHub.common.interceptor;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class IdTableNameHandler implements TableNameHandler {
    /**
     * Which tables can use this dynamic table name rule
     * Table Name ->Divide into Several Tables
     */
    private static Map<String, Integer> configTableInfoMap = new HashMap();

    static {
        configTableInfoMap.put("sys_union_dept", 10);
    }

    //To avoid multi-threaded data conflicts, use ThreadLocal
    private static final ThreadLocal<Long> idData = new ThreadLocal<>();

    /**
     * Before dividing the specific method into tables, it is specified to rely on the ID of the table
     *
     * @param id id
     */
    public static void initCurrentId(Long id) {
        idData.set(id);
    }

    public static Long getCurrentId() {
        return idData.get();
    }

    /**
     * After use, remove to prevent memory leakage
     */
    public static void removeCurrentId() {
        idData.remove();
    }

    @Override
    public String dynamicTableName(String sql, String tableName) {
        if (StringUtils.isEmpty(tableName) ||
                !configTableInfoMap.containsKey(tableName)) {
            return tableName;
        }
        // Number of sub tables
        Integer tableSize = configTableInfoMap.get(tableName);

        // The current ID to be split into tables
        Long currentId = getCurrentId();
        // Which table should the current ID data be saved in
        int tableIndex = (int) (currentId % tableSize);
        // Delete current ID
        removeCurrentId();
        return tableName + "_" + tableIndex;
    }
}
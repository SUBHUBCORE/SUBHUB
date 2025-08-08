package io.subHub.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.io.Serializable;
import java.util.Collection;

/**
 * Basic service interface, all service interfaces must inherit
 *
 * @author By
 */
public interface BaseService<T> {
    Class<T> currentModelClass();

    /**
     * <p>
     * Insert a record (selection field, policy insertion)
     * </p>
     *
     */
    boolean insert(T entity);

    /**
     * <p>
     * Insert (batch), this method does not support Oracle or SQL Server
     * </p>
     *
     * @param entityList
     */
    boolean insertBatch(Collection<T> entityList);

    /**
     * <p>
     * Insert (batch), this method does not support Oracle or SQL Server
     * </p>
     *
     * @param entityList Entity Object Collection
     * @param batchSize  Insert batch quantity
     */
    boolean insertBatch(Collection<T> entityList, int batchSize);

    /**
     * <p>
     * Select modifications based on ID
     * </p>
     *
     * @param entity Entity Object
     */
    boolean updateById(T entity);

    /**
     * <p>
     * Update records based on where Entity conditions
     * </p>
     *
     * @param entity        Entity Object
     * @param updateWrapper Entity object encapsulation operation class {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    boolean update(T entity, Wrapper<T> updateWrapper);

    /**
     * <p>
     * Batch update based on ID
     * </p>
     *
     * @param entityList Entity Object Collection
     */
    boolean updateBatchById(Collection<T> entityList);

    /**
     * <p>
     * Batch update based on ID
     * </p>
     *
     * @param entityList Entity Object Collection
     * @param batchSize  Update batch quantity
     */
    boolean updateBatchById(Collection<T> entityList, int batchSize);

    /**
     * <p>
     * Search based on ID
     * </p>
     *
     * @param id Primary Key ID
     */
    T selectById(Serializable id);

    /**
     * <p>
     * Delete based on ID
     * </p>
     *
     * @param id Primary Key ID
     */
    boolean deleteById(Serializable id);

    /**
     * <p>
     * Delete (batch delete based on ID)
     * </p>
     *
     * @param idList List of primary key IDs
     */
    boolean deleteBatchIds(Collection<? extends Serializable> idList);
}

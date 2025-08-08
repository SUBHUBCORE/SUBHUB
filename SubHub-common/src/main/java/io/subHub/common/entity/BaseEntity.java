package io.subHub.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *Basic entity class, all entities need to inherit
 *
 * @author By
 */
@Data
public abstract class BaseEntity implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDt;

}

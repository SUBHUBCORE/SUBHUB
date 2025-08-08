package io.subHub.modules.sensitiveWords.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sensitive_words")
public class SensitiveWordsEntity {
    /**
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     */
    private String category;
    /**
     *
     */
    private String terms;

    private Date createDt;

}

package io.subHub.modules.rssMedium.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("rss_medium")
public class RssMediumEntity {
    /**
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     */
    private Long projectId;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private String content;
    /**
     *
     */
    private String contentPart;
    /**
     *
     */
    private String link;
    /**
     *
     */
    private String guid;

    /**
     *
     */
    private Date contentPubDate;
    /**
     *
     */
    private Date createDt;
    /**
     *
     */
    private Date updateDt;
}

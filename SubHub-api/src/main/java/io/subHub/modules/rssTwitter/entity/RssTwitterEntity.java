package io.subHub.modules.rssTwitter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("rss_twitter")
public class RssTwitterEntity {
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
    private String tweetsId;
    /**
     *
     */
    private String content;
    /**
     *
     */
    private String mediaUrl;
    /**
     *
     */
    private String mediaType;
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

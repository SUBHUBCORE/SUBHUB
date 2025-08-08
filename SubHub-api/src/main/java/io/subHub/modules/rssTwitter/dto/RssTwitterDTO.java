package io.subHub.modules.rssTwitter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class RssTwitterDTO implements Serializable {
    private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "")
	private Long id;
	@ApiModelProperty(value = "")
	private Long projectId;
	@ApiModelProperty(value = "")
	private String tweetsId;
	@ApiModelProperty(value = "")
	private String content;
	@ApiModelProperty(value = "")
	private String mediaUrl;
	@ApiModelProperty(value = "")
	private String mediaType;
	@ApiModelProperty(value = "")
	private Date contentPubDate;
	@ApiModelProperty(value = "")
	private Date createDt;
	@ApiModelProperty(value = "")
	private Date updateDt;

}
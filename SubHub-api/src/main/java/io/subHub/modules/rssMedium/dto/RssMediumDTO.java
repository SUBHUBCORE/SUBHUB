package io.subHub.modules.rssMedium.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;


@Data
public class RssMediumDTO implements Serializable {
    private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "")
	private Long id;
	@ApiModelProperty(value = "")
	private Long projectId;
	@ApiModelProperty(value = "")
	private String title;
	@ApiModelProperty(value = "")
	private String content;
	@ApiModelProperty(value = "")
	private String contentPart;
	@ApiModelProperty(value = "")
	private String link;
	@ApiModelProperty(value = "")
	private String guid;
	@ApiModelProperty(value = "")
	private Date contentPubDate;
	@ApiModelProperty(value = "")
	private Date createDt;
	@ApiModelProperty(value = "")
	private Date updateDt;

	public void setContent(String content) {
		this.content = (content == null) ? "" : content;
	}

	public void setContentPart(String contentPart) {
		this.contentPart = (contentPart == null) ? "" : contentPart;
	}
}
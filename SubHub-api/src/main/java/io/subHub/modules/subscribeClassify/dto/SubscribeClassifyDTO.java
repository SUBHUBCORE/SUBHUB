package io.subHub.modules.subscribeClassify.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author by
 * @since 1.0.0 2024-02-22
 */
@Data
@ApiModel(value = "SubscribeClassify")
public class SubscribeClassifyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	private Integer id;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private Integer value;

	@ApiModelProperty(value = "0=Enable；1=Disable")
	private Integer status;

	@ApiModelProperty(value = "sort")
	private Integer sort;

	@ApiModelProperty(value = "")
	private Date createDt;

	@ApiModelProperty(value = "")
	private Date updateDt;


}
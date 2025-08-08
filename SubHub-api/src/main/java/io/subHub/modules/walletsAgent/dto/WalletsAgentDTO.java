package io.subHub.modules.walletsAgent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-02-27
 */
@Data
@ApiModel(value = "")
public class WalletsAgentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	private Integer id;

	@ApiModelProperty(value = "")
	private String srcAddress;

	@ApiModelProperty(value = "")
	private String dstAddress;

	@ApiModelProperty(value = "")
	private String walletName;

	@ApiModelProperty(value = "")
	private Date createTime;


}
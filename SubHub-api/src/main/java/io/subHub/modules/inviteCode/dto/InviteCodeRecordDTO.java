package io.subHub.modules.inviteCode.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * Invitation Code Record
 *
 * @author by 
 * @since 1.0.0 2024-01-22
 */
@Data
@ApiModel(value = "Invitation Code Record Form")
public class InviteCodeRecordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	private Integer id;

	@ApiModelProperty(value = "")
	private String inviteCode;

	@ApiModelProperty(value = "")
	private Integer useStatus;

	@ApiModelProperty(value = "")
	private Long useUserId;

	@ApiModelProperty(value = "")
	private String claimStatus;

	@ApiModelProperty(value = "")
	private String recipientName;

	@ApiModelProperty(value = "")
	private Date activationDt;

	@ApiModelProperty(value = "")
	private Date createDt;

	@ApiModelProperty(value = "")
	private Date updateDt;

}
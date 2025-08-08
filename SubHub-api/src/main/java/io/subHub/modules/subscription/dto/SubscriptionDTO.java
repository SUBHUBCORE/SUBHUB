package io.subHub.modules.subscription.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-01-24
 */
@Data
@ApiModel(value = "")
public class SubscriptionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	private Long id;

	@ApiModelProperty(value = "")
	private String address;

	@ApiModelProperty(value = "")
	private String pid;

	@ApiModelProperty(value = "Subscriptions_id associated with projects_id")
	private Integer belongsToId;

	@ApiModelProperty(value = "")
	private String networkName;

	@ApiModelProperty(value = "")
	private Date createAt;

	@ApiModelProperty(value = "")
	private Date updateAt;

	@ApiModelProperty(value = "")
	private int subscribeSource;
	
	@ApiModelProperty(value = "")
	private int subscribeStatus;

	public void setNetworkName(String networkName) {
		if (networkName.equals("Unkown") || networkName.equals("Default") || StringUtils.isEmpty(networkName)){
			this.networkName = "BNB Chain";
		}else {
			this.networkName = networkName;
		}
	}
}
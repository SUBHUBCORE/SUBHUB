package io.subHub.modules.project.dto;

import io.subHub.common.validator.group.AddGroup;
import io.subHub.common.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;


/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-01-22
 */
@Data
@ApiModel(value = "")
public class ProjectDTO implements Serializable {

	private static final long serialVersionUID = 5913830023449702956L;
	@ApiModelProperty(value = "app_key")
	@Null(message="ID must be empty", groups = AddGroup.class)
	private Long id;

	@ApiModelProperty(value = "")
	@NotNull(message="", groups = AddGroup.class)
//	@NotNull(message="", groups = UpdateGroup.class)
	private String name;

	@ApiModelProperty(value = "")
	private Integer certifications;

	@ApiModelProperty(value = "")
	private String certFailReason;

	@ApiModelProperty(value = "")
	private Integer displayRank;

	private String type;

	@ApiModelProperty(value = "")
//	@NotNull(message="classifyType cannot be empty", groups = UpdateGroup.class)
	@NotNull(message="classifyType cannot be empty", groups = AddGroup.class)
	private int[] classifyType;

	@ApiModelProperty(value = "")
//	@NotNull(message="Logo cannot be empty", groups = UpdateGroup.class)
	@NotNull(message="Logo cannot be empty", groups = AddGroup.class)
	private String logo;

	@ApiModelProperty(value = "")
	@NotNull(message="description cannot be empty", groups = AddGroup.class)
//	@NotNull(message="description cannot be empty", groups = UpdateGroup.class)
	private String description;

	@ApiModelProperty(value = "")
	private String hashedPassword;

	@ApiModelProperty(value = "")
	private String walletAddress;

	@ApiModelProperty(value = "")
	private String pid;

	@ApiModelProperty(value = "")
	private String walletName;

	@ApiModelProperty(value = "")
	private String nft;

	@ApiModelProperty(value = "")
	private String website;

	@ApiModelProperty(value = "")
//	@NotNull(message="twitter cannot be empty", groups = AddGroup.class)
//	@NotNull(message="twitter cannot be empty", groups = UpdateGroup.class)
	private String twitter;

	private String twitterId;

	@ApiModelProperty(value = "")
	private String discord;

	@ApiModelProperty(value = "")
	private String telegram;
	
	@ApiModelProperty(value = "")
	private String medium;

	@ApiModelProperty(value = "")
	private String customProperties;

	@ApiModelProperty(value = "User_id associated with project_id")
	private Long belongsToId;

	@ApiModelProperty(value = "")
	private String inviteCode;

	private Integer frequencyLimit;

	@ApiModelProperty(value = "")
	private Date createAt;

	@ApiModelProperty(value = "")
	private Date updateDt;

	@ApiModelProperty(value = "")
	private String email;

	@ApiModelProperty(value = "")
	private String emailVerifyCode;

	public void setClassifyType(int[] classifyType) {
		if (classifyType.length > 0){
			this.type = Arrays.stream(classifyType).mapToObj(String::valueOf).collect(Collectors.joining(","));
		}
		this.classifyType = classifyType;
	}

	public void setType(String type) {
		if (StringUtils.isNotEmpty(type)){
			this.classifyType = Arrays.stream(type.split(","))
					.filter(s -> !s.trim().isEmpty())
					.mapToInt(Integer::parseInt)
					.toArray();
//			this.classifyType = Arrays.asList(type.split(",")).stream().mapToInt(Integer::parseInt).toArray();
		}
		this.type = type;
	}
}
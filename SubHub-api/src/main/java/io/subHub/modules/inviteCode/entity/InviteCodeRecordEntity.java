package io.subHub.modules.inviteCode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Invitation Code Record Form
 *
 * @author by 
 * @since 1.0.0 2024-01-22
 */
@Data
@TableName("invite_code_record")
public class InviteCodeRecordEntity {
	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 */
	private String inviteCode;
			/**
	 */
	private Integer useStatus;
			/**
	 */
	private Long useUserId;
			/**
	 */
	private String claimStatus;
			/**
	 */
	private String recipientName;
			/**
	 */
	private Date activationDt;
			/**
	 * 
	 */
	private Date createDt;
			/**
	 * 
	 */
	private Date updateDt;
	}

package io.subHub.modules.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-01-22
 */
@Data
@TableName("projects")
public class ProjectEntity {
	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
			/**
	 */
	private String name;
			/**
	 */
	private Integer certifications;
			/**
	 */
	private String certFailReason;
			/**
	 */
	private Integer displayRank;
			/**
	 * 
	 */
	private String type;
			/**
	 * 
	 */
	private String logo;
			/**
	 * 
	 */
	private String description;
			/**
	 * 
	 */
	private String hashedPassword;
			/**
	 * 
	 */
	private String walletAddress;

	private String pid;
			/**
	 * 
	 */
	private String nft;
			/**
	 * 
	 */
	private String website;
			/**
	 * 
	 */
	private String twitter;
			/**
	 *
	 */
	private String twitterId;
			/**
	 * 
	 */
	private String discord;
			/**
	 * 
	 */
	private String telegram;
	/**
	 *
	 */
	private String medium;
			/**
	 * 
	 */
	private String customProperties;
			/**
	 */
	private Long belongsToId;
			/**
	 * 
	 */
	private String inviteCode;

	private Integer frequencyLimit;
			/**
	 * 
	 */
	private Date createAt;
			/**
	 * 
	 */
	private Date updateDt;
	}

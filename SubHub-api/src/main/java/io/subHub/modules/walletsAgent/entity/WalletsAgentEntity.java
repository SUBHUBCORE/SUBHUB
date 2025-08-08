package io.subHub.modules.walletsAgent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-02-27
 */
@Data
@TableName("wallets_agent")
public class WalletsAgentEntity{
	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 
	 */
	private String srcAddress;
			/**
	 * 
	 */
	private String dstAddress;
			/**
	 * 
	 */
	private String walletName;
			/**
	 * 
	 */
	private Date createTime;
	}

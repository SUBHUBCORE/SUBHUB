package io.subHub.modules.subscription.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-01-24
 */
@Data
@TableName("subscriptions")
public class SubscriptionEntity{
	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 
	 */
	private String address;
	/**
	 *
	 */
	private String pid;
	/**
	 * Subscriptions_id associated with projects_id
	 */
	private Integer belongsToId;
	/**
	 * 
	 */
	private String networkName;
	/**
	 * 
	 */
	private Date createAt;

	private Date updateAt;

	private int subscribeSource;

	private int subscribeStatus;


}

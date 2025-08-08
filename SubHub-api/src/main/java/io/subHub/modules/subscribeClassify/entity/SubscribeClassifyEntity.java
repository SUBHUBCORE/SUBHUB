package io.subHub.modules.subscribeClassify.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Subscription classification
 *
 * @author by 
 * @since 1.0.0 2024-02-22
 */
@Data
@TableName("subscribe_classify")
public class SubscribeClassifyEntity {
	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
			/**
	 * 
	 */
	private String name;
			/**
	 * 
	 */
	private Integer value;
			/**
	 * 0=Enable；1=Disable
	 */
	private Integer status;

			/**
	 *
	 */
	private Integer sort;
			/**
	 * 
	 */
	private Date createDt;
			/**
	 * 
	 */
	private Date updateDt;
	}

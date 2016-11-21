/*
 * Copyright@2012 成都时时客科技有限责任公司
 * All rights reserved.
 *　
 * 项 目 名 称: CalmRouter
 * 文    件   名: DishType.java
 * 标	题: 
 * 描	述: 
 * 作	者：  wangw@shishike.com
 * 创 建 时 间: 20152015年7月27日上午10:58:46
 * 版	本: V1.0 
 * ***************************************************************************/
package cn.ibabygroup.pros.imservice.model.enums;

/**
 * @author night
 *
 */
public enum SenderTypeEnum {
	
	MM(1), Doctor(2), Assistant(3), System(4);

	private int code;
	/**
	 *   1—孕妈
		 2—医生
		 3—小助手
	 	 4--系统
	 * @param code
     */
	SenderTypeEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}

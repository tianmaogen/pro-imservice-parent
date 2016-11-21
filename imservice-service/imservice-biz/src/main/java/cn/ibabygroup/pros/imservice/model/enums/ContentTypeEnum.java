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
public enum ContentTypeEnum {

	Event(-1), Text(1), Pic(2), Coordinate(3), Audio(4), Dynamic(5), Post(6);

	private int code;
	/**
	 *   1—文本消息
		 2—图片消息
		 3—坐标消息
	 	 4—音频消息
	 	 5—动态消息
	 	 6—帖子消息
	 * @param code
     */
	ContentTypeEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}

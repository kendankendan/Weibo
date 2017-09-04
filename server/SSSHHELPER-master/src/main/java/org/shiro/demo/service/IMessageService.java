package org.shiro.demo.service;

import org.shiro.demo.entity.Message;

/**
 * @author chenyd
 *消息接口
 */
public interface IMessageService extends IBaseService{

	/**发送消息（将消息存入数据库表）
	 * @param message
	 * @return
	 */
	public boolean sendMessage(Message message);
	
	/**将未读消息变为已读
	 * @param message
	 * @return
	 */
	public boolean readMessage(Message message);
}

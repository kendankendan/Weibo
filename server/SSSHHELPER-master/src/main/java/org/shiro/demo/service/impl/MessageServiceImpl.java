package org.shiro.demo.service.impl;

import javax.annotation.Resource;

import org.shiro.demo.entity.Message;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.IMessageService;
import org.springframework.stereotype.Service;

@Service("messageService")
public class MessageServiceImpl extends DefultBaseService implements IMessageService{

	@Resource(name = "baseService")
	private IBaseService baseService;
	@Override
	public boolean sendMessage(Message message) {
		boolean flag = false;
		try {
			baseService.save(message);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	public boolean readMessage(Message message) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			message.setMessageUnRead(message.message_read);
			baseService.update(message);
			//baseService.executeBySQL("update weibo_message set messageUnRead = ", params)
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

}

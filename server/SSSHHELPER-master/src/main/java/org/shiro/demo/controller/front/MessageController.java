package org.shiro.demo.controller.front;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Attention;
import org.shiro.demo.entity.Message;
import org.shiro.demo.entity.Post;
import org.shiro.demo.service.IAccountService;
import org.shiro.demo.service.IMessageService;
import org.shiro.demo.service.IPostService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="front/message")
public class MessageController {
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IPostService postService;
	
	/**发送消息
	 * @param acceptUserId
	 * @param sendUserId
	 * @param weiboId
	 * @param messageContent
	 * @return
	 */
	@RequestMapping(value = "/sendmessage",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String sendMessage(@RequestParam(value="acceptUserId")int acceptUserId,@RequestParam(value="sendUserId")int sendUserId,@RequestParam(value="weiboId")String weiboId,@RequestParam(value="messageContent")String messageContent ) {
		String result = new String();
		returnDataInterface returnDataInterface = null;
		Message message = new Message(acceptUserId, sendUserId, messageContent, weiboId);
		if ((accountService.getAccountByUserid(acceptUserId)==null)||(accountService.getAccountByUserid(sendUserId)==null)) {
			returnDataInterface = new returnDataInterface(100, "用户不存在", "");
		} else {
			if (messageService.sendMessage(message)) {
				returnDataInterface = new returnDataInterface(200, "消息发送成功", "");
				
			} else {
				returnDataInterface = new returnDataInterface(100, "消息发送失败", "");

			}
		}
		result = FastJsonTool.createJsonString(returnDataInterface);
		return result;
	}
	@RequestMapping(value = "/getmessage",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String getUnReadMessage(@RequestParam(value="acceptUserId")int acceptUserId,@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize) {
		String result = new String();
		returnDataInterface returnDataInterface = null;
		List<QueryCondition> queryConditions = new ArrayList<>();
		QueryCondition queryCondition = new QueryCondition("acceptUserId",QueryCondition.EQ,acceptUserId);
		queryConditions.add(queryCondition);
		Pagination<Message> messages = messageService.getPagination(Message.class, queryConditions, "order by  messageUnRead DESC,messageTime DESC", page, pageSize);
		List<Message> list = messages.getRecordList();
		List<MessageVO> voList = new ArrayList<>();
		//List<Message> list = messageService.getByJpql("select a from weibo_message as a WHERE a.acceptUserId = ? order by a.messageUnRead DESC,a.messageTime DESC LIMIT ?,?", acceptUserId,(page*pageSize),pageSize);
		for (int i = 0; i < list.size(); i++) {
			Message message = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			Post post = postService.getOneWeibo(message.getWeiboId());
			MessageVO messageVO = new MessageVO(message);
			
			messageVO.setSendUserName(accountService.getAccountByUserid(message.getSendUserId()).getUserName());
			messageVO.setSendUserHead(accountService.getAccountByUserid(message.getSendUserId()).getUserHead());

			messageVO.setWeiboTime(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(post.getWeiboTime()*1000)));
			messageVO.setUserName(accountService.getAccountByUserid(post.getUserId()).getUserName());
			messageVO.setWeiboPhoto(post.getWeiboPhoto());
			messageVO.setWeiboTopic(post.getWeiboTopic());
			messageVO.setWeiboAt(post.getWeiboAt());
			messageVO.setWeiboCollect(post.getWeiboCollect());
			messageVO.setUserHead(accountService.getAccountByUserid(post.getUserId()).getUserHead());
			messageVO.setWeiboLike(post.getWeiboLike());
			messageVO.setWeiboForward(post.getWeiboForward());
			messageVO.setWeiboComment(post.getWeiboComment());
			if (post.getWeiboFwContent()==null) {
				messageVO.setWeiboContent(post.getWeiboContent());
			}else {
				messageVO.setWeiboContent(post.getWeiboFwContent());
			}
			voList.add(messageVO);
			messageService.readMessage(message);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", voList);
		map.put("total", messages.getRecordCount());
		String vo = FastJsonTool.createJsonString(map);
		returnDataInterface = new returnDataInterface(200, "获得消息成功",vo);
		result = FastJsonTool.createJsonString(returnDataInterface);
		return result;
	}
	/*@RequestMapping(value = "/gethistorymessage",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String getHistoryMessage(@RequestParam(value="acceptUserId")int acceptUserId,@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize) {
		String result = new String();
		returnDataInterface returnDataInterface = null;
		List<QueryCondition> queryConditions = new ArrayList<>();
		QueryCondition queryCondition = new QueryCondition("acceptUserId",QueryCondition.EQ,acceptUserId);
		queryConditions.add(queryCondition);
		Pagination<Message> messages = messageService.getPagination(Message.class, queryConditions, "order by messageTime DESC", page, pageSize);
		List<Message> list = messages.getRecordList();
		System.out.println(messages.getRecordCount());
		Map<String, Object> map2 = new HashMap<String, Object>();
		for (int i = 0; i < list.size(); i++) {
			Message message = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sendUserId", message.getSendUserId());
			map.put("sendUserName", accountService.getAccountByUserid(message.getSendUserId()).getUserName());
			map.put("weiboId", message.getWeiboId());
			map.put("weiboContent",postService.getOneWeibo(message.getWeiboId()).getWeiboContent());
			map.put("messageContent", message.getMessageContent());
			map.put("messageTime", message.getMessageTime());
		}
		map2.put("total", messages.getRecordCount());
		String vo = FastJsonTool.createJsonString(map2);
		returnDataInterface = new returnDataInterface(200, "获得历史消息成功", vo);
		result = FastJsonTool.createJsonString(returnDataInterface);
		return result;
	}*/
}

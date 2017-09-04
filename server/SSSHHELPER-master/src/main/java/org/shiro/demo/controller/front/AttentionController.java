package org.shiro.demo.controller.front;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Attention;
import org.shiro.demo.service.IAccountService;
import org.shiro.demo.service.IAttentionService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.vo.AttentionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping(value="front/attention")
public class AttentionController {

	@Autowired
	private IAttentionService attentionService;
	@Autowired
	private IAccountService accountService;
	
	/**关注功能
	 * @param userId
	 * @param attentionUserId
	 * @return string
	 */
	@RequestMapping(value = "/insertattention",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String insertAttention(@RequestParam(value="userId")int userId,@RequestParam(value="attentionUserId")int attentionUserId ) {
		String result = new String();
		returnDataInterface returnDataInterface = null;
		 if ((accountService.getAccountByUserid(Integer.valueOf(userId))==null)||(accountService.getAccountByUserid(Integer.valueOf(attentionUserId))==null)) {
		    	returnDataInterface = new returnDataInterface(100, "用户不存在", "");
			} else {
				if (userId==attentionUserId) {
					returnDataInterface = new returnDataInterface(100, "不能关注自己", "");
				} else {

		long attentionTime = System.currentTimeMillis()/1000;
		Attention attention = new Attention(userId, attentionUserId, attentionTime);
		if (attentionService.attentionExist(userId, attentionUserId)) {
			returnDataInterface = new returnDataInterface(100, "不能重复关注", "");
		} else {
			boolean flag = attentionService.insertAttention(attention);
            if (flag) {
            	returnDataInterface = new returnDataInterface(200, "关注成功", "");
			} else {
				returnDataInterface = new returnDataInterface(100, "关注失败", "");
			}
		}
			}
			}
		result = FastJsonTool.createJsonString(returnDataInterface);
		return result;
	}
	@RequestMapping(value="/attentionexist",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String attentionExist(@RequestParam(value="userId")int userId,@RequestParam(value="attentionUserId")int attentionUserId) {
		String result = new String();
		returnDataInterface returnDataInterface;
		if (attentionService.attentionExist(userId, attentionUserId)) {
			returnDataInterface = new returnDataInterface(200, "已关注该用户", "");
		} else {

			returnDataInterface = new returnDataInterface(100, "未关注该用户", "");
		}
		result = FastJsonTool.createJsonString(returnDataInterface);
		return result;
	}
	
	/**得到粉丝
	 * @param attentionUserId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/getfans",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String getFans(@RequestParam(value="attentionUserId")Integer attentionUserId,@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize) {
		returnDataInterface returnDataInterface = null;
		String result = new String();
		List<QueryCondition> queryConditions = new ArrayList<>();
		if (accountService.getAccountByUserid(attentionUserId)==null) {
			returnDataInterface = new returnDataInterface(100, "用户不存在", "");
			result = FastJsonTool.createJsonString(returnDataInterface);
			return result;
		} else {
		QueryCondition queryCondition = new QueryCondition("attentionUserId",QueryCondition.EQ,attentionUserId);
		queryConditions.add(queryCondition);
		Pagination<Attention> attentions = attentionService.getPagination(Attention.class, queryConditions, "order by attentionTime DESC", page, pageSize);
		List<Attention> list = attentions.getRecordList();
		Map<String, Object> map2 = new HashMap<String, Object>();
		for (int i = 0; i < list.size(); i++) {
			Attention attention = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("attentionId", attention.getAttentionId());
			map.put("userId", attention.getUserId());
			map.put("userName", accountService.getAccountByUserid(attention.getUserId()).getUserName());
			map.put("userIntroduce", accountService.getAccountByUserid(attention.getUserId()).getUserIntroduce());
			map.put("userHead", accountService.getAccountByUserid(attention.getUserId()).getUserHead());
			map.put("attentionTime", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(attention.getAttentionTime()*1000)));
			map2.put(Integer.toString(i+1), map);
		}
		//map2.put("total","total:"+Long.toString(attentions.getRecordCount()));
		Collection<Object> list2 = map2.values();
		returnDataInterface = new returnDataInterface(200, "获得粉丝成功", list2);
		result = FastJsonTool.createJsonString(returnDataInterface);
		return result;
		}
	}
	
	/**得到已关注的人
	 * @param userId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/getfollows",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String getFollows(@RequestParam(value="userId")Integer userId,@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize) {
		returnDataInterface returnDataInterface = null;
		String result = new String();
		List<QueryCondition> queryConditions = new ArrayList<>();
		if (accountService.getAccountByUserid(userId)==null) {
			returnDataInterface = new returnDataInterface(100, "用户不存在", "");
			result = FastJsonTool.createJsonString(returnDataInterface);
			return result;
		} else {
		QueryCondition queryCondition = new QueryCondition("userId",QueryCondition.EQ,userId);
		queryConditions.add(queryCondition);
		Pagination<Attention> attentions = attentionService.getPagination(Attention.class, queryConditions, "order by attentionTime DESC", page, pageSize);
		List<Attention> list = attentions.getRecordList();
		Map<String, Object> map2 = new HashMap<String, Object>();
		for (int i = 0; i < list.size(); i++) {
			Attention attention = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("attentionId", attention.getAttentionId());
			map.put("attentionUserId", attention.getAttentionUserId());
			map.put("userName", accountService.getAccountByUserid(attention.getAttentionUserId()).getUserName());
			map.put("userIntroduce", accountService.getAccountByUserid(attention.getAttentionUserId()).getUserIntroduce());
			map.put("userHead", accountService.getAccountByUserid(attention.getAttentionUserId()).getUserHead());
			map.put("attentionTime", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(attention.getAttentionTime()*1000)));
			map2.put(Integer.toString(i+1), map);
		}
		//map2.put("total","total:"+Long.toString(attentions.getRecordCount()));
		Collection<Object> list2 = map2.values();
		returnDataInterface = new returnDataInterface(200, "获得关注人成功", list2);
		result = FastJsonTool.createJsonString(returnDataInterface);
		return result;
		}
	}
	@RequestMapping(value="/deleteattention",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String deleteAttention(@RequestParam(value="userId")int userId,@RequestParam(value="attentionUserId")int attentionUserId) {
		String result = new String();
		returnDataInterface returnDataInterface;
		if (attentionService.attentionExist(userId, attentionUserId)) {
			if (attentionService.deleteAttention(userId, attentionUserId)) {
				
				returnDataInterface = new returnDataInterface(200, "取消关注成功", "");
			}else {
				
				returnDataInterface = new returnDataInterface(100, "取消关注失败", "");
			}
		} else {

			returnDataInterface = new returnDataInterface(100, "您还未关注过该用户", "");
		}
		result = FastJsonTool.createJsonString(returnDataInterface);
		return result;  
	}
	}

	

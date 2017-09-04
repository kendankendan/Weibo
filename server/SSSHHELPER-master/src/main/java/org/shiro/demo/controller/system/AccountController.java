package org.shiro.demo.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Account;
import org.shiro.demo.entity.User;
import org.shiro.demo.service.IAccountService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.ReturnDataUtil;
import org.shiro.demo.vo.AccountVO;
import org.shiro.demo.vo.SysAccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/account")
public class AccountController {

	@Autowired
	private IAccountService accountService;
	
	/**
	 * 分页获取所有的会员信息
	 */
	@RequestMapping(value = "/getpageaccount", method = RequestMethod.POST)
	@ResponseBody
	public String systemGetUserByPage(@RequestParam(value = "page") Integer page,@RequestParam(value = "rows") Integer pageSize,
			@RequestParam(value="loginname",defaultValue="")String userLoginName) {
		String returnResult = "";
		List<QueryCondition> queryConditions = new ArrayList<>();
		if(!"".equals(userLoginName)){
			queryConditions.add(new QueryCondition("userLoginName",QueryCondition.LK,userLoginName));
		}
		Pagination<Account> accountPagination = accountService.getPagination(Account.class,queryConditions,null, page, pageSize);
		Map<String, Object> accountVOMap = SysAccountVO.changeUser2UserVO(accountPagination);
		returnResult = FastJsonTool.createJsonString(accountVOMap);
		
		return returnResult;
	}
	
	
	/**
	 * 修改禁言状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatespeak",method=RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String updateSpeak(@RequestParam(value="userid")Integer userId){
		String returnData = "";
		Account account = accountService.getById(Account.class, userId);
		if(null==account){
			returnData = "0";
			return returnData;
		}
		try {
			Integer userSpeak = account.getUserSpeak();
			if(Account.USER_NO_SPEAK==userSpeak){
				account.setUserSpeak(Account.USER_SPEAK);
			}else{
				account.setUserSpeak(Account.USER_NO_SPEAK);
			}
			accountService.update(account);
			returnData = "1";
		} catch (Exception e) {
			e.printStackTrace();
			returnData = "0";
		}
		return returnData;
	}
	
	/**
	 * 修改禁言状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatePwd",method=RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String updatePwd(@RequestParam(value="rowstr")String rowstr){
		System.out.println(rowstr);
		String returnData = ReturnDataUtil.FAIL;
		try {
			JSONArray jsonArray = new JSONArray(rowstr);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			Integer userid  = jsonObject.getInt("userId");
			String password = jsonObject.getString("userPassword");
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
			Account account = accountService.getById(Account.class, userid);
			account.setUserPwd(hashed);
			accountService.update(account);
			returnData = "1";
		} catch (Exception e) {
			e.printStackTrace();
			returnData = "0";
		}
		return returnData;
	}
	
	/**
	 * 修改禁用状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatelive",method=RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String updateLive(@RequestParam(value="userid")Integer userId){
		String returnData = "";
		Account account = accountService.getById(Account.class, userId);
		if(null==account){
			returnData = "0";
			return returnData;
		}
		try {
			Integer userAlive = account.getUserAlive();
			if(Account.USER_NO_ALIVE==userAlive){
				account.setUserAlive(Account.USER_ALIVE);
			}else{
				account.setUserAlive(Account.USER_NO_ALIVE);
			}
			accountService.update(account);
			returnData = "1";
		} catch (Exception e) {
			e.printStackTrace();
			returnData = "0";
		}
		return returnData;
	}
	
}

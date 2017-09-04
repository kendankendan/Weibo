package org.shiro.demo.controller.system;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.User;
import org.shiro.demo.service.IUserService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.ReturnDataUtil;
import org.shiro.demo.vo.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Resource(name = "userService")
	private IUserService userService;

	// int pageSize = Integer.parseInt(request.getParameter("rows"));
	// int page = Integer.parseInt(request.getParameter("page"));
	/**
	 * 分页获取所有用户信息
	 */
	@RequestMapping(value = "/systemgetpageuser", method = RequestMethod.POST)
	@ResponseBody
	public String systemGetUserByPage(@RequestParam(value = "page") Integer page,@RequestParam(value = "rows") Integer pageSize) {
		String returnResult = "";
		Pagination<User> userPagination = userService.getPagination(User.class,
				null, null, page, pageSize);
		Map<String, Object> userVOMap = UserVO
				.changeUser2UserVO(userPagination);
		returnResult = FastJsonTool.createJsonString(userVOMap);
		return returnResult;
	}

	/**
	 * 分页获取所有用户信息
	 */
	@RequestMapping(value = "/systemgetalluser", method = RequestMethod.POST)
	@ResponseBody
	public String systemGetAllUser() {
		String returnResult = "";
		List<User> alluser = userService.getAll(User.class);
		List<UserVO> alluservo  = UserVO.changeUser2UserVO(alluser);
		returnResult = FastJsonTool.createJsonString(alluservo);
		return returnResult;
	}
	
	/**
	 * 新增用户
	 * @param rowstr 用户信息
	 * @return
	 */
	@RequestMapping(value = "/systeminsertuser", method = RequestMethod.POST)
	@ResponseBody
	public String systemInsertUser(@RequestParam(value="rowstr")String rowstr){
		System.out.println(rowstr);
		String returnData = ReturnDataUtil.FAIL;
		try {
			JSONArray jsonArray = new JSONArray(rowstr);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			String account  = jsonObject.getString("account");
			String password = jsonObject.getString("password");
			String nickname = jsonObject.getString("nickname");
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
			
			User user = new User(account, hashed, nickname);
			boolean flag = userService.register(user);
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	
	/**
	 * 更新用户
	 * @param rowstr 用户信息
	 * @return
	 */
	@RequestMapping(value = "/systemupdateuser", method = RequestMethod.POST)
	@ResponseBody
	public String systemUpdateUser(@RequestParam(value="rowstr")String rowstr){
		System.out.println(rowstr);
		String returnData = ReturnDataUtil.FAIL;
		try {
			JSONArray jsonArray = new JSONArray(rowstr);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			Long id = jsonObject.getLong("id");
			String account  = jsonObject.getString("account");
			String password = jsonObject.getString("password");
			String nickname = jsonObject.getString("nickname");
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
			User user = userService.getById(User.class, id);
			user.setAccount(account);
			user.setNickname(nickname);
			user.setPassword(hashed);
			boolean flag = userService.updateUser(user);
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	/**
	 * 删除用户
	 * @param ids 用户id
	 * @return
	 */
	@RequestMapping(value = "/systemdeleteuser", method = RequestMethod.POST)
	@ResponseBody
	public String systemDeleteUser(@RequestParam(value="ids")Long id){
		String returnData = ReturnDataUtil.FAIL;
		try {
			userService.deleteUser(id);
			returnData = ReturnDataUtil.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	
	/**
	 * 注册
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	@RequiresRoles("administrator")
	public boolean register(User user) {
		return userService.register(user);
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	@ResponseBody
	public String sss(@RequestParam(value = "roleid") String roleid) {
		System.out.println("roleid is " + roleid);
		return "[{\"text\": \"Item1\",\"state\": \"closed\",\"children\": [{\"text\": \"Item11\"},{\"text\": \"Item12\"}]},{\"text\": \"Item2\"}]";
	}
}

package org.shiro.demo.controller.system;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Role;
import org.shiro.demo.entity.User;
import org.shiro.demo.entity.UserRole;
import org.shiro.demo.service.IRoleService;
import org.shiro.demo.service.IUserRoleService;
import org.shiro.demo.service.IUserService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.ReturnDataUtil;
import org.shiro.demo.vo.RoleVO;
import org.shiro.demo.vo.UserRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/userrole")
public class UserRoleController {

	@Autowired
	private IUserRoleService userRoleService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	/**
	 * 分页获取所有用户的绑定角色信息
	 * @param page 当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/systemgetpageuserrole",method=RequestMethod.POST)
	@ResponseBody
	public String systemGetUserRoleByPage(@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize){
		String returnResult = "";
		Pagination<UserRole> userRolePagination = userRoleService.getPagination(UserRole.class, null, "order by user.id", page, pageSize);
		Map<String, Object> useRoleVOMap = UserRoleVO.changeUserRole2UserRoleVO(userRolePagination);
		returnResult =  FastJsonTool.createJsonString(useRoleVOMap);
		return returnResult;
	}
	
	
	/**
	 * 新增用户绑定的角色
	 * @param rowstr 用户信息
	 * @return
	 */
	@RequestMapping(value = "/systeminsertuserrole", method = RequestMethod.POST)
	@ResponseBody
	public String systemInsertUserRole(@RequestParam(value="userid")Long userid,@RequestParam(value="roleid")Long roleid){
		String returnData = ReturnDataUtil.FAIL;
		try {
			User user = userService.getById(User.class, userid);
			Role role = roleService.getById(Role.class, roleid);
			UserRole userRole = new UserRole(user, role);
			boolean flag = userRoleService.insertUserRole(userRole);
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	
	
	
	/**
	 * 删除绑定的角色
	 * @param ids 用户id
	 * @return
	 */
	@RequestMapping(value = "/systemdeleteuserrole", method = RequestMethod.POST)
	@ResponseBody
	public String systemDeleteUser(@RequestParam(value="ids")Long id){
		String returnData = ReturnDataUtil.FAIL;
		try {
			userRoleService.deleteUserRole(id);
			returnData = ReturnDataUtil.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
}

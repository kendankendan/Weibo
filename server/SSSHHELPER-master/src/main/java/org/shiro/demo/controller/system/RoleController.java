package org.shiro.demo.controller.system;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Role;
import org.shiro.demo.entity.User;
import org.shiro.demo.service.IRoleService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.ReturnDataUtil;
import org.shiro.demo.vo.RoleVO;
import org.shiro.demo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/role")
public class RoleController {

	@Autowired
	private IRoleService roleService;
	
	/**
	 * 分页获取所有角色信息
	 * @param page 当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/systemgetpagerole",method=RequestMethod.POST)
	@ResponseBody
	public String systemGetRoleByPage(@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize){
		String returnResult = "";
		Pagination<Role> rolePagination = roleService.getPagination(Role.class, null, null, page, pageSize);
		Map<String, Object> roleVOMap = RoleVO.changeRole2RoleVO(rolePagination);
		returnResult =  FastJsonTool.createJsonString(roleVOMap);
		return returnResult;
	}
	
	/**
	 * 获取所有角色
	 */
	@RequestMapping(value = "/systemgetallrole", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String systemGetAllRole() {
		String returnResult = "";
		List<Role> allrole = roleService.getAll(Role.class);
		List<RoleVO> allrolevo  = RoleVO.changeRole2RoleVO(allrole);
		returnResult = FastJsonTool.createJsonString(allrolevo);
		return returnResult;
	}
	/**
	 * 新增角色
	 * @param rowstr 用户信息
	 * @return
	 */
	@RequestMapping(value = "/systeminsertrole", method = RequestMethod.POST)
	@ResponseBody
	public String systemInsertRole(@RequestParam(value="rowstr")String rowstr){
		System.out.println(rowstr);
		String returnData = ReturnDataUtil.FAIL;
		try {
			JSONArray jsonArray = new JSONArray(rowstr);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			String name  = jsonObject.getString("name");
			String description = jsonObject.getString("description");
			Role role = new Role(name, description);
			boolean flag = roleService.insertRole(role);
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	
	/**
	 * 更新角色
	 * @param rowstr 角色信息
	 * @return
	 */
	@RequestMapping(value = "/systemupdaterole", method = RequestMethod.POST)
	@ResponseBody
	public String systemUpdateRole(@RequestParam(value="rowstr")String rowstr){
		System.out.println(rowstr);
		String returnData = ReturnDataUtil.FAIL;
		try {
			JSONArray jsonArray = new JSONArray(rowstr);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			Long id = jsonObject.getLong("id");
			String name  = jsonObject.getString("name");
			String description = jsonObject.getString("description");
			Role role = roleService.getById(Role.class, id);
			role.setDescription(description);
			role.setName(name);
			boolean flag = roleService.updateRole(role);
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	/**
	 * 删除角色
	 * @param ids 角色id
	 * @return
	 */
	@RequestMapping(value = "/systemdeleterole", method = RequestMethod.POST)
	@ResponseBody
	public String systemDeleteRole(@RequestParam(value="ids")Long id){
		String returnData = ReturnDataUtil.FAIL;
		try {
			roleService.deleteRole(id);
			returnData = ReturnDataUtil.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
}

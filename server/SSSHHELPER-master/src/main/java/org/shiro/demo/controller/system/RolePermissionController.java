package org.shiro.demo.controller.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Permission;
import org.shiro.demo.entity.Role;
import org.shiro.demo.service.IPermissionService;
import org.shiro.demo.service.IRolePermissionService;
import org.shiro.demo.service.IRoleService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.ReturnDataUtil;
import org.shiro.demo.vo.PermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 角色权限
 * @author Christy
 *
 */
@Controller
@RequestMapping(value="/rolepermission")
public class RolePermissionController {

	@Autowired
	private IRolePermissionService rolePermissionService;
	@Autowired
	private IPermissionService permissionService;
	@Autowired
	private IRoleService roleService;
	
	/**
	 * 显示权限树
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="systemgetpertrees",method=RequestMethod.POST)
	public String systemGetPermissionTree(@RequestParam(value="roleid")Long roleid){
		String returnData = "";
		List<Permission> allPermissions = permissionService.getALLPermission();
		List<PermissionVO>  allPersVO = PermissionVO.changeListPers2ListPersVO(allPermissions);
		
		/*Subject currUser = SecurityUtils.getSubject();
		String account = currUser.getPrincipal().toString();
		List<Permission> userPermission = permissionService.getUserPermissions(account);*/
		
		Role role = roleService.getById(Role.class, roleid);
		Collection<Permission> userPermission = role.getPmss();
		List<PermissionVO> combinePermissionVO = PermissionVO.combinePermission2PermissionVO(allPersVO, userPermission);
		
		
		returnData = FastJsonTool.createJsonString(combinePermissionVO);
		return returnData;
	}
	
	/**
	 * 获取所有的权限名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="systemgetallpers",method=RequestMethod.POST)
	public String systemGetAllPermission(){
		String returnData = "";
		List<Permission> allPermissions = permissionService.getALLPermission();
		List<PermissionVO>  allPersVO = PermissionVO.changeListPers2ListPersVO(allPermissions);
		returnData = FastJsonTool.createJsonString(allPersVO);
		return returnData;
	}
	
	/**
	 * 获取用户对应的权限
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="systemgetuserpers",method=RequestMethod.POST)
	public String systemGetUserPermission(){
		String returnData = "";
		Subject currUser = SecurityUtils.getSubject();
		String account = currUser.getPrincipal().toString();
		List<Permission> allPermissions = permissionService.getUserPermissions(account);
		List<PermissionVO>  allPersVO = PermissionVO.changeListPers2ListPersVO(allPermissions);
		returnData = FastJsonTool.createJsonString(allPersVO);
		return returnData;
	}
	
	/**
	 * 更新用户权限
	 * @param permissids 权限id组
	 * @param roleid 角色id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="systemupdatepermiss",method=RequestMethod.POST)
	public String systemUpdatePermission(@RequestParam(value="permiss[]",required=false)Long[] permissids,
			@RequestParam(value="roleid")Long roleid){
		String returnData = ReturnDataUtil.FAIL;
		if(null==permissids){//为空删除该角色所有权限
			boolean flag = rolePermissionService.deletePermissbyRole(roleid);
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		}else{
			Role role = roleService.getById(Role.class, roleid);
			List<Permission> permissions = permissionService.getByIds(Permission.class, permissids);
			role.setPmss(permissions);
			boolean flag = roleService.updateRole(role);
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		}
		return returnData;
		
	}
}

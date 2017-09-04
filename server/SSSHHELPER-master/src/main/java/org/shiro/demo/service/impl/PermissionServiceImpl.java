package org.shiro.demo.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Permission;
import org.shiro.demo.entity.Role;
import org.shiro.demo.entity.User;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.IPermissionService;
import org.shiro.demo.service.IRoleService;
import org.shiro.demo.service.IUserService;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionServiceImpl extends DefultBaseService implements IPermissionService{

	@Resource(name="baseService")
	private IBaseService baseService;
	
	@Resource(name="userService")
	private IUserService userService;

	public List<Permission> getALLPermission() {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		QueryCondition e = new QueryCondition("parentid is null");
		queryConditions.add(e);
		List<Permission> allPermissions = baseService.get(Permission.class, queryConditions);
		return allPermissions;
	}

	public List<Permission> getUserPermissions(String account) {
		List<Permission> userPermissions  = new ArrayList<Permission>();
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		QueryCondition e = new QueryCondition("user.account = '"+ account +"'");
		queryConditions.add(e);
		User currUser = userService.getByAccount(account);
		Collection<Role> roles = currUser.getRoles();
		if(null!=roles){
			for(Role role : roles){
				Collection<Permission> pmss = role.getPmss();
				if(null!= pmss){
					for(Permission permission : pmss){
						userPermissions.add(permission);
					}
				}
			}
		}
		return userPermissions;
	}
	
}

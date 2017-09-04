package org.shiro.demo.service.impl;

import javax.annotation.Resource;

import org.shiro.demo.entity.User;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.IRolePermissionService;
import org.shiro.demo.service.IUserRoleService;
import org.shiro.demo.service.IUserService;
import org.springframework.stereotype.Service;

@Service("rolePermissionService")
public class RolePermissionServiceImpl extends DefultBaseService implements IRolePermissionService{

	@Resource(name="baseService")
	private IBaseService baseService;

	public boolean deletePermissbyRole(Long roleid) {
		boolean flag = false;
		try {
			baseService.executeJpql("delete RolePermission as rp where rp.role.id=?", roleid);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
}

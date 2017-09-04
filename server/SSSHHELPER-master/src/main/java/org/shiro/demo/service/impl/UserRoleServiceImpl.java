package org.shiro.demo.service.impl;

import javax.annotation.Resource;

import org.shiro.demo.entity.User;
import org.shiro.demo.entity.UserRole;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.IUserRoleService;
import org.shiro.demo.service.IUserService;
import org.springframework.stereotype.Service;

@Service("userroleService")
public class UserRoleServiceImpl extends DefultBaseService implements IUserRoleService{

	@Resource(name="baseService")
	private IBaseService baseService;

	public boolean insertUserRole(UserRole userRole) {
		boolean flag = false;
		try {
			baseService.save(userRole);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteUserRole(Long id) {
		boolean flag = false;
		try {
			baseService.delete(UserRole.class, id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
}

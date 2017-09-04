package org.shiro.demo.service.impl;

import javax.annotation.Resource;

import org.shiro.demo.entity.Role;
import org.shiro.demo.entity.User;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.IRoleService;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl extends DefultBaseService implements IRoleService{

	@Resource(name="baseService")
	private IBaseService baseService;

	public boolean insertRole(Role role) {
		boolean flag = false;
		try {
			baseService.save(role);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteRole(Long id) {
		boolean flag = false;
		try {
			baseService.delete(Role.class, id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateRole(Role role) {
		boolean flag = false;
		try {
			baseService.update(role);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
}

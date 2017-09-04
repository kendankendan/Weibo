package org.shiro.demo.service;

import org.shiro.demo.entity.UserRole;



public interface IUserRoleService extends IBaseService{
	

	/**
	 * 新增用户绑定的角色
	 * @param user
	 * @return
	 */
	public boolean insertUserRole(UserRole userRole);
	
	/**
	 * 删除用户绑定的角色
	 * @param id 用户id
	 * @return
	 */
	public boolean deleteUserRole(Long id);
	
	
}

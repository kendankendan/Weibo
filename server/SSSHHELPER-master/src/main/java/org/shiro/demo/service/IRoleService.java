package org.shiro.demo.service;

import org.shiro.demo.entity.Role;

/**
 * 角色业务层
 * @author Christy
 *
 */
public interface IRoleService extends IBaseService{
	
	/**
	 * 新增角色
	 * @param user
	 * @return
	 */
	public boolean insertRole(Role role);
	
	/**
	 * 删除角色
	 * @param id 用户id
	 * @return
	 */
	public boolean deleteRole(Long id);
	
	/**
	 * 更新用户
	 * @param user 用户
	 * @return
	 */
	public boolean updateRole(Role role);
}

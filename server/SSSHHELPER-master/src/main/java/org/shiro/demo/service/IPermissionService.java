package org.shiro.demo.service;

import java.util.List;

import org.shiro.demo.entity.Permission;
import org.shiro.demo.entity.User;

/**
 * 权限业务层
 * @author Christy
 *
 */
public interface IPermissionService extends IBaseService{
	
	/**
	 * 获取所有的权限
	 * @return
	 */
	public List<Permission> getALLPermission();
	
	/**
	 * 根据用户账号查询对应权限
	 * @param account 用户账号
	 * @return
	 */
	public List<Permission> getUserPermissions(String account);
	
	
}

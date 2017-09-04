package org.shiro.demo.service;

/**
 * 角色权限业务层接口
 * @author Christy
 *
 */
public interface IRolePermissionService extends IBaseService{
	
	/**
	 * 删除特定角色下所有权限
	 * @param roleid 角色id
	 * @return
	 */
	public boolean deletePermissbyRole(Long roleid);
}

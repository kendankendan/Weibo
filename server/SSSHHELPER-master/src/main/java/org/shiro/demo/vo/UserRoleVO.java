package org.shiro.demo.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.UserRole;

/**
 * 用户角色关系绑定显示层
 * @author Christy
 *
 */
public class UserRoleVO {

	private Long id;//id
	private String account;//账号
	private String rolename;//角色名称
	
	public UserRoleVO(Long id, String account, String rolename) {
		super();
		this.id = id;
		this.account = account;
		this.rolename = rolename;
	}

	public UserRoleVO(UserRole userRole) {
		super();
		this.id = userRole.getId();
		this.account = userRole.getUser().getAccount();
		this.rolename = userRole.getRole().getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static Map<String, Object> changeUserRole2UserRoleVO(Pagination<UserRole> pagination){
		List<UserRole> recordList = pagination.getRecordList();
		List<UserRoleVO> list = new ArrayList<UserRoleVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(UserRole item : recordList){
			list.add(new UserRoleVO(item));
		}
		map.put("rows", list);
		map.put("total", pagination.getRecordCount());
		return map;
	}
}

package org.shiro.demo.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Role;
/**
 * 角色显示层
 * @author Christy
 *
 */
public class RoleVO{

	private Long id;//id
	private String name;//角色名
	private String description;//描述
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public RoleVO() {
		super();
	}
	
	public RoleVO(Long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public RoleVO(Role role) {
		super();
		this.id = role.getId();
		this.name = role.getName();
		this.description = role.getDescription();
	}
	
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static Map<String, Object> changeRole2RoleVO(Pagination<Role> pagination){
		List<Role> recordList = pagination.getRecordList();
		List<RoleVO> userVOList = new ArrayList<RoleVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(Role item : recordList){
			userVOList.add(new RoleVO(item));
		}
		map.put("rows", userVOList);
		map.put("total", pagination.getRecordCount());
		return map;
	}
	
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static List<RoleVO> changeRole2RoleVO(List<Role> recordList){
		List<RoleVO> roleVOList = new ArrayList<RoleVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(Role item : recordList){
			roleVOList.add(new RoleVO(item));
		}
		return roleVOList;
	}
}

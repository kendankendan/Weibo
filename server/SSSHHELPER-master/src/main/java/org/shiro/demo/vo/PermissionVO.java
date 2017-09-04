package org.shiro.demo.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.shiro.demo.entity.Permission;

/**
 * 权限显示层实体类
 * @author Christy
 *
 */
public class PermissionVO {

	private final static boolean UNCHECKSTATE = false;//关闭
	
	private final static boolean CHECKSTATE = true;//打开
	
	private Long id;//id
	
	private String text;//权限名称
	
	private boolean checked;
	
	private List<PermissionVO> children;//子权限

	

	public PermissionVO(Long id, String text, boolean checked,
			List<PermissionVO> children) {
		super();
		this.id = id;
		this.text = text;
		this.checked = checked;
		this.children = children;
	}

	public PermissionVO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	

	public List<PermissionVO> getChildren() {
		return children;
	}

	public void setChildren(List<PermissionVO> children) {
		this.children = children;
	}
	
	public PermissionVO(Permission permission){
		this.id = permission.getId();
		this.text = permission.getName();
		this.checked = PermissionVO.UNCHECKSTATE;
		this.children = new ArrayList<PermissionVO>();
	}
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	

	/**
	 * 将权限集合转换为显示层的权限集合
	 * @param persList
	 * @return
	 */
	public static List<PermissionVO> changeListPers2ListPersVO(List<Permission> persList) {
		List<PermissionVO> persVOList = new ArrayList<PermissionVO>(); 
		if(null!=persList){
			for(Permission parentPer : persList){
				PermissionVO permissionVO = new PermissionVO(parentPer);
				Collection<Permission> childrenPers = parentPer.getChildren();
				if(null!=childrenPers){
					List<PermissionVO> childPersVOList = new ArrayList<PermissionVO>(); 
					for(Permission childPer : childrenPers){
						childPersVOList.add(new PermissionVO(childPer));
					}
					permissionVO.setChildren(childPersVOList);
				}
				persVOList.add(permissionVO);
			}
		}
		return persVOList;
	}
	
	
	/**
	 * 将所有权限的权限树与用户权限树合并
	 * @param allPermiss 所有权限
	 * @param userPermiss 用户权限
	 * @return
	 */
	public static List<PermissionVO> combinePermission2PermissionVO(List<PermissionVO> allPermiss,Collection<Permission> userPermiss){
		List<Permission> list = new ArrayList<Permission>();
		for(Permission item: userPermiss){
			list.add(item);
		}
		return PermissionVO.combinePermission2PermissionVO(allPermiss, list);
	}
	
	/**
	 * 将所有权限的权限树与用户权限树合并
	 * @param allPermiss 所有权限
	 * @param userPermiss 用户权限
	 * @return
	 */
	public static List<PermissionVO> combinePermission2PermissionVO(List<PermissionVO> allPermiss,List<Permission> userPermiss){
		for(Permission userItem : userPermiss){
			//遍历所有的父权限，如果相同则为父权限以及下面的子权限更改状态
			for(int i = 0;i<allPermiss.size();i++){
				PermissionVO allItem = allPermiss.get(i);
				if( allItem.getId() == userItem.getId() ){
					allItem.setChecked(PermissionVO.CHECKSTATE);
					allPermiss.set(i, allItem);
				}else{//遍历子权限
					List<PermissionVO> children = allItem.getChildren();
					if(null!=children){
						for(int j=0;j<children.size();j++){
							PermissionVO childItem = children.get(j);
							if(childItem.getId()== userItem.getId()){
								childItem.setChecked(PermissionVO.CHECKSTATE);
								children.set(j, childItem);
								allItem.setChildren(children);
								allPermiss.set(i, allItem);
							}
						}
					}
				}
			}
		}
		return allPermiss;
	}
}

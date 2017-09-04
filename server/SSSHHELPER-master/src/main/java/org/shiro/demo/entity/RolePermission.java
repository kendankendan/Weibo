package org.shiro.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 角色权限实体类
 * @author Christy
 *
 */
@Entity
@Table(name="cmsrolepms")
public class RolePermission {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="roleid")
	private Role role;
	
	@ManyToOne
	@JoinColumn(name="pmsid")
	private Permission permission;

	public RolePermission(Long id, Role role, Permission permission) {
		super();
		this.id = id;
		this.role = role;
		this.permission = permission;
	}

	public RolePermission() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
}

package org.shiro.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 用户角色模块
 * @author Christy
 *
 */
@Entity
@Table(name="cmsuserrole")
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;
	@ManyToOne
	@JoinColumn(name="roleid")
	private Role role;
	public UserRole(Long id, User user, Role role) {
		super();
		this.id = id;
		this.user = user;
		this.role = role;
	}
	public UserRole() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public UserRole(User user, Role role) {
		super();
		this.user = user;
		this.role = role;
	}
	
	
}

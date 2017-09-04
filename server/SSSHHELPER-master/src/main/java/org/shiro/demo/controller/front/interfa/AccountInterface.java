package org.shiro.demo.controller.front.interfa;

public class AccountInterface {

	private Integer userId;
	
	private String userName;
	
	private String userHead;
	
	private Integer userUnRead;

	public AccountInterface() {
		super();
	}

	public AccountInterface(Integer userId, String userName, String userHead,
			Integer userUnRead) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userHead = userHead;
		this.userUnRead = userUnRead;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserHead() {
		return userHead;
	}

	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}

	public Integer getUserUnRead() {
		return userUnRead;
	}

	public void setUserUnRead(Integer userUnRead) {
		this.userUnRead = userUnRead;
	}
	
}

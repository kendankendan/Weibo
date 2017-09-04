package org.shiro.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 微博用户
 * @author guoy1
 *
 */

@Entity
@Table(name = "user_account")
public class Account {

	/**
	 * 已禁用
	 */
	public static final Integer USER_NO_ALIVE = 0;
	
	/**
	 * 未禁用
	 */
	public static final Integer USER_ALIVE = 1;
	
	/**
	 * 已禁言
	 */
	public static final Integer USER_NO_SPEAK = 0;
	
	/**
	 * 未禁言
	 */
	public static final Integer USER_SPEAK = 1;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId")
	private Integer userId;//用户编号
	
	@Column(name="userName")
	private String userName;//用户昵称
	
	@Column(name="userHead")
	private String userHead;//用户头像
	
	@Column(name="userLoginName")
	private String userLoginName;//登陆名(邮箱/手机)
	
	@Column(name="userPwd")
	private String userPwd;//密码
	
	@Column(name="userSex")
	private Integer userSex;//性别
	
	@Column(name="userSpeak")
	private Integer userSpeak;//禁言 
	
	@Column(name="userUnRead")
	private Integer userUnRead;//消息未读数
	
	@Column(name="userRegisterDate")
	private Long userRegisterDate;//注册日期
	
	@Column(name="userIntroduce")
	private String userIntroduce;//个人介绍
	
	@Column(name="userAlive")
	private Integer userAlive;//禁用

	public Account() {
		super();
	}

	
	public Account(Integer userId, String userName, String userHead,
			String userLoginName, String userPwd, Integer userSex,
			Integer userSpeak, Integer userUnRead, Long userRegisterDate,
			String userIntroduce, Integer userAlive) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userHead = userHead;
		this.userLoginName = userLoginName;
		this.userPwd = userPwd;
		this.userSex = userSex;
		this.userSpeak = userSpeak;
		this.userUnRead = userUnRead;
		this.userRegisterDate = userRegisterDate;
		this.userIntroduce = userIntroduce;
		this.userAlive = userAlive;
	}


	public Account(String userName, String userHead, String userLoginName,
			String userPwd, Integer userSex, Integer userSpeak,
			Integer userUnRead, Long userRegisterDate, String userIntroduce,
			Integer userAlive) {
		super();
		this.userName = userName;
		this.userHead = userHead;
		this.userLoginName = userLoginName;
		this.userPwd = userPwd;
		this.userSex = userSex;
		this.userSpeak = userSpeak;
		this.userUnRead = userUnRead;
		this.userRegisterDate = userRegisterDate;
		this.userIntroduce = userIntroduce;
		this.userAlive = userAlive;
	}
	
	public Account(String userName, String userHead, String userLoginName,
			String userPwd, Integer userSex, Long userRegisterDate, String userIntroduce) {
		super();
		this.userName = userName;
		this.userHead = userHead;
		this.userLoginName = userLoginName;
		this.userPwd = userPwd;
		this.userSex = userSex;
		this.userSpeak = USER_SPEAK;
		this.userUnRead = 0;
		this.userRegisterDate = userRegisterDate;
		this.userIntroduce = userIntroduce;
		this.userAlive = USER_ALIVE;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		if(userName == null)
			return "";
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

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public Integer getUserSex() {
		return userSex;
	}

	public void setUserSex(Integer userSex) {
		this.userSex = userSex;
	}

	public Integer getUserSpeak() {
		return userSpeak;
	}

	public void setUserSpeak(Integer userSpeak) {
		this.userSpeak = userSpeak;
	}

	public Integer getUserUnRead() {
		return userUnRead;
	}

	public void setUserUnRead(Integer userUnRead) {
		this.userUnRead = userUnRead;
	}

	public Long getUserRegisterDate() {
		return userRegisterDate;
	}

	public void setUserRegisterDate(Long userRegisterDate) {
		this.userRegisterDate = userRegisterDate;
	}

	public String getUserIntroduce() {
		return userIntroduce;
	}

	public void setUserIntroduce(String userIntroduce) {
		this.userIntroduce = userIntroduce;
	}

	public Integer getUserAlive() {
		return userAlive;
	}

	public void setUserAlive(Integer userAlive) {
		this.userAlive = userAlive;
	}
	
}

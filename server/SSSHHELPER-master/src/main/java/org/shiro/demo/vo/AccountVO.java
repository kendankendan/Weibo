package org.shiro.demo.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Account;


/**
 * 微博用户显示类
 * @author guoy1
 *
 */

public class AccountVO {

	
	private Integer userId;//用户编号
	
	private String userName;//用户昵称
	
	private String userHead;//用户头像
	
	private String userLoginName;//登陆名(邮箱/手机)
	
	private Integer userSex;//性别
	
	private Integer userSpeak;//禁言 
	
	private Integer userUnRead;//消息未读数
	
	private String userRegisterDate;//注册日期
	
	private String userIntroduce;//个人介绍
	
	private Integer userAlive;//禁用


	public AccountVO() {
		super();
	}

	public AccountVO(Integer userId, String userName, String userHead,
			String userLoginName, Integer userSex,
			Integer userSpeak, Integer userUnRead, Long userRegisterDate,
			String userIntroduce, Integer userAlive) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userHead = userHead;
		this.userLoginName = userLoginName;
		this.userSex = userSex;
		this.userSpeak = userSpeak;
		this.userUnRead = userUnRead;
		this.userRegisterDate = userRegisterDate+"";
		this.userIntroduce = userIntroduce;
		this.userAlive = userAlive;
	}

	public AccountVO(Account account) {
		super();
		this.userId = account.getUserId();
		this.userName = account.getUserName();
		this.userHead = account.getUserHead();
		this.userLoginName = account.getUserLoginName();
		this.userSex = account.getUserSex();
		this.userSpeak = account.getUserSpeak();
		this.userUnRead = account.getUserUnRead();
		this.userRegisterDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(account.getUserRegisterDate()*1000));
		this.userIntroduce = account.getUserIntroduce();
		this.userAlive = account.getUserAlive();
	}
	
	public AccountVO(String userName, String userHead, String userLoginName,
			String userPwd, Integer userSex, Integer userSpeak,
			Integer userUnRead, Long userRegisterDate, String userIntroduce,
			Integer userAlive) {
		super();
		this.userName = userName;
		this.userHead = userHead;
		this.userLoginName = userLoginName;
		this.userSex = userSex;
		this.userSpeak = userSpeak;
		this.userUnRead = userUnRead;
		this.userRegisterDate = userRegisterDate+"";
		this.userIntroduce = userIntroduce;
		this.userAlive = userAlive;
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

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
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

	public String getUserRegisterDate() {
		return userRegisterDate;
	}

	public void setUserRegisterDate(String userRegisterDate) {
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
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static Map<String, Object> changeUser2UserVO(Pagination<Account> pagination){
		List<Account> recordList = pagination.getRecordList();
		List<AccountVO> userVOList = new ArrayList<AccountVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(Account item : recordList){
			userVOList.add(new AccountVO(item));
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
	public static List<AccountVO> changeUser2UserVO(List<Account> list){
		List<Account> recordList = list;
		List<AccountVO> voList = new ArrayList<AccountVO>();
		for(Account item : recordList){
			voList.add(new AccountVO(item));
		}
		return voList;
	}
}

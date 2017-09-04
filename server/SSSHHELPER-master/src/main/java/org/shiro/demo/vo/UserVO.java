package org.shiro.demo.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.User;

/**
 * 用户显示层
 * @author Christy
 *
 */
public class UserVO {

	private Long id;//id
	private String account;//账号
	private String password;//密码
	private String nickname;//昵称
	public UserVO(Long id, String account, String password, String nickname) {
		super();
		this.id = id;
		this.account = account;
		this.password = password;
		this.nickname = nickname;
	}
	public UserVO() {
		super();
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public UserVO(User user) {
		super();
		this.id = user.getId();
		this.account = user.getAccount();
		this.password = user.getPassword();
		this.nickname = user.getNickname();
	}
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static Map<String, Object> changeUser2UserVO(Pagination<User> pagination){
		List<User> recordList = pagination.getRecordList();
		List<UserVO> userVOList = new ArrayList<UserVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(User item : recordList){
			userVOList.add(new UserVO(item));
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
	public static List<UserVO> changeUser2UserVO(List<User> userlist){
		List<User> recordList = userlist;
		List<UserVO> userVOList = new ArrayList<UserVO>();
		for(User item : recordList){
			userVOList.add(new UserVO(item));
		}
		return userVOList;
	}
}

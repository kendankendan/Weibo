package org.shiro.demo.service;

import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.entity.Account;

public interface IAccountService extends IBaseService{

	/**
	 * 验证用户是否存在
	 * @param loginName
	 * @param userPwd
	 * @return 0:不存在的账号;1:成功;2:密码错误;3:用户被禁用;
	 */
	public returnDataInterface validateAccount(String loginName,String userPwd);
	
	/**
	 * 注册用户
	 * @param account
	 * @return
	 */
	public boolean regist(Account account);
	
	/**
	 * 验证是否存在相同的登陆名称
	 * @param loginName
	 * @return
	 */
	public boolean isExistLoginName(String loginName);
	
	/**
	 * 验证是否存在相同的用户名
	 * @param loginName
	 * @return
	 */
	public boolean isExistUserName(String userName);
	
	/**
	 * 通过userid获取username
	 * @param userid
	 * @return
	 */
	public Account getAccountByUserid(Integer userid);
	
	/**
	 * 通过username获取account
	 * @param userid
	 * @return
	 */
	public Account getAccountByUserName(String username);
}

package org.shiro.demo.service.impl;

import javax.annotation.Resource;

import org.mindrot.jbcrypt.BCrypt;
import org.shiro.demo.controller.front.interfa.AccountInterface;
import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.entity.Account;
import org.shiro.demo.service.IAccountService;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.util.FastJsonTool;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl extends DefultBaseService implements IAccountService{

	public static final Integer VALIDATE_NO_ACCOUNT = 0;//不存在的账号
	
	public static final Integer VALIDATE_SUCCESS = 1;//成功
	
	public static final Integer VALIDATE_ERROR_PWD = 2;//密码错误
	
	public static final Integer VALIDATE_NOT_ALIVE = 3;//用户被禁用
	
	
	
	@Resource(name="baseService")
	private IBaseService baseService;
	
	@Override
	public returnDataInterface validateAccount(String loginName, String userPwd) {
		returnDataInterface returnDataInterface = null;
		Account account = (Account) baseService.getUniqueResultByJpql("from Account as o where o.userLoginName=?", loginName);
		if( null == account ){//账号不存在
			returnDataInterface = new returnDataInterface(100, "账号不存在或密码错误", "");
		}else{
			if(!BCrypt.checkpw(userPwd, account.getUserPwd())){//密码错误
				returnDataInterface = new returnDataInterface(100, "账号不存在或密码错误", "");
			}else{
				if(account.getUserAlive()==Account.USER_NO_ALIVE){//账号被禁用
					returnDataInterface = new returnDataInterface(100, "账号被禁用", "");
				}else{//用户正确
					returnDataInterface = new returnDataInterface(200, "成功", "");
					AccountInterface accountInterface = new AccountInterface(account.getUserId(), account.getUserName(), 
							account.getUserHead(), account.getUserUnRead());
					returnDataInterface.setData(FastJsonTool.createJsonString(accountInterface));
				}
			}
		}
		return returnDataInterface;
	}

	@Override
	public boolean regist(Account account) {
		boolean flag = false;
		try {
			baseService.save(account);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean isExistLoginName(String loginName) {
		Account account = (Account) baseService.getUniqueResultByJpql("from Account as o where o.userLoginName=? ",loginName);
		if(null==account){
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isExistUserName(String userName) {
		Account account = (Account) baseService.getUniqueResultByJpql("from Account as o where  o.userName=?",userName);
		if(null==account){
			return false;
		}
		return true;
	}

	
	@Override
	public Account getAccountByUserid(Integer userid) {
		Account account = baseService.getById(Account.class, userid);
		if(null==account){
			System.out.println("null");
			return null;
		}else{
			return account;
		}
	}

	@Override
	public Account getAccountByUserName(String username) {
		Account account = (Account) baseService.getUniqueResultByJpql("from Account as o where  o.userName=?",username);
		return account;
	}

}

package org.shiro.demo.service;

import org.shiro.demo.entity.Account;
import org.shiro.demo.entity.Post;

import java.util.List;

/**
 * 发微博类
 * Created by luowz on 2017/8/3.
 */

public interface IPostService extends IBaseService{

	/**
	 * 验证微博是否存在
	 * @param weiboId
	 * @return null 表示微博不存在
	 */
	public Post getOneWeibo(String weiboId);

	/**
	 * 验证微博是否存在
	 * @param weiboId
	 * @return 0:不存在 1 存在
	 */
	public boolean isWeiboExist(String weiboId);

	/**
	 * 插入微博
	 * @param postWeibo
	 * @return
	 */
	public Boolean postWeibo(Post postWeibo);

	/**
	 * 删除微博
	 * @param id
	 * @return
	 */
	public Boolean deleteWeibo(String id);

	/**
	 * 获取所有微博
	 * @return
	 */
	public List<Post> getAllWeibo();

	/**
	 * 判断用户名是否存在
	 * @return
	 */
	public Boolean validateAccount(String []usernames);

	public Account getAccountByUserName(String userName);
}

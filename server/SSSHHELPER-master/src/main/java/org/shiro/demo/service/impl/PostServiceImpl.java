package org.shiro.demo.service.impl;

import org.shiro.demo.entity.Account;
import org.shiro.demo.entity.Post;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.IPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("postService")
public class PostServiceImpl extends DefultBaseService implements IPostService{

	@Resource(name="baseService")
	private IBaseService baseService;

	public Post getOneWeibo(String weiboId) {
		Post postObj = (Post) baseService.getById(Post.class, weiboId);
		return postObj;
	}

	public boolean isWeiboExist(String weiboId) {
		Post postObj = (Post) baseService.getById(Post.class, weiboId);
		return (postObj != null);
	}

	/**
	 * 插入微博
	 * @param postWeibo
	 * @return
	 */

	public Boolean postWeibo(Post postWeibo){
		Boolean returnData = false;
		try{
			baseService.save(postWeibo);
			returnData = true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}

	/**
	 * 删除微博
	 * @param id
	 * @return
	 */
	public Boolean deleteWeibo(String id){
		Boolean returnData = false;
		Post weibo = null;
		try{
			weibo = (Post) baseService.getUniqueResultByJpql("from Post as o where o.weiboId=?", id);
			weibo.setWeiboAlive(0);
			baseService.update(weibo);
			returnData = true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}

	/**
	 * 获取所有微博
	 * @return
	 */
	public List<Post> getAllWeibo(){
		return null;
	}

	/**
	 * 判断用户名是否存在
	 * @return
	 */
	public Boolean validateAccount(String []usernames) {
		Boolean result = true;
		for(int i = 0; i< usernames.length; i++)
		{
			Account account = (Account) baseService.getUniqueResultByJpql("from Account as o where o.userName=?", usernames[i]);
			if( null == account ) {
				result = false;
		    }
		}
		return result;
	}

	/**
	 * 根据用户名得到实体
	 * @return
	 */
	public Account getAccountByUserName(String userName) {
		Account account = (Account) baseService.getUniqueResultByJpql("from Account as o where o.userName=?", userName);
		if(null==account){
			System.out.println("null");
			return null;
		}else{
			return account;
		}
	}
}

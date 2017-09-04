package org.shiro.demo.service;

import java.util.List;

import org.shiro.demo.entity.Comment;

/**
 * @author chenyd
 *评论管理服务层
 */
public interface ICommentService extends IBaseService{

	/**新增评论
	 * @param comment
	 * @return true/false
	 */
	public boolean insertComment(Comment comment);
	
	
	/**输入微博id
	 * @param weiboId
	 * @return  所有符合的评论list
	 */
	public List<Comment> getComment(String weiboId);
	
	/**输入微博id
	 * @param weiboId
	 * @return  是否存在该微博
	 */
	public boolean weiboExist(String weiboId);

}

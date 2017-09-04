package org.shiro.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Comment;
import org.shiro.demo.entity.Post;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.ICommentService;
import org.springframework.stereotype.Service;

@Service("commentService")
public class CommentServiceImpl extends DefultBaseService implements ICommentService{

	@Resource(name="baseService")
	private IBaseService baseService;
	//插入评论功能
	@Override
	public boolean insertComment(Comment comment) {
        boolean flag = false;
		try {
			baseService.save(comment);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	//无分页根据微博 查询评论
	@Override
	public List<Comment> getComment(String weiboId) {

		List<Comment> comments = null;
		List<QueryCondition> queryConditions = new ArrayList<>();
		QueryCondition queryCondition = new QueryCondition("weiboId",QueryCondition.EQ,weiboId);
		queryConditions.add(queryCondition);
		comments = baseService.get(Comment.class, queryConditions);
		return comments;
	}
	@Override
	//判断微博是否存在
	public boolean weiboExist(String weiboId) {

		List<QueryCondition> queryConditions = new ArrayList<>();
		QueryCondition queryCondition = new QueryCondition("weiboId",QueryCondition.EQ,weiboId);
		queryConditions.add(queryCondition);
		long count = baseService.getRecordCount(Post.class, queryConditions);
		if (count==0) {
			
			return false;
		} else {
            return true;
		}
	}

	

}

package org.shiro.demoTest.serviceTest;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.shiro.demo.entity.Comment;
import org.shiro.demo.entity.Post;
import org.shiro.demo.service.IAccountService;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.ICommentService;
import org.shiro.demo.service.IPostService;
import org.shiro.demo.service.impl.AccountServiceImpl;
import org.shiro.demo.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class CommentSeviceTest {

	@Autowired
	private ICommentService commentservice;
	@Autowired 
	private IBaseService baseService;
	@Autowired
	private IPostService postService;
	
	Integer userId = 19950615;
	String userName = "cdn";
	String weiboId = "12345678909876543210123456789012";
	String commentContent = "cco";
	long commentTime = System.currentTimeMillis()/1000;
	Comment comment = new Comment(userId, userName, weiboId, commentContent, commentTime);
	String []aStrings = {};
	Post postWeibo = new Post("test012", 1955993, commentTime, "", "", 1, 0, 0, 0, 0, "", "", true, true,aStrings );
	/**
	 * 期望评论插入成功单元测试
	 */
	@Test
	@Transactional
	@Rollback(true)// 
	public void except_insertCommentSuccess_test() {
		if (commentservice.insertComment(comment)) {
			
			assertEquals(comment, baseService.getById(Comment.class, comment.getCommentId()));
		}
	
	}
	/**
	 * 期望微博存在
	 */
	@Test
	@Transactional
	@Rollback(true)// 
	public void except_weiboExistById_test() {
		
		assertEquals(true, postService.postWeibo(postWeibo));
		assertEquals(true,commentservice.weiboExist("test012"));
		
	}
	

}

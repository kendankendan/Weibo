package org.shiro.demo.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 评论实体
 *
 */
@Entity
@Table(name="weibo_comment")
public class Comment {
	@Id
	@Column(name="commentId")
	private String commentId;
	
	@Column(name="userId")
	private Integer userId;
	
	@Column(name="userName")
	private String userName;
	
	@Column(name="weiboId")
	private String weiboId;
	
	@Column(name="commentContent")
	private String commentContent;
	
	@Column(name="commentTime")
	private Long commentTime;

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public int getUserId() {
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

	public String getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Long getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Long commentTime) {
		this.commentTime = commentTime;
	}
	

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comment(String commentId, Integer userId, String userName, String weiboId, String commentContent, Long commentTime) {
		super();
		this.commentId = commentId;
		this.userId = userId;
		this.weiboId = weiboId;
		this.commentContent = commentContent;
		this.commentTime = commentTime;
		this.userName = userName;
	}

	public Comment(Integer userId,String userName, String weiboId, String commentContent, Long commentTime) {
		super();
		this.commentId=UUID.randomUUID().toString().replace("-", "");
		this.userId = userId;
		this.weiboId = weiboId;
		this.commentContent = commentContent;
		this.commentTime = commentTime;
		this.userName = userName;
	}
	
	
}

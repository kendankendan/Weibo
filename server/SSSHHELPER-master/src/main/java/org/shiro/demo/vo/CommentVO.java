package org.shiro.demo.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Comment;

/**
 * @author chenyd
 *评论的显示类
 */

public class CommentVO {
    private String commentId;  //评论编号
	
	private Integer userId;      //用户编号
	
	private String userName;
	
	private String userHead;
	
	private String weiboId;   //微博编号
	
	private String commentContent;//评论内容
	
	private String commentTime; //评论时间

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
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

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public String getUserHead() {
		return userHead;
	}

	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}

	public CommentVO() {
		super();
	}

	public CommentVO(String commentId,String userName, Integer userId, String weiboId, String commentContent, Long commentTime) {
		super();
		this.commentId = commentId;
		this.userId = userId;
		this.weiboId = weiboId;
		this.commentContent = commentContent;
		this.userName = userName;
		this.commentTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(commentTime*1000));
	}

	public CommentVO(Comment comment) {
		super();
		this.commentId = comment.getCommentId();
		this.userId = comment.getUserId();
		this.weiboId = comment.getWeiboId();
		this.commentContent = comment.getCommentContent();
		this.userName = comment.getUserName();
		this.commentTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(comment.getCommentTime()*1000));
	}
	
	/**实体转化为显示层实体类
	 * @param 分页
	 * @return
	 */
	public static Map<String, Object> comment2commentVO(Pagination<Comment> pagination) {
		List<Comment> recordList = pagination.getRecordList();
		List<CommentVO> VOList = new ArrayList<CommentVO>();
		Map<String, Object> map = new HashMap<>();
		for (Comment comment : recordList) {
			VOList.add(new CommentVO(comment));
		}
		map.put("rows", VOList);
		map.put("total", pagination.getRecordCount());
		return map;
	}
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static List<CommentVO> comment2commentVO(List<Comment> recordList){
		List<CommentVO> VOList = new ArrayList<CommentVO>();
	//	Map<String, Object> map = new HashMap<String, Object>();
		for(Comment item : recordList){
			VOList.add(new CommentVO(item));
		}
		return VOList;
	}
	
}

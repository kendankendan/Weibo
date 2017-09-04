package org.shiro.demo.controller.front;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Attention;
import org.shiro.demo.entity.Comment;
import org.shiro.demo.entity.Message;
import org.shiro.demo.entity.Post;
import org.shiro.demo.service.IAccountService;
import org.shiro.demo.service.ICommentService;
import org.shiro.demo.service.IMessageService;
import org.shiro.demo.service.IPostService;
import org.shiro.demo.service.impl.PostServiceImpl;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="front/comment")
public class CommentController {

	@Autowired
	private ICommentService commentService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IPostService PostService;
	
	/**
	 * 新增评论
	 * @param 评论信息
	 * @return
	 */
	@RequestMapping(value = "/insertcomment",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String insertComment(@RequestParam(value="userId")String userId,@RequestParam(value="userName")String userName,@RequestParam(value="weiboId")String weiboId,@RequestParam(value="commentContent")String commentContent) {
		String result = new String();
		    returnDataInterface returnDataInterface = null;
		    if (accountService.getAccountByUserid(Integer.valueOf(userId))==null) {
		    	returnDataInterface = new returnDataInterface(100, "用户不存在", "");
			} else {
				if (accountService.getAccountByUserid(Integer.valueOf(userId)).getUserSpeak()==0) {
					
					returnDataInterface = new returnDataInterface(100, "用户被禁言", "");
				} else {

			Long commentTime = System.currentTimeMillis()/1000;
			Comment comment = new Comment(new Integer(userId),userName, weiboId, commentContent, commentTime);
			boolean flag = commentService.insertComment(comment);
			CommentVO commentVO = new CommentVO(comment);
			commentVO.setUserHead(accountService.getAccountByUserid(Integer.valueOf(userId)).getUserHead());
			if (flag) {
				int acceptUserId = PostService.getOneWeibo(weiboId).getUserId();
				String string = "评论了你："+commentContent;
				messageService.sendMessage(new Message(acceptUserId, Integer.valueOf(userId), string, weiboId));
				Post weibo = PostService.getOneWeibo(weiboId);
				synchronized (this) {
					weibo.setWeiboComment(weibo.getWeiboComment()+1);
					PostService.update(weibo);
				}
				returnDataInterface = new returnDataInterface(200, "评论成功", new FastJsonTool().createJsonString(commentVO));
			} else {
				returnDataInterface = new returnDataInterface(100, "评论失败", "");
			}
				}
			}
		  result = FastJsonTool.createJsonString(returnDataInterface);
	
		return result;
		}
	/**分页查询评论功能
	 * @param weiboId
	 * @param page
	 * @param pageSize
	 * @return 
	 */
	@RequestMapping(value = "/getcomment",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String getComment(@RequestParam(value="weiboId")String weiboId,@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize) {
		returnDataInterface returnDataInterface = null;
		String result = new String();
		if (commentService.weiboExist(weiboId)) {
			List<QueryCondition> queryConditions = new ArrayList<>();
			QueryCondition queryCondition = new QueryCondition("weiboId",QueryCondition.EQ,weiboId);
			queryConditions.add(queryCondition);
			Pagination<Comment> comments = commentService.getPagination(Comment.class, queryConditions, "order by commentTime DESC", page, pageSize);
			List<Comment> list = comments.getRecordList();
			List<CommentVO> volist = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				Comment comment = list.get(i);
				CommentVO commentVO = new CommentVO(comment);
				commentVO.setUserName(accountService.getAccountByUserid(comment.getUserId()).getUserName());
				commentVO.setUserHead(accountService.getAccountByUserid(comment.getUserId()).getUserHead());
				volist.add(commentVO);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", volist);
			map.put("total", comments.getRecordCount());
			String vo = FastJsonTool.createJsonString(map);
			returnDataInterface = new returnDataInterface(200, "获得评论成功", vo);
		} else {
            returnDataInterface = new returnDataInterface(100,"该微博不存在","");
		}
		result = FastJsonTool.createJsonString(returnDataInterface);
		return result;
	}
}

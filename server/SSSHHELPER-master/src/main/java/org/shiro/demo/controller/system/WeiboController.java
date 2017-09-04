package org.shiro.demo.controller.system;

import java.util.Map;

import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Account;
import org.shiro.demo.entity.Post;
import org.shiro.demo.service.IAccountService;
import org.shiro.demo.service.IPostService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.vo.AccountVO;
import org.shiro.demo.vo.PostVO;
import org.shiro.demo.vo.SysAccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/weibo")
public class WeiboController {

	@Autowired
	private IPostService postService;
	
	/**
	 * 分页获取所有的微博信息
	 */
	@RequestMapping(value = "/getpageweibo", method = RequestMethod.POST)
	@ResponseBody
	public String systemGetUserByPage(@RequestParam(value = "page") Integer page,@RequestParam(value = "rows") Integer pageSize) {
		String returnResult = "";
		Pagination<Post> postPagination = postService.getPagination(Post.class,null, null, page, pageSize);
		Map<String, Object> postVOMap = PostVO.changePost2PostVO(postPagination);
		returnResult = FastJsonTool.createJsonString(postVOMap);
		return returnResult;
	}
	
	
	
	
	/**
	 * 修改禁用状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatelive",method=RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String updateLive(@RequestParam(value="weiboid")String weiboid){
		String returnData = "";
		Post post = postService.getById(Post.class, weiboid);
		if(null==post){
			returnData = "0";
			return returnData;
		}
		try {
			Integer weiboAlive = post.getWeiboAlive();
			if(0==weiboAlive){
				post.setWeiboAlive(1);
			}else{
				post.setWeiboAlive(0);
			}
			postService.update(post);
			returnData = "1";
		} catch (Exception e) {
			e.printStackTrace();
			returnData = "0";
		}
		return returnData;
	}
	
}

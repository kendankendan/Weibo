package org.shiro.demo.controller.front.controller;
import net.sf.json.JSONObject;

import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Account;
import org.shiro.demo.entity.Attention;
import org.shiro.demo.entity.Post;
import org.shiro.demo.service.*;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.vo.PostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.util.*;

/**
 * 发微博controller
 * Created by luowz on 2017/8/3.
 */

@Controller
@RequestMapping(value="/front/weibo")
public class postFrontController {

	@Autowired
	private IPostService postService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private ILikeService likeService;
	@Autowired
	private IMarkService markService;
	@Autowired
	private IAttentionService attentionService;

	/**
	 * 新增微博
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/postWeibo", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String postWeibo(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "weiboContent") String weiboContent, @RequestParam(value = "file",required = false )MultipartFile file, HttpServletRequest request, @RequestParam(value = "weiboAt") String weiboAt, @RequestParam(value = "weiboTopic") String weiboTopic) {
		String returnData = "";
		returnDataInterface returnDataInterface = null;
		Account a = accountService.getAccountByUserid(userId);
		Integer userSpeak = a.getUserSpeak();
		if(userSpeak == 0){
			returnDataInterface = new returnDataInterface(100, "用户被禁言", "");
		}else{
			try {
				//获得系统的时间，单位为毫秒,转换为秒
				long totalMilliSeconds = System.currentTimeMillis();
				long weiboTime = totalMilliSeconds / 1000;
				//uuid生成
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				//weiboAt分割，根据用户名查用户表看存不存在
				if(!weiboAt.equals("")){
					String usernames[] = weiboAt.split("@");
					boolean flagValidate = postService.validateAccount(usernames);
					if (!flagValidate) {
						returnDataInterface = new returnDataInterface(100, "@的用户名不存在", "");
					} else {
						//一些默认值
						Integer weiboAlive = 1;
						Integer weiboCollect = 0;
						Integer weiboForward = 0;
						Integer weiboComment = 0;
						Integer weiboLike = 0;
						//weibophoto
						String weiboPhoto = "";
						if(null!=file) {
							String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
							String saveUrl = request.getContextPath() + "/upload/";
							initFile(filePath);
							String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
							if(!((".jpg").equals(ext.toLowerCase())||(".png").equals(ext.toLowerCase())
									||(".svg").equals(ext.toLowerCase())||(".jpeg").equals(ext.toLowerCase())
									||(".bmp").equals(ext.toLowerCase()))){
								returnDataInterface = new returnDataInterface(100, "更新失败,文件格式错误", "");
								return returnData;
							}
							if(file.getSize()>5400000){
								returnDataInterface = new returnDataInterface(100, "更新失败,图片超过大小", "");
								return returnData;
							}
							String newfilename = System.currentTimeMillis() + ext;
							String PathAndName = filePath + newfilename;
							saveUrl = saveUrl + newfilename;
							weiboPhoto = saveUrl;
							File resultFile = new File(PathAndName);
							file.transferTo(resultFile);
						}
						//得到account
						Account account = accountService.getAccountByUserid(userId);
						Post postWeibo = new Post(uuid, userId, weiboTime, weiboContent, weiboPhoto, weiboAlive, weiboCollect, weiboForward, weiboComment, weiboLike, weiboAt, weiboTopic, false, false, usernames);
						postWeibo.setUserName(account.getUserName());
						postWeibo.setUserHead(account.getUserHead());
						postWeibo.setWeiboRootId("-1");
						boolean flag = postService.postWeibo(postWeibo);

						PostVO postVO = new PostVO(postWeibo);
						if (flag) {
							returnDataInterface = new returnDataInterface(200, "发表成功", new FastJsonTool().createJsonString(postVO));
						} else {
							returnDataInterface = new returnDataInterface(100, "发表失败", "");
						}
					}
				}else{
					String usernames[] = weiboAt.split("@");
					//一些默认值
					Integer weiboAlive = 1;
					Integer weiboCollect = 0;
					Integer weiboForward = 0;
					Integer weiboComment = 0;
					Integer weiboLike = 0;
					//得到account
					Account account = accountService.getAccountByUserid(userId);
					//weibophoto
					String weiboPhoto = "";
					if(null!=file) {
						String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
						String saveUrl = request.getContextPath() + "/upload/";
						initFile(filePath);
						String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
						String newfilename = System.currentTimeMillis() + ext;
						String PathAndName = filePath + newfilename;
						if(!((".jpg").equals(ext.toLowerCase())||(".png").equals(ext.toLowerCase())
								||(".svg").equals(ext.toLowerCase())||(".jpeg").equals(ext.toLowerCase())
								||(".bmp").equals(ext.toLowerCase()))){
							returnDataInterface = new returnDataInterface(100, "更新失败,文件格式错误", "");
							return returnData;
						}
						if(file.getSize()>5400000){
							returnDataInterface = new returnDataInterface(100, "更新失败,图片超过大小", "");
							return returnData;
						}
						saveUrl = saveUrl + newfilename;
						weiboPhoto = saveUrl;
						File resultFile = new File(PathAndName);
						file.transferTo(resultFile);
					}
					Post postWeibo = new Post(uuid, userId, weiboTime, weiboContent, weiboPhoto, weiboAlive, weiboCollect, weiboForward, weiboComment, weiboLike, weiboAt, weiboTopic, false, false, usernames);
					boolean flag = postService.postWeibo(postWeibo);
					postWeibo.setUserName(account.getUserName());
					postWeibo.setUserHead(account.getUserHead());
					postWeibo.setWeiboRootId("-1");
					PostVO postVO = new PostVO(postWeibo);
					if (flag) {
						returnDataInterface = new returnDataInterface(200, "发表成功", new FastJsonTool().createJsonString(postVO));
					} else {
						returnDataInterface = new returnDataInterface(100, "发表失败", "");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnData = FastJsonTool.createJsonString(returnDataInterface);
		return returnData;
	}

	/**
	 * 删除微博
	 * 改 weiboalive 的状态
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/deleteWeibo", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String deleteWeibo(@RequestParam(value = "weiboId") String id) {
		String returnData = "";
		returnDataInterface returnDataInterface = null;
		try {
			boolean flag = postService.deleteWeibo(id);
			if (flag) {
				//根据weiboId得到微博，查rootid是否为空，为空就不操作，
				// 否则取出rootid的微博，将转发量减一
				//parentid是否和rootid相等 相等就不操作 否则 就把parentid的微博拿出来 转发量减一
				Post post = postService.getOneWeibo(id);
				if(post.getWeiboRootId() == null){

				}else{
					Post postForward = postService.getOneWeibo(post.getWeiboRootId());
					postForward.setWeiboForward(postForward.getWeiboForward()-1);
					postService.save(postForward);
					if(post.getWeiboParentId() == post.getWeiboRootId()){

					}else{
						Post postParent = postService.getOneWeibo(post.getWeiboParentId());
						postParent.setWeiboForward(postParent.getWeiboForward()-1);
						postService.save(postForward);
					}
				}
				returnDataInterface = new returnDataInterface(200, "删除成功", "");
			} else {
				returnDataInterface = new returnDataInterface(100, "删除失败", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		returnData = FastJsonTool.createJsonString(returnDataInterface);
		return returnData;
	}

	/**
	 * 分页获取所有微博信息
	 * @param userId
	 * @param page     当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/getpageweibo", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String GetWeiboByPage(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer pageSize) {
		String returnResult = "";
		returnDataInterface returnDataInterface = null;
		List<QueryCondition> query = new ArrayList<QueryCondition>();
		QueryCondition queryCondition = new QueryCondition("weiboAlive", QueryCondition.EQ, 1);
		query.add(queryCondition);
		Pagination<Post> rolePagination = postService.getPagination(Post.class, query, "order by weiboTime desc", page, pageSize);
		List<Post> postList = rolePagination.getRecordList();
		List<PostVO> VOList = new ArrayList<PostVO>();
		//设置username，weiboisLike. weiboisCollect
		for (int i = 0; i < postList.size(); i++) {
			//得到account
			Account account = accountService.getAccountByUserid(postList.get(i).getUserId());
			String userName = account.getUserName();
			Post item = postList.get(i);
			item.setUserName(userName);
			postList.get(i).setUserHead(account.getUserHead());
			boolean weiboisLike = likeService.isLikeExist(userId, postList.get(i).getWeiboId());
			postList.get(i).setWeiboisLike(weiboisLike);
			//微博是否被收藏
			boolean weiboisCollect = markService.isMarkExist(userId, postList.get(i).getWeiboId());
			postList.get(i).setWeiboisCollect(weiboisCollect);
			PostVO postVO = new PostVO(postList.get(i));
			//判断是否有rootId,有的话返回rootId对应的微博
			if(postList.get(i).getWeiboRootId() != null && !postList.get(i).getWeiboRootId().equals("-1")){
				Post rootWeibo = postService.getOneWeibo(postList.get(i).getWeiboRootId());
				Integer userId2 = rootWeibo.getUserId();
				Account account2 = accountService.getAccountByUserid(userId2);
				rootWeibo.setUserHead(account2.getUserHead());
				postVO.setRootWeibo(rootWeibo);
			}else{
				postVO.setWeiboRootId("-1");
			}
			VOList.add(postVO);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", VOList);
		map.put("total", rolePagination.getRecordCount());
		String vo = JSONObject.fromObject(map).toString();
		returnDataInterface = new returnDataInterface(200, "微博获取成功", vo);
		returnResult = FastJsonTool.createJsonString(returnDataInterface);
		return returnResult;
	}

	/**
	 * 根据用户id分页获取所有微博信息
	 *
	 * @param visitId  （当前登录的用户id）
	 * @param userId   （被查看的用户id）
	 * @param page     当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/getWeiboByUserIdPage", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String GetWeiboByUserIdPage(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer pageSize, @RequestParam(value = "userId") Integer userId, @RequestParam(value = "visitId") Integer visitId) {
		String returnResult = "";
		returnDataInterface returnDataInterface = null;
		List<QueryCondition> query = new ArrayList<QueryCondition>();
		QueryCondition queryCondition = new QueryCondition("userId", QueryCondition.EQ, userId);
		query.add(queryCondition);
		query.add(new QueryCondition("weiboAlive", QueryCondition.EQ, 1));
		Pagination<Post> rolePagination = postService.getPagination(Post.class, query, "order by weiboTime desc", page, pageSize);
		//设置username，weiboisLike. weiboisCollect
		List<Post> postList = rolePagination.getRecordList();
		List<PostVO> VOList = new ArrayList<PostVO>();
		for (int i = 0; i < postList.size(); i++) {
			Account account = accountService.getAccountByUserid(postList.get(i).getUserId());
			postList.get(i).setUserName(account.getUserName());
			postList.get(i).setUserHead(account.getUserHead());
			//调这个接口看是否被赞过（当前登录的用户id是否赞过被查看的用户的微博）
			boolean weiboisLike = likeService.isLikeExist(visitId, postList.get(i).getWeiboId());
			postList.get(i).setWeiboisLike(weiboisLike);
			//微博是否被收藏
			boolean weiboisCollect = markService.isMarkExist(visitId, postList.get(i).getWeiboId());
			postList.get(i).setWeiboisCollect(weiboisCollect);
			PostVO postVO = new PostVO(postList.get(i));
			//判断是否有rootId,有的话返回rootId对应的微博
			if(postList.get(i).getWeiboRootId() != null && !postList.get(i).getWeiboRootId().equals("-1")){
				Post rootWeibo = postService.getOneWeibo(postList.get(i).getWeiboRootId());
				Integer userId2 = rootWeibo.getUserId();
				Account account2 = accountService.getAccountByUserid(userId2);
				rootWeibo.setUserHead(account2.getUserHead());
				postVO.setRootWeibo(rootWeibo);
			}else{
				postVO.setWeiboRootId("-1");
			}
			VOList.add(postVO);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", VOList);
		map.put("total", rolePagination.getRecordCount());
		String vo = JSONObject.fromObject(map).toString();
		returnDataInterface = new returnDataInterface(200, "个人微博获取成功", vo);
		returnResult = FastJsonTool.createJsonString(returnDataInterface);
		return returnResult;
	}

	/**
	 * 查看关注人的微博
	 *
	 * @param userId   （当前登录的用户id）
	 * @param page     当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/getWeiboAttentionPage", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String GetWeiboAttentionPage(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer pageSize, @RequestParam(value = "userId") Integer userId) {
		String returnResult = "";
		returnDataInterface returnDataInterface = null;
		//根据userId得到关注的人
		List<Attention> accountList = attentionService.getFollow(userId);
		if (accountList.size() != 0 ) {
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			String sql = " weiboAlive=1 and ";
			sql = sql + "userId=" + accountList.get(0).getAttentionUserId();
			for (int i = 1; i < accountList.size(); i++) {
				sql = sql + " or userId=" + accountList.get(1).getAttentionUserId();
			}
			queryConditions.add(new QueryCondition(sql));
			Pagination<Post> pagination = postService.getPagination(Post.class, queryConditions, "order by weiboTime desc", page, pageSize);
			//设置username，weiboisLike. weiboisCollect
			List<Post> postList = pagination.getRecordList();
			List<PostVO> VOList = new ArrayList<PostVO>();
			for (int i = 0; i < postList.size(); i++) {
				Account account = accountService.getAccountByUserid(postList.get(i).getUserId());
				postList.get(i).setUserName(account.getUserName());
				postList.get(i).setUserHead(account.getUserHead());
				//调这个接口看是否被赞过（当前登录的用户id是否赞过被查看的用户的微博）
				boolean weiboisLike = likeService.isLikeExist(userId, postList.get(i).getWeiboId());
				postList.get(i).setWeiboisLike(weiboisLike);
				//微博是否被收藏
				boolean weiboisCollect = markService.isMarkExist(userId, postList.get(i).getWeiboId());
				postList.get(i).setWeiboisCollect(weiboisCollect);
				PostVO postVO = new PostVO(postList.get(i));
				//判断是否有rootId,有的话返回rootId对应的微博
				if(postList.get(i).getWeiboRootId() != null && !postList.get(i).getWeiboRootId().equals("-1")){
					Post rootWeibo = postService.getOneWeibo(postList.get(i).getWeiboRootId());
					Integer userId2 = rootWeibo.getUserId();
					Account account2 = accountService.getAccountByUserid(userId2);
					rootWeibo.setUserHead(account2.getUserHead());
					postVO.setRootWeibo(rootWeibo);
				}else{
					postVO.setWeiboRootId("-1");
				}
				VOList.add(postVO);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", VOList);
			map.put("total", pagination.getRecordCount());
			String vo = JSONObject.fromObject(map).toString();
			returnDataInterface = new returnDataInterface(200, "关注人微博获取成功", vo);
		}else{
			returnDataInterface = new returnDataInterface(100, "没有关注人", "");
		}
		returnResult = FastJsonTool.createJsonString(returnDataInterface);
		return returnResult;
	}

	/**
	 * 根据话题找到所有的微博
	 *
	 * @param weiboTopic   （话题）
	 * @param page     当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/getWeiboByTopic", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String GetWeiboByTopic(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer pageSize, @RequestParam(value = "weiboTopic") String weiboTopic) {
		String returnResult = "";
		returnDataInterface returnDataInterface = null;
		List<QueryCondition> query = new ArrayList<QueryCondition>();
		QueryCondition queryCondition = new QueryCondition("weiboTopic", QueryCondition.EQ, weiboTopic);
		query.add(queryCondition);
		query.add(new QueryCondition("weiboAlive", QueryCondition.EQ, 1));
		Pagination<Post> rolePagination = postService.getPagination(Post.class, query, "order by weiboTime desc", page, pageSize);
		List<Post> postList = rolePagination.getRecordList();
		List<PostVO> VOList = new ArrayList<PostVO>();
		//设置username，weiboisLike. weiboisCollect
		for (int i = 0; i < postList.size(); i++) {
			//得到account
			Account account = accountService.getAccountByUserid(postList.get(i).getUserId());
			postList.get(i).setUserName(account.getUserName());
			postList.get(i).setUserHead(account.getUserHead());
			PostVO postVO = new PostVO(postList.get(i));
			//判断是否有rootId,有的话返回rootId对应的微博
			if(postList.get(i).getWeiboRootId() != null && !postList.get(i).getWeiboRootId().equals("-1")){
				Post rootWeibo = postService.getOneWeibo(postList.get(i).getWeiboRootId());
				Integer userId2 = rootWeibo.getUserId();
				Account account2 = accountService.getAccountByUserid(userId2);
				rootWeibo.setUserHead(account2.getUserHead());
				postVO.setRootWeibo(rootWeibo);
			}else{
				postVO.setWeiboRootId("-1");
			}
			VOList.add(postVO);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", VOList);
		map.put("total", rolePagination.getRecordCount());
		String vo = JSONObject.fromObject(map).toString();
		returnDataInterface = new returnDataInterface(200, "微博获取成功", vo);
		returnResult = FastJsonTool.createJsonString(returnDataInterface);
		return returnResult;
	}

	/**
	 * 根据userName找到所有的微博
	 * @param visitId   （当前登录的用户）
	 * @param userName   （用户名）
	 * @param page     当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/getWeiboByUserName", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String GetWeiboByUserName(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer pageSize, @RequestParam(value = "userName") String userName, @RequestParam(value = "visitId") Integer visitId) {
		String returnResult = "";
		returnDataInterface returnDataInterface = null;
		Account a = postService.getAccountByUserName(userName);
		if(a != null){
			//根据userName得到userId
			Integer userId = a.getUserId();
			List<QueryCondition> query = new ArrayList<QueryCondition>();
			QueryCondition queryCondition = new QueryCondition("userId", QueryCondition.EQ, userId);
			query.add(queryCondition);
			query.add(new QueryCondition("weiboAlive", QueryCondition.EQ, 1));
			Pagination<Post> rolePagination = postService.getPagination(Post.class, query, "order by weiboTime desc", page, pageSize);
			//设置username，weiboisLike. weiboisCollect
			List<Post> postList = rolePagination.getRecordList();
			List<PostVO> VOList = new ArrayList<PostVO>();
			for (int i = 0; i < postList.size(); i++) {
				Account account = accountService.getAccountByUserid(postList.get(i).getUserId());
				postList.get(i).setUserName(account.getUserName());
				postList.get(i).setUserHead(account.getUserHead());
				//调这个接口看是否被赞过（当前登录的用户id是否赞过被查看的用户的微博）
				boolean weiboisLike = likeService.isLikeExist(visitId, postList.get(i).getWeiboId());
				postList.get(i).setWeiboisLike(weiboisLike);
				//微博是否被收藏
				boolean weiboisCollect = markService.isMarkExist(visitId, postList.get(i).getWeiboId());
				postList.get(i).setWeiboisCollect(weiboisCollect);
				PostVO postVO = new PostVO(postList.get(i));
				//判断是否有rootId,有的话返回rootId对应的微博
				if(postList.get(i).getWeiboRootId() != null && !postList.get(i).getWeiboRootId().equals("-1")){
					Post rootWeibo = postService.getOneWeibo(postList.get(i).getWeiboRootId());
					Integer userId2 = rootWeibo.getUserId();
					Account account2 = accountService.getAccountByUserid(userId2);
					rootWeibo.setUserHead(account2.getUserHead());
					postVO.setRootWeibo(rootWeibo);
				}else{
					postVO.setWeiboRootId("-1");
				}
				VOList.add(postVO);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", VOList);
			map.put("total", rolePagination.getRecordCount());
			String vo = JSONObject.fromObject(map).toString();
			returnDataInterface = new returnDataInterface(200, "个人微博获取成功", vo);
		}else{
			returnDataInterface = new returnDataInterface(100, "该用户名不存在", "");
		}
		returnResult = FastJsonTool.createJsonString(returnDataInterface);
		return returnResult;
	}

	/**
	 * 分页获取所有微博信息（不加userId）
	 * @param page     当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/getpageweiboNoUserName", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String GetpageweiboNoUserName(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer pageSize) {
		String returnResult = "";
		returnDataInterface returnDataInterface = null;
		List<QueryCondition> query = new ArrayList<QueryCondition>();
		QueryCondition queryCondition = new QueryCondition("weiboAlive", QueryCondition.EQ, 1);
		query.add(queryCondition);
		Pagination<Post> rolePagination = postService.getPagination(Post.class, query, "order by weiboTime desc", page, pageSize);
		List<Post> postList = rolePagination.getRecordList();
		List<PostVO> VOList = new ArrayList<PostVO>();
		//设置username，weiboisLike. weiboisCollect
		for (int i = 0; i < postList.size(); i++) {
			//得到account
			Account account = accountService.getAccountByUserid(postList.get(i).getUserId());
			postList.get(i).setUserName(account.getUserName());
			postList.get(i).setUserHead(account.getUserHead());
			PostVO postVO = new PostVO(postList.get(i));
			//判断是否有rootId,有的话返回rootId对应的微博
			if(postList.get(i).getWeiboRootId() != null && !postList.get(i).getWeiboRootId().equals("-1")){
				Post rootWeibo = postService.getOneWeibo(postList.get(i).getWeiboRootId());
				Integer userId2 = rootWeibo.getUserId();
				Account account2 = accountService.getAccountByUserid(userId2);
				rootWeibo.setUserHead(account2.getUserHead());
				postVO.setRootWeibo(rootWeibo);
			}else{
				postVO.setWeiboRootId("-1");
			}
			VOList.add(postVO);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", VOList);
		map.put("total", rolePagination.getRecordCount());
		String vo = JSONObject.fromObject(map).toString();
		returnDataInterface = new returnDataInterface(200, "微博获取成功", vo);
		returnResult = FastJsonTool.createJsonString(returnDataInterface);
		return returnResult;
	}

	private void initFile(String filePath){
		File filedir = new File(filePath);
		if (!filedir.exists()){
			filedir.mkdir();
		}
	}
}

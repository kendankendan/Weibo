package org.shiro.demo.controller.front;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;
import org.shiro.demo.controller.front.interfa.returnDataInterface;
import org.shiro.demo.entity.Account;
import org.shiro.demo.service.IAccountService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value="/front/account")
public class accountFrontController {

	@Autowired
	private IAccountService accountService;
	
	/**
	 * 用户注册
	 * @param name
	 * @return  
	 * 
	 */
	@RequestMapping(value = "/register",method=RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String register(@RequestParam(value="username")String userName,@RequestParam(value="loginname")String userLoginName,@RequestParam(value="password")String userPwd){
		String returnData = "";
		returnDataInterface returnDataInterface = null;
		if(null==userLoginName || "".equals(userLoginName) || null == userPwd || "".equals(userPwd)){
			returnDataInterface = new returnDataInterface(100, "账号和密码不能为空", "");
		}
		boolean exist = accountService.isExistLoginName(userLoginName);
		if(exist){//存在相同
			returnDataInterface = new returnDataInterface(100, "存在相同的账号", "");
			returnData = FastJsonTool.createJsonString(returnDataInterface);
			return returnData;
		}
		exist = accountService.isExistUserName(userName);
		if(exist){//存在相同
			returnDataInterface = new returnDataInterface(100, "存在相同昵称", "");
			returnData = FastJsonTool.createJsonString(returnDataInterface);
			return returnData;
		}
		String hashed = BCrypt.hashpw(userPwd, BCrypt.gensalt());
		Account account = new Account(userName, "/weibo/upload/person.png", userLoginName, hashed, 0, System.currentTimeMillis()/1000, "");
		boolean flag = accountService.regist(account);
		
		if(flag){
			returnDataInterface = new returnDataInterface(200, "注册成功", "");
		}else{
			returnDataInterface = new returnDataInterface(100, "注册失败", "");
		}
		returnData = FastJsonTool.createJsonString(returnDataInterface);
		return returnData;
	}
	
	/**
	 * 修改头像
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/alertphoto",method=RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String alertPhoto(@RequestParam(value="userId")Integer userId,@RequestParam(value = "file",required = false )MultipartFile file,HttpServletRequest request){
		String returnData = "";
		returnDataInterface returnDataInterface = null;
		String userHead = "";
		if(null!=file){
			String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
			String saveUrl = request.getContextPath() + "/upload/";
			System.out.println(filePath);
			System.out.println(saveUrl);
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
	        userHead = saveUrl;
	        File resultFile = new File(PathAndName);
	        try{
	            file.transferTo(resultFile);
	            Account account = accountService.getById(Account.class, userId);
	            account.setUserHead(userHead);
	            accountService.update(account);
	            returnDataInterface = new returnDataInterface(200, "更新成功", userHead);
	        } catch (IOException e1){
	            e1.printStackTrace();
	            returnDataInterface = new returnDataInterface(100, "更新失败", userHead);
	        }
		}
		returnData = FastJsonTool.createJsonString(returnDataInterface);
		return returnData;
	}
	
	
	/**
	 * 修改资料
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateinfo",method=RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String updateInfo(@RequestParam(value="userId")Integer userId,
			@RequestParam(value="username",required=false)String userName,
			@RequestParam(value="sex",required=false)Integer userSex,
			@RequestParam(value="introduce")String userIntroduce,
			@RequestParam(value = "file",required = false )MultipartFile file,HttpServletRequest request){
		String returnData = "";
		returnDataInterface returnDataInterface = null;
		Account account = accountService.getById(Account.class, userId);
		String userHead = "";
		if(null!=(userIntroduce)){
			account.setUserIntroduce(userIntroduce);
		}
		if(null!=userSex){
			account.setUserSex(userSex);
		}
		if(userName.equals(account.getUserName())){
			
		}else{
			if(null!=(userName)){
				account.setUserName(userName);
			}
			boolean exist = accountService.isExistUserName(userName);
			if(exist){//存在相同
				returnDataInterface = new returnDataInterface(100, "存在相同昵称", "");
				returnData = FastJsonTool.createJsonString(returnDataInterface);
				return returnData;
			}
		}
		if(null!=file){
			String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
			String saveUrl = request.getContextPath() + "/upload/";
			System.out.println(filePath);
			System.out.println(saveUrl);
			initFile(filePath);
			String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			if(!((".jpg").equals(ext.toLowerCase())||(".png").equals(ext.toLowerCase())
					||(".svg").equals(ext.toLowerCase())||(".jpeg").equals(ext.toLowerCase()))){
				returnDataInterface = new returnDataInterface(100, "更新失败,文件格式错误", userHead);
				returnData = FastJsonTool.createJsonString(returnDataInterface);
				return returnData;
			}
			if(file.getSize()>5400000){
				returnDataInterface = new returnDataInterface(100, "更新失败,图片超过大小", userHead);
				returnData = FastJsonTool.createJsonString(returnDataInterface);
				return returnData;
			}
	        String newfilename = System.currentTimeMillis() + ext;
	        String PathAndName = filePath + newfilename;
	        saveUrl = saveUrl + newfilename;
	        userHead = saveUrl;
	        File resultFile = new File(PathAndName);
	        try{
	            file.transferTo(resultFile);
	            account.setUserHead(userHead);
	        } catch (IOException e1){
	            e1.printStackTrace();
	            returnDataInterface = new returnDataInterface(100, "图片上传失败", "");
	            returnData = FastJsonTool.createJsonString(returnDataInterface);
	            return returnData;
	        }
		}
		try {
			accountService.update(account);
			returnDataInterface = new returnDataInterface(200, "更新成功", "");
		} catch (Exception e) {
			e.printStackTrace();
			returnDataInterface = new returnDataInterface(100, "更新失败", "");
		}
		returnData = FastJsonTool.createJsonString(returnDataInterface);
		return returnData;
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatepwd",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String updatePwd(@RequestParam(value="userId")Integer userId,@RequestParam(value="password")String password){
		String returnData = "";
		returnDataInterface returnDataInterface = null;
		Account account = accountService.getById(Account.class, userId);
		if(null == account){
			returnDataInterface = new returnDataInterface(100, "更新失败", "");
			returnData = FastJsonTool.createJsonString(returnDataInterface);
			return returnData;
		}
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
		account.setUserPwd(hashed);
		try {
			accountService.update(account);
			returnDataInterface = new returnDataInterface(200, "更新成功", "");
		} catch (Exception e) {
			e.printStackTrace();
			returnDataInterface = new returnDataInterface(100, "更新失败", "");
		}
		returnData = FastJsonTool.createJsonString(returnDataInterface);
		return returnData;
	}
	
	/**
	 * 用户登陆
	 * @param userLoginName 登陆名
	 * @param userPwd 密码
	 * @return
	 * todo  密码的解密与比对
	 */
	@RequestMapping(value = "/login",method=RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String login(@RequestParam(value="loginname")String userLoginName,@RequestParam(value="password")String userPwd){
		String returnData = "";
		returnDataInterface returnState =  accountService.validateAccount(userLoginName, userPwd);
		returnData = FastJsonTool.createJsonString(returnState);
		return returnData;
	}
	
	
	/**
	 * 获取个人资料
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getinfo",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String getInfo(@RequestParam(value="userId")Integer userId){
		String returnData = "";
		returnDataInterface returnDataInterface = null;
		Account account = accountService.getById(Account.class, userId);
		if(null==account){
			returnDataInterface = new returnDataInterface(100, "获取失败", "");
		}else{
			returnDataInterface = new returnDataInterface(200, "获取成功", FastJsonTool.createJsonString(new AccountVO(account)));
		}
		returnData = FastJsonTool.createJsonString(returnDataInterface);
		return returnData;
	}
	
	/**
	 * 获取个人资料
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getinfoByUsername",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String getInfo(@RequestParam(value="userName")String username){
		String returnData = "";
		returnDataInterface returnDataInterface = null;
		Account account = accountService.getAccountByUserName(username);
		if(null==account){
			returnDataInterface = new returnDataInterface(100, "获取失败", "");
		}else{
			returnDataInterface = new returnDataInterface(200, "获取成功", FastJsonTool.createJsonString(new AccountVO(account)));
		}
		returnData = FastJsonTool.createJsonString(returnDataInterface);
		return returnData;
	}
	
	@RequestMapping(value = "/getpath",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String login(HttpServletRequest request){
		String returnData = "";
		String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
		String saveUrl = request.getContextPath() + "/upload/";
		return filePath+" "+saveUrl;
	}
	
	private void initFile(String filePath){
		File filedir = new File(filePath);
        if (!filedir.exists()){
            filedir.mkdir();
        }
	}
}

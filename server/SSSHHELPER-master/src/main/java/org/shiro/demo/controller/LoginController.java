package org.shiro.demo.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.mindrot.jbcrypt.BCrypt;
import org.shiro.demo.entity.User;
import org.shiro.demo.service.IUserService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.PublicKeyMap;
import org.shiro.demo.util.RSAUtils;
import org.shiro.demo.util.ValidateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 登陆
	 * @param currUser
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login" ,method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String login(User currUser,HttpSession session, HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();
		logger.info("rsa:"+currUser.getPassword());
		String pwd  = RSAUtils.decryptStringByJs(currUser.getPassword());
		logger.info(pwd);
		String hashed = BCrypt.hashpw(pwd, BCrypt.gensalt());
		logger.info("hashed= "+hashed);
		User user = userService.getByAccount(currUser.getAccount());
		if(null==user){
			return "redirect:/";
		}
		logger.info("user real pwd: "+user.getPassword());
		UsernamePasswordToken token = null;
		try{
			if(BCrypt.checkpw(pwd, user.getPassword())){
				token = new UsernamePasswordToken(currUser.getAccount(),user.getPassword());
			}else{
				token = new UsernamePasswordToken(currUser.getAccount(),hashed);
			}
			subject.login(token);
			return "redirect:/system/main.jsp";
		}catch(Exception e){
			token.clear();
			e.printStackTrace();
			return "redirect:/";
		}
	}
	/**
	 * 登出
	 * @return
	 */
	@RequestMapping(value = "/loginout" ,method=RequestMethod.GET,produces={"application/json;charset=UTF-8"})
	public String loginOut(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/";
	}
	/**
	 * 生成验证码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/validateCode")
	public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-cache");
		String verifyCode = ValidateCode.generateTextCode(ValidateCode.TYPE_NUM_ONLY, 4, null);
		request.getSession().setAttribute("validateCode", verifyCode);
		response.setContentType("image/jpeg");
		BufferedImage bim = ValidateCode.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);
		ImageIO.write(bim, "JPEG", response.getOutputStream());
	}
	
	/**
	 * 获取公钥
	 * @return
	 */
	@RequestMapping(value="/getrasrepair")
	@ResponseBody
	public String getRasRepair(){
		PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
		System.out.println(publicKeyMap);
		return FastJsonTool.createJsonString(publicKeyMap);
	}
}

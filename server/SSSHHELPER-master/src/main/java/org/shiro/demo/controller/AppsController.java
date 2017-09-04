package org.shiro.demo.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.RSAUtils;
import org.shiro.demo.util.SplitParamsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/app")
public class AppsController {
	
	@RequiresPermissions("admin:user")
	//@RequiresUser
	@ResponseBody
	@RequestMapping(value = "/getdata" ,method=RequestMethod.GET)
	public String getdata(){
		return "app:getdata";
	}
	
	@ResponseBody
	@RequestMapping(value = "/error" ,method=RequestMethod.GET)
	public String errormessage(){
		return "Unauthenticated,return to login";
	}

	@ResponseBody
	@RequestMapping(value="/getdata1",method=RequestMethod.GET)
	public String getdata1(@RequestParam(value="params")String params){
		String encryptString = RSAUtils.decryptString(params);
		System.out.println(encryptString);
		Map<String, String> splitParams = SplitParamsUtil.splitParams(encryptString, "&", "=");
		return FastJsonTool.createJsonString(splitParams);
	}
}

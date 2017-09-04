package org.shiro.demo.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/front")
public class testFrontController {

	@RequestMapping(value = "/testv",method=RequestMethod.GET)
	@ResponseBody
	public String testV(@RequestParam(value="name")String name){
		return name+" ssss";
	}
	
}

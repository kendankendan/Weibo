package org.shiro.demo.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

public class MyAccessFilter extends AccessControlFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		if(SecurityUtils.getSubject().isAuthenticated()){
			return true;
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		response.getWriter().write("fail ! return to login");
		return false;
	}

}

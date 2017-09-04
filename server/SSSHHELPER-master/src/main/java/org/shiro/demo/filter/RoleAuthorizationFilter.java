package org.shiro.demo.filter;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

/**
 * 
 * 1.自定义角色鉴权过滤器(满足其中一个角色则认证通过) 2.扩展异步请求认证提示功能;
 * 
 * @author shadow
 * 
 */
public class RoleAuthorizationFilter extends AuthorizationFilter {

	
	

	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws IOException {

		System.out.println("enter app author");
		Subject subject = getSubject(request, response);
		Object principal = subject.getPrincipal();
		PrincipalCollection principals = subject.getPrincipals();
		UsernamePasswordToken token = new UsernamePasswordToken();
		System.out.println("xxxxxx++++++");
		subject.login(token);
		
		boolean authenticated = subject.isAuthenticated();

		if(authenticated){
		}else{
			throw new UnauthenticatedException();
		}
		
		String[] rolesArray = (String[]) mappedValue;

		if (rolesArray == null || rolesArray.length == 0) {
			// no roles specified, so nothing to check - allow access.
			return true;
		}

		Set<String> roles = CollectionUtils.asSet(rolesArray);
		for (String role : roles) {
			if (subject.hasRole(role)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		response.getWriter().write("limit with the Authorization");
		return false;
	}

}
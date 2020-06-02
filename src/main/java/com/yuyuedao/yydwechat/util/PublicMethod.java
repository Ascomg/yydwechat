package com.yuyuedao.yydwechat.util;


import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

public class PublicMethod {

	@Autowired
	private HttpServletRequest request;

	/**
	 * 获取request
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return request;
	}

	/**
	 * 获取session
	 * 
	 * @return
	 */
	protected HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		return session;
	}

	/**
	 * 从session中获取参数值
	 * @param parameterName
	 * @return
	 */
	protected String getDataBySession(String parameterName) {
		return getSession().getAttribute(parameterName) == null ? null : getSession().getAttribute(parameterName).toString();
	}

	protected String isnull(Object o, String init) {
		return (o == null) ? init : o.toString();
	}
	
	public String srch_qxaccount(String field){
		String xstr="";
		//String userrole=getDataBySession(IUserConstants.userrole);
		xstr=" "+field+" = '"+getDataBySession(IUserConstants.accountid)+"' ";
	/*	if("0".equals(userrole)){
			xstr=" 1=1";
		}else{
		
		
		}*/
	
		return xstr;
	}
	

}

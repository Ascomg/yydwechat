package com.yuyuedao.yydwechat.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yuyuedao.yydwechat.entity.X_userinfo;
import com.yuyuedao.yydwechat.service.LoginService;
import com.yuyuedao.yydwechat.util.IUserConstants;
import com.yuyuedao.yydwechat.util.PublicMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class LoginController extends PublicMethod {
	@Resource
	private LoginService loginService;
	
	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	public void exit(HttpServletRequest req,HttpServletResponse resp) {

		HttpSession session =req.getSession();
		session.setAttribute(IUserConstants.userid,null);
		session.setAttribute(IUserConstants.username, "");
		resp.setStatus(302);
		resp.setHeader("location",  "login");
	}


	@RequestMapping(value = { "/login" },method = RequestMethod.GET)
	public String Login() {
		return "login";
	}

	@RequestMapping(value = { "/login" },method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> Login(X_userinfo user) {
		Map<String,Object> returnmap=new HashMap<>();
			boolean islogin=loginService.CheckLogin(user);
			returnmap.put("status",islogin);
			if(islogin){
				returnmap.put("data",user);
				returnmap.put("message","登录成功");
			}else{
				returnmap.put("message","用户名或密码不正确");
			}
		returnmap.put("status",true);

		return returnmap;
	}


	@RequestMapping(value = { "/load" },method = RequestMethod.GET)
	public String Load(HttpServletRequest req) {
		System.out.println(req);
		return "main";
	}



}
package com.yuyuedao.yydwechat.util;

import com.thoughtworks.xstream.mapper.Mapper;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     * 基于URL实现的拦截器
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();

        if (path.matches(Const.DRAW_PATH)) {

            Object code = request.getParameter("code");
            String debug=request.getParameter("debug");

            if(debug!=null){
                request.getSession().setAttribute(WeiXinOpenConstants.USER_OPENID, "oFkIV1G1tmo7IE7KqS4A2OeVK-Tg");
                return true;
            }else{
                if (code == null) {
                    String uri = request.getServerName() +request.getRequestURI()+"?"+request.getQueryString();
                    String targetUrl = "http://" + uri;
                    //	System.out.println("targetUrl:"+targetUrl);
                    String shareurl = WeiXinOpenConstants.WEB_OAUTH_URL.replace("APPID", WxConstants.appid).replace("REDIRECT_URI", URLEncoder.encode(targetUrl, "UTF-8")).replace("SCOPE", WeiXinOpenConstants.SNSAPI_BASE);
                    ((HttpServletResponse) response).sendRedirect(shareurl);
                    return true;
                } else {
                    if (!"authdeny".equals(code)) {
                        String requestUrl = WeiXinOpenConstants.WEB_OAUTH_ACCESSTOKEN_URL.replace("APPID", WxConstants.appid).replace("SECRET",WxConstants.appsecret).replace("CODE", code.toString());

                        String result = HttpUtil.sendGet(requestUrl, "UTF-8");
                        JSONObject res = JSONObject.fromObject(result);
                        System.out.println(res);
                        request.getSession().setAttribute(WeiXinOpenConstants.USER_OPENID, res.get("openid"));

                    }
                    return true;
                }

            }



        }else if (path.matches(Const.NO_INTERCEPTOR_PATH)) {
            //不需要的拦截直接过
            return true;
        }else {
            // 这写你拦截需要干的事儿，比如取缓存，SESSION，权限判断等

            HttpSession session = request.getSession();
            if (session.getAttribute(IUserConstants.userid) == null&&session.getAttribute(WeiXinOpenConstants.USER_OPENID)== null) {
                response.setStatus(302);
                response.setHeader("location", "login");
            }
            return true;
        }



    }
}

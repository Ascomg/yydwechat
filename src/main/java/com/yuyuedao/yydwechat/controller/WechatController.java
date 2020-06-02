package com.yuyuedao.yydwechat.controller;


import com.yuyuedao.yydwechat.service.WechatService;
import com.yuyuedao.yydwechat.util.CheckoutUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@Controller
@RequestMapping({ "weixin/callback" })
public class WechatController {


    @Resource
    WechatService wechatService;

    private static Logger logger = LoggerFactory.getLogger(WechatController.class);
    /**
     * 微信消息接收和token验证
     *
     * @param request
     * @param response
     * @throws IOException
     */
        @RequestMapping(value = { "/get" }, method = { RequestMethod.POST, RequestMethod.GET })
        public void get(HttpServletRequest request,
                HttpServletResponse response) throws Exception {
            // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
            request.setCharacterEncoding("UTF-8");  //微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
            response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
            boolean ispost = Objects.equals("POST",request.getMethod().toUpperCase());
            PrintWriter print;
            if (ispost) {
                String respMessage;

                try {
                    //logger.info("11111");

                    respMessage = wechatService.weixinPost(request,response);
                    print = response.getWriter();
                    if(respMessage==null){
                        respMessage="";
                    }
                    print.write(respMessage);
                    print.close();

                    //logger.info("2222");

                } catch (Exception e) {
                    logger.error("Failed to convert the message from weixin!");
                    e.printStackTrace();
                }

            }else{

            //if (isGet) {
                // 微信加密签名
                String signature = request.getParameter("signature");
                // 时间戳
                String timestamp = request.getParameter("timestamp");
                // 随机数
                String nonce = request.getParameter("nonce");
                // 随机字符串
                String echostr = request.getParameter("echostr");
                // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
                if (signature != null && CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
                    try {
                        print = response.getWriter();
                        print.write(echostr);
                        print.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }


}

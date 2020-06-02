package com.yuyuedao.yydwechat.controller;


import com.yuyuedao.yydwechat.service.SelectService;
import com.yuyuedao.yydwechat.util.PublicMethod;
import com.yuyuedao.yydwechat.util.SelectItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/select")
public class SelectController extends PublicMethod {

        @Resource
        private SelectService selectService;

        //获取自定义菜单链接
        @RequestMapping(value = "/getUrlList", method = RequestMethod.GET)
        @ResponseBody
        public List<SelectItem> getUrlList() {
            return selectService.getUrlList();
        }

        //获取关键词列表
        @RequestMapping(value = "/getKeyList", method = RequestMethod.GET)
        @ResponseBody
        public List<SelectItem> getKeyList( )   {
            return selectService.getKeyList();
        }

        //获取海报问答列表
        @RequestMapping(value = "/getQuestion", method = RequestMethod.GET)
        @ResponseBody
        public List<SelectItem> getQuestion()  {
            return selectService.getQuestion();
        }


    //获取海报模板列表
    @RequestMapping(value = "/getPoster", method = RequestMethod.GET)
    @ResponseBody
    public List<SelectItem> getPoster( ) {
        return selectService.getPoster();
    }

    //获取海报问答列表
    @RequestMapping(value = "/getDrawActivity", method = RequestMethod.GET)
    @ResponseBody
    public List<SelectItem> getDrawActivity( )  {
        return selectService.getDrawActivity();
    }


    //获取海报模板列表
    @RequestMapping(value = "/getPosterActivity", method = RequestMethod.GET)
    @ResponseBody
    public List<SelectItem> getPosterActivity( )   {
        return selectService.getPosterActivity();

    }



//
//        @RequestMapping(value = "/getSjlmList.do", method = RequestMethod.GET)
//        public @ResponseBody
//        List<SelectItem> getSjlmList(HttpServletRequest request) throws UnsupportedEncodingException {
//            List<SelectItem> list = null;
//            list = dao.getSjlmImg();
//            return list;
//        }
//
//        //获取评价参数
//        @RequestMapping(value = "/getappraise.do", method = RequestMethod.GET)
//        public @ResponseBody
//        List<SelectItem> getAppraise() {
//            List<SelectItem> list = dao.getAppraise();
//            return list;
//        }
//
//        //获取商家联盟分类
//        @RequestMapping(value = "/getSjlmflList.do", method = RequestMethod.GET)
//        public @ResponseBody
//        List<SelectItem> getSjlmflList(HttpServletRequest request) throws UnsupportedEncodingException {
//            List<SelectItem> list = null;
//            list = dao.getSjlmList();
//            return list;
//        }
//
//        /**
//         * 获取商品分类列表
//         * @param request
//         * @return
//         * @throws UnsupportedEncodingException
//         */
//        @RequestMapping(value = "/getSpStype.do", method = RequestMethod.GET)
//        public @ResponseBody
//        List<SelectItem> getSpStypeList(HttpServletRequest request) throws UnsupportedEncodingException {
//            List<SelectItem> list = null;
//            list = dao.getSpStypeList();
//            return list;
//        }
//
//
//        @RequestMapping(value = "/getwxsendStype.do", method = RequestMethod.GET)
//        public @ResponseBody
//        List<SelectItem> getwxsendStype(HttpServletRequest request) throws UnsupportedEncodingException {
//            List<SelectItem> list = null;
//            list = dao.getwxsendStype();
//            return list;
//        }
//
//        @RequestMapping(value = "/sendcode.do", method = RequestMethod.POST)
//        public @ResponseBody
//        Map<String, Object> sendCode(HttpServletRequest request) {
//            Map<String, Object> rmap = null;
//            String phone=request.getParameter("phone");
//            String accountid=request.getParameter("accountid");
//            dao.SwitchZrDatasource();
//            rmap = dao.send(accountid,phone);
//
//            return rmap;
//        }
//
//
//    }



}

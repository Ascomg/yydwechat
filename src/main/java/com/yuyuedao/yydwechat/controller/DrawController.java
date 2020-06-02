package com.yuyuedao.yydwechat.controller;


import com.yuyuedao.yydwechat.entity.*;
import com.yuyuedao.yydwechat.mapper.generator.WPFansMapper;
import com.yuyuedao.yydwechat.service.ActivityService;
import com.yuyuedao.yydwechat.service.DrawService;
import com.yuyuedao.yydwechat.service.NewsService;
import com.yuyuedao.yydwechat.util.PublicMethod;
import com.yuyuedao.yydwechat.util.WechatqQRcode;
import com.yuyuedao.yydwechat.util.WeiXinOpenConstants;
import com.yuyuedao.yydwechat.util.WxUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/draw")
public class DrawController extends  PublicMethod {

    @Resource
    private DrawService drawService;

    @Resource
    private NewsService newsService;

    @Resource
    private ActivityService activityService;

    @Resource
    private WechatqQRcode wechatqQRcode;

    @Resource
    private WPFansMapper wpFansMapper;



    @Resource
    private WxUtil wxUtil;

    @RequestMapping(value = "getList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(@Param("sname") String title, GridRequestDto dto) {
        int index=dto.getPageIndex()-1;
        int size=dto.getPageSize();
        int start = index * size, limit = start + size;
        Map<String,Object> map=null;
        Map<String,Object> rmap=null;
        try{

            rmap =drawService.getList(title,start,limit);
        }catch(Exception e){
            e.printStackTrace();
            rmap=new HashMap<String,Object>();
            rmap.put("message", e.getMessage());
            rmap.put("status", false);
        }
        return rmap;
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addInfo(DrawActivity drawActivity){

        Map<String,Object> returnMap=new HashMap<>();

        try{

            if(drawActivity!=null){
                int count=drawService.add(drawActivity);
                if(count>0){
                    returnMap.put("status",true);
                    returnMap.put("message","新增成功");
                }else{
                    returnMap.put("status",false);
                    returnMap.put("message","新增失败");
                }
            }else{
                returnMap.put("status",false);
                returnMap.put("message","没有新增的信息");
            }



        }catch(Exception e){
            e.printStackTrace();
            returnMap.put("status",false);
            returnMap.put("message",e.getMessage());
        }

        return returnMap;
    }



    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteInfo(@RequestParam("id") Integer sid){

        Map<String,Object> returnMap=new HashMap<>();


        try{

                if(sid!=null){
                int count=drawService.delete(sid);
                if(count>0){
                    returnMap.put("status",true);
                    returnMap.put("message","删除成功");
                }else{
                    returnMap.put("status",false);
                    returnMap.put("message","删除失败");
                }
            }else{
                returnMap.put("status",false);
                returnMap.put("message","没有要删除的信息");
            }



        }catch(Exception e){
            e.printStackTrace();
            returnMap.put("status",false);
            returnMap.put("message",e.getMessage());
        }

        return returnMap;
    }




    /***
     *
     * @param sid
     * @return
     */
    @RequestMapping(value = "/getById", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> getbyid(@RequestParam("sid") Integer sid )  {
        Map<String,Object> returnMap =new HashMap<String,Object>();
        try{
            if(sid!=null){
                DrawActivity news=drawService.getById(sid);
                returnMap.put("data",news);
                returnMap.put("status", true);
            }else{
                returnMap.put("status", false);
                returnMap.put("message", "请选择需要修改的记录!");
            }
        }catch(Exception e){
            e.printStackTrace();
            returnMap.put("message", e.getMessage());
            returnMap.put("status", false);
        }
        return returnMap;
    }


    /***
     *根据sid获取关键字详情
     * @param sid
     * @return
     */
    @RequestMapping(value = "/getDetails", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDetails(@RequestParam("sid") Integer sid )  {
        ModelAndView modelAndView=new ModelAndView();
        try{
            if(sid!=null){
                DrawActivity news=drawService.getById(sid);
                List<DrawParticipant> drawParticipantList=drawService.drawCountByOpenId(sid);
                List<DrawUser> drawUserList=drawService.drawCountById(sid);
                int count=drawParticipantList.size();
                String drawText="立即抽奖";
                if(count>0){
                    drawText="已抽奖";
                }
                if(news.getLotterytime().getTime()-(new Date()).getTime()<=0){
                    drawText="已结束";
                }
                modelAndView.setViewName("weixin/draw_details");
                modelAndView.addObject("data",news);
                modelAndView.addObject("sid",sid);
                modelAndView.addObject("isdraw",drawText);
                modelAndView.addObject("participantCount",drawUserList.size());
                modelAndView.addObject("drawUser",drawUserList);
                modelAndView.addObject("config",wxUtil.getshare(WeiXinOpenConstants.DRAW_URL.replace("SID",sid.toString())));
                modelAndView.addObject("status", true);
            }else{
                modelAndView.addObject("status", false);
                modelAndView.addObject("message", "请选择需要修改的记录!");
            }
        }catch(Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("status", false);
        }
        return modelAndView;
    }

    /***
     *根据sid获取关键字详情
     * @param sid
     * @return
     */
    @RequestMapping(value = "/getNews", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getNews(@RequestParam("sid") String sid )  {
        ModelAndView modelAndView=new ModelAndView();
        try{
            if(sid!=null){
                W_p_newsDetails news=newsService.getInfo(sid);
                modelAndView.setViewName("weixin/newsContent");
                modelAndView.addObject("data",news);
                modelAndView.addObject("status", true);
            }else{
                modelAndView.addObject("status", false);
                modelAndView.addObject("message", "请选择需要修改的记录!");
            }
        }catch(Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("status", false);
        }
        return modelAndView;
    }

    /***
     *根据sid获取关键字详情
     * @param
     * @return
     */
    @RequestMapping(value = "/draw")
    public String draw(@RequestParam("sid") Integer sid,Model model)  {
        try{
            if(sid!=null){

                DrawParticipant drawParticipant=new DrawParticipant();
                drawParticipant.setDrawActivityId(sid);
                if(drawService.draw(drawParticipant)>0){
                    List<DrawUser> drawUserList=drawService.drawCountById(sid);
                    model.addAttribute("isdraw","已抽奖");
                    model.addAttribute("participantCount",drawUserList.size());
                    model.addAttribute("drawUser",drawUserList);
                    model.addAttribute("sid",sid);

                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return "weixin/draw_details::article_type";
    }

    /***
     *根据sid获取关键字详情
     * @param sid
     * @return
     */
    @RequestMapping(value = "/saveImage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> saveImage(@RequestParam("sid") String sid )  {
        Map<String,Object> returnMap=new HashMap<>();
        try{
            if(sid!=null){
                String openId=getDataBySession(WeiXinOpenConstants.USER_OPENID);
                WPFansExample wpFansExample=new WPFansExample();
                wpFansExample.createCriteria().andOpenidEqualTo(openId);
                List<WPFans> wpFansList = wpFansMapper.selectByExample(wpFansExample);
                String userpath=wpFansList.get(0).getHeadUrl();
                String accessToken=wxUtil.getToken();
                DrawActivity drawActivity=drawService.getById(Integer.parseInt(sid));
                String qrcodeimg=wechatqQRcode.getQRImg(accessToken,openId,Integer.parseInt(sid));
                String imgurl= wechatqQRcode.showDrawImg(userpath,qrcodeimg,openId,drawActivity);
                returnMap.put("status",true);
                returnMap.put("imgurl",imgurl);
            }else{
                returnMap.put("status",false);
            }
        }catch(Exception e){
            e.printStackTrace();
            returnMap.put("status",true);
        }
        return returnMap;
    }

    @RequestMapping(value = "/drawUser")
    public String drawUser(@RequestParam("sid") Integer sid,@RequestParam("index") Integer i,Model model)  {
        String html="weixin/details";
        try{
            if(sid!=null){
                model.addAttribute("sid",sid);
                if(i==1){
                    html="weixin/details::draw_img";
                    List<DrawUser> drawUserList=drawService.drawCountById(sid);
                    model.addAttribute("draw",drawUserList);
                    model.addAttribute("participantCount",drawUserList.size());
                }
                if(i==0){
                    html="weixin/details::draw_user";
                    List<Activity> activity=activityService.getParticipantList(sid.toString(),"draw");
                    model.addAttribute("drawUser",activity);
                }
                if(i==2){
                    List<Activity> activity=activityService.getParticipantList(sid.toString(),"draw");
                    List<DrawUser> drawUserList=drawService.drawCountById(sid);
                    model.addAttribute("draw",drawUserList);
                    model.addAttribute("participantCount",drawUserList.size());
                    model.addAttribute("drawUser",activity);
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return html;
    }

    @RequestMapping(value = "getDrawDetails")
    @ResponseBody
    public Map<String,Object> getDrawDetails(@RequestParam("sid") Integer sid,@RequestParam("openid") String openid){
//        int index=dto.getPageIndex()-1;
//        int size=dto.getPageSize();
//        int start = index * size, limit = start + size;
        Map<String,Object> map=null;
        Map<String,Object> rmap=null;
        try{
            rmap=new HashMap<>();
            rmap.put("drawUser",drawService.getDrawDetails(sid,openid));
            rmap.put("status", true);
        }catch(Exception e){
            e.printStackTrace();
            rmap=new HashMap<>();
            rmap.put("message", e.getMessage());
            rmap.put("status", false);
        }
        return rmap;
    }



}

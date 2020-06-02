package com.yuyuedao.yydwechat.service;


import com.yuyuedao.yydwechat.controller.TestController;
import com.yuyuedao.yydwechat.entity.*;
import com.yuyuedao.yydwechat.mapper.KeyMapper;
import com.yuyuedao.yydwechat.mapper.MenuMapper;
import com.yuyuedao.yydwechat.mapper.NewsMapper;
import com.yuyuedao.yydwechat.mapper.generator.*;
import com.yuyuedao.yydwechat.util.*;
import javafx.util.StringConverter;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.*;

@Service("WechatService")
public class WechatService {

    @Autowired
    private WechatUserinfo userinfo;

    @Autowired
    private WechatqQRcode wechatqrcode;

    @Resource
    private WPGzhfMapper wpGzhfMapper;

    @Resource
    private NewsMapper newsMapper;

    @Resource
    private KeyMapper keyMapper;

    @Resource
    private PosterActivityMapper posterActivityMapper;

    @Resource
    private PosterMapper posterMapper;

    @Resource
    private PosterQuestionDetailsMapper posterQuestionDetailsMapper;

    @Resource
    private WPFansMapper wpFansMapper;


    @Resource
    private TextMsgMapper textMsgMapper;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private WxUtil wxUtil;

    @Resource
    private ActivityParticipantMapper activityParticipantMapper;

    @Resource
    private DrawActivityMapper drawActivityMapper;

    private static Logger logger = LoggerFactory.getLogger(WechatService.class);

       /**
     //     * 处理微信发来的请求
     //     *
     //     * @param request
     //     * @return
     //     */
    public  String weixinPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String respMessage = null;
         String astoken=wxUtil.getToken();

        String accountid=WxConstants.accountid;

        try {

            // xml请求解析
            Map<String, String> requestMap = MessageUtil.xmlToMap(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 消息内容
            String content = requestMap.get("Content");


            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                //这里根据关键字执行相应的逻辑
                TextMessage text = new TextMessage();


                //先查询关键字回复有没有对应回复
                W_p_key key=keyMapper.getByKey(accountid,content);//getFirstObjectByHql(" from w_p_key_response where accountid='"+accountid+"' and keyword='"+content+"' ");

                if(key!=null){
                    //关键字有对应回复
                    if(key.getType().equals("text")){
                        text.setContent(key.getTextMsg());
                    }
                    if(key.getType().equals("poster")){

                        ActivityParticipant activityParticipant=new ActivityParticipant();
                        activityParticipant.setActivityid(key.getPosterActivityId());
                        activityParticipant.setCreatetime(new Date());
                        activityParticipant.setOpenid(fromUserName);
                        activityParticipant.setStype("poster");
                        return posterMethod(fromUserName,astoken,key.getPosterActivityId(),activityParticipant);

                    }
                    if(key.getType().equals("news")){

                        return  getNewsReply(fromUserName, toUserName, accountid, key.getNewSid());

                    }
                } else {

                    //关键字没有对应回复先查询是不是海报问答回复
                    TextMsgExample textMsgExample = new TextMsgExample();
                    textMsgExample.createCriteria().andOpenidEqualTo(fromUserName);
                    textMsgExample.setOrderByClause("createTime");
                    List<TextMsg> textMsgList1 = textMsgMapper.selectByExample(textMsgExample);

                    if (textMsgList1.size() > 0) {

                        Integer prevQuestion = textMsgList1.get(textMsgList1.size() - 1).getQuestion();
                        Integer prevQuestionSort = textMsgList1.get(textMsgList1.size() - 1).getQuestionsort();

                        PosterQuestionDetailsExample posterQuestionDetailsExample = new PosterQuestionDetailsExample();
                        posterQuestionDetailsExample.createCriteria().andQuestionIdEqualTo(prevQuestion).andQuestionSortEqualTo(prevQuestionSort);
                        List<PosterQuestionDetails> posterQuestionDetailsList = posterQuestionDetailsMapper.selectByExample(posterQuestionDetailsExample);

                        if (null != posterQuestionDetailsList && !posterQuestionDetailsList.isEmpty()) {
                            Date end = new Date();
                           if (end.getTime() - textMsgList1.get(textMsgList1.size() - 1).getCreatetime().getTime() < 300000){
                                if (posterQuestionDetailsList.get(0).getPosterAnswer1().equals(content) || posterQuestionDetailsList.get(0).getPosterAnswer2().equals(content) || posterQuestionDetailsList.get(0).getPosterAnswer3().equals(content) || posterQuestionDetailsList.get(0).getPosterAnswer4().equals(content) || ("1").equals(content) || ("2").equals(content) || ("3").equals(content) || ("4").equals(content)) {

                                    posterQuestionDetailsExample.clear();
                                    posterQuestionDetailsExample.createCriteria().andQuestionIdEqualTo(prevQuestion).andQuestionSortEqualTo(prevQuestionSort + 1);
                                    posterQuestionDetailsList = posterQuestionDetailsMapper.selectByExample(posterQuestionDetailsExample);


                                    if (null != posterQuestionDetailsList && !posterQuestionDetailsList.isEmpty()) {
                                        int k = 0;
                                        String question = posterQuestionDetailsList.get(0).getPosterQuestion() + "\n";

                                        if (posterQuestionDetailsList.get(0).getPosterAnswer1() != null && !posterQuestionDetailsList.get(0).getPosterAnswer1().isEmpty()) {
                                            k++;
                                            question += "<a href=\"weixin://bizmsgmenu?msgmenucontent=" + posterQuestionDetailsList.get(0).getPosterAnswer1() + "&msgmenuid=10" + k + "\">" + k + "." + posterQuestionDetailsList.get(0).getPosterAnswer1() + "</a>\n";
                                        }
                                        if (posterQuestionDetailsList.get(0).getPosterAnswer2() != null && !posterQuestionDetailsList.get(0).getPosterAnswer2().isEmpty()) {
                                            k++;
                                            question += "<a href=\"weixin://bizmsgmenu?msgmenucontent=" + posterQuestionDetailsList.get(0).getPosterAnswer2() + "&msgmenuid=10" + k + "\">" + k + "." + posterQuestionDetailsList.get(0).getPosterAnswer2() + "</a>\n";
                                        }
                                        if (posterQuestionDetailsList.get(0).getPosterAnswer3() != null && !posterQuestionDetailsList.get(0).getPosterAnswer3().isEmpty()) {
                                            k++;
                                            question += "<a href=\"weixin://bizmsgmenu?msgmenucontent=" + posterQuestionDetailsList.get(0).getPosterAnswer3() + "&msgmenuid=10" + k + "\">" + k + "." + posterQuestionDetailsList.get(0).getPosterAnswer3() + "</a>\n";
                                        }
                                        if (posterQuestionDetailsList.get(0).getPosterAnswer4() != null && !posterQuestionDetailsList.get(0).getPosterAnswer4().isEmpty()) {
                                            k++;
                                            question += "<a href=\"weixin://bizmsgmenu?msgmenucontent=" + posterQuestionDetailsList.get(0).getPosterAnswer4() + "&msgmenuid=10" + k + "\">" + k + "." + posterQuestionDetailsList.get(0).getPosterAnswer4() + "</a>\n";
                                        }

                                        text.setContent(question);
                                        textMsgExample.clear();
                                        textMsgExample.createCriteria().andQuestionEqualTo(prevQuestion).andOpenidEqualTo(fromUserName);
                                        TextMsg textMsg = new TextMsg();
                                        textMsg.setQuestionsort(prevQuestionSort + 1);
                                        textMsgMapper.updateByExampleSelective(textMsg, textMsgExample);
                                    } else {

                                        text.setContent(posterImg(fromUserName, astoken, textMsgList1.get(textMsgList1.size() - 1).getActivity()));

                                        textMsgExample.clear();
                                        textMsgExample.createCriteria().andQuestionEqualTo(prevQuestion).andOpenidEqualTo(fromUserName);
                                        TextMsg textMsg = new TextMsg();
                                        textMsgMapper.deleteByExample(textMsgExample);

                                    }

                                } else {
                                        text.setContent("请选择上面的选项");

                                }
                        }else{
                               //都没有则查询默认回复
                                WPGzhfExample wpGzhfExample = new WPGzhfExample();
                                wpGzhfExample.createCriteria().andAccountidEqualTo(WxConstants.accountid).andStypeEqualTo("0");
                                List<WPGzhf> hf = wpGzhfMapper.selectByExample(wpGzhfExample);

                                if (null != hf && !hf.isEmpty()) {
                                    if (null == hf.get(0)) {

                                    } else if (null != hf.get(0).getNewsid() && !"".equals(hf.get(0).getNewsid())) {

                                    } else {
                                        if (hf.get(0).getTextmsg() == null) {
                                            return  getNewsReply(fromUserName, toUserName, accountid, key.getNewSid());

                                        } else {
                                            text.setContent(hf.get(0).getTextmsg());

                                        }
                                    }
                                }

                            }

                       }

                    }

                    else{
                        WPGzhfExample wpGzhfExample=new  WPGzhfExample();
                        wpGzhfExample.createCriteria().andAccountidEqualTo(WxConstants.accountid).andStypeEqualTo("0");
                        List<WPGzhf> hf=wpGzhfMapper.selectByExample(wpGzhfExample);


                        if(null != hf && !hf.isEmpty()){
                            if(null==hf.get(0)){

                            }else if(null!=hf.get(0).getNewsid()&&!"".equals(hf.get(0).getNewsid())){

                                return  getNewsReply(fromUserName, toUserName, accountid, hf.get(0).getNewsid());
                            }else{

                                    text.setContent(hf.get(0).getTextmsg());


                            }
                        }
                    }s
                    }s


                text.setToUserName(fromUserName);
                text.setFromUserName(toUserName);
                text.setCreateTime(new Date().getTime() + "");
                text.setMsgType(msgType);



               if(text.getContent()!=null&&text.getContent()!=""){
                respMessage = MessageUtil.textMessageToXml(text);
                }


            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {// 事件推送
                String eventType = requestMap.get("Event");// 事件类型

                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 订阅
                    String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应

                    WPFans fans=new WPFans();
                    List<WPFans> fansList=new ArrayList<>();
                    WPFansExample wpFansExample=new WPFansExample();
                    wpFansExample.createCriteria().andOpenidEqualTo(fromUserName);
                    fansList=wpFansMapper.selectByExample(wpFansExample);

                    String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+astoken+"&openid="+fromUserName+"&lang=zh_CN";

                    JSONObject jsonObject= new JSONObject();

                    jsonObject=WxUtil.httpRequest(url,"GET",null);


                    if(null!=jsonObject&&!jsonObject.isEmpty()) {
                        if(jsonObject.containsKey("errcode")){
                            return null;
                        }else{
                            fans.setHeadUrl(jsonObject.getString("headimgurl"));
                            fans.setNickname(jsonObject.getString("nickname"));
                        }
                    }

                    if(null !=fansList&&fansList.size()>0){
                        fans.setSubscribe(1);
                        fans.setSubtime(new Date());
                        wpFansMapper.updateByExampleSelective(fans,wpFansExample);
                    }else{
                        fans.setAccountid(WxConstants.accountid);
                        fans.setOpenid(fromUserName);
                        fans.setSubscribe(1);
                        fans.setSubtime(new Date());
                        wpFansMapper.insert(fans);
                    }


                    if (eventKey.contains("poster")) {

                        ActivityParticipant activityParticipant=new ActivityParticipant();
                        String[] str=eventKey.replace("qrscene_","").split("poster");
                        Integer activity=Integer.parseInt(str[0]);
                        String invateOpenid=str[1];
                        fans.setInvateOpenid(invateOpenid);
                        fans.setActivityid(activity);
                        activityParticipant.setActivityid(activity);
                        activityParticipant.setCreatetime(new Date());
                        activityParticipant.setInvateOpenid(invateOpenid);
                        activityParticipant.setOpenid(fromUserName);
                        activityParticipant.setStype("poster");
                        wpFansMapper.updateByExampleSelective(fans,wpFansExample);
                        return posterMethod(fromUserName,astoken,activity,activityParticipant);

                    }else if(eventKey.contains("draw")){
                        ActivityParticipant activityParticipant=new ActivityParticipant();
                        String[] str=eventKey.replace("qrscene_","").split("draw");
                        Integer activity=Integer.parseInt(str[0]);
                        String invateOpenid=str[1];
                        fans.setInvateOpenid(invateOpenid);
                        fans.setActivityid(activity);
                        activityParticipant.setActivityid(activity);
                        activityParticipant.setCreatetime(new Date());
                        activityParticipant.setInvateOpenid(invateOpenid);
                        activityParticipant.setOpenid(fromUserName);
                        activityParticipant.setStype("draw");
                        wpFansMapper.updateByExampleSelective(fans,wpFansExample);
                        activityParticipantMapper.insert(activityParticipant);
                        DrawActivity drawActivity=drawActivityMapper.selectByPrimaryKey(activity);
                        if(drawActivity==null){
                            TextMessage text = new TextMessage();
                            text.setToUserName(fromUserName);
                            text.setFromUserName(toUserName);
                            text.setCreateTime(new Date().getTime() + "");
                            text.setContent(fans.getNickname()+",活动已经结束了");
                            respMessage = MessageUtil.textMessageToXml(text);
                        }else{
                            List<Article> arts= new ArrayList<Article>();
                            Article art=new Article();
                            art.setDescription("快来抽奖吧");
                            art.setUrl(WeiXinOpenConstants.WEB_OAUTH_URL.replace("APPID", WxConstants.appid).replace("REDIRECT_URI", URLEncoder.encode(WeiXinOpenConstants.DRAW_URL.replace("SID",activity.toString()), "UTF-8")).replace("SCOPE", WeiXinOpenConstants.SNSAPI_BASE));
                            art.setTitle(drawActivity.getSname()+"正在等着你~");
                            art.setPicUrl(WxConstants.domain+drawActivity.getPicurl());
                            arts.add(art);
                            NewsMessage newsMsg=new NewsMessage();
                            newsMsg.setToUserName(fromUserName);
                            newsMsg.setFromUserName(toUserName);
                            newsMsg.setCreateTime(new Date().getTime());
                            newsMsg.setArticleCount(1);
                            newsMsg.setArticles(arts);
                            newsMsg.setMsgType("news");
                            return MessageUtil.newsMessageToXml(newsMsg);
                        }



                    }
                    else{
                        TextMessage text = new TextMessage();
                        text.setToUserName(fromUserName);
                        text.setFromUserName(toUserName);

                        text.setCreateTime(new Date().getTime() + "");


                        WPGzhfExample wpGzhfExample=new  WPGzhfExample();//(W_p_gzhf)dao.getFirstObjectByHql("  from w_p_gzhf where accountid='"+accountid+"' and stype='默认回复' ");
                        wpGzhfExample.createCriteria().andAccountidEqualTo(WxConstants.accountid).andStypeEqualTo("关注回复");

                        List<WPGzhf> hf=wpGzhfMapper.selectByExample(wpGzhfExample);

                        //if(hf.size()>0){
                        if(null != hf && !hf.isEmpty()){
                            if(hf.get(0).getType().equals("text")){
                                text.setMsgType("text");
                                text.setContent(hf.get(0).getTextmsg().replace("xxx",jsonObject.getString("nickname")));

                            }else if(hf.get(0).getType().equals("news")){


                            }else{

                            }
                        }


                        respMessage = MessageUtil.textMessageToXml(text);
                    }




                } else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅
                    WPFans fans=new WPFans();
                    List<WPFans> fansList=new ArrayList<>();
                    WPFansExample wpFansExample=new WPFansExample();
                    wpFansExample.createCriteria().andOpenidEqualTo(fromUserName);
                    fansList=wpFansMapper.selectByExample(wpFansExample);
                    if(fansList.size()>0){
                        fans.setSubscribe(0);
                        wpFansMapper.updateByExampleSelective(fans,wpFansExample);
                    }
                }else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
                    String menukey = requestMap.get("EventKey");
                    TextMessage text = new TextMessage();
                    W_p_menu menu=menuMapper.getMenuByKey(menukey);
                    W_p_key key=keyMapper.getById(Integer.parseInt(menu.getKeyid()));

                    if(key.getType().equals("text")){
                        text.setContent(key.getTextMsg());
                    }else if(key.getType().equals("poster")){
                        ActivityParticipant activityParticipant=new ActivityParticipant();
                        activityParticipant.setActivityid(key.getPosterActivityId());
                        activityParticipant.setCreatetime(new Date());
                        activityParticipant.setOpenid(fromUserName);
                        activityParticipant.setStype("poster");
                        return posterMethod(fromUserName,astoken,key.getPosterActivityId(),activityParticipant);

                    }
                    else{
                        return  getNewsReply(fromUserName, toUserName, accountid, key.getNewSid());

                    }

                    text.setToUserName(fromUserName);
                    text.setFromUserName(toUserName);

                    text.setCreateTime(new Date().getTime() + "");
                    //respMessage = MessageUtil.textMessageToXml(text);

                    if(text.getContent()!=null&&text.getContent()!=""){
                        respMessage = MessageUtil.textMessageToXml(text);
                    }

                }


            }
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage()+"9999");
        }
        return respMessage;
    }


    /**
      * 发送客服消息
      * @param openId 要发给的用户
      * @param accessToken 微信公众号token
      * @param weixinAppId 微信公众号APPID
      */
    private   void  sendCustomMessage(String openId,String accessToken,Poster poster,Integer activityId,List<PosterQuestionDetails> posterQuestionDetailsList){
        try {
            RestTemplate rest = new RestTemplate();
            String postUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken;
            //推送图文消息
            WPFansExample wpFansExample=new WPFansExample();
            wpFansExample.createCriteria().andOpenidEqualTo(openId);
            List<WPFans> wpFansList = wpFansMapper.selectByExample(wpFansExample);
            boolean ismessage=false;
            boolean isquestion=false;
            if(poster.getMsg()!=null&&poster.getMsg()!=""){
                Message message = new Message();
                message.setTouser(openId);//普通用户openid
                message.setMsgtype("text");
                TextContent content=new TextContent();
                content.setContent(poster.getMsg().replace("xxx",wpFansList.get(0).getNickname()));
                message.setText(content);

                HttpHeaders headers = new HttpHeaders();
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);
                headers.add("Accept", MediaType.APPLICATION_JSON.toString());
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("touser",message.getTouser() );
                jsonObj.put("msgtype",message.getMsgtype());
                jsonObj.put("text",content);
                HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);

                WeixinResponse response2 = rest.postForObject(postUrl, formEntity, WeixinResponse.class, new HashMap<String,String>());

                    if(response2.getErrcode()==0){//发送成功-退出循环发送
                        isquestion=true;
                    }


            }else{
                isquestion=true;
            }

            if(isquestion&&null!=posterQuestionDetailsList&&!posterQuestionDetailsList.isEmpty()){

                TextContent content=new TextContent();

                if(posterQuestionDetailsList.size()>1){

                }else{
                    ismessage=true;
                }
                int k=0;
                String question=posterQuestionDetailsList.get(0).getPosterQuestion().replace("xxx",wpFansList.get(0).getNickname())+"\n";
                if(posterQuestionDetailsList.get(0).getPosterAnswer1()!=null&&!posterQuestionDetailsList.get(0).getPosterAnswer1().isEmpty()){
                    k++;
                    question+="<a href=\"weixin://bizmsgmenu?msgmenucontent="+posterQuestionDetailsList.get(0).getPosterAnswer1()+"&msgmenuid=10"+k+"\">"+k+"."+posterQuestionDetailsList.get(0).getPosterAnswer1()+"</a>\n";
                }
                if(posterQuestionDetailsList.get(0).getPosterAnswer2()!=null&&!posterQuestionDetailsList.get(0).getPosterAnswer2().isEmpty()){
                    k++;
                    question+="<a href=\"weixin://bizmsgmenu?msgmenucontent="+posterQuestionDetailsList.get(0).getPosterAnswer2()+"&msgmenuid=10"+k+"\">"+k+"."+posterQuestionDetailsList.get(0).getPosterAnswer2()+"</a>\n";
                }
                if(posterQuestionDetailsList.get(0).getPosterAnswer3()!=null&&!posterQuestionDetailsList.get(0).getPosterAnswer3().isEmpty()){
                    k++;
                    question+="<a href=\"weixin://bizmsgmenu?msgmenucontent="+posterQuestionDetailsList.get(0).getPosterAnswer3()+"&msgmenuid=10"+k+"\">"+k+"."+posterQuestionDetailsList.get(0).getPosterAnswer3()+"</a>\n";
                }
                if(posterQuestionDetailsList.get(0).getPosterAnswer4()!=null&&!posterQuestionDetailsList.get(0).getPosterAnswer4().isEmpty()){
                    k++;
                    question+="<a href=\"weixin://bizmsgmenu?msgmenucontent="+posterQuestionDetailsList.get(0).getPosterAnswer4()+"&msgmenuid=10"+k+"\">"+k+"."+posterQuestionDetailsList.get(0).getPosterAnswer4()+"</a>\n";
                }


                content.setContent(question);

                HttpHeaders headers = new HttpHeaders();
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);
                headers.add("Accept", MediaType.APPLICATION_JSON.toString());
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("touser",openId );
                jsonObj.put("msgtype","text");
                jsonObj.put("text",content);
                HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);

                WeixinResponse response2 = rest.postForObject(postUrl, formEntity, WeixinResponse.class, new HashMap<String,String>());

                if(response2.getErrcode()==0){//发送成功-退出循环发送

                    TextMsg textMsg=new TextMsg();
                    textMsg.setCreatetime(new Date());
                    textMsg.setQuestion(posterQuestionDetailsList.get(0).getQuestionId());
                    textMsg.setQuestionsort(1);
                    textMsg.setOpenid(openId);
                    textMsg.setActivity(activityId);
                    textMsgMapper.insert(textMsg);


                }
            }else{
                ismessage=true;
            }






            if(ismessage) {

                String userpath = wpFansList.get(0).getHeadUrl();
                String qrcodeimg=wechatqrcode.getQRcode(accessToken,openId,activityId);
                String imgurl=wechatqrcode.showQrcode(userpath,qrcodeimg,openId,poster);
                String media_id=wechatqrcode.uploadQRcode(accessToken,imgurl);
                Message message1 = new Message();
                message1.setTouser(openId);//普通用户openid
                message1.setMsgtype("image");
                MediaContent content1 = new MediaContent();

                content1.setMedia_id(media_id);
                message1.setImage(content1);

                WeixinResponse response1 = rest.postForObject(postUrl, message1, WeixinResponse.class, new HashMap<String, String>());


            }


        } catch (Exception e) {
            //LOG.error("发送客服消息失败,openId="+openId,e);
            e.printStackTrace();
            logger.info(e.getMessage()+"888");
        }
}



        public  String posterMethod(String fromUserName,String astoken,Integer activityId,ActivityParticipant activityParticipant){

           Thread insertThread = new Thread(new Runnable() {
               @Override
               public void run() {
                   activityParticipantMapper.insert(activityParticipant);
                   PosterActivity posterActivities=posterActivityMapper.selectByPrimaryKey(activityId);
                   Poster poster=posterMapper.selectByPrimaryKey(posterActivities.getPosterId());
                   List<PosterQuestionDetails> posterQuestionDetailsList=null;
                   if(null!=posterActivities.getQuestionId()){
                       PosterQuestionDetailsExample posterQuestionDetailsExample=new PosterQuestionDetailsExample();
                       posterQuestionDetailsExample.createCriteria().andQuestionIdEqualTo(posterActivities.getQuestionId());
                       posterQuestionDetailsExample.setOrderByClause("question_sort");
                       posterQuestionDetailsList=posterQuestionDetailsMapper.selectByExample(posterQuestionDetailsExample);
                   }
                   sendCustomMessage(fromUserName,astoken,poster,activityId,posterQuestionDetailsList);
               }
           });

           insertThread.start();
           //避免超时时微信重新请求
           return "";


        }


    public  String posterImg(String fromUserName,String accessToken,Integer activityId ){

        Thread inThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                RestTemplate rest = new RestTemplate();
                String postUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken;

                PosterActivity posterActivities=posterActivityMapper.selectByPrimaryKey(activityId);
                Poster poster=posterMapper.selectByPrimaryKey(posterActivities.getPosterId());

                WPFansExample wpFansExample=new WPFansExample();
                wpFansExample.createCriteria().andOpenidEqualTo(fromUserName);
                List<WPFans> wpFansList = wpFansMapper.selectByExample(wpFansExample);
                String userpath = wpFansList.get(0).getHeadUrl();
                String qrcodeimg=wechatqrcode.getQRcode(accessToken,fromUserName,activityId);
                String imgurl= null;
                imgurl = wechatqrcode.showQrcode(userpath,qrcodeimg,fromUserName,poster);
                String media_id=wechatqrcode.uploadQRcode(accessToken,imgurl);
                Message message1 = new Message();
                message1.setTouser(fromUserName);//普通用户openid
                message1.setMsgtype("image");
                MediaContent content1 = new MediaContent();

                content1.setMedia_id(media_id);
                message1.setImage(content1);

                WeixinResponse response1 = rest.postForObject(postUrl, message1, WeixinResponse.class, new HashMap<String, String>());


                } catch (IOException e) {
                    e.printStackTrace();
                    logger.info(e.getMessage());
                }

            }
        });

        inThread.start();
        //避免超时时微信重新请求
        return "正在为你生成专属海报哦~";


    }



    public String getNewsReply(String fromUserName ,String toUserName,String accountid,String newsid ){
        List<News> item=newsMapper.getByNewsId(newsid,accountid);
        List<Article> arts= new ArrayList<Article>();
        int ii=0;
        for(News i:item){
            Article art=new Article();
            art.setDescription(i.getDescription());
            art.setPicUrl(WxConstants.domain +i.getPicUrl().replace('\\', '/'));
            art.setTitle(i.getTitle());
            art.setUrl(i.getUrl());
            if(i.getUrlType().equals("1")){
                art.setUrl(WxConstants.domain + "draw/getNews?sid=" + i.getSid());
            }
            arts.add(art);
            if(ii>0){
                art.setPicUrl(WxConstants.domain +i.getSmallPic().replace('\\', '/'));
            }
            ii++;
        }
        if(item.size()==0){
            return "";
        }else{
            NewsMessage newsMsg=new NewsMessage();
            newsMsg.setToUserName(fromUserName);
            newsMsg.setFromUserName(toUserName);
            newsMsg.setCreateTime(new Date().getTime());
            newsMsg.setArticleCount(item.size());
            newsMsg.setArticles(arts);
            newsMsg.setMsgType("news");
            return MessageUtil.newsMessageToXml(newsMsg);
        }



    }






}

package com.yuyuedao.yydwechat.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;

import javax.annotation.Resource;

import com.yuyuedao.yydwechat.entity.Auth_args;
import com.yuyuedao.yydwechat.mapper.WxUtilMapper;
import net.sf.json.JSONObject;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/wechat")
public class AuthController extends PublicMethod{


	@Resource
	private WxUtilMapper wxUtilMapper;
	
//	@RequestMapping(value = "auth.do", method = RequestMethod.GET)
//	public void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException, ParseException {
//
//			Map<String,Object> data=new HashMap<String,Object>();
//			data=getPreAuthCode();
//			if((boolean) data.get("status")){
//				String pre_auth_code =(String) data.get("data");
//				   JSONObject json = new JSONObject();
//				    json.accumulate("component_appid",WxConstants.appid);
//				    json.accumulate("authorization_code",pre_auth_code);
//				    String url="https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid="
//				    +WxConstants.appid+"&pre_auth_code="+pre_auth_code+"&redirect_uri="+WxConstants.redirect_uri;
//				    try {
//						resp.sendRedirect(url);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//			}else{
//				System.out.println( data.get("mesg"));
//			}
//
//
//
//	}
//
//
//	public Map<String,Object> getPreAuthCode() throws ParseException{
//		Map<String,Object> rmap=new HashMap<String,Object>();
//		Map<String,Object> data=getAuthToken();
//
//		if((boolean) data.get("status")){
//			JSONObject json = new JSONObject();
//			json.accumulate("component_appid",WxConstants.appid);
//			String pre_auth_code=HttpUtil.sendPostUrl(WxConstants.pre_code_url+data.get("data"), json.toString(), "UTF-8");;
//			JSONObject code=JSONObject.fromObject(pre_auth_code);
//			if(code.containsKey("errcode")){
//				rmap.put("status", false);
//				rmap.put("mesg", "获取pre_auth_code："+code.getString("errcode")+":"+code.getString("errmsg"));
//			}else{
//				rmap.put("status", true);
//				rmap.put("data", code.getString("pre_auth_code"));
//			}
//		}else{
//			rmap=data;
//		}
//
//		return rmap;
//
//	}
//
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "template.do", method = RequestMethod.GET)
//	@ResponseBody
//	public Map<String,Object> setTemplatMessage() throws IOException, ParseException{
//		String accountId=getDataBySession(IUserConstants.accountid);
//		int sz=Integer.parseInt(dao.getSeqBySql("select count(*) from w_mb_shortid where accountid='"+accountId+"' "));
//		Map<String, Object> rmap=null;
//		if(sz<=0){
//			IndustryDto it=new IndustryDto("31","1");
//			JSONObject json = JSONObject.fromObject(it);
//			rmap = messageDeal.industrySet(json.toString(), accountId);
//		}
//		List<String> list=dao.queryAllBySql("select distinct shortid from w_mb_shortid where accountid='system' ");
//		List<String> listname=dao.queryAllBySql("select distinct sname from w_mb_shortid where accountid='system' ");
//
//		rmap =messageDeal.get_template(accountId);
//		if((boolean) rmap.get("status")){
//			JSONArray tlist = JSONArray.fromObject(rmap.get("template_list").toString());
//			if(tlist.size()>0){
//				for(int i=0;i<tlist.size();i++){
//
//					JSONObject temp = tlist.getJSONObject(i);
//					if(listname.contains(temp.getString("title"))){
//						int exist=Integer.parseInt(dao.getSeqBySql("select count(*) from w_mb_shortid where accountid='"+accountId+"' and sname='"+temp.getString("title")+"'  "));
//						if(exist==0){
//							dao.executeSql(" insert into w_mb_shortid(shortid,sname,industry,template_id,accountid)select shortid,sname,industry,'"+temp.getString("template_id")+"' template_id,'"+accountId+"'accountid from w_mb_shortid where accountid='system' and sname='"+temp.getString("title")+"'  ");
//							}else{
//							dao.executeSql(" update w_mb_shortid set template_id='"+temp.get("template_id")+"' where  accountid='"+accountId+"' and sname ='"+temp.get("title")+"' ");
//
//						}
//
//						listname.remove(temp.getString("title"));
//					}
//					if(listname.size()==0){
//						break;
//					}
//
//				}
//			}
//			for(int i=0;i<listname.size();i++){
//				ShortidDto st=new ShortidDto(list.get(i));
//				Map<String,Object> rmap2=messageDeal.add_template(JSONObject.fromObject(st).toString(), accountId);
//
//				if((boolean) rmap2.get("status")){
//					dao.executeSql(" insert into w_mb_shortid(shortid,sname,industry,template_id,accountid)select shortid,sname,industry,'"+rmap2.get("template_id")+"' template_id,'"+accountId+"'accountid from w_mb_shortid where accountid='system' and shortid='"+list.get(i)+"' ");
//				}
//				rmap=rmap2;
//			}
//
//		}
//
//
//
//		/*else{
//			List<String> list=dao.queryAllBySql("select distinct shortid from w_mb_shortid where accountid='system' ");
//
//			Map<String, Object> rmap1=null;
//			rmap1 =messageDeal.get_template(accountId);
//			rmap1.get("template_list");
//			JSONArray tlist = JSONArray.fromObject(rmap1.toString());
//			if(tlist.size()>0){
//				for(int i=0;i<tlist.size();i++){
//					System.out.println("list:"+list.size());
//					JSONObject temp = tlist.getJSONObject(i);
//					if(list.contains(temp.getString("title"))){
//						dao.executeSql(" update w_mb_shortid set template_id='"+temp.get("template_id")+"' where  accountid='"+accountId+"' and sname ='"+temp.get("title")+"' ");
//						list.remove(temp.getString("title"));
//					}
//
//
//				}
//			}
//
//
//
//		}*/
//
//		return rmap;
//
//
//	}
	public Map<String,Object>  getAuthToken() throws ParseException{
		Map<String,Object> rmap=new HashMap<String,Object>();
		Auth_args args=wxUtilMapper.selectArgs();

		String auth_token=args.getAuthToken();//dao.getSeqBySql("select auth_token from auth_args");
		String token_time=args.getAuthTokenTime().toString();//dao.getSeqBySql("select auth_token_time from auth_args");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date end = new Date();
		Date date=new Date(args.getAuthTokenTime().getTime());


		if (auth_token == null || "".equals(auth_token)||(end.getTime() - date.getTime()) / 1000 / 3600 >= 2){
			JSONObject json = new JSONObject();
		    json.accumulate("component_appid",WxConstants.appid);
		    json.accumulate("component_appsecret",WxConstants.appsecret);
		    json.accumulate("component_verify_ticket",args.getAuthTicket());
			String component_access_token=HttpUtil.sendPostUrl(WxConstants.token_url, json.toString(), "UTF-8");
			System.out.println("component_access_token::"+component_access_token);
			JSONObject token=JSONObject.fromObject(component_access_token);
			if(token.containsKey("errcode")){
				rmap.put("status", false);
				rmap.put("mesg", "获取component_access_token："+token.getString("errcode")+":"+token.getString("errmsg"));
			}else{
				//dao.executeSql(" update auth_args set auth_token='"+token.getString("component_access_token")+"',AUTH_TOKEN_TIME=getdate()  where component_appid='"+WxConstants.appid+"' ") ;
				wxUtilMapper.updateArgs(token.getString("component_access_token"),WxConstants.appid);
				rmap.put("status", true);
				rmap.put("data",token.getString("component_access_token"));
			}

		}else{
			rmap.put("status", true);
			rmap.put("data",auth_token);
		}
		return rmap;
	}
//
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "oldtemplate.do", method = RequestMethod.GET)
//	@ResponseBody
//	public Map<String,Object> oldsetTemplatMessage() throws IOException, ParseException{
//
//		String accountId=getDataBySession(IUserConstants.accountid);
//		int sz=Integer.parseInt(dao.getSeqBySql("select count(*) from w_mb_shortid where accountid='"+accountId+"' "));
//		Map<String, Object> rmap=null;
//		System.out.println("sz:"+sz);
//		if(sz<=0){
//			IndustryDto it=new IndustryDto("31","1");
//			JSONObject json = JSONObject.fromObject(it);
//
//			rmap = messageDeal.industrySet(json.toString(), accountId);
//			List<String> list=dao.queryAllBySql("select distinct shortid from w_mb_shortid where accountid='system' ");
//
//			for(int i=0;i<list.size();i++){
//				ShortidDto st=new ShortidDto(list.get(i));
//				Map<String,Object> rmap2=messageDeal.add_template(JSONObject.fromObject(st).toString(), accountId);
//
//				if((boolean) rmap2.get("status")){
//					dao.executeSql(" insert into w_mb_shortid(shortid,sname,industry,template_id,accountid)select shortid,sname,industry,'"+rmap2.get("template_id")+"' template_id,'"+accountId+"'accountid from w_mb_shortid where accountid='system' and shortid='"+list.get(i)+"' ");
//				}
//				rmap=rmap2;
//			}
//
//		}else{
//			List<String> list=dao.queryAllBySql("select distinct shortid from w_mb_shortid where accountid='system' ");
//			for(int i=0;i<list.size();i++){
//				ShortidDto st=new ShortidDto(list.get(i));
//				Map<String,Object> rmap2=messageDeal.add_template(JSONObject.fromObject(st).toString(), accountId);
//				if((boolean) rmap2.get("status")){
//					//dao.executeSql(" insert into w_mb_shortid(shortid,sname,industry,template_id,accountid)select shortid,sname,industry,'"+rmap2.get("template_id")+"' template_id,'"+accountId+"'accountid from w_mb_shortid where accountid='system' and shortid='"+list.get(i)+"' ");
//					dao.executeSql(" update w_mb_shortid set template_id='"+rmap2.get("template_id")+"' where  accountid='"+accountId+"' and shortid ='"+list.get(i)+"' ");
//				}
//				rmap=rmap2;
//			}
//
//
//		}
//
//		return rmap;
//
//
//	}
//	@SuppressWarnings({ "unchecked" })
//	@RequestMapping(value = "/gridlist.do", method = RequestMethod.GET)
//	@ResponseBody
//	public  Map<String,Object> gridlist() {
//		Map<String,Object> rmap=new HashMap<String,Object>();
//		List<Map<String,String>> returnMap =dao.queryMapAllBySql("select sid,mchid,mchsecret, authorizer_appid,authorizer_access_token,authorizer_refresh_token," +
//				" sm,account_name,head_img,qrcode_url,ysid,principal_name  from a_account where accountid='"+getDataBySession(IUserConstants.accountid)+"'");
//		rmap.put("data",returnMap);
//		int sz=Integer.parseInt(dao.getSeqBySql("select count(*) from w_mb_shortid where accountid='"+getDataBySession(IUserConstants.accountid)+"' "));
//		if(sz>0||returnMap.size()<0){
//			rmap.put("sz", true);
//		}else{
//			rmap.put("sz", false);
//		}
//		rmap.put("status", true);
//		return rmap;
//	}
//
//
//	@RequestMapping(value = "/save.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Map<String,Object> save(RequestDto dto) {
//		Map<String,Object> rmap=new HashMap<String,Object>();
//		JSONObject parms=JSONObject.fromObject(dto.getParms());
//		dao.executeSql(" update a_account set mchid='"+parms.get("mchid")+"' , mchsecret='"+parms.get("mchsecret")+"'  where sid='"+parms.get("sid")+"' ");
//		rmap.put("status", true);
//		return rmap;
//	}
//
}


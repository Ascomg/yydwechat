package com.yuyuedao.yydwechat.util;


import com.yuyuedao.yydwechat.entity.A_account;
import com.yuyuedao.yydwechat.mapper.WxUtilMapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;


@Service("weixinAccountService")
@Transactional
public class WeixinAccountService extends PublicMethod{
//	@Autowired
//	private BaseDao dao;
	@Autowired
	private AuthController auth;

	@Resource
	private WxUtilMapper wxUtilMapper;
	
	public String getAccessToken() throws ParseException {

		A_account account =wxUtilMapper.getAccountInfo(getDataBySession(IUserConstants.accountid));// (A_account)dao.getFirstObjectByHql("from a_account where accountid='"+getDataBySession(IUserConstants.accountid)+"'");
		String token =null;
		if(account==null){

		}else{

			token= account.getAuthorizerAccessToken();//getAuthorizer_access_token();
			Date end=new Date();
			Date start = new Date(account.getAuthorizerTokenTime().getTime());

			if (token == null || "".equals(token)||(end.getTime() - start.getTime()) / 1000 / 3000 >= 2) {

				JSONObject json4 = new JSONObject();
			    json4.accumulate("component_appid",WxConstants.appid);
			    json4.accumulate("authorizer_appid",account.getAuthorizerAppid());//上一步获取token的时候获取
			    json4.accumulate("authorizer_refresh_token", account.getAuthorizerRefreshToken());

				Map<String,Object> component= auth.getAuthToken();
			    if((boolean) component.get("status")){
			    	String access_token=HttpUtil.sendPostUrl(WxConstants.refresh_token_url+component.get("data"), json4.toString(), "UTF-8");
				    JSONObject jsonObject=JSONObject.fromObject(access_token);
				   if(jsonObject.containsKey("errcode")){
					   token = null;
					   System.out.println(jsonObject.get("errcode").toString()+jsonObject.get("errmsg"));
				   }else{
//						dao.executeSql(" update a_account set authorizer_access_token='"+jsonObject.getString("authorizer_access_token")+"'," +
//								"  authorizer_refresh_token='"+jsonObject.getString("authorizer_refresh_token")+"' ," +
//								" AUTHORIZER_TOKEN_TIME=getdate() where sid='"+account.getSid()+"' ");

					   A_account accounts=new A_account();
					   accounts.setAuthorizerAccessToken(jsonObject.getString("authorizer_access_token"));
					   accounts.setAuthorizerRefreshToken(jsonObject.getString("authorizer_refresh_token"));
					   accounts.setSid(account.getSid());
					   accounts.setAuthorizerTokenTime(new Date());
//                    dao.executeSql(" update a_account set authorizer_access_token='"+jsonObject.getString("authorizer_access_token")+"'," +
//                            "  authorizer_refresh_token='"+jsonObject.getString("authorizer_refresh_token")+"' ," +
//                            " AUTHORIZER_TOKEN_TIME=getdate() where sid='"+account.getSid()+"' ");
					   wxUtilMapper.updateAccount(accounts);


						token=jsonObject.getString("authorizer_access_token");
				   }
			    }else{
			    	token = null;
			    	System.out.println(component.get("mesg"));
			    }
			}

		}

		return token;
	}


//	public Map<String,Object> getAccessToken(String accountId) throws ParseException {
//		Map<String,Object> rmap=new HashMap<String,Object>();
//
//		A_account account =
//		String token = account.getAuthorizer_access_token();
//		Date end=dateclass.getNowTime();
//		Date start = new Date(account.getAuthorizer_token_time().getTime());
//		rmap.put("token", token);
//	    rmap.put("status", true);
//		if (token == null || "".equals(token)||(end.getTime() - start.getTime()) / 1000 / 3000 >= 2) {
//			JSONObject json4 = new JSONObject();
//		    json4.accumulate("component_appid",WxConstants.appid);
//		    json4.accumulate("authorizer_appid",account.getAuthorizer_appid());//上一步获取token的时候获取
//		    json4.accumulate("authorizer_refresh_token", account.getAuthorizer_refresh_token());
//		    Map<String,Object> component= auth.getAuthToken();
//		    if((boolean) component.get("status")){
//		    	String access_token=HttpUtil.sendPostUrl(WxConstants.refresh_token_url+component.get("data"), json4.toString(), "UTF-8");
//			    JSONObject jsonObject=JSONObject.fromObject(access_token);
//			    System.out.println("access_token:::"+access_token);
//			   if(jsonObject.containsKey("errcode")){
//				   token = null;
//				   rmap.put("mesg", "获取token:"+jsonObject.get("errcode").toString()+jsonObject.get("errmsg"));
//				   rmap.put("token", token);
//				   rmap.put("status", false);
//			   }else{
//
//				   token=jsonObject.getString("authorizer_access_token");
//					dao.executeSql(" update a_account set authorizer_access_token='"+jsonObject.getString("authorizer_access_token")+"'," +
//							"  authorizer_refresh_token='"+jsonObject.getString("authorizer_refresh_token")+"' ," +
//							" AUTHORIZER_TOKEN_TIME=getdate() where sid='"+account.getSid()+"' ");
//
//				   rmap.put("token", token);
//				   rmap.put("status", true);
//			   }
//		    }else{
//		    	rmap=component;
//		    }
//		}
//		return rmap;
//	}

}
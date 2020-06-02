package com.yuyuedao.yydwechat.util;

public class WeiXinOpenConstants {

	public static final String WEIXIN_ACCOUNT = "WEIXIN_ACCOUNT";
	public static final String ACCOUNTID = "accountid";
	public static final String FXOPENID = "fxopenid";
	public static final String USER_OPENID = "USER_OPENID";
	public static final String MASTERID = "MASTERID";
	public static final String targetUrl = "targetUrl";
	public static final String SHOP_DATABASE = "database";

	public static final String SHOP_MEND = "mend";
	public static final String FANS = "FANS";

	public static final String SUCAI_SHARE_STATUS = "Y";	//素材状态，共享和非共享
	public static final String SUCAI_UNSHARE_STATUS = "N";

	public static final String SNSAPI_USERINFO = "snsapi_userinfo";	//弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息

	public static final String SNSAPI_BASE = "snsapi_base";

 	public final static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    public static String WEB_OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    public static String WEB_OAUTH_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"; 
  
    //public static String domain="http://wx.51jdwy.cn/zrwechat/"; //http://www.szzsofti.com/zrwechat/ http://18a75912f3.iok.la/zrwechat/

	public static String DRAW_URL="http://d55c9020.nat1.nsloop.com/yydwechat/draw/getDetails?sid=SID";
    public static String industry="https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
}
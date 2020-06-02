package com.yuyuedao.yydwechat.util;

public class WxConstants {
	
	//第三方平台参数
	public static String encodingAesKey = "8uUSy6UcuR2U4tWZqdvuhcXnbr7dqCI382rshTVlZHY";
	public static String token = "24A19B2BBA58C8F98998AFAC2F760C4F";
	public static String appid="wx81889fdaf4ab03cf";//"wxe828674208eedb08";//
	public static String domain="http://d55c9020.nat1.nsloop.com/yydwechat/";

	
	public static String appsecret="57a69827065d2e5c3c2c6c4580650cd7";//"b69fa753a009c288150c7d776b988ecc";
	public static String token_url="https://api.weixin.qq.com/cgi-bin/component/api_component_token";
	public static String pre_code_url="https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=";
	public static String authorizer_access_token_url="https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=";
	public static String info="https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token=";
	
	
	public static String redirect_uri="http://d55c9020.nat1.nsloop.com/yydwechat/weixin/callback/get";   //http://www.szzsofti.com/

	
	public static String refresh_token_url="https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=";
	public static String userinfo="https://api.weixin.qq.com/cgi-bin/user/info?access_token=";
	public static String WEB_OAUTH="https://open.weixin.qq.com/connect/oauth2/authorize?appid=WXAPPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&component_appid=COMPONENT_APPID#wechat_redirect";
	public static String WEB_OAUTH_ACCESSTOKEN="https://api.weixin.qq.com/sns/oauth2/component/access_token?appid=WXAPPID&code=CODE&grant_type=authorization_code&component_appid=COMPONENT_APPID&component_access_token=COMPONENT_ACCESS_TOKEN";

	public static String accountid="24A19B2BBA58C8F98998AFAC2F7";

	public static String fileurl="D:/work/yydwechat/src/main/resources/static/";


}

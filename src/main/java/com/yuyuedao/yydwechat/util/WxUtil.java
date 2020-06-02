package com.yuyuedao.yydwechat.util;

import com.yuyuedao.yydwechat.entity.AToken;
import com.yuyuedao.yydwechat.entity.ATokenExample;
import com.yuyuedao.yydwechat.entity.A_account;
import com.yuyuedao.yydwechat.mapper.WxUtilMapper;
import com.yuyuedao.yydwechat.mapper.generator.ATokenMapper;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.ConnectException;
import java.net.URL;


import javax.net.ssl.X509TrustManager;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

@Service
public class WxUtil extends PublicMethod {

    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    // 菜单创建（POST） 限100（次/天）
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    //客服接口地址
    public static String send_message_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

    @Resource
    private WxUtilMapper wxUtilMapper;

    @Resource
    private AuthController authController;

    @Resource
    private ATokenMapper aTokenMapper;


    public Map<String, String> getshare(String url) throws Exception {
        String jsapi_ticket;

        ATokenExample aTokenExample=new ATokenExample();

        aTokenExample.createCriteria().andAppidEqualTo(WxConstants.appid);

        List<AToken> aToken=aTokenMapper.selectByExample(aTokenExample);


        String ticket=aToken.get(0).getJsticket();
        Date time=aToken.get(0).getJsticketTime();//getJsticket_time();
        long currentTime = new Date().getTime() - 1000 * 3600 * 2 + 30*60*1000;
        if(ticket==null||(time!=null&&time.getTime()<currentTime)){
            jsapi_ticket=Sign.getJsapiTicket(getToken());
            AToken newToken=new AToken();
            newToken.setJsticket(jsapi_ticket);
            newToken.setJsticketTime(new Date());
            aTokenMapper.updateByExampleSelective(newToken,aTokenExample);
        }else{
            jsapi_ticket=ticket;
        }
        Map<String, String> ret =Sign.sign(jsapi_ticket, url);
        ret.put("appid", WxConstants.appid);
        return ret;
    }


    public Map<String,Object> getAccessToken(String accountId) throws Exception {
        Map<String,Object> rmap=new HashMap<String,Object>();

        A_account account =wxUtilMapper.getAccountInfo(accountId);
        String token = account.getAuthorizerAccessToken();//.getAuthorizer_access_token();
        Date end=new Date();
        Date start = new Date(account.getAuthorizerTokenTime().getTime());
        rmap.put("token", token);
        rmap.put("status", true);
        if (token == null || "".equals(token)||(end.getTime() - start.getTime()) / 1000 / 3000 >= 2) {
            JSONObject json4 = new JSONObject();
            json4.accumulate("component_appid",WxConstants.appid);
            json4.accumulate("authorizer_appid",account.getAuthorizerAppid());//上一步获取token的时候获取
            json4.accumulate("authorizer_refresh_token", account.getAuthorizerRefreshToken());
            Map<String,Object> component= authController.getAuthToken();
            if((boolean) component.get("status")){
                String access_token=HttpClientUtil.doPostJson(WxConstants.refresh_token_url+component.get("data"), json4.toString(), "UTF-8");
                JSONObject jsonObject=JSONObject.fromObject(access_token);
                System.out.println("access_token:::"+access_token);
                if(jsonObject.containsKey("errcode")){
                    token = null;
                    rmap.put("mesg", "获取token:"+jsonObject.get("errcode").toString()+jsonObject.get("errmsg"));
                    rmap.put("token", token);
                    rmap.put("status", false);
                }else{

                    token=jsonObject.getString("authorizer_access_token");
                    A_account accounts=new A_account();
                    accounts.setAuthorizerAccessToken(jsonObject.getString("authorizer_access_token"));
                    accounts.setAuthorizerRefreshToken(jsonObject.getString("authorizer_refresh_token"));
                    accounts.setSid(account.getSid());
//                    dao.executeSql(" update a_account set authorizer_access_token='"+jsonObject.getString("authorizer_access_token")+"'," +
//                            "  authorizer_refresh_token='"+jsonObject.getString("authorizer_refresh_token")+"' ," +
//                            " AUTHORIZER_TOKEN_TIME=getdate() where sid='"+account.getSid()+"' ");
                    wxUtilMapper.updateAccount(accounts);
                    rmap.put("token", token);
                    rmap.put("status", true);
                } }
        else{
                rmap=component;
            }
        }
        return rmap;
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());

        } catch (ConnectException ce) {

        } catch (Exception e) {

        }
        return jsonObject;
    }


    public  String getToken() {
        //A_account account = wxUtilMapper.getAccountInfo(getDataBySession(IUserConstants.accountid));// (A_account)dao.getFirstObjectByHql("from a_account where accountid='"+getDataBySession(IUserConstants.accountid)+"'");
        ATokenExample aTokenExample=new ATokenExample();

        aTokenExample.createCriteria().andAppidEqualTo(WxConstants.appid);

        List<AToken> aToken=aTokenMapper.selectByExample(aTokenExample);

        String token = null;
        if (aToken == null) {

        } else {

            Date end = new Date();

            Date start = new Date(aToken.get(0).getAuthTokenTime().getTime());

            token=aToken.get(0).getAuthToken();

            if (token == null || "".equals(token) || (end.getTime() - start.getTime()) / 1000 / 3000 >= 2) {
                token=CommonUtil.getToken(WxConstants.appid,WxConstants.appsecret).getAccessToken();
                AToken newToken=new AToken();
                newToken.setAuthToken(token);
                newToken.setAuthTokenTime(new Date());
                aTokenMapper.updateByExampleSelective(newToken,aTokenExample);
            }
        }
        return token;
    }

    public Map<String, Object> sendmbMessage(String json) throws IOException {
        Map<String,Object>  rmap =new HashMap<String,Object>();//客服接口地址
        String send_message_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        try {
                String url = send_message_url.replace("ACCESS_TOKEN",getToken());
                JSONObject jsonObject = WxUtil.httpRequest(url, "POST", json);
                //System.out.println("调用模板消息接口"+jsonObject.toString());
                Map<String,Object>  map = JsonUtils.parseMap(jsonObject.toString());
                if(map.get("errmsg").equals("ok")){
                    rmap.put("status", true) ;
                }else{
                    rmap.put("status", false) ;
                    rmap.put("mesg", "微信平台错误码:"+map.get("errmsg"));
                }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rmap;
    }
}

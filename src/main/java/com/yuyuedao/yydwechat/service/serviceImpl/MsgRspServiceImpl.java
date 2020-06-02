package com.yuyuedao.yydwechat.service.serviceImpl;

import com.yuyuedao.yydwechat.entity.WPGzhf;
import com.yuyuedao.yydwechat.entity.WPGzhfExample;
import com.yuyuedao.yydwechat.entity.W_p_gzhf;
import com.yuyuedao.yydwechat.mapper.MsgRspMapper;
import com.yuyuedao.yydwechat.mapper.generator.WPGzhfMapper;
import com.yuyuedao.yydwechat.service.MsgRespService;
import com.yuyuedao.yydwechat.util.FormUpload;
import com.yuyuedao.yydwechat.util.IUserConstants;
import com.yuyuedao.yydwechat.util.PublicMethod;
import com.yuyuedao.yydwechat.util.WxUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MsgRspServiceImpl extends PublicMethod implements MsgRespService {

    @Resource
    private MsgRspMapper msgRspMapper;

	@Resource
	private WPGzhfMapper wpGzhfMapper;

	@Resource
	private WxUtil wxUtil;

    @Override
    public W_p_gzhf getbyMrhf(String type) {
        return msgRspMapper.getByMr(type);
    }

    @Override
    public int add(WPGzhf info, HttpServletRequest request) throws Exception {
        Map<String,Object> rmap=new HashMap<String,Object>();
		WPGzhfExample wpGzhfExample=new WPGzhfExample();
		info.setAddtime(new Date());
		info.setNewsid(null);
		info.setAccountid(getDataBySession(IUserConstants.accountid));

		if(info.getType().equals("image")){
			String access=wxUtil.getAccessToken("11").get("token").toString();
			File img=new File(request.getSession().getServletContext().getRealPath("")+"/"+info.getImgurl());
			 String url ="https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+access+"&type=image";
		        String jsonStr = FormUpload.formUpload(url, img);
		        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		        if(jsonStr.indexOf("errcode") == -1) {
		        	info.setMediaid(jsonObject.get("media_id").toString());

		        }else{
		        	rmap.put("status", false);
		    		rmap.put("message",jsonObject.get("errmsg") );
		    		return -1;
		        }

		}

		if(info.getSid()!=null){
			return wpGzhfMapper.updateByPrimaryKey(info);
		}else{
			return wpGzhfMapper.insert(info);
		}
		//dao.saveOrUpdate(c);
		//log.log(menu_id, "", "关注回复设置");



    }
}

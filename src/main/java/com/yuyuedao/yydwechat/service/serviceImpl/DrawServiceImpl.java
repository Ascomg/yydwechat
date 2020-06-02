package com.yuyuedao.yydwechat.service.serviceImpl;

import com.yuyuedao.yydwechat.entity.*;
import com.yuyuedao.yydwechat.mapper.DrawMapper;
import com.yuyuedao.yydwechat.mapper.generator.DrawActivityMapper;
import com.yuyuedao.yydwechat.mapper.generator.DrawParticipantMapper;
import com.yuyuedao.yydwechat.service.DrawService;
import com.yuyuedao.yydwechat.util.PublicMethod;
import com.yuyuedao.yydwechat.util.WeiXinOpenConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DrawServiceImpl extends PublicMethod implements DrawService {

    @Resource
    private DrawActivityMapper drawActivityMapper;

    @Resource
    private DrawParticipantMapper drawParticipantMapper;

    @Resource
    private DrawMapper drawMapper;

    @Override
    public int add(DrawActivity drawActivity) {

        return drawActivityMapper.insertSelective(drawActivity);
    }

    @Override
    public Map<String, Object> getList(String title, int start, int limit) {
        Map<String,Object> returnMap=new HashMap<String,Object>();
        DrawActivityExample drawActivityExample=new DrawActivityExample();
        if(title!=null&&title!=""){
            drawActivityExample.createCriteria().andSnameEqualTo(title);
        }
        returnMap.put("data", drawActivityMapper.selectByExample(drawActivityExample));
        //returnMap.put( "total", dao.getSeqBySql(sql));
        returnMap.put("status", true);
        return returnMap;
    }

    @Override
    public int delete(Integer sid) {
        return drawActivityMapper.deleteByPrimaryKey(sid);
    }

    @Override
    public DrawActivity getById(Integer sid) {
        return drawActivityMapper.selectByPrimaryKey(sid);
    }

    @Override
    public W_p_newsDetails getByNewsId(Integer sid) {
        return null;
    }

    @Override
    public int draw(DrawParticipant drawParticipant) {
        drawParticipant.setCreatetime(new Date());
        drawParticipant.setOpenid(getDataBySession(WeiXinOpenConstants.USER_OPENID));
        return drawParticipantMapper.insert(drawParticipant);
    }

    @Override
    public List<DrawParticipant> drawCountByOpenId(Integer sid) {
        DrawParticipantExample drawParticipantExample=new DrawParticipantExample();
        drawParticipantExample.createCriteria().andDrawActivityIdEqualTo(sid).andOpenidEqualTo(getDataBySession(WeiXinOpenConstants.USER_OPENID));
         return drawParticipantMapper.selectByExample(drawParticipantExample);
    }

    @Override
    public List<DrawUser> drawCountById(Integer sid) {
        return drawMapper.drawCountById(sid);
    }

    @Override
    public List<DrawUser> getDrawDetails(Integer activityId,String openid) {
        return  drawMapper.getDrawDetails(activityId,openid);
    }
}

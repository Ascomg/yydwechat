package com.yuyuedao.yydwechat.service.serviceImpl;

import com.yuyuedao.yydwechat.entity.Activity;
import com.yuyuedao.yydwechat.entity.ActivityParticipant;
import com.yuyuedao.yydwechat.entity.ActivityParticipantExample;
import com.yuyuedao.yydwechat.mapper.ActivityMapper;
import com.yuyuedao.yydwechat.mapper.generator.ActivityParticipantMapper;
import com.yuyuedao.yydwechat.service.ActivityService;
import com.yuyuedao.yydwechat.util.PublicMethod;
import com.yuyuedao.yydwechat.util.WeiXinOpenConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ActivityServiceImpl extends PublicMethod implements ActivityService {

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private ActivityParticipantMapper activityParticipantMapper;

    @Override
    public List<Activity> getParticipantList(String s, String keyword) {
        return activityMapper.getParticipantList(s,keyword);
    }

    @Override
    public List<ActivityParticipant> getParticipantCount(Integer sid,String type) {
        ActivityParticipantExample activityParticipantExample=new ActivityParticipantExample();
        activityParticipantExample.createCriteria().andActivityidEqualTo(sid).andStypeEqualTo(type);
        activityParticipantExample.isDistinct();
        return activityParticipantMapper.selectByExample(activityParticipantExample);
    }

    @Override
    public int insert(ActivityParticipant activityParticipant) {

        activityParticipant.setCreatetime(new Date());
        activityParticipant.setOpenid(getDataBySession(WeiXinOpenConstants.USER_OPENID));
        activityParticipant.setStype("draw");
        return  activityParticipantMapper.insert(activityParticipant);

    }
}

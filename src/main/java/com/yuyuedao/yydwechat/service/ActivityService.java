package com.yuyuedao.yydwechat.service;

import com.yuyuedao.yydwechat.entity.Activity;
import com.yuyuedao.yydwechat.entity.ActivityParticipant;

import java.util.List;

public interface ActivityService {

    List<Activity>  getParticipantList(String keyword,String type);

    List<ActivityParticipant> getParticipantCount(Integer sid,String type);

    int insert(ActivityParticipant activityParticipant);

}

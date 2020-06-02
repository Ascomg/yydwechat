package com.yuyuedao.yydwechat.service;

import com.yuyuedao.yydwechat.entity.DrawActivity;
import com.yuyuedao.yydwechat.entity.DrawParticipant;
import com.yuyuedao.yydwechat.entity.DrawUser;
import com.yuyuedao.yydwechat.entity.W_p_newsDetails;

import java.util.List;
import java.util.Map;

public interface DrawService {

    int add(DrawActivity drawActivity);

    Map<String,Object> getList(String title, int start, int limit);

    int delete(Integer sid);

    DrawActivity getById(Integer sid);

    W_p_newsDetails getByNewsId(Integer sid);

    int draw(DrawParticipant drawParticipant);

    List<DrawParticipant> drawCountByOpenId(Integer sid);

    List<DrawUser> drawCountById(Integer sid);

    List<DrawUser> getDrawDetails(Integer activityId,String openid);




}

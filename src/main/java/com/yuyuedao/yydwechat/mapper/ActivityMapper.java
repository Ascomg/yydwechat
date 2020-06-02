package com.yuyuedao.yydwechat.mapper;

import com.yuyuedao.yydwechat.entity.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityMapper {

     List<Activity> getParticipantList(@Param("activityId") String activityId,@Param("type") String type);


}

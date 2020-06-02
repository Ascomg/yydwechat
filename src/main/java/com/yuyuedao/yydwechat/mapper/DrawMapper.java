package com.yuyuedao.yydwechat.mapper;

import com.yuyuedao.yydwechat.entity.DrawParticipant;
import com.yuyuedao.yydwechat.entity.DrawUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DrawMapper {

    @Select("select a.openid,head_url,a.createtime from draw_participant a,w_p_fans b where a.openid=b.openid  and a.draw_activity_id=#{activityId} ")
    List<DrawUser> drawCountById(Integer activityId);


    @Insert("INSERT into draw_winning(openid,activityid,activityname,prize) select top ${count} openid,activityId,(select sname from draw_activity where sid=#{activityId}), #{prize} prize from activity_participant where activityId=#{activityId} and stype='draw' and openid not in (select  openid from draw_winning where activityid=#{activityId}) order by NEWID()")
    int insertDrawFromInvate(@Param("count") Integer count,@Param("activityId") Integer activityId,@Param("prize") String prize);

    @Insert("INSERT into draw_winning(openid,activityid,activityname,prize) select top ${count} openid,draw_activity_id,(select sname from draw_activity where sid=#{activityId}), #{prize} prize from draw_participant where draw_activity_id=#{activityId}  and openid not in (select  openid from draw_winning where activityid=#{activityId}) order by NEWID()")
    int insertDraw(@Param("count") Integer count,@Param("activityId") Integer activityId,@Param("prize") String prize);

    @Select("select openid from draw_participant where draw_activity_id=#{activityId} and openid not in  (select openid from draw_activity where sid=#{activityId} )")
    List<DrawParticipant> getDrawUser(Integer activityId);

    @Select("select a.openid,head_url,a.createtime from draw_participant a,w_p_fans b where a.openid=b.openid  and a.draw_activity_id=#{activityId} ")
    List<DrawUser> getDrawDetails(@Param("activityId")Integer activityId,@Param("openid") String openid);



}

package com.yuyuedao.yydwechat.util;

import com.yuyuedao.yydwechat.entity.ActivityParticipant;
import com.yuyuedao.yydwechat.entity.ActivityParticipantExample;
import com.yuyuedao.yydwechat.entity.DrawActivity;
import com.yuyuedao.yydwechat.entity.DrawActivityExample;
import com.yuyuedao.yydwechat.mapper.generator.ActivityParticipantMapper;
import com.yuyuedao.yydwechat.mapper.generator.DrawActivityMapper;
import net.sf.json.JSONObject;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
public class DrawTask implements SchedulingConfigurer {

    @Resource
    private DrawActivityMapper drawActivityMapper;

    @Resource
    private ActivityParticipantMapper activityParticipantMapper;

    @Resource
    private WxUtil wxUtil;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        DrawActivityExample drawActivityExample = new DrawActivityExample();
        List<DrawActivity> drawActivityList = drawActivityMapper.selectByExample(drawActivityExample);

        String[] strNow1 = new SimpleDateFormat("yyyy-MM-dd").format(drawActivityList.get(0).getLotterytime()).split("-");

        Integer.parseInt(strNow1[0]);            //获取年
        Integer.parseInt(strNow1[1]);            //获取月
        Integer.parseInt(strNow1[2]);


        String[] strNow2 = new SimpleDateFormat("HH:mm:ss").format(drawActivityList.get(0).getLotterytime()).split(":");

        Integer.parseInt(strNow2[0]);            //获取时（12小时制）
        Integer.parseInt(strNow2[1]);            //获取分


        String cron = "0 " + strNow2[1] + " " + strNow2[0] + " " + strNow1[2] + " " + strNow1[1] + " ? ";
        Integer activity = drawActivityList.get(0).getSid();

        scheduledTaskRegistrar.addCronTask(new Runnable() {
            @Override
            public void run() {

                try {
                    System.out.println("定时器");
                    ActivityParticipantExample activityParticipantExample = new ActivityParticipantExample();
                    activityParticipantExample.createCriteria().andActivityidEqualTo(activity).andStypeEqualTo("draw");
                    List<ActivityParticipant> activityParticipantList = activityParticipantMapper.selectByExample(activityParticipantExample);



                    String status = "";
                    for (ActivityParticipant activityParticipant : activityParticipantList) {
                        Map<String, DataItem> data = new HashMap<String, DataItem>();
                        data.put("keyword1", new DataItem(drawActivityList.get(0).getSname(), ""));            //名称
                        data.put("keyword2", new DataItem("一等奖", ""));    //时间
                        data.put("first", new DataItem("恭喜你中奖啦", ""));        //内容
                        data.put("remark", new DataItem("快来联系客服兑奖吧", ""));            //广告语

                        Map<String, Object> msgMap = new HashMap<String, Object>();
                        CommonMb mb = new CommonMb();
                        mb.setTemplate_id("kn7mbqH87qAIdcU1-f6GbcdyChcNbEvfbXE1Zbomw6Y");
                        mb.setData(data);
                        mb.setTouser(activityParticipant.getOpenid());
                        JSONObject jsonObj = JSONObject.fromObject(mb);

                        msgMap = wxUtil.sendmbMessage(jsonObj.toString());
                        if ((boolean) msgMap.get("status")) {
                            status = "已发送";
                        } else {
                            status = "发送失败，原因：接口异常，" + msgMap.get("mesg").toString();
                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }, cron);


    }



}

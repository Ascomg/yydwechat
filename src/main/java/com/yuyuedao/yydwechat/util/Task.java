package com.yuyuedao.yydwechat.util;

import com.yuyuedao.yydwechat.entity.*;
import com.yuyuedao.yydwechat.mapper.DrawMapper;
import com.yuyuedao.yydwechat.mapper.generator.DrawActivityMapper;
import com.yuyuedao.yydwechat.mapper.generator.DrawWinningMapper;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Lazy(false)
@Component
@EnableScheduling
public class Task implements SchedulingConfigurer {

    @Resource
    private DrawActivityMapper drawActivityMapper;

    @Resource
    private WxUtil wxUtil;

    @Resource
    private DrawWinningMapper drawWinningMapper;

    @Resource
    private DrawMapper drawMapper;

    private static String cron;

    private static List<DrawActivity> drawActivityList;




    public Task(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                DrawActivityExample drawActivityExample = new DrawActivityExample();
                drawActivityList = drawActivityMapper.selectByExample(drawActivityExample);
                String[] strNow1 = new SimpleDateFormat("yyyy-MM-dd").format(drawActivityList.get(0).getLotterytime()).split("-");

                Integer.parseInt(strNow1[0]);            //获取年
                Integer.parseInt(strNow1[1]);            //获取月
                Integer.parseInt(strNow1[2]);


                String[] strNow2 = new SimpleDateFormat("HH:mm:ss").format(drawActivityList.get(0).getLotterytime()).split(":");

                Integer.parseInt(strNow2[0]);            //获取时（12小时制）
                Integer.parseInt(strNow2[1]);            //获取分


                cron = "0 " + strNow2[1] + " " + strNow2[0] + " " + strNow1[2] + " " + strNow1[1] + " ? ";


            }
        });
    }


    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {


        DrawActivityExample drawActivityExample = new DrawActivityExample();
        drawActivityList = drawActivityMapper.selectByExample(drawActivityExample);
        String[] strNow1 = new SimpleDateFormat("yyyy-MM-dd").format(drawActivityList.get(0).getLotterytime()).split("-");

        Integer.parseInt(strNow1[0]);            //获取年
        Integer.parseInt(strNow1[1]);            //获取月
        Integer.parseInt(strNow1[2]);


        String[] strNow2 = new SimpleDateFormat("HH:mm:ss").format(drawActivityList.get(0).getLotterytime()).split(":");

        Integer.parseInt(strNow2[0]);            //获取时（12小时制）
        Integer.parseInt(strNow2[1]);            //获取分


        cron = "0 " + strNow2[1] + " " + strNow2[0] + " " + strNow1[2] + " " + strNow1[1] + " ? ";

        Integer activity = drawActivityList.get(0).getSid();


        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {

                try {
                    System.out.println("定时器");

                    //一等奖设置
                    int count=drawActivityList.get(0).getQuotaOne();
                    int formCount=count*2/3;
                    int onec=count-formCount;
                    int insertCount=drawMapper.insertDrawFromInvate(formCount,drawActivityList.get(0).getSid(),"一等奖");
                    if(insertCount<=0){

                    }
                    if(insertCount<formCount){
                        onec=count-insertCount;
                    }
                    if(drawMapper.insertDrawFromInvate(onec,drawActivityList.get(0).getSid(),"一等奖")<=0){

                    }

                    //二等奖设置
                    count=drawActivityList.get(0).getQuotaTwo();
                    formCount=count*2/3;
                    onec=count-formCount;
                    insertCount=drawMapper.insertDrawFromInvate(formCount,drawActivityList.get(0).getSid(),"二等奖");
                    if(insertCount<=0){

                    }
                    if(insertCount<formCount){
                        onec=count-insertCount;
                    }
                    if(drawMapper.insertDrawFromInvate(onec,drawActivityList.get(0).getSid(),"二等奖")<=0){

                    }

                    //三等奖设置
                    count=drawActivityList.get(0).getQuotaThree();
                    formCount=count*2/3;
                    onec=count-formCount;
                    insertCount=drawMapper.insertDrawFromInvate(formCount,drawActivityList.get(0).getSid(),"三等奖");
                    if(insertCount<=0){

                    }
                    if(insertCount<formCount){
                        onec=count-insertCount;
                    }
                    if(drawMapper.insertDrawFromInvate(onec,drawActivityList.get(0).getSid(),"三等奖")<=0){

                    }




                    //从中奖名单中发送
                    DrawWinningExample drawWinningExample=new DrawWinningExample();
                    drawWinningExample.createCriteria().andActivityidEqualTo(drawActivityList.get(0).getSid());
                    List<DrawWinning> drawWinningList=drawWinningMapper.selectByExample(drawWinningExample);


                    String status = "";
                    String name="";
                    for (DrawWinning drawWinning : drawWinningList) {
                        name=drawWinning.getActivityname();
                        Map<String, DataItem> data = new HashMap<String, DataItem>();
                        data.put("keyword1", new DataItem(drawWinning.getActivityname(), ""));            //名称
                        data.put("keyword2", new DataItem(drawWinning.getPrize(), ""));    //时间
                        data.put("first", new DataItem("恭喜你中奖啦", ""));        //内容
                        data.put("remark", new DataItem("快来联系客服兑奖吧", ""));            //广告语

                        Map<String, Object> msgMap = new HashMap<String, Object>();
                        CommonMb mb = new CommonMb();
                        mb.setTemplate_id("kn7mbqH87qAIdcU1-f6GbcdyChcNbEvfbXE1Zbomw6Y");
                        mb.setData(data);
                        mb.setTouser(drawWinning.getOpenid());
                        JSONObject jsonObj = JSONObject.fromObject(mb);

                        msgMap = wxUtil.sendmbMessage(jsonObj.toString());
                        if ((boolean) msgMap.get("status")) {
                            status = "已发送";
                        } else {
                            status = "发送失败，原因：接口异常，" + msgMap.get("mesg").toString();
                        }
                    }


                    //未中奖名单发送
                    List<DrawParticipant> drawParticipantList=drawMapper.getDrawUser(drawActivityList.get(0).getSid());


                    for (DrawParticipant drawParticipant : drawParticipantList) {
                        Map<String, DataItem> data = new HashMap<String, DataItem>();
                        data.put("keyword1", new DataItem(name, ""));            //名称
                        data.put("keyword2", new DataItem("", ""));    //时间
                        data.put("first", new DataItem("很遗憾你未中奖", ""));        //内容
                        data.put("remark", new DataItem("下次好运哦", ""));            //广告语

                        Map<String, Object> msgMap = new HashMap<String, Object>();
                        CommonMb mb = new CommonMb();
                        mb.setTemplate_id("kn7mbqH87qAIdcU1-f6GbcdyChcNbEvfbXE1Zbomw6Y");
                        mb.setData(data);
                        mb.setTouser(drawParticipant.getOpenid());
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
        }, new Trigger(){
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                DrawActivityExample drawActivityExample = new DrawActivityExample();
                drawActivityList = drawActivityMapper.selectByExample(drawActivityExample);
                String[] strNow1 = new SimpleDateFormat("yyyy-MM-dd").format(drawActivityList.get(1).getLotterytime()).split("-");

                Integer.parseInt(strNow1[0]);            //获取年
                Integer.parseInt(strNow1[1]);            //获取月
                Integer.parseInt(strNow1[2]);


                String[] strNow2 = new SimpleDateFormat("HH:mm:ss").format(drawActivityList.get(1).getLotterytime()).split(":");

                Integer.parseInt(strNow2[0]);            //获取时（12小时制）
                Integer.parseInt(strNow2[1]);            //获取分


                cron = "0 " + strNow2[1] + " " + strNow2[0] + " " + strNow1[2] + " " + strNow1[1] + " ? ";

                CronTrigger cronTrigger=new CronTrigger(cron);
                Date nextDate=cronTrigger.nextExecutionTime(triggerContext);
                return nextDate;
            }
        });


    }



}

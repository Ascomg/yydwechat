package com.yuyuedao.yydwechat.controller;

import com.yuyuedao.yydwechat.entity.Activity;
import com.yuyuedao.yydwechat.service.ActivityService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @RequestMapping(value = "/getParticipantList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(@Param("name") String keyword) {
//		int index=dto.getPageIndex()-1;
//		int size=dto.getPageSize();
//		int start = index * size, limit = start + size;
//		Map<String,Object> map=null;
        Map<String,Object> returnMap=new HashMap<String,Object>();

        try{
            returnMap.put("data",activityService.getParticipantList(keyword,""));
            returnMap.put("status", true);
        }catch(Exception e){
            e.printStackTrace();
            returnMap.put("message", e.getMessage());
            returnMap.put("status", false);
        }

        return returnMap;

    }
}

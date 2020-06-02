package com.yuyuedao.yydwechat.controller;

import com.yuyuedao.yydwechat.entity.WPGzhf;
import com.yuyuedao.yydwechat.entity.W_p_gzhf;
import com.yuyuedao.yydwechat.entity.W_p_key;
import com.yuyuedao.yydwechat.mapper.NewsMapper;
import com.yuyuedao.yydwechat.service.MsgRespService;
import com.yuyuedao.yydwechat.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/textmsg")
public class MsgRespController {

    @Resource
    private MsgRespService msgRespService;

    @Resource
	private NewsService newsService;

//
	@RequestMapping(value = "/getbyidmrhf", method = RequestMethod.GET)
	@ResponseBody
    public Map<String,Object> getbyidmrhf(@RequestParam("type") String type)  {
		Map<String,Object> returnMap= new HashMap<String,Object>();

		try{
			W_p_gzhf data=msgRespService.getbyMrhf(type);
			if(data!=null){

				returnMap.put("data",data);
				if(data.getNewsid()!=null&&data.getNewsid()!=""){
					returnMap.put("list",newsService.getByNewsId(data.getNewsid()));
				}
				returnMap.put("status",true);
			}else{
				returnMap.put("status",false);
			}


		}catch(Exception e){
			e.printStackTrace();
			returnMap.put("status",false);
		}



		return returnMap;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Map<String,Object> add( WPGzhf data , HttpServletRequest req){
		Map<String,Object> returnMap =new HashMap<String,Object>();
		try{
			if(data!=null){

				if(msgRespService.add(data,req)>0){
					returnMap.put("status", true);
					returnMap.put("message", "新增成功!");
				}else{
					returnMap.put("status", false);
					returnMap.put("message", "新增失败!");
				}
			}else{
				returnMap.put("status", false);
				returnMap.put("message", "没有新增的信息!");
			}
		}catch(Exception e){
			e.printStackTrace();
			returnMap.put("message", e.getMessage());
			returnMap.put("status", false);
		}
		return returnMap;
	}
	
	
}

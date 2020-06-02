package com.yuyuedao.yydwechat.controller;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.yuyuedao.yydwechat.entity.W_p_key;
import com.yuyuedao.yydwechat.service.KeyService;
import com.yuyuedao.yydwechat.service.NewsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/key")
public class KeyController {

	@Resource
	private KeyService keyService;

	@Resource
	private NewsService newsService;


	/***
	 * 获取关键字列表
	 * @param keyword
	 * @return
	 */

	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getList(@Param("srch_key") String keyword) {
//		int index=dto.getPageIndex()-1;
//		int size=dto.getPageSize();
//		int start = index * size, limit = start + size;
//		Map<String,Object> map=null;
		Map<String,Object> returnMap=new HashMap<String,Object>();
		try{
			returnMap.put("data",keyService.selectAll(keyword));
			returnMap.put("status", true);
		}catch(Exception e){
			e.printStackTrace();
			returnMap.put("message", e.getMessage());
			returnMap.put("status", false);
		}

		return returnMap;

	}

	/***
	 * 删除关键字
	 * @param sid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String,Object> delete(@RequestParam("sid") Integer sid)  {
		Map<String,Object> returnMap =new HashMap<String,Object>();
		try{
			if(sid!=null){
				int count=keyService.delete(sid);
				if(count>0){
					returnMap.put("status", true);
					returnMap.put("message", "删除成功!");
				}else{
					returnMap.put("status", false);
					returnMap.put("message", "删除失败!");
				}
			}else{
				returnMap.put("status", false);
				returnMap.put("message", "没有要删除的信息!");
			}
		}catch(Exception e){
			e.printStackTrace();
			returnMap.put("message", e.getMessage());
			returnMap.put("status", false);
		}
		return returnMap;
	}

	/***
	 *根据sid获取关键字详情
	 * @param sid
	 * @return
	 */
	@RequestMapping(value = "/getById", method = RequestMethod.POST)
	public @ResponseBody
	Map<String,Object> getbyid(@RequestParam("sid") Integer sid )  {
		Map<String,Object> returnMap =new HashMap<String,Object>();
		try{
			if(sid!=null){
				W_p_key news=keyService.getById(sid);
				if(news.getNewSid()!=null&&news.getNewSid()!=""){
					returnMap.put("list",newsService.getByNewsId(news.getNewSid()));
				}
				returnMap.put("data",news);
				returnMap.put("status", true);
			}else{
				returnMap.put("status", false);
				returnMap.put("message", "请选择需要修改的记录!");
			}
		}catch(Exception e){
			e.printStackTrace();
			returnMap.put("message", e.getMessage());
			returnMap.put("status", false);
		}
		return returnMap;
	}

	/***
	 * 增加关键字信息
	 * @param keys
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> add(W_p_key keys){
		Map<String,Object> returnMap =new HashMap<String,Object>();
		try{
			if(keys!=null){

				Integer count=keyService.add(keys);

				if(count==-1) {
					returnMap.put("status", false);
					returnMap.put("message", "关键字录入重复！");
				}else{
					returnMap.put("status", true);
					returnMap.put("message", "新增成功!");
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

	@RequestMapping(value = "/saveNews", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveNews(@RequestParam(value = "sid",required = false) String sid,@RequestParam("key") String keyword, @RequestParam ("parms") String newsList){
		Map<String,Object> returnMap=new HashMap<>();
		String[] items=newsList.split("-");
		try{
			if(newsList!=null&&newsList.length()>0){
				int count=keyService.saveNews(sid,keyword,items);
				if(count>0){
					returnMap.put("status", true);
					returnMap.put("message", "新增成功!");
				}
				if(count==-1){
					returnMap.put("status", false);
					returnMap.put("message", "关键字图文回复已存在!");
				}
				if(count==-2){
					returnMap.put("status", false);
					returnMap.put("message", "删除失败!");
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

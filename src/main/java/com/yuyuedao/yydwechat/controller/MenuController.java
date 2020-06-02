package com.yuyuedao.yydwechat.controller;

import com.yuyuedao.yydwechat.entity.*;
import com.yuyuedao.yydwechat.mapper.generator.WPFansMapper;
import com.yuyuedao.yydwechat.service.LoginService;
import com.yuyuedao.yydwechat.service.MenuService;
import com.yuyuedao.yydwechat.util.IUserConstants;
import com.yuyuedao.yydwechat.util.PublicMethod;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/menu")
public class MenuController extends PublicMethod {
	@Resource
	private MenuService menuService;

	@Resource
	private WPFansMapper wpFansMapper;

	//返回菜单导航栏
	@RequestMapping(value ="/menu",method=RequestMethod.GET)
	@ResponseBody
	public List<Menu> getMenu(){
		return menuService.getMenu();
	}


	/**
	 * 返回菜单主页面
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value ="/showmenu",method=RequestMethod.GET)
	public String getShowMenu(@RequestParam("menu_id") String menuId ){
		return menuService.getShowMenu(menuId);
	}

	/**
	 * 返回菜单子页面
	 * @param pId
	 * @param type
	 * @return
	 */
	@RequestMapping(value ="/showpage",method=RequestMethod.GET)
	public String getShowPage(@RequestParam("menu_id") String pId,@RequestParam("type") String type ){
		return menuService.getShowPage(pId,type);
	}

	/**
	 *
	 * @param menuList
	 * @return
	 */

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> add(@RequestBody  List<W_p_menu> menuList){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		List<W_p_menu> fm= new ArrayList<>();
		try{
			if(menuList!=null){
				if(menuService.addMenu(menuList)>0){
					returnMap.put("status", true);
					returnMap.put("message", "新增菜单成功!");
				}
				else{
					returnMap.put("status", false);
					returnMap.put("message", "新增失败!");
				}
			}
			else{

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


	@RequestMapping(value = "getCount",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object>  getCount(){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		WPFansExample wpFansExample=new WPFansExample();
		wpFansExample.createCriteria().andAccountidEqualTo(getDataBySession(IUserConstants.accountid));
		List<String> countNum = new ArrayList<String>();
		List<String> countDate = new ArrayList<String>();
		List<Fans>  fansList=menuService.getCount(getDataBySession(IUserConstants.accountid));
		for (int i = 0; i < fansList.size(); i++) {
			countNum.add(fansList.get(i).getNums());
			countDate.add(fansList.get(i).getSubTime());
		}
		returnMap.put("fansNew",countNum);
		returnMap.put("fansDate",countDate);
		returnMap.put("status",true);
		return returnMap;


	}


	@RequestMapping(value ="/getmenu",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getWPMenu(){
		Map<String,Object> returnMap=new HashMap<>();
		try{
			returnMap.put("button",menuService.getWPMenu());
			returnMap.put("status",true);
		}catch(Exception e){
			e.printStackTrace();
			returnMap.put("status",false);
			returnMap.put("message",e.getMessage());
		}

		return returnMap;
	}


	/**
	 * 微信同步菜单
	 * @return
	 */
	@RequestMapping(value = "/sameMenu", method = RequestMethod.POST)
	public @ResponseBody
	Map<String,Object> sameMenu()  {
		Map<String,Object> rmap=null;
		try{
			int count=menuService.sameMenu();
			if(count==-1){
				rmap=new HashMap<>();
				rmap.put("message","暂无菜单，同步菜单失败！");
				rmap.put("status",false);
				return rmap;
			}
			if(count==-2){
				rmap=new HashMap<>();
				rmap.put("message","获取token失败，检查白名单配置或者appsecret是否正确！");
				rmap.put("status",false);
				return rmap;
			}
			if(count>0){
				rmap=new HashMap<>();
				rmap.put("message","同步菜单成功");
				rmap.put("status",true);
				return rmap;
			}

			if(count<-2){
				rmap=new HashMap<>();
				rmap.put("message","同步菜单失败");
				rmap.put("status",false);
				return rmap;
			}


		}catch(Exception e){
			e.printStackTrace();
			rmap=new HashMap<>();
			rmap.put("message", e.getMessage());
			rmap.put("status", false);
		}
		return rmap;
	}




}
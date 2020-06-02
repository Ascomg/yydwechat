package com.yuyuedao.yydwechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.yuyuedao.yydwechat.entity.*;
import com.yuyuedao.yydwechat.service.PosterService;
import com.yuyuedao.yydwechat.util.FileUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/poster")
public class PosterController {

	@Resource
	private PosterService posterService;

	@RequestMapping(value = "/gridlist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> gridlist(@Param("sname") String title, GridRequestDto dto) {
		int index=dto.getPageIndex()-1;
		int size=dto.getPageSize();
		int start = index * size, limit = start + size;
		Map<String,Object> map=null;
		Map<String,Object> rmap=null;
		try{

			 rmap =posterService.getList(title,start,limit);
		}catch(Exception e){
			e.printStackTrace();
			rmap=new HashMap<String,Object>();
			rmap.put("message", e.getMessage());
			rmap.put("status", false);
		}
		return rmap; 
	}


	@RequestMapping(value = "/additem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> additem(Poster poster){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		try{
			if(poster!=null){
				Integer count=posterService.addInfo(poster);
				if(count>0){
					returnMap.put("status", true);
					returnMap.put("message", "新增信息成功！");
				}else{
					returnMap.put("status", false);
					returnMap.put("message", "没有新增的信息!");
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


	@RequestMapping(value = "/uploadold", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> uploadold(HttpServletRequest request) {
		Map<String,Object> rmap =null;
	    try {
	      rmap = posterService.upLoad(request);
	    } catch (Exception e) {
	    	e.printStackTrace();
		  	rmap=new HashMap<String,Object>();
			rmap.put("message", e.getMessage());
			rmap.put("status", false);
			rmap.put("code", 2);
			rmap.put("msg", e.getMessage());
	    }
	    return rmap;
	}


	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> projectPictureUpload(@RequestParam(value = "projectImg",required = true) MultipartFile file) throws FileNotFoundException {
		Map<String,Object> rmap =null;
		//将图片上传到服务器
		if(file.isEmpty()){
			rmap=new HashMap<String,Object>();
			rmap.put("message","项目图片不能为空");
			rmap.put("status", false);
			rmap.put("code", 2);
			rmap.put("msg", "项目图片不能为空");
			return rmap;
		}
		//原始文件名
		String originalFilename = file.getOriginalFilename();
		//文件后缀
		String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		//图片名称为uuid+图片后缀防止冲突
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = formatter.format(date)+"."+suffix;
		//String fileName = UUID.randomUUID().toString()+"."+suffix;
		String os = System.getProperty("os.name");
		//文件保存路径
		String filePath="";
		//if(os.toLowerCase().startsWith("win")){
			//windows下的路径
			String classpath = ResourceUtils.getFile("classpath:").getAbsolutePath();
			filePath="D:/work/yydwechat/src/main/resources/static/imgs/";

//		}else {
//			//linux下的路径
//			filePath="/root/pictureUpload/project/";
//		}
		try {
			//写入图片
			Boolean writePictureflag = FileUtil.uploadFile(file.getBytes(),filePath,fileName);
			if(writePictureflag == false){
				//上传图片失败
				rmap=new HashMap<String,Object>();
				rmap.put("message","上传图片失败");
				rmap.put("status", false);
				rmap.put("code", 2);
				rmap.put("msg", "上传图片失败");
				return rmap;
			}
			//上传成功后，将可以访问的完整路径返回
			String fullImgpath = "imgs/"+fileName;
			rmap=new HashMap<String,Object>();
			rmap.put("newName",fullImgpath);
			rmap.put("message","上传图片成功");
			rmap.put("status", true);
			rmap.put("code", 2);
			rmap.put("msg", "上传图片成功");
			return rmap;
		} catch (Exception e) {
			e.printStackTrace();
			//上传图片失败
			rmap=new HashMap<String,Object>();
			rmap.put("message","上传图片失败");
			rmap.put("status", false);
			rmap.put("code", 2);
			rmap.put("msg", "上传图片失败");
			return rmap;
		}
	}

	@RequestMapping(value = "deleteitem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteItem(@Param("id") Integer id){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		try{
			Integer count=posterService.deleteInfo(id);
			if(count==-1){
				returnMap.put("status",false);
				returnMap.put("message", "该素材正在使用不能删除!");
				return returnMap;
			}
			if(count>0){
				returnMap.put("status",true);
				returnMap.put("message", "删除成功");
			}else{
				returnMap.put("status",false);
				returnMap.put("message", "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			//上传图片失败
			returnMap.put("message",e.getMessage());
			returnMap.put("status", false);
		}

		return returnMap;
	}



	@RequestMapping(value = "/gridQuestionList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> gridQuestionList(@Param("sname") String title, GridRequestDto dto) {
		int index=dto.getPageIndex()-1;
		int size=dto.getPageSize();
		int start = index * size, limit = start + size;
		Map<String,Object> map=null;
		Map<String,Object> rmap=null;
		try{

			rmap =posterService.getQuestionList(title,start,limit);
		}catch(Exception e){
			e.printStackTrace();
			rmap=new HashMap<String,Object>();
			rmap.put("message", e.getMessage());
			rmap.put("status", false);
		}
		return rmap;
	}

	@RequestMapping(value = "/gridQuestionDetails", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> gridQuestionDetails(@RequestParam("sid") Integer title, GridRequestDto dto) {
		int index=dto.getPageIndex()-1;
		int size=dto.getPageSize();
		int start = index * size, limit = start + size;
		Map<String,Object> map=null;
		Map<String,Object> rmap=null;
		try{

			rmap =posterService.getQuestionDetails(title,start,limit);
		}catch(Exception e){
			e.printStackTrace();
			rmap=new HashMap<String,Object>();
			rmap.put("message", e.getMessage());
			rmap.put("status", false);
		}
		return rmap;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> save(@RequestBody  PosterA posterAList)  {
		Map<String,Object> returnMap=new HashMap<String,Object>();

		try{
			if(posterAList!=null){
				List<PosterQuestionDetails> posterQuestionDetails=posterAList.getPosterQuestionDetails();
				PosterQuestions posterQuestions=new PosterQuestions();
				posterQuestions.setSid(posterAList.getSid());
				posterQuestions.setSname(posterAList.getsName());
				Integer count=posterService.save(posterQuestionDetails,posterQuestions);
				if(count>0){
					returnMap.put("status", true);
					returnMap.put("message", "新增信息成功！");
				}else{
					returnMap.put("status", false);
					returnMap.put("message", "没有新增的信息!");
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

	@RequestMapping(value = "/gridActivityList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> gridActivityList(@RequestParam(value = "sname",required = false) String title, GridRequestDto dto) {
		int index=dto.getPageIndex()-1;
		int size=dto.getPageSize();
		int start = index * size, limit = start + size;
		Map<String,Object> map=null;
		Map<String,Object> rmap=null;
		try{

			rmap =posterService.getActivityList(title,start,limit);
		}catch(Exception e){
			e.printStackTrace();
			rmap=new HashMap<String,Object>();
			rmap.put("message", e.getMessage());
			rmap.put("status", false);
		}
		return rmap;
	}


	@RequestMapping(value = "addActivity",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addActivity(PosterActivity posterActivity){
		Map<String,Object> returnMap=null;
		try{
			int count=posterService.addActivity(posterActivity);
			if(count>0){
				returnMap=new HashMap<>();
				returnMap.put("status",true);
				returnMap.put("message","新增信息成功");
			}else{
				returnMap=new HashMap<>();
				returnMap.put("status",false);
				returnMap.put("message","新增信息失败");
			}

		}catch(Exception e){
			e.printStackTrace();
			returnMap=new HashMap<>();
			returnMap.put("status",false);
			returnMap.put("message",e.getMessage());
		}

		return returnMap;
	}



}

package com.yuyuedao.yydwechat.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuyuedao.yydwechat.entity.News;
import com.yuyuedao.yydwechat.entity.W_p_news;
import com.yuyuedao.yydwechat.entity.W_p_newsDetails;
import org.apache.ibatis.annotations.Param;


public interface NewsService {

	
	 Map<String,Object> getList(String title, int start,   int limit);

	 int addInfo(W_p_newsDetails newsDetails);


	 Map<String, Object> upLoad(HttpServletRequest request);


	 int deleteInfo(Integer id);

	 List<W_p_newsDetails> getNewsList();

	 W_p_newsDetails getInfo(String sid);


	 List<News> getByNewsId(String newSid);


	int saveNews(String sid,String[] items);



	
//	public Map<String,Object> delete(String id,String menu_id);
//
//	public Map<String,Object> deleteitem(String id,String menu_id);
//
//	public Map<String,Object> getbyid(String code_id);
//
//	public Map<String,Object> getbysid(String code_id);
//
//	public Map<String, Object> getitem( String node) ;
//
//	public Map<String,Object> additem(W_p_newsDetails c, String menu_id);
//
//	public String isnull(String str);
//	public Map<String, Object> uploadold(HttpServletRequest request);
//
//	public Map<String,Object> edit(W_p_news c, String menu_id);
//
//	public Map<String,Object> edititem(W_p_newsDetails c, String menu_id);
//
//	public boolean checkCodeName(String code_name,Integer msgid,String type);
//
//
//	public boolean checkOrders(Integer order,Integer msgid,String type);
//	public boolean checkOrders(Integer order,Integer msgid,Integer sid,String type);
//
//
//	public void deletefile(String srch_nr, HttpServletRequest request);
//	public Map<String,Object> getNewslist();
//
//	public Map<String,Object> saveNews(String sid,String[] c,String menu_id);

}

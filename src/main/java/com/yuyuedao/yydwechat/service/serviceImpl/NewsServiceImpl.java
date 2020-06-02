package com.yuyuedao.yydwechat.service.serviceImpl;


import com.yuyuedao.yydwechat.entity.*;
import com.yuyuedao.yydwechat.mapper.KeyMapper;
import com.yuyuedao.yydwechat.mapper.NewsMapper;
import com.yuyuedao.yydwechat.mapper.generator.WPGzhfMapper;
import com.yuyuedao.yydwechat.service.NewsService;

import com.yuyuedao.yydwechat.util.FileUtil;
import com.yuyuedao.yydwechat.util.IUserConstants;
import com.yuyuedao.yydwechat.util.PublicMethod;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class NewsServiceImpl extends PublicMethod implements NewsService {
	@Resource
	private NewsMapper newsMapper;

	@Resource
	private KeyMapper keyMapper;

	@Resource
	private WPGzhfMapper wpGzhfMapper;


	@Override
	public Map<String, Object> getList(String title, int start, int limit) {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		returnMap.put("data", newsMapper.selectAll(title,start,limit));
		//returnMap.put("total", dao.getSeqBySql(sql));
		returnMap.put("status", true);
		return returnMap;
	}

	@Override
	public int addInfo(W_p_newsDetails newsDetails) {
		return newsMapper.addInfo(newsDetails);
	}

		public Map<String, Object> upLoad(HttpServletRequest request)  {
		Map<String, Object> rmap = new HashMap<String, Object>();
		String filepath=request.getSession().getServletContext().getRealPath("");
		String saveDir = request.getParameter("saveDir");

//		String newName = FileUtil(request, 100 * 1024 * 1024,"upload/"+saveDir+"/", filepath);
//		rmap.put("newName", newName);
//		rmap.put("status", true);
//		Map<String, Object> reqmap = new HashMap<String, Object>();
//		reqmap.put("src", "../"+newName);
//		reqmap.put("title", "ss");
//		rmap.put("data", reqmap);
//		rmap.put("code",0);
		return rmap;
	}

	@Override
	public int deleteInfo(Integer id) {
		 if(newsMapper.getCount(id)>0){
		 	return -1;
		 }

//				if(Integer.parseInt(dao.getSeqBySql(" select count(*) from w_p_news where itemsid='"+id+"' "))>0){
//
//			rmap.put("status", false);
//			rmap.put("message", "该素材正在使用不能删除!");


			ServletContext context = getRequest().getSession().getServletContext();
		    W_p_newsDetails news =newsMapper.getInfo(id);

		    if(news!=null){
				//String pic_path = dao.getSeqBySql("select picurl from w_p_newsitem where sid="+id+"");
				if(news.getPicUrl()!=null&&!news.getPicUrl().contains("resources")){

					String picRealPath = context.getRealPath(news.getPicUrl());
					FileUtil.deleteFile(picRealPath);
				}
				//String pic_path2= dao.getSeqBySql("select smallpic from w_p_newsitem where sid="+id+"");
				if(news.getPicUrl()!=null&&news.getSmallPic().contains("resources")){
					String picRealPath2 = context.getRealPath(news.getSmallPic());
					FileUtil.deleteFile(picRealPath2);
				}
			}



//			dao.executeSql("delete from W_p_newsitem where sid ="+id+" ");
//			log.log(menu_id, id, "删除");
//			rmap.put("status", true);
//			rmap.put("message", "操作成功!");


		return newsMapper.deleteItems(id);
	}

	@Override
	public List<W_p_newsDetails> getNewsList() {
		return newsMapper.selectByAccountId(getDataBySession(IUserConstants.accountid));
	}

	@Override
	public W_p_newsDetails getInfo(String sid) {
		return newsMapper.getInfo(Integer.parseInt(sid));
	}

	@Override
	public List<News> getByNewsId(String newSid) {
		return newsMapper.getByNewsId(newSid,getDataBySession(IUserConstants.accountid));
	}


	@Override
	public int saveNews(String sid, String[] items) {

		String newSid="";
		String accountId=getDataBySession(IUserConstants.accountid);

		if(sid==null||"".equals(sid)){
				newSid=keyMapper.getNewsId(sid);
				WPGzhf info=new WPGzhf();
				info.setAccountid(accountId);
				info.setStype(items[items.length-1]);
				info.setNewsid(newSid);
				info.setAddtime(new Date());
				wpGzhfMapper.insert(info);

		}else{

				newSid=keyMapper.getNewsId(sid);
				if(null!=newSid&&!"".equals(newSid)){
					int deleteCount= newsMapper.delete(newSid);
					if(deleteCount>0){
						if(items.length==1){
							//newsMapper.updateInfo(newSid,items[items.length-1],accountId);
							WPGzhf info=new WPGzhf();
							info.setStype(items[items.length-1]);
							info.setType("news");
							info.setNewsid(newSid);
							info.setSid(Integer.parseInt(sid));
							info.setAccountid(accountId);
							info.setAddtime(new Date());
							wpGzhfMapper.updateByPrimaryKey(info);
						}
					}else{
						return -2;
					}

				}else{
					newSid=keyMapper.getNewsId(null);
					WPGzhf info=new WPGzhf();
					info.setStype(items[items.length-1]);
					info.setType("news");
					info.setNewsid(newSid);
					info.setSid(Integer.parseInt(sid));
					info.setAccountid(accountId);
					info.setAddtime(new Date());
					wpGzhfMapper.updateByPrimaryKey(info);
				}




		}
		List<W_p_news> newsList=new ArrayList<>();
		for(int i=1;i<items.length;i++){

			W_p_news news=new W_p_news();
			news.setNewSid(newSid);
			news.setItemSid(items[i-1]);
			news.setAccountId(accountId);
			news.setOrders(i);
			newsList.add(news);
		}

		return newsMapper.add(newsList);
		//log.log(menu_id, newsid, "关键字图文回复");
	}


//	public <List> W_p_newsDetails getNewsList(){
//		Map<String,Object> rmap=new HashMap<String,Object>();
//		List<W_p_newsDetails> fm=dao.queryAllBySql("select sid,title,picurl,smallpic from w_p_newsitem where accountid='"+getDataBySession(IUserConstants.accountid)+"' ",new String[]{"sid","title","smallpic","picurl"}, W_p_newsDetails.class);
//		if(fm.size()>0){
//			rmap.put("code", fm);
//			rmap.put("status", true);
//
//		}else{
//			rmap.put("status", false);
//		}

//		return rmap;
//	}

//	public Map<String,Object> delete(String id,String menu_id){
//		Map<String,Object> rmap=new HashMap<String,Object>();
//		W_p_news fm=(W_p_news)dao.get(W_p_news.class, Integer.parseInt(id));
//		Integer hyynum=Integer.parseInt(dao.getSeqBySql("select count(1) from w_p_subscribe where msgtype='news' and textid='"+fm.getSid()+"' and accountid='"+getDataBySession(IUserConstants.accountid)+"' "));
//		Integer keynum=Integer.parseInt(dao.getSeqBySql("SELECT count(1) from w_p_key_response where type='news' and textid='"+fm.getSid()+"' and accountid='"+getDataBySession(IUserConstants.accountid)+"' "));
//		if(hyynum>0){
//			rmap.put("status", false);
//			rmap.put("message", "该图文消息在关注欢迎语中被使用，不能删除！");
//			return rmap;
//		}else if(keynum>0){
//			rmap.put("status", false);
//			rmap.put("message", "该图文消息在关键字回复中被使用，不能删除！");
//			return rmap;
//		}else{
//			List<W_p_newsDetails> itemList = dao.queryAllBySql("select * from w_p_newsitem where templateid='"+id+"'", W_p_newsDetails.class);
//			ServletContext context = getRequest().getSession().getServletContext();
//			for(W_p_newsDetails newsitem : itemList){
//				FileUtils.deleteFile(context.getRealPath(newsitem.getPicurl()));
//			}
//			dao.executeSql("delete from W_p_news where sid ='"+id+"' ");
//			dao.executeSql("delete from W_p_newsitem where templateid ='"+id+"' ");
//		}
//		log.log(menu_id, id, "删除");
//		rmap.put("status", true);
//		rmap.put("message", "操作成功!");
//		return rmap;
//	}
//
//	public Map<String,Object> deleteitem(String id,String menu_id){
//		Map<String,Object> rmap=new HashMap<String,Object>();
//		if(Integer.parseInt(dao.getSeqBySql(" select count(*) from w_p_news where itemsid='"+id+"' "))>0){
//
//			rmap.put("status", false);
//			rmap.put("message", "该素材正在使用不能删除!");
//
//		}else{
//			ServletContext context = getRequest().getSession().getServletContext();
//			String pic_path = dao.getSeqBySql("select picurl from w_p_newsitem where sid="+id+"");
//			if(pic_path.contains("resources")){
//
//			}else{
//				String picRealPath = context.getRealPath(pic_path);
//				FileUtils.deleteFile(picRealPath);
//			}
//			String pic_path2= dao.getSeqBySql("select smallpic from w_p_newsitem where sid="+id+"");
//			if(pic_path2.contains("resources")){
//				String picRealPath2 = context.getRealPath(pic_path2);
//				FileUtils.deleteFile(picRealPath2);
//			}
//			dao.executeSql("delete from W_p_newsitem where sid ="+id+" ");
//			log.log(menu_id, id, "删除");
//			rmap.put("status", true);
//			rmap.put("message", "操作成功!");
//		}
//
//		return rmap;
//	}
//
//	public Map<String,Object> getbyid(String code_id){
//		Map<String,Object> rmap=new HashMap<String,Object>();
//		W_p_news fm=(W_p_news)dao.get(W_p_news.class,Integer.parseInt(code_id) );
//		rmap.put("code", fm);
//		rmap.put("status", true);
//		rmap.put("message", "操作成功!");
//		return rmap;
//	}
//
//	public Map<String,Object> getbysid(String code_id){
//		Map<String,Object> rmap=new HashMap<String,Object>();
//		W_p_newsDetails fm=(W_p_newsDetails)dao.get(W_p_newsDetails.class,Integer.parseInt(code_id) );
//		rmap.put("data", fm);
//		rmap.put("status", true);
//		rmap.put("message", "操作成功!");
//		return rmap;
//	}
//
//	public Map<String, Object> getitem( String node) {
//		Map<String, Object> returnMap = new HashMap<String, Object>();
//		if (node == null || "".equals(node)) {
//			returnMap.put("data", null);
//			returnMap.put("status", true);
//			return returnMap;
//		}
//		String sql = "select a.sid,a.picurl,a.title,convert(varchar(20),b.addtime,23) addtime from w_p_newsitem a,w_p_news b where  a.templateid =b.sid and a.templateid ='" +node + "'  ";
//		returnMap.put("data", dao.queryMapAllBySql(sql	+ " order by  orders "));
//		returnMap.put("status", true);
//		return returnMap;
//	}
//
//	@Override
//	public Map<String, Object> additem(W_p_newsDetails c, String menu_id) {
//		return null;
//	}
//
//	public Map<String,Object> add(W_p_news c,String menu_id){
//		Map<String,Object> rmap=new HashMap<String,Object>();
//		c.setAddtime(dateclass.getNowTime());
//		c.setAccountid(getDataBySession(IUserConstants.accountid));
//		dao.save(c);
//		log.log(menu_id, "", "新增");
//		rmap.put("status", true);
//		rmap.put("message", "操作成功!");
//		return rmap;
//	}
//
//	public Map<String,Object> additem(W_p_newsDetails c, String menu_id){
//		Map<String,Object> rmap=new HashMap<String,Object>();
//		c.setOprq(dateclass.getNowTime());
//		c.setAccountid(getDataBySession(IUserConstants.accountid));
//		dao.save(c);
//		log.log(menu_id, "", "新增");
//		rmap.put("status", true);
//		rmap.put("message", "添加成功!");
//		return rmap;
//	}
//
//	public String isnull(String str){
//		if(str!=null&&str.length()>0&&!"null".equals(str)){
//			return "'"+str+"'";
//		}else{
//			return "''";
//		}
//	}
//
//	public Map<String, Object> uploadold(HttpServletRequest request) throws Exception {
//		Map<String, Object> rmap = new HashMap<String, Object>();
//		String filepath=request.getSession().getServletContext().getRealPath("");
//		String saveDir = request.getParameter("saveDir");
//		String newName = FileUtils.UploadFileNochange(request, 100 * 1024 * 1024,"upload/"+getDataBySession(IUserConstants.accountid)+"/"+saveDir+"/", filepath);
//		rmap.put("newName", newName);
//		rmap.put("status", true);
//		Map<String, Object> reqmap = new HashMap<String, Object>();
//		reqmap.put("src", "../"+newName);
//		reqmap.put("title", "ss");
//		rmap.put("data", reqmap);
//		rmap.put("code",0);
//		return rmap;
//	}
//
//	@Override
//	public Map<String, Object> edit(W_p_news c, String menu_id) {
//		return null;
//	}
//
//	public Map<String,Object> edit(W_p_news c,String menu_id) throws IllegalAccessException, InvocationTargetException{
//		Map<String,Object> rmap=new HashMap<String,Object>();
//		c.setAddtime(dateclass.getNowTime());
//		c.setAccountid(getDataBySession(IUserConstants.accountid));
//		dao.update(c);
//		log.log( menu_id, "", "修改");
//		rmap.put("status", true);
//		rmap.put("message", "操作成功!");
//		return rmap;
//	}
//
//	public Map<String,Object> edititem(W_p_newsDetails c, String menu_id) throws IllegalAccessException, InvocationTargetException{
//		Map<String,Object> rmap=new HashMap<String,Object>();
//		c.setOprq(dateclass.getNowTime());
//		c.setAccountid(getDataBySession(IUserConstants.accountid));
//		dao.update(c);
//		log.log( menu_id, "", "修改");
//		rmap.put("status", true);
//		rmap.put("message", "修改成功!");
//		return rmap;
//	}
//
//	public boolean checkCodeName(String code_name,Integer msgid,String type){
//		String sql="";
//
//		if(type.equals("修改")){
//			 sql="select count(*) from W_p_news where sname='"+code_name+"' and sid<>"+msgid+" ";
//			 return (Integer.parseInt(dao.getSeqBySql(sql)))>0;
//		}else{
//			 sql="select count(*) from W_p_news where sname='"+code_name+"' ";
//
//			 return (Integer.parseInt(dao.getSeqBySql(sql)))>0;
//		}
//
//
//	}
//
//	public boolean checkOrders(Integer order,Integer msgid,String type){
//
//		String sql="select count(*) from W_p_newsitem where orders='"+order+"' and templateid="+msgid+" ";
//		return (Integer.parseInt(dao.getSeqBySql(sql)))>0;
//
//
//	}
//
//	public boolean checkOrders(Integer order,Integer msgid,Integer sid,String type){
//
//		String sql="select count(*) from W_p_newsitem where orders='"+order+"' and templateid="+msgid+" and sid<>"+sid+" ";
//		return (Integer.parseInt(dao.getSeqBySql(sql)))>0;
//	}
//
//	public void deletefile(String srch_nr, HttpServletRequest request) {
//		// 保存文件 文件夹路径
//		String rootPath = request.getSession().getServletContext().getRealPath(srch_nr);
//		FileUtils.deleteFile(rootPath);
//	}
//
//	public Map<String,Object> getNewslist(){
//		Map<String,Object> rmap=new HashMap<String,Object>();
//		List<W_p_newsDetails> fm=dao.queryAllBySql("select sid,title,picurl,smallpic from w_p_newsitem where accountid='"+getDataBySession(IUserConstants.accountid)+"' ",new String[]{"sid","title","smallpic","picurl"}, W_p_newsDetails.class);
//		if(fm.size()>0){
//			rmap.put("code", fm);
//			rmap.put("status", true);
//
//		}else{
//			rmap.put("status", false);
//		}
//
//		return rmap;
//	}
//
//	public Map<String,Object> saveNews(String sid,String[] c,String menu_id){
//		Map<String,Object> rmap=new HashMap<String,Object>();
//
//		String newsid="";
//		if(sid==null||"".equals(sid)){
//			newsid=dao.getSeqBySql("select newid()");
//			dao.executeSql(" insert into w_p_gzhf(newsid,addtime,accountid,stype)values('"+newsid+"',getdate(),'"+getDataBySession(IUserConstants.accountid)+"','"+c[c.length-1]+"')");
//		}else{
//			newsid=dao.getSeqBySql(" select newsid from w_p_gzhf where sid='"+sid+"' ");
//			if(null!=newsid&&!"".equals(newsid)){
//				dao.executeSql("delete from w_p_news where newsid='"+newsid+"' ");
//				if(c.length==1){
//					dao.executeSql("update w_p_gzhf set newsid=null where accountid='"+getDataBySession(IUserConstants.accountid)+"' and stype='"+c[c.length-1]+"' ");
//				}
//			}else{
//				newsid=dao.getSeqBySql("select newid()");
//				dao.executeSql("update w_p_gzhf set newsid='"+newsid+"' where accountid='"+getDataBySession(IUserConstants.accountid)+"' and stype='"+c[c.length-1]+"' ");
//				}
//
//		}
//		for(int i=1;i<c.length;i++){
//			dao.executeSql("insert into w_p_news(newsid,itemsid,accountid,orders)values('"+newsid+"','"+c[i-1]+"','"+getDataBySession(IUserConstants.accountid)+"',"+i+")");
//		}
//		log.log(menu_id, newsid, "关注回复图文设置");
//		rmap.put("status", true);
//		rmap.put("message", "操作成功!");
//		return rmap;
//	}
}

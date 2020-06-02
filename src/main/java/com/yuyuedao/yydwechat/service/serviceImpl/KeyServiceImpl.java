package com.yuyuedao.yydwechat.service.serviceImpl;
import java.util.ArrayList;
import java.util.List;


import com.yuyuedao.yydwechat.entity.W_p_key;
import com.yuyuedao.yydwechat.entity.W_p_news;
import com.yuyuedao.yydwechat.mapper.KeyMapper;
import com.yuyuedao.yydwechat.mapper.NewsMapper;
import com.yuyuedao.yydwechat.service.KeyService;
import com.yuyuedao.yydwechat.util.IUserConstants;
import com.yuyuedao.yydwechat.util.PublicMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class KeyServiceImpl extends PublicMethod implements KeyService  {

    @Resource
    private KeyMapper keyMapper;

    @Resource
    private NewsMapper newsMapper;

    @Override
    public List<W_p_key> selectAll(String keyword) {
        return keyMapper.selectAll(keyword);
    }

    @Override
    public int add(W_p_key keys) {
        System.out.println(getDataBySession(IUserConstants.accountid));
        keys.setAccountId(getDataBySession(IUserConstants.accountid));
        Integer count=keyMapper.checkName(keys.getKeyword(),null,"add");
        if(count>0){
            return -1;
        }else{
            return keyMapper.add(keys);
        }

    }

    @Override
    public int saveNews(String sid, String keyword, String[] items) {

		String newSid="";
		String accountId=getDataBySession(IUserConstants.accountid);
		Integer count=keyMapper.checkKey(keyword,sid,accountId);

		if(sid==null||"".equals(sid)){
			if(count>0){
				return -1;
			}else{
                newSid=keyMapper.getNewsId(sid);
			    W_p_key keys=new W_p_key();
			    keys.setAccountId(accountId);
			    keys.setType(items[items.length-1]);
			    keys.setKeyword(keyword);
			    keys.setNewSid(newSid);
			    keyMapper.add(keys);
			}

		}else{
        	if(count>=1){
                return -1;
			}else{
                newSid=keyMapper.getNewsId(sid);
				if(null!=newSid&&!"".equals(newSid)){
				    int deleteCount= newsMapper.delete(newSid);
				    if(deleteCount>0){
                        if(items.length==1){
                            keyMapper.updateInfo(newSid,items[items.length-1],accountId);
                        }
                    }else{
                        return -2;
                    }

				}else{
                    newSid=keyMapper.getNewsId(null);
                    keyMapper.updateInfo(newSid,items[items.length-1],accountId);
				}

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

    @Override
    public int delete(Integer sid) {

        return keyMapper.delete(sid);
    }

    @Override
    public W_p_key getById(Integer sid) {

        return keyMapper.getById(sid);
    }


//	public Map<String,Object> getList(Map<String,Object> map,int start,int limit)throws JsonParseException, JsonMappingException, IOException{
//		Map<String,Object> returnMap=new HashMap<String,Object>();
//		String sql="SELECT a.sid,a.keyword,case when a.type='news' then '图文消息' else '文本消息' end  type,a.createtime from w_p_key_response a " +
//				" where "+getWhere(map)+" order by sid desc";
//		returnMap.put("data", dao.queryMapAllBySql(sql, start, limit));
//		sql="select count(*) from W_p_key_response a where "+getWhere(map);
//		returnMap.put("total", dao.getSeqBySql(sql));
//		returnMap.put("status", true);
//		return returnMap;
//	}
//
//	public Map<String,Object> delete(String id,String menu_id){
//		Map<String,Object> rmap=new HashMap<String,Object>();
//		String newsid=dao.getSeqBySql("select newsid from W_p_key_response  where sid ="+id+"");
//		dao.executeSql("delete from W_p_key_response where sid ="+id+" ");
//		if(null!=newsid&&!"".equals(newsid)){
//			dao.executeSql("delete from w_p_news where newsid='"+newsid+"' ");
//		}
//		log.log(menu_id, id, "删除");
//		rmap.put("status", true);
//		rmap.put("message", "操作成功!");
//		return rmap;
//	}
//	public Map<String,Object> getbyid(String code_id){
//		Map<String,Object> rmap=new HashMap<String,Object>();
//		W_p_key_response fm=(W_p_key_response)dao.get(W_p_key_response.class,Integer.parseInt(code_id) );
//		rmap.put("list","");
//		if(null!= fm.getNewsid()){
//			List<W_p_newsitem> items=dao.queryAllBySql("SELECT a.title,a.smallpic,a.picurl,a.sid,b.orders from w_p_newsitem a," +
//					" w_p_news b where a.sid=b.itemsid and  a.accountid='"+getDataBySession(IUserConstants.accountid)+"' and b.newsid='"+fm.getNewsid()+"' ", W_p_newsitem.class);
//			rmap.put("list", items);
//		}
//		rmap.put("data", fm);
//		rmap.put("status", true);
//		rmap.put("message", "操作成功!");
//		return rmap;
//	}
//
//	public Map<String,Object> add(W_p_key_response c,String menu_id){
//		Map<String,Object> rmap=new HashMap<String,Object>();
//
//		c.setCreatetime(dateclass.getNowTime());
//		c.setAccountid(getDataBySession(IUserConstants.accountid));
//		dao.save(c);
//		log.log(menu_id, "", "新增");
//		rmap.put("status", true);
//		rmap.put("message", "操作成功!");
//
//
//		return rmap;
//	}
//
//	public Map<String,Object> edit(W_p_key_response c,String menu_id) throws IllegalAccessException, InvocationTargetException{
//		Map<String,Object> rmap=new HashMap<String,Object>();
//
//		c.setCreatetime(dateclass.getNowTime());
//		c.setAccountid(getDataBySession(IUserConstants.accountid));
//		dao.update(c);
//		log.log( menu_id, "", "修改");
//		rmap.put("status", true);
//		rmap.put("message", "操作成功!");
//
//		return rmap;
//	}
//
//	public boolean checkCodeName(String keyword,int msgid,String type){
//		String sql="";
//		String where=srch_qxaccount("accountid");
//		if(type.equals("修改")){
//			 sql="select count(*) from W_p_key_response where keyword='"+keyword+"'  and sid<>"+msgid+" and "+where+" ";
//			 return (Integer.parseInt(dao.getSeqBySql(sql)))>0;
//		}else{
//			 sql="select count(*) from W_p_key_response where   keyword='"+keyword+"' and "+where+" ";
//
//			 return (Integer.parseInt(dao.getSeqBySql(sql)))>0;
//		}
//
//
//	}
//	public Map<String,Object> saveNews(String sid,String key,String[] c,String menu_id){
//
//		Map<String,Object> rmap=new HashMap<String,Object>();
//
//		String newsid="";
//		if(sid==null||"".equals(sid)){
//			int exist=Integer.parseInt(dao.getSeqBySql("select count(*) from w_p_key_response where accountid='"+getDataBySession(IUserConstants.accountid)+"' and keyword='"+key+"' "));
//
//			if(exist>0){
//				rmap.put("status", false);
//				rmap.put("message", "该关键字已经存在！");
//				return rmap;
//			}else{
//				newsid=dao.getSeqBySql("select newid()");
//				dao.executeSql(" insert into w_p_key_response(newsid,createtime,keyword,accountid,type)values('"+newsid+"',getdate(),'"+key+"','"+getDataBySession(IUserConstants.accountid)+"','"+c[c.length-1]+"')");
//			}
//
//		}else{
//			int exist=Integer.parseInt(dao.getSeqBySql("select count(*) from w_p_key_response where sid<>'"+sid+"' and accountid='"+getDataBySession(IUserConstants.accountid)+"' and keyword='"+key+"' "));
//
//			if(exist>=1){
//				rmap.put("status", false);
//				rmap.put("message", "该关键字已经存在！");
//				return rmap;
//			}else{
//				newsid=dao.getSeqBySql(" select newsid from w_p_key_response where sid='"+sid+"' ");
//				if(null!=newsid&&!"".equals(newsid)){
//					dao.executeSql("delete from w_p_news where newsid='"+newsid+"' ");
//					if(c.length==1){
//						dao.executeSql("update w_p_key_response set newsid=null where accountid='"+getDataBySession(IUserConstants.accountid)+"' and type='"+c[c.length-1]+"' ");
//					}
//				}else{
//					newsid=dao.getSeqBySql("select newid()");
//					dao.executeSql("update w_p_key_response set newsid='"+newsid+"' where accountid='"+getDataBySession(IUserConstants.accountid)+"' and type='"+c[c.length-1]+"' ");
//					}
//
//			}
//
//
//		}
//		for(int i=1;i<c.length;i++){
//			dao.executeSql("insert into w_p_news(newsid,itemsid,accountid,orders)values('"+newsid+"','"+c[i-1]+"','"+getDataBySession(IUserConstants.accountid)+"',"+i+")");
//		}
//		log.log(menu_id, newsid, "关键字图文回复");
//		rmap.put("status", true);
//		rmap.put("message", "操作成功!");
//		return rmap;
//	}
}

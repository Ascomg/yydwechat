package com.yuyuedao.yydwechat.service.serviceImpl;


import com.yuyuedao.yydwechat.entity.*;
import com.yuyuedao.yydwechat.mapper.generator.PosterActivityMapper;
import com.yuyuedao.yydwechat.mapper.generator.PosterMapper;
import com.yuyuedao.yydwechat.mapper.generator.PosterQuestionDetailsMapper;
import com.yuyuedao.yydwechat.mapper.generator.PosterQuestionsMapper;
import com.yuyuedao.yydwechat.service.NewsService;
import com.yuyuedao.yydwechat.service.PosterService;
import com.yuyuedao.yydwechat.util.FileUtil;
import com.yuyuedao.yydwechat.util.IUserConstants;
import com.yuyuedao.yydwechat.util.PublicMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class PosterServiceImpl extends PublicMethod implements PosterService {
	@Resource
	private PosterMapper posterMapper;

	@Resource
	private PosterQuestionsMapper posterQuestionsMapper;

	@Resource
	private PosterQuestionDetailsMapper posterQuestionDetailsMapper;

	@Resource
	private PosterActivityMapper posterActivityMapper;


	@Override
	public Map<String, Object> getList(String title, int start, int limit) {
		Map<String,Object> returnMap=new HashMap<String,Object>();
        PosterExample posterExample=new PosterExample();
        if(title!=null&&title!=""){
			posterExample.createCriteria().andSnameEqualTo(title);
		}
        List<Poster> list=posterMapper.selectByExample(posterExample);
		returnMap.put("data", list);
		returnMap.put("total", list.size());
		returnMap.put("status", true);
		return returnMap;
	}

    @Override
    public int addInfo(Poster poster) {
        poster.setAccountid(getDataBySession(IUserConstants.accountid));
        return posterMapper.insert(poster);
    }

	@Override
	public Map<String, Object> getQuestionList(String title, int start, int limit) {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		PosterQuestionsExample posterQuestionsExample=new PosterQuestionsExample();
		if(title!=null&&title!=""){
			posterQuestionsExample.createCriteria().andSnameEqualTo(title);
		}
		returnMap.put("data", posterQuestionsMapper.selectByExample(posterQuestionsExample));
		//returnMap.put("total", dao.getSeqBySql(sql));
		returnMap.put("status", true);
		return returnMap;
	}

	@Override
	public Map<String, Object> getQuestionDetails(Integer title, int start, int limit) {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		PosterQuestionDetailsExample posterQuestionDetailsExample=new PosterQuestionDetailsExample();
		if(title!=null){
			posterQuestionDetailsExample.createCriteria().andQuestionIdEqualTo(title);
		}
		returnMap.put("data", posterQuestionDetailsMapper.selectByExample(posterQuestionDetailsExample));
		//returnMap.put("total", dao.getSeqBySql(sql));
		returnMap.put("status", true);
		return returnMap;
	}


	public Map<String, Object> upLoad(HttpServletRequest request)  {
		Map<String, Object> rmap = new HashMap<String, Object>();
		String filepath=request.getSession().getServletContext().getRealPath("");
		String saveDir = request.getParameter("saveDir");

		return rmap;
	}

	@Override
	public Map<String, Object> getActivityList(String title, int start, int limit) {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		PosterActivityExample posterActivityExample=new PosterActivityExample();
		if(title!=null&&title!=""){
			posterActivityExample.createCriteria().andSnameEqualTo(title);
		}
		returnMap.put("data", posterActivityMapper.selectByExample(posterActivityExample));
		//returnMap.put("total", dao.getSeqBySql(sql));
		returnMap.put("status", true);
		return returnMap;
	}

	@Override
	public int deleteInfo(Integer id) {
		 if(posterMapper.selectByPrimaryKey(id)==null){
		 	return -1;
		 }

		return posterMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int save(List<PosterQuestionDetails> posterQuestionDetailsList,PosterQuestions posterQuestions) {
		Integer sid =posterQuestions.getSid();
		if(posterQuestionsMapper.insertSelective(posterQuestions) > 0){//如果插入成功
			sid = posterQuestions.getSid();//i为主键自增id
		}
		int count=0;
		for(PosterQuestionDetails details:posterQuestionDetailsList){
			details.setQuestionId(sid);
			posterQuestionDetailsMapper.insert(details);
			count++;
		}

		return count;
	}

	@Override
	public int addActivity(PosterActivity posterActivity) {

		return posterActivityMapper.insert(posterActivity);
	}


}

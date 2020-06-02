package com.yuyuedao.yydwechat.mapper;

import com.yuyuedao.yydwechat.entity.News;
import com.yuyuedao.yydwechat.entity.W_p_news;
import com.yuyuedao.yydwechat.entity.W_p_newsDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsMapper {


    List<W_p_newsDetails>  selectAll(@Param("title") String title, @Param("start") int start,@Param("limit")  int limit);


    int addInfo(W_p_newsDetails newsDetails);

    int getCount(Integer id);

    W_p_newsDetails getInfo(Integer id);

    int deleteItems(Integer id);

    List<W_p_newsDetails> selectByAccountId(String accountId);


    int delete(String id);

    int add(List<W_p_news> news);


    List<News> getByNewsId(@Param("newSid") String newSid,@Param("accountId") String accountId);







}

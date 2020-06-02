package com.yuyuedao.yydwechat.mapper;

import com.yuyuedao.yydwechat.entity.W_p_key;
import com.yuyuedao.yydwechat.entity.W_p_newsDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KeyMapper {


    List<W_p_key>  selectAll(@Param("keyword") String keyword);

    Integer add(W_p_key keys);

    Integer checkName(@Param("keyword") String keyword,@Param("sid") String sid,@Param("type") String type);

    Integer saveNews(@Param("sid") String sid,@Param("keyword") String keyword,@Param("items") String[] items);


    Integer checkKey(@Param("keyword") String keyword,@Param("sid") String sid,@Param("accountId") String accountId);

    String getNewsId(@Param("sid") String sid);


    Integer updateInfo(@Param("newSid") String newSid,@Param("type") String type,@Param("accountId") String accountId);


    Integer delete(@Param("sid") Integer sid);

    W_p_key getById(@Param("sid") Integer sid);

    W_p_key getByNewsId(@Param("sid") Integer sid,@Param("accountId") String accountId);

    W_p_key getByKey(@Param("accountid") String accountid,@Param("content") String content);

}

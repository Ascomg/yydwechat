package com.yuyuedao.yydwechat.service;

import com.yuyuedao.yydwechat.entity.W_p_key;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KeyService {

    List<W_p_key> selectAll(String keyword);

    int add(W_p_key keys);

    int saveNews(String sid, String keyword,String[] items);

    int delete(Integer sid);

    W_p_key getById(Integer sid);







}

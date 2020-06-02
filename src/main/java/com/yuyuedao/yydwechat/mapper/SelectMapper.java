package com.yuyuedao.yydwechat.mapper;

import com.yuyuedao.yydwechat.util.SelectItem;

import java.util.List;

public interface SelectMapper {


    List<SelectItem> getUrlList();

    List<SelectItem> getKeyList();

    List<SelectItem> getQuestion();

    List<SelectItem> getPoster();

    List<SelectItem> getPosterActivity();
}

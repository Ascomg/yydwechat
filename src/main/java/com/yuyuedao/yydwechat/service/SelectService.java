package com.yuyuedao.yydwechat.service;

import com.yuyuedao.yydwechat.util.SelectItem;

import java.util.List;

public interface SelectService {


    List<SelectItem> getUrlList();

    List<SelectItem> getKeyList();

    List<SelectItem> getQuestion();

    List<SelectItem> getPoster();

    List<SelectItem> getPosterActivity();

    List<SelectItem> getDrawActivity();
}

package com.yuyuedao.yydwechat.service;

import com.yuyuedao.yydwechat.entity.WPGzhf;
import com.yuyuedao.yydwechat.entity.W_p_gzhf;

import javax.servlet.http.HttpServletRequest;

public interface MsgRespService {

    W_p_gzhf getbyMrhf (String type);

    int add(WPGzhf info, HttpServletRequest request) throws Exception;
}

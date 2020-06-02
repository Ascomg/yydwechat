package com.yuyuedao.yydwechat.service;

import com.yuyuedao.yydwechat.entity.LoginResult;

public interface LoginService1 {

    LoginResult login(String userName, String password);

    void logout();
}

package com.yuyuedao.yydwechat.service.serviceImpl;


import com.yuyuedao.yydwechat.mapper.UserinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl{ //implements LoginService {

    @Resource
    private UserinfoMapper info;

//    @Override
//    public boolean CheckLogin(Userinfo userinfo) {
//        int count=info.Login(userinfo.getPhone(),userinfo.getPwd());
//
//        if(count>0){
//            return true;
//        }else{
//            return false;
//        }
//
//    }

}

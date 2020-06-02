package com.yuyuedao.yydwechat.service;


import com.yuyuedao.yydwechat.entity.X_userinfo;
import com.yuyuedao.yydwechat.mapper.UserinfoMapper;
import com.yuyuedao.yydwechat.util.IUserConstants;
import com.yuyuedao.yydwechat.util.MD5;
import com.yuyuedao.yydwechat.util.PublicMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class LoginService extends PublicMethod {
    //public boolean CheckLogin(Userinfo userinfo);

    @Resource
    private UserinfoMapper userinfoMapper;


    public boolean test() {
        //int count=info.Login(userinfo.getPhone(),userinfo.getPwd());
        int count=userinfoMapper.getCount();

        System.out.println(count);
        if(count>0){
            return true;
        }else{
            return false;
        }

    }

    public boolean CheckLogin(X_userinfo userinfo) {
        System.out.println(userinfo.getUserId());
        System.out.println(userinfo.getUserPwd());
        String userpwd = MD5.getInstance().getMD5(userinfo.getUserPwd());
        X_userinfo user=userinfoMapper.getByNamepwd(userinfo.getUserId(),userpwd);
        System.out.println(user);
        if(user!=null){
            HttpSession session = getRequest().getSession();
            session.setAttribute(IUserConstants.userid, user.getUserId());
            session.setAttribute(IUserConstants.username,user.getUserName());
            session.setAttribute(IUserConstants.accountid,user.getAccountId());
            return true;

        }else{
            return false;
        }

    }
}

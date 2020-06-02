package com.yuyuedao.yydwechat.mapper;

import com.yuyuedao.yydwechat.entity.X_userinfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserinfoMapper {

     Integer getCount();

     X_userinfo getByNamepwd(String name, String pwd);


}

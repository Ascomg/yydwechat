package com.yuyuedao.yydwechat.mapper;

import com.yuyuedao.yydwechat.entity.A_account;
import com.yuyuedao.yydwechat.entity.Auth_args;
import org.apache.ibatis.annotations.Param;

public interface WxUtilMapper {

    A_account getAccountInfo(@Param("accountId") String accountId);


    int updateAccount(A_account account);

    Auth_args selectArgs();

    int updateArgs(@Param("auth_token") String auth_token,@Param("component_appid") String component_appid);


    int updateA(A_account account);
}

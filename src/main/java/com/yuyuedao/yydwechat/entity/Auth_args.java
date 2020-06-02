package com.yuyuedao.yydwechat.entity;

import java.util.Date;

public class Auth_args {

    private String authToken,componentAppid,authTicket;

    private Date  authTokenTime;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getComponentAppid() {
        return componentAppid;
    }

    public void setComponentAppid(String componentAppid) {
        this.componentAppid = componentAppid;
    }

    public String getAuthTicket() {
        return authTicket;
    }

    public void setAuthTicket(String authTicket) {
        this.authTicket = authTicket;
    }

    public Date getAuthTokenTime() {
        return authTokenTime;
    }

    public void setAuthTokenTime(Date authTokenTime) {
        this.authTokenTime = authTokenTime;
    }
}

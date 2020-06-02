package com.yuyuedao.yydwechat.entity;

import java.util.Date;

public class DrawUser {
    private String openid,headUrl;
    private Integer invateCount;
    private Date createTime;


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }


    public Integer getInvateCount() {
        return invateCount;
    }

    public void setInvateCount(Integer invateCount) {
        this.invateCount = invateCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

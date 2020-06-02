package com.yuyuedao.yydwechat.entity;

import java.util.Date;


public class A_account {
	private String authorizerAppid,headImg,qrcodeUrl,authorizerAccessToken,authorizerRefreshToken
	,principalName,sm,sid,accountid,ysid,accountName,mchid,mchsecret,jsticket,apiTicket;
	private Date authorizerTokenTime,jsticketTime,apiTicketTime;

	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getAuthorizerAppid() {
		return authorizerAppid;
	}

	public void setAuthorizerAppid(String authorizerAppid) {
		this.authorizerAppid = authorizerAppid;
	}


	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public String getAuthorizerAccessToken() {
		return authorizerAccessToken;
	}

	public void setAuthorizerAccessToken(String authorizerAccessToken) {
		this.authorizerAccessToken = authorizerAccessToken;
	}

	public String getAuthorizerRefreshToken() {
		return authorizerRefreshToken;
	}

	public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
		this.authorizerRefreshToken = authorizerRefreshToken;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getYsid() {
		return ysid;
	}

	public void setYsid(String ysid) {
		this.ysid = ysid;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	public String getMchsecret() {
		return mchsecret;
	}

	public void setMchsecret(String mchsecret) {
		this.mchsecret = mchsecret;
	}

	public String getJsticket() {
		return jsticket;
	}

	public void setJsticket(String jsticket) {
		this.jsticket = jsticket;
	}

	public String getApiTicket() {
		return apiTicket;
	}

	public void setApiTicket(String apiTicket) {
		this.apiTicket = apiTicket;
	}

	public Date getAuthorizerTokenTime() {
		return authorizerTokenTime;
	}

	public void setAuthorizerTokenTime(Date authorizerTokenTime) {
		this.authorizerTokenTime = authorizerTokenTime;
	}

	public Date getJsticketTime() {
		return jsticketTime;
	}

	public void setJsticketTime(Date jsticketTime) {
		this.jsticketTime = jsticketTime;
	}

	public Date getApiTicketTime() {
		return apiTicketTime;
	}

	public void setApiTicketTime(Date apiTicketTime) {
		this.apiTicketTime = apiTicketTime;
	}
}

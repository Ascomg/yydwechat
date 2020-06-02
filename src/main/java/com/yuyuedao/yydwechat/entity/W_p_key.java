package com.yuyuedao.yydwechat.entity;

import java.util.Date;


public class W_p_key {
	private String sid;
	private String type,keyword,accountId,textMsg,newSid;
	private Date createTime;
	private Integer posterActivityId,drawActivityId;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getTextMsg() {
		return textMsg;
	}

	public void setTextMsg(String textMsg) {
		this.textMsg = textMsg;
	}

	public String getNewSid() {
		return newSid;
	}

	public void setNewSid(String newSid) {
		this.newSid = newSid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getPosterActivityId() {
		return posterActivityId;
	}

	public void setPosterActivityId(Integer posterActivityId) {
		this.posterActivityId = posterActivityId;
	}

	public Integer getDrawActivityId() {
		return drawActivityId;
	}

	public void setDrawActivityId(Integer drawActivityId) {
		this.drawActivityId = drawActivityId;
	}
}

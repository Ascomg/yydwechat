package com.yuyuedao.yydwechat.entity;

import java.util.Date;


public class W_p_news {
	private Integer sid,orders;
	private String sName;
	private Date createTime;
	private String newSid,itemSid,accountId;


	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getNewSid() {
		return newSid;
	}

	public void setNewSid(String newSid) {
		this.newSid = newSid;
	}

	public String getItemSid() {
		return itemSid;
	}

	public void setItemSid(String itemSid) {
		this.itemSid = itemSid;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}


	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}
}

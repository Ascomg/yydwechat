package com.yuyuedao.yydwechat.entity;

import java.beans.Transient;
import java.util.Date;
import java.util.Set;


public class X_userinfo {
	private Integer sId;
	private String userId;
	private String userName;
	private String userPwd;
	private String accountId;
	private Integer userRole;
	private String userFlag;
	private Date userRq;
	private String userSm;
	private String userAccountName;
	private String randCode;
	private Set<Role> roles;

	public Integer getsId() {
		return sId;
	}

	public void setsId(Integer sId) {
		this.sId = sId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Integer getUserRole() {
		return userRole;
	}

	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}

	public String getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	public Date getUserRq() {
		return userRq;
	}

	public void setUserRq(Date userRq) {
		this.userRq = userRq;
	}

	public String getUserSm() {
		return userSm;
	}

	public void setUserSm(String userSm) {
		this.userSm = userSm;
	}

	public String getUserAccountName() {
		return userAccountName;
	}

	public void setUserAccountName(String userAccountName) {
		this.userAccountName = userAccountName;
	}

	public String getRandCode() {
		return randCode;
	}

	public void setRandCode(String randCode) {
		this.randCode = randCode;
	}
}
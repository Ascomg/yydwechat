package com.yuyuedao.yydwechat.util;

import java.util.List;
import java.util.Map;

public class ResultDto {
	private boolean status;
	private String message;
	private List<Map<String,Object>> data;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	
}

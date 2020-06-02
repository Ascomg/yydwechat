package com.yuyuedao.yydwechat.util;

public class SelectItem {
	private String combid,combtext;

	public String getCombid() {
		return combid;
	}

	public void setCombid(String combid) {
		this.combid = combid;
	}

	public String getCombtext() {
		return combtext;
	}

	public void setCombtext(String combtext) {
		this.combtext = combtext;
	}

	public SelectItem() {
		super();
	}

	public SelectItem(String combtext, String combid) {
		super();
		this.combtext = combtext;
		this.combid = combid;
	}
	
	@Override
	public boolean equals(Object obj) {
		SelectItem temps = (SelectItem)obj;
		return this.getCombid().equals(temps.getCombid());
	}
}

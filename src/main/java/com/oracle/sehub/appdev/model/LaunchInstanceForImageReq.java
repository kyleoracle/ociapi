package com.oracle.sehub.appdev.model;

public class LaunchInstanceForImageReq {
	private String prefix;
	private Integer count;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}

package com.yz.javabean;

public class Mv {
	private String mv_url;
	private String mv;
	@Override
	public String toString() {
		return "Mv [mv_url=" + mv_url + ", mv=" + mv + "]";
	}
	public String getMv_url() {
		return mv_url;
	}
	public void setMv_url(String mv_url) {
		this.mv_url = mv_url;
	}
	public String getMv() {
		return mv;
	}
	public void setMv(String mv) {
		this.mv = mv;
	}
	
}

package com.yz.javabean;

public class Music {
	private String mu_url;
	private String music;
	public String getMu_url() {
		return mu_url;
	}
	public void setMu_url(String mu_url) {
		this.mu_url = mu_url;
	}
	public String getMusic() {
		return music;
	}
	public void setMusic(String music) {
		this.music = music;
	}
	@Override
	public String toString() {
		return "Music [mu_url=" + mu_url + ", music=" + music + "]";
	}

}

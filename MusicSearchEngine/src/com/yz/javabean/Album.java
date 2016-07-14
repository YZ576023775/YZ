package com.yz.javabean;

public class Album {
	private String al_url;
	private String album;
	public String getAl_url() {
		return al_url;
	}
	public void setAl_url(String al_url) {
		this.al_url = al_url;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	@Override
	public String toString() {
		return "Album [al_url=" + al_url + ", album=" + album + "]";
	}

}

package com.yz.javabean;

public class Artist {
	private String ar_url;
	private String artist;
	public String getAr_url() {
		return ar_url;
	}
	public void setAr_url(String ar_url) {
		this.ar_url = ar_url;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	@Override
	public String toString() {
		return "Artist [artist=" + artist + ", ar_url=" + ar_url + "]";
	}
	

}

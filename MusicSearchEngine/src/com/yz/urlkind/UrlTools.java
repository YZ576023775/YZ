package com.yz.urlkind;



public class UrlTools {
	String MusicUrlRgx;
	String MvUrlRgx;
	String ArtistUrlRgx;
	String AlbumUrlRgx;	
	
	public UrlTools() {
	}
	public UrlTools(String musicUrlRgx, String mvUrlRgx, String artistUrlRgx,
			String albumUrlRgx) {
		MusicUrlRgx = musicUrlRgx;
		MvUrlRgx = mvUrlRgx;
		ArtistUrlRgx = artistUrlRgx;
		AlbumUrlRgx = albumUrlRgx;
	}
	public String getMusicUrlRgx() {
		return MusicUrlRgx;
	}
	public void setMusicUrlRgx(String musicUrlRgx) {
		MusicUrlRgx = musicUrlRgx;
	}
	public String getMvUrlRgx() {
		return MvUrlRgx;
	}
	public void setMvUrlRgx(String mvUrlRgx) {
		MvUrlRgx = mvUrlRgx;
	}
	public String getArtistUrlRgx() {
		return ArtistUrlRgx;
	}
	public void setArtistUrlRgx(String artistUrlRgx) {
		ArtistUrlRgx = artistUrlRgx;
	}
	public String getAlbumUrlRgx() {
		return AlbumUrlRgx;
	}
	public void setAlbumUrlRgx(String albumUrlRgx) {
		AlbumUrlRgx = albumUrlRgx;
	}
	
	@Override
	public String toString() {
		return "UrlTools [MusicUrlRgx=" + MusicUrlRgx + ", MvUrlRgx="
				+ MvUrlRgx + ", ArtistUrlRgx=" + ArtistUrlRgx
				+ ", AlbumUrlRgx=" + AlbumUrlRgx + "]";
	}
	public  boolean isNotEmpty(String url){
		if (url!=null&&url.length()!=0) {
			return true;
		}else {
			return false;
		}
		
	}
	 public   boolean isArtistUrl(String url){
		 if (url.matches(ArtistUrlRgx)) {
			return true;
		}else {
			return false;
		}
		 
	 }
	 public  boolean isMusicUrl(String url){
		 if(url.matches(MusicUrlRgx)){
			 return true;
		 }else {
			 return false;
			
		}
	 }
	 public  boolean isMvUrl(String url){
		 if(url.matches(MvUrlRgx)){
			 return true;
		 }else {
			 return false;
			 
		 }
	 }
	 public  boolean isAlbumUrl(String url){
		 if(url.matches(AlbumUrlRgx)){
			 return true;
		 }else {
			 return false;
			 
		 }
	 }
}

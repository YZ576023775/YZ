package com.yz.crawl;

import com.yz.urlkind.UrlTools;

public class CopyOfMusicCrawlStart {
	static int exitCount = 0;

	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			new Thread(new MyRunnable1()).start();
			
		}
			
		}
		
	}
class MyRunnable1 implements Runnable{
	String crawDepth = "4"; // 爬虫深度
	String threadCount = "10"; // 线程数量
	String max="200"; //最大爬取url数量
	String rootUrl="http://music.163.com";
	String MusicUrlRgx="http://music\\.163\\.com/#/song\\?id=.*";
	String MvUrlRgx="http://music\\.163\\.com/#/mv\\?id=.*";
	String ArtistUrlRgx="http://music\\.163\\.com/#/artist\\?id=.*";
	String AlbumUrlRgx="http://music\\.163\\.com/#/album\\?id=.*";	 
	//UrlTools urlTools=new UrlTools(MusicUrlRgx, MvUrlRgx, ArtistUrlRgx, AlbumUrlRgx);
	String encodeUrl="";
//	String encodeUrl="http://music\\.163\\.com/.*";
	String ParseUrlRgx="a[href]";
//	String crawDepth = "4"; // 爬虫深度
//	String threadCount = "10"; // 线程数量
//	String max="200"; //最大爬取url数量
//	String rootUrl="http://www.kuwo.cn";
//	String MusicUrlRgx="http://www\\.kuwo\\.cn/yinyue/.*(\\?catalog=.*)?";
//	String MvUrlRgx="http://www\\.kuwo\\.cn/mv/.*/";
//	String ArtistUrlRgx="http://www\\.kuwo\\.cn/artist/content\\?name=.*||http://www\\.kuwo\\.cn/mingxing/.*/";
//	String AlbumUrlRgx="http://www\\.kuwo\\.cn/album/.*/";	 
//	//UrlTools urlTools=new UrlTools(MusicUrlRgx, MvUrlRgx, ArtistUrlRgx, AlbumUrlRgx);
//	String encodeUrl="http://www\\.kuwo\\.cn/(artist|mingxing)/.*";
//	String ParseUrlRgx="a[href~=http://www\\.kuwo\\.cn.*]";
	
	
	@Override
	public String toString() {
		return "MyRunnable [rootUrl=" + rootUrl + ", threadCount="
				+ threadCount + ", crawDepth=" + crawDepth + ", max=" + max
				+ ", encodeUrl=" + encodeUrl + ", MusicUrlRgx=" + MusicUrlRgx
				+ ", MvUrlRgx=" + MvUrlRgx + ", ArtistUrlRgx=" + ArtistUrlRgx
				+ ", AlbumUrlRgx=" + AlbumUrlRgx + ", ParseUrlRgx="
				+ ParseUrlRgx + "]";
	}

	@Override
	public void run() {
		
		
		 WebCrawler wc = new WebCrawler();
		// wc.addUrl("http://www.126.com", 1);
		// wc.addUrl("http://music.163.com/#", 1);
		// wc.addUrl("http://y.qq.com/#", 1);
		// wc.addUrl("http://music.baidu.com/", 1);
	    
		wc.addUrl(rootUrl, "", 1);
		wc.setRootUrl(rootUrl);
		wc.setThreadCount(Integer.parseInt(threadCount));
		wc.setCrawDepth(Integer.parseInt(crawDepth));
		wc.setMax(Integer.parseInt(max));
		wc.setEncodeUrl(encodeUrl);
		wc.setParseUrlRgx(ParseUrlRgx);
//		UrlTools urlTools = new UrlTools(MusicUrlRgx, MvUrlRgx, ArtistUrlRgx, AlbumUrlRgx);
		UrlTools urlTools = new UrlTools();
		urlTools.setAlbumUrlRgx(AlbumUrlRgx);
		urlTools.setArtistUrlRgx(ArtistUrlRgx);
		urlTools.setMusicUrlRgx(MusicUrlRgx);
		urlTools.setMvUrlRgx(MvUrlRgx);
		wc.setUrlTools(urlTools);
		System.out.println(wc);
		long start = System.currentTimeMillis();
		System.out.println("开始爬虫.........................................");
		wc.begin();
		while (true) {
			if (wc.notCrawlurlSet.isEmpty()
					&& Thread.activeCount() == 1
					|| wc.count == wc.threadCount) {
				long end = System.currentTimeMillis();
				System.out
						.println("总共爬了" + wc.allurlSet.size() + "个网页");
				System.out.println("总共耗时" + (end - start) / 1000 + "秒");
				synchronized (CopyOfMusicCrawlStart.class) {

					CopyOfMusicCrawlStart.exitCount++;
					if (CopyOfMusicCrawlStart.exitCount == 1) {
						System.exit(1);
					};
				}
				// System.exit(1);
				// break;
			}

		}
	}
	
}
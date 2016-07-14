package com.yz.crawl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.yz.urlkind.UrlTools;

public class NewMusicCrawlStart {
	 final static Logger logger = Logger.getLogger(NewMusicCrawlStart.class);
	 static int size;
	static int exitCount = 0;

	public static void main(String[] args) {
		
		SAXReader reader = new SAXReader();               
		Document document = null;
			try {
				document = reader.read(NewMusicCrawlStart.class.getClassLoader().getResourceAsStream("crawls.xml"));
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			List<Element> list = new ArrayList<Element>();
			list= root.elements();
			size=list.size();
			System.out.println(list.size());
			ExecutorService es = Executors.newFixedThreadPool(size);
		for (int i = 0; i < size; i++) {
			Element ele = list.get(i);
			String path = ele.attributeValue("resource");
			Document doc=null;
			Element element=null;
			try {
				doc = reader.read(NewMusicCrawlStart.class.getClassLoader().getResourceAsStream(path));
				element = doc.getRootElement();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println(element.elementTextTrim("rootUrl"));
			
			MyRunnable2 mr = new MyRunnable2();
			mr.setRootUrl(element.elementTextTrim("rootUrl"));
			mr.setUserAgent(element.elementTextTrim("userAgent"));
			mr.setMaxConnectionTime(element.elementTextTrim("maxConnectionTime"));
			mr.setMaxReadTime(element.elementTextTrim("maxReadTime"));
			mr.setThreadCount(element.elementTextTrim("threadCount"));
			mr.setCrawDepth(element.elementTextTrim("crawDepth"));
			mr.setMax(element.elementTextTrim("max"));
			mr.setEncodeUrl(element.elementTextTrim("encodeUrl"));
			mr.setMusicUrlRgx(element.element("urlToolsRgx").elementTextTrim("MusicUrlRgx"));
			mr.setMvUrlRgx(element.element("urlToolsRgx").elementTextTrim("MvUrlRgx"));
			mr.setArtistUrlRgx(element.element("urlToolsRgx").elementTextTrim("ArtistUrlRgx"));
			mr.setAlbumUrlRgx(element.element("urlToolsRgx").elementTextTrim("AlbumUrlRgx"));
			mr.setParseUrlRgx(element.elementTextTrim("ParseUrlRgx"));
			mr.setReadPageBean(element.elementTextTrim("readPageBean"));
			System.out.println(mr);
			es.execute(mr);
			
		}
		es.shutdown();
		
	}
}
class MyRunnable2 implements Runnable{
	private String rootUrl;
	private String userAgent;
	private String maxConnectionTime;
	private String maxReadTime;
	private String threadCount;
	private String crawDepth;
	private String max;
	private String encodeUrl;
	private String MusicUrlRgx;
	private String MvUrlRgx;
	private String ArtistUrlRgx;
	private String AlbumUrlRgx;
	private String ParseUrlRgx;
	private String readPageBean;




	@Override
	public String toString() {
		return "MyRunnable [rootUrl=" + rootUrl + ", userAgent=" + userAgent
				+ ", maxConnectionTime=" + maxConnectionTime + ", maxReadTime="
				+ maxReadTime + ", threadCount=" + threadCount + ", crawDepth="
				+ crawDepth + ", max=" + max + ", encodeUrl=" + encodeUrl
				+ ", MusicUrlRgx=" + MusicUrlRgx + ", MvUrlRgx=" + MvUrlRgx
				+ ", ArtistUrlRgx=" + ArtistUrlRgx + ", AlbumUrlRgx="
				+ AlbumUrlRgx + ", ParseUrlRgx=" + ParseUrlRgx
				+ ", readPageBean=" + readPageBean + "]";
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	public void setMaxConnectionTime(String maxConnectionTime) {
		this.maxConnectionTime = maxConnectionTime;
	}
	
	public void setMaxReadTime(String maxReadTime) {
		this.maxReadTime = maxReadTime;
	}
	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	public void setThreadCount(String threadCount) {
		this.threadCount = threadCount;
	}

	public void setCrawDepth(String crawDepth) {
		this.crawDepth = crawDepth;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public void setEncodeUrl(String encodeUrl) {
		this.encodeUrl = encodeUrl;
	}

	public void setMusicUrlRgx(String musicUrlRgx) {
		MusicUrlRgx = musicUrlRgx;
	}

	public void setMvUrlRgx(String mvUrlRgx) {
		MvUrlRgx = mvUrlRgx;
	}

	public void setArtistUrlRgx(String artistUrlRgx) {
		ArtistUrlRgx = artistUrlRgx;
	}

	public void setAlbumUrlRgx(String albumUrlRgx) {
		AlbumUrlRgx = albumUrlRgx;
	}

	public void setParseUrlRgx(String parseUrlRgx) {
		ParseUrlRgx = parseUrlRgx;
	}
	

	public void setReadPageBean(String readPageBean) {
		this.readPageBean = readPageBean;
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
		wc.setUserAgent(userAgent);
		wc.setMaxConnectionTime(Integer.parseInt(maxConnectionTime));
		wc.setMaxReadTime(Integer.parseInt(maxReadTime));
		wc.setThreadCount(Integer.parseInt(threadCount));
		wc.setCrawDepth(Integer.parseInt(crawDepth));
		wc.setMax(Integer.parseInt(max));
		wc.setEncodeUrl(encodeUrl);
		wc.setParseUrlRgx(ParseUrlRgx);
		wc.setReadPageBean(readPageBean);
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
				synchronized (NewMusicCrawlStart.class) {

					NewMusicCrawlStart.exitCount++;
					if (NewMusicCrawlStart.exitCount == NewMusicCrawlStart.size) {
						System.exit(1);
					}else {
						break;
					}
				}
				// System.exit(1);
				// break;
			}

		}
	}
	
}
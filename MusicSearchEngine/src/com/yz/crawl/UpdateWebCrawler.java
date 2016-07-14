package com.yz.crawl;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.yz.javabean.Album;
import com.yz.javabean.Artist;
import com.yz.javabean.Music;
import com.yz.javabean.Mv;
import com.yz.lucene.LuceneDao;
import com.yz.readpage.ReadPage;
import com.yz.urlkind.UrlTools;

/*
 * 下载
 * 		能下载 并 符合保存条件的保存
 * 解析：选择a【href】标签
 * 		去除url空格\s\r\n\t
 * 		转成abs绝对路径
 * 		提取当前域名的url放入  剔除javascript:
 */

public class UpdateWebCrawler{
	private static  Logger logger = Logger.getLogger(UpdateWebCrawler.class);
	Set<String> allurlSet = new HashSet<String>();// 所有的网页url，需要更高效的去重可以考虑HashSet
	ArrayList<String> notCrawlurlSet = new ArrayList<String>();// 未爬过的网页url
	HashMap<String, Integer> depth = new HashMap<String, Integer>();// 所有网页的url深度
	HashMap<String, String> messsageDesc = new HashMap<String, String>();// 所有网页的url描述信息
	int count = 0; // 表示有多少个线程处于wait状态
	public   final Object signal = new Object(); // 线程间通信变量 error静态用于整个类互斥
	

	String userAgent="Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
	int crawDepth = 4; // 爬虫深度
	int threadCount = 10; // 线程数量
	int max=200; //最大爬取url数量

	int maxConnectionTime=20000;
	int maxReadTime=20000;

	String rootUrl="http://www.kuwo.cn";
//	String MusicUrlRgx="http://www\\.kuwo\\.cn/yinyue/.*(\\?catalog=.*)?";
//	String MvUrlRgx="http://www\\.kuwo\\.cn/mv/.*/";
//	String ArtistUrlRgx="http://www\\.kuwo\\.cn/artist/content\\?name=.*||http://www\\.kuwo\\.cn/mingxing/.*/";
//	String AlbumUrlRgx="http://www\\.kuwo\\.cn/album/.*/";	 
//	UrlTools urlTools=new UrlTools(MusicUrlRgx, MvUrlRgx, ArtistUrlRgx, AlbumUrlRgx);
	String encodeUrl="http://www\\.kuwo\\.cn/(artist|mingxing)/.*";
	String ParseUrlRgx="a[href]";
	UrlTools urlTools;
	String readPageBean;
	




	@Override
	public String toString() {
		return "WebCrawler [count=" + count + ", userAgent=" + userAgent
				+ ", crawDepth=" + crawDepth + ", threadCount=" + threadCount
				+ ", max=" + max + ", maxConnectionTime=" + maxConnectionTime
				+ ", maxReadTime=" + maxReadTime + ", rootUrl=" + rootUrl
				+ ", encodeUrl=" + encodeUrl + ", ParseUrlRgx=" + ParseUrlRgx
				+ ", urlTools=" + urlTools + ", readPageBean=" + readPageBean
				+ "]";
	}


	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	
	public void setMaxConnectionTime(int maxConnectionTime) {
		this.maxConnectionTime = maxConnectionTime;
	}
	
	
	public void setMaxReadTime(int maxReadTime) {
		this.maxReadTime = maxReadTime;
	}

	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	public void setUrlTools(UrlTools urlTools) {
		this.urlTools = urlTools;
	}

	public void setCrawDepth(int crawDepth) {
		this.crawDepth = crawDepth;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public void setMax(int max) {
		this.max = max;
	}


	public void setEncodeUrl(String encodeUrl) {
		this.encodeUrl = encodeUrl;
	}


	public void setParseUrlRgx(String parseUrlRgx) {
		ParseUrlRgx = parseUrlRgx;
	}


	public void setReadPageBean(String readPageBean) {
		this.readPageBean = readPageBean;
	}


	public static void main(String[] args) {
		final UpdateWebCrawler wc = new UpdateWebCrawler();
//		 wc.addUrl("http://www.126.com", 1);
		// wc.addUrl("http://music.163.com/#", 1);
//		wc.addUrl("http://y.qq.com/#", 1);
//		wc.addUrl("http://music.baidu.com/", 1);
		wc.addUrl("http://www.kuwo.cn","",1);
		long start = System.currentTimeMillis();
		System.out.println("开始爬虫.........................................");
		wc.begin();
		while (true) {
			if (wc.notCrawlurlSet.isEmpty() && Thread.activeCount() == 1
					|| wc.count == wc.threadCount) {
				long end = System.currentTimeMillis();
				System.out.println("总共爬了" + wc.allurlSet.size() + "个网页");
				System.out.println("总共耗时" + (end - start) / 1000 + "秒");
				System.exit(1);
				// break;
			}
		}
	}

	 void begin() {
		for (int i = 0; i < threadCount; i++) {
			new Thread(new Runnable() {
				public void run() {
					// System.out.println("当前进入"+Thread.currentThread().getName());
					// while(!notCrawlurlSet.isEmpty()){
					// ----------------------------------（1）
					// String tmp = getAUrl();
					// crawler(tmp);
					// }
					while (true) {
						// System.out.println("当前进入"+Thread.currentThread().getName());
						String tmp = getAUrl();
						if (tmp != null ) {
							crawler(tmp);
						} else {
							synchronized (signal) { // ------------------（2）
								try {
									count++;
									System.out.println("当前有" + count + "个线程在等待");
									signal.wait();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									logger.debug(e.getMessage());
								}
							}

						}
					}
				}
			}, "thread-" + i).start();
		}
	}

	public  String getAUrl() {
		synchronized(Object.class){		//get 和 add不互斥
		if (notCrawlurlSet.isEmpty())
			return null;
		String tmpAUrl;
		// synchronized(notCrawlurlSet){
		tmpAUrl = notCrawlurlSet.get(0);
		notCrawlurlSet.remove(0);
		// }
		return tmpAUrl;
	}
	}

	// public synchronized boolean isEmpty() {
	// boolean f = notCrawlurlSet.isEmpty();
	// return f;
	// }

	public synchronized void addUrl(String url,String desc, int d) {
//		if (urlTools.isNotEmpty(url)&&urlTools.isNotEmpty(desc)){
		/*
		 * 补充要保存的url 然而不用解析 故不用add
		 */
			if (allurlSet.contains(url)&&!urlTools.isNotEmpty(messsageDesc.get(url))
					&&urlTools.isNotEmpty(desc)){
//			allurlSet.remove(url);
				if (urlTools.isArtistUrl(url)) {
					Artist artist = new Artist();
					artist.setArtist(desc);
					artist.setAr_url(url);
					System.out.println("补充解析过没保存的："+artist);
				}else if (urlTools.isMusicUrl(url)) {
					Music music = new Music();
					music.setMu_url(url);
					music.setMusic(desc);
					System.out.println("补充解析过没保存的："+music);
				}else if (urlTools.isMvUrl(url)) {
					Mv mv = new Mv();
					mv.setMv(desc);
					mv.setMv_url(url);
					System.out.println("补充解析过没保存的："+mv);
				}else if (urlTools.isAlbumUrl(url)) {
					Album album = new Album();
					album.setAl_url(url);
					album.setAlbum(desc);
					System.out.println("补充解析过没保存的："+album);
					
				}
		}else {
			notCrawlurlSet.add(url);
			allurlSet.add(url);
			depth.put(url, d);
			messsageDesc.put(url,desc);
		}
	}

	/*
	 * //爬网页sUrl public void crawler(String sUrl){ Document document=null; try {
	 * int d = depth.get(sUrl); if(d<crawDepth){ document = Jsoup.connect(sUrl)
	 * .userAgent("Mozilla") .timeout(20000) .post();
	 * System.out.println("爬网页"+sUrl
	 * +"成功，深度为"+d+" 是由线程"+Thread.currentThread().getName()+"来爬");
	 * //解析网页内容，从中提取链接 parseContext(document.toString(),d+1); } } catch
	 * (IOException e) { // TODO Auto-generated catch block
	 * System.out.println("url:"+sUrl+"-获取html出错！"); e.printStackTrace(); }
	 * 
	 * 
	 * 
	 * }
	 */
	public void crawler(String sUrl) {
		int d = depth.get(sUrl);
		String m = messsageDesc.get(sUrl);
		if (sUrl.matches(encodeUrl)/*&&(
				sUrl.contains("[")||sUrl.contains("]"))*/) {
	/*		String[] s = (sUrl.split("/"));
			String encode=null;
			try {
				if (s[4].matches("(\\w)*\\?")) {
					
					encode = URLEncoder.encode(s[4],"utf-8");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error("编码失败", e);
			}
			sUrl = s[0]+"//"+s[2]+"/"+s[3]+"/"+encode+"/";
		}*/
			Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]+");
			Matcher matcher = pattern.matcher(sUrl);
			while (matcher.find()) {
				// System.out.println(matcher.group());
				// System.out.println(URLEncoder.encode(matcher.group(), "utf-8"));
				try {
					/*System.out.println("encode:" + encode);
					System.out.println(sUrl.replaceFirst(group, encode));*/
					sUrl=sUrl.replace(matcher.group(), URLEncoder.encode(matcher.group(), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					logger.error("编码失败！", e);
				}
			}
			}
//		logger.info("sUrl*********"+sUrl+"depth*********"+depth+"d*********"+d+"\n");
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom()
			        .setSocketTimeout(maxReadTime)
			        .setConnectTimeout(maxConnectionTime)
			        .build();
			
			// 创建httpget.
			HttpGet HttpGet = new HttpGet(sUrl);
			HttpGet.setConfig(requestConfig);
			HttpGet.setHeader("User-Agent",userAgent);
//			//请求超时
//			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000); 
//			//读取超时
//			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(HttpGet);

			// 获取响应实体
			HttpEntity entity = response.getEntity();
//			 System.out.println(response.getStatusLine());
			String entityContent = EntityUtils.toString(entity, "utf-8");
			if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				// 打印响应内容长度
				// System.out.println("Response content length: " +
				// entity.getContentLength());
				// 打印响应内容
				// System.out.println("Response content: " +
				// EntityUtils.toString(entity));
//				System.out.println("可下载页面的：url	"+sUrl+"m	"+m);
				if (m!=null&&m.length()!=0) {
					LuceneDao luceneDao = new LuceneDao();
				if (urlTools.isArtistUrl(sUrl)) {
					Artist artist = new Artist();
					artist.setArtist(m);
					artist.setAr_url(sUrl);
					System.out.println(artist);
					luceneDao.updateByUrl(sUrl, artist);
				}else if (urlTools.isMusicUrl(sUrl)) {
					Music music = new Music();
					music.setMu_url(sUrl);
					music.setMusic(m);
					System.out.println(music);
					luceneDao.updateByUrl(sUrl, music);
				}else if (urlTools.isMvUrl(sUrl)) {
					Mv mv = new Mv();
					mv.setMv(m);
					mv.setMv_url(sUrl);
					System.out.println(mv);
					luceneDao.updateByUrl(sUrl, mv);
				}else if (urlTools.isAlbumUrl(sUrl)) {
					Album album = new Album();
					album.setAl_url(sUrl);
					album.setAlbum(m);
					System.out.println(album);
					luceneDao.updateByUrl(sUrl, album);
					
				}
				}
				/*System.out.println("爬网页" + sUrl+"\t"+m+"\n"+ "成功，深度为" + d + " 是由线程"
						+ Thread.currentThread().getName() + "来爬");*/
				logger.info("爬网页" + sUrl+"\t"+m+"\n"+ "成功，深度为" + d + " 是由线程"
						+ Thread.currentThread().getName() + "来爬");
				// 解析网页内容，从中提取链接
				if(d<crawDepth){
					parseContext(entityContent, d+1);
				}
			}
		} catch (Exception e) {
			logger.error("下载"+sUrl+"出错！"+e.getMessage());
		}/*finally{
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}*/

	}
/*
	
	 * //爬网页sUrl public void crawler(String sUrl){ URL url; try { url = new
	 * URL(sUrl); // HttpURLConnection urlconnection =
	 * (HttpURLConnection)url.openConnection(); URLConnection urlconnection =
	 * url.openConnection(); urlconnection.addRequestProperty("User-Agent",
	 * "Mozilla"); InputStream is = url.openStream(); BufferedReader bReader =
	 * new BufferedReader(new InputStreamReader(is)); StringBuffer sb = new
	 * StringBuffer();//sb为爬到的网页内容 String rLine = null;
	 * while((rLine=bReader.readLine())!=null){ sb.append(rLine);
	 * sb.append("/r/n"); }
	 * 
	 * int d = depth.get(sUrl);
	 * System.out.println("爬网页"+sUrl+"成功，深度为"+d+" 是由线程"+
	 * Thread.currentThread().getName()+"来爬"); if(d<crawDepth){ //解析网页内容，从中提取链接
	 * parseContext(sb.toString(),d+1); } // System.out.println(sb.toString());
	 * 
	 * 
	 * } catch (IOException e) { // crawlurlSet.add(sUrl); //
	 * notCrawlurlSet.remove(sUrl);
	 * logger.error("下载"+sUrl+"失败！\n"+e.getMessage()"错误信息："+e.getMessage()); } }
	 */

	/*
	 * private static String trim(String s, int width) { if (s.length() > width)
	 * return s.substring(0, width-1) + "."; else return s; } private static
	 * void print(String msg, Object... args) {
	 * System.out.println(String.format(msg, args)); }
	 */
	private boolean isHostUrl(String url) {
			if (url.matches("^[a-zA-z]+://.*")&&url.startsWith(rootUrl)) {
				return true;
			} else {
				return false;
			}
	}
	
	// 从context提取url地址
	public void parseContext(String context, int dep) {	//*********************************************
		if (urlTools.isNotEmpty(readPageBean)) {
			try {
					
				ReadPage readPage = (ReadPage) Class.forName(readPageBean).newInstance();
				context = readPage.solveEmptyAttr(context);
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		Document document = Jsoup.parse(context);
		Elements elements = document.select(ParseUrlRgx);
		// Elements elements =
		// document.select("a[href~=http://www\\.kuwo\\.cn/(artist|yinyue|mv)/.*]");
		// Elements elements =
		// document.select("a[href~=^http://www.kuwo.cn|music.baidu.com/artist|yinyue|song/*");
		// Elements elements =
		// document.select("a[href~=song|artist|album|mv|songlist//*]");
		for (Element link : elements) {
//			 System.out.println("解析出来的a标签	"+link.toString());
			 String href = link.attr("href").replaceAll( "\\s*||\t|\r|\n", "");
//			String href = link.attr("abs:href");	//error	必须用jsoup链接解析！！
			/*	if (rootUrl.startsWith("http://music.163.com")) {
					if (href.matches("https?.*|javascript:.*")) {
					} else {
						if (href.equals("#")) {
							href=rootUrl+"/#";
						} else {
							href=rootUrl+"/#"+href;
						}
					}
				}else {
					href =href.matches("https?.*|javascript:.*")?href:(rootUrl+href);
					
				}*/
				try {
					href =href.matches("https?.*|javascript:.*|javascrip.*|xshare.*")?href:(new URL(new URL(rootUrl), href).toString());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			 System.out.println("加了abs后url	"+href);
			String desc = link.text();
			String desc1 = link.attr("title");
//			if (href.matches("http://www\\.xiami\\.com/album/[0-9]+.*")) {
//				
//				System.out.println(link.toString()+":::::"+"title*********"+desc1+"text*****"+desc);
//			}
			// System.out.println("相对:"+str1+"绝对:"+str);


			if (/*
			 * str.matches("http://www\\.kuwo\\.cn/(artist|yinyue|mv)/.*")
			 * &&
			 */isHostUrl(href)) { // 取出一些不是url的地址
//				System.out.println("解析出来的为HostUrl的a标签	"+href);
				if (!allurlSet.contains(href)&&allurlSet.size()<max) {
					if (desc1!=null&&!desc1.equals("")&&desc1.length()!=0) {
						addUrl(href,desc1,dep);// 加入一个新的url
					}else {
						addUrl(href,desc,dep);// 加入一个新的url
					}
					if (count > 0) { // 如果有等待的线程，则唤醒
						synchronized (signal) { // ---------------------（2）
							count--;
							signal.notify();
						}
					}

				}
			}
		}

	}


}
/*
 * //从context提取url地址 public void parseContext(String context,int dep) { String
 * regex = "<a href.*?/a>"; // String regex = "<title>.*?</title>"; String s =
 * "fdfd<title>我 是</title><a href=\"http://www.iteye.com/blogs/tag/Google\">Google</a>fdfd<>"
 * ; // String regex ="http://.*?>"; Pattern pt = Pattern.compile(regex);
 * Matcher mt = pt.matcher(context); while (mt.find()) { //
 * System.out.println(mt.group()); Matcher myurl =
 * Pattern.compile("href=\".*?\"").matcher( mt.group()); while(myurl.find()){
 * String str = myurl.group().replaceAll("href=\"|\"", ""); //
 * System.out.println("网址是:"+ str); if(str.contains("http:")){ //取出一些不是url的地址
 * if(!allurlSet.contains(str)){ addUrl(str, dep);//加入一个新的url if(count>0){
 * //如果有等待的线程，则唤醒 synchronized(signal) { //---------------------（2） count--;
 * signal.notify(); } }
 * 
 * } } } } }
 */

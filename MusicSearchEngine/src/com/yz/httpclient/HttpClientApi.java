package com.yz.httpclient;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.yz.readpagebean.XiamiReadPage;


public class HttpClientApi {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		HttpPost httpPost = new HttpPost("http://music.163.com/#");
//		List<NameValuePair> list = new ArrayList<NameValuePair>();
//		list.add(new BasicNameValuePair("naeme","value"));
////		httpPost.setEntity((HttpEntity) list);
//		try {
//			HttpEntity httpEntity = new UrlEncodedFormEntity(list,"utf-8");
//			httpPost.setEntity(httpEntity);
//			CloseableHttpResponse rs = httpClient.execute(httpPost);
//			System.out.println(EntityUtils.toString(rs.getEntity(), "utf-8"));
//			Document document =Jsoup.parse(EntityUtils.toString(rs.getEntity()));
//			Elements elements = document.select("a[href]");
//			System.out.println(elements.toString());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Document doc = Jsoup.connect("http://www.xiami.com/").timeout(3000).userAgent("Mozilla").get();
		Elements eles = doc.select("div[class=info]");
		if (!eles.isEmpty()) {
			for (Element element : eles) {
//				System.out.println("div[class=info]	"+element);
				Element aWithDesc = element.select("a[href~=/album/[0-9]+.*]").first();
//				System.out.println("aWithDesc	"+aWithDesc);
				if (aWithDesc!=null) {
					Element divImg = element.firstElementSibling();
//					System.out.println("divImg	"+divImg);
					if (divImg!=null) {
						Element aWithoutDesc = divImg.select("a[href~=/album/[0-9]+.*]").first();
//						System.out.println("aWithoutDesc	"+aWithoutDesc);
						aWithoutDesc.attr("title", aWithDesc.text());
					}
					
				}
			}
//			for (Element element : doc.select("a[href]")) {
//				System.out.println(element);
//			}
			System.out.println(XiamiReadPage.class);
			System.out.println(Class.forName("com.yz.readpagebean.XiamiReadPage"));
		}
//		//绝对路径
//		String absolutePath = "http://www.xiami.com";
//		//相对路径
//		String relativePath = "/song/1775880892?spm=a1z1s.6659513.0.0.72G6AN";
//
//		//以下方法对相对路径进行转换
//		URL absoluteUrl = new URL(absolutePath);
//
//		URL parseUrl = new URL(absoluteUrl ,relativePath );
//
//		//最终结果
//		System.out.println("相对路径转换后的绝对路径:" + parseUrl.toExternalForm()); 
//		ExecutorService es = Executors.newFixedThreadPool(2);
//		for (int i = 0; i < 10; i++) {
//			final int a = i;
//			es.execute(new Runnable() {
//				
//				@Override
//				public void run() {
//System.out.println(a);					
//				}
//			});
//		}
//		es.shutdown();
		
	}
	}


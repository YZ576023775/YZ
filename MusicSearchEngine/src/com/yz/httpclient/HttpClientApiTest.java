package com.yz.httpclient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class HttpClientApiTest {

	@Test
	public void test() throws UnsupportedEncodingException {
		/*
		 * Pattern pattern =
		 * Pattern.compile("http://www\\.kuwo\\.cn/(artist|yinyue|mv)/.*");
		 * Matcher matcher =
		 * pattern.matcher("http://www.kuwo.cn/artist/3638735/"); while
		 * (matcher.find()) { System.out.println("asd");
		 * System.out.println(matcher.group());
		 * 
		 * } System.out.println(Arrays.toString(
		 * "http://www.kuwo.cn/mingxing/BTOB%26%E6%B3%AB%E9%9B%85%5B4Minute%5D/"
		 * .split("/")));
		 * 
		 * String url = "http://www.kuwo.cn/mingxing/BTOB&泫雅[4Minute]/"; if
		 * (url.matches("http://www\\.kuwo\\.cn/(artist|mingxing)/.*")&&(
		 * url.contains("[")||url.contains("]"))) { String[] s =
		 * ("http://www.kuwo.cn/mingxing/钟铉[SHINee]/".split("/")); String encode
		 * = URLEncoder.encode(s[4],"utf-8"); String string =
		 * s[0]+"//"+s[2]+"/"+s[3]+"/"+encode+"/";
		 * System.out.println("___"+string);
		 * System.out.println(URLDecoder.decode(
		 * "http://www.kuwo.cn/artist/content%3Fname%3D%E7%8E%87%E6%99%BA%26HaNi%5BEXID%5D/"
		 * , "utf-8"));
		 * 
		 * } System.out.println("是爱迪生".matches("[\u4e00-\u9fa5]*")); //
		 * System.out.println(matchRegex("[\u4e00-\u9fa5]*",
		 * "http://www.kuwo.cn/artist/content?name=率智&HaNi[EXID]/"));
		 */

		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]+");
		String string = "http://www.kuwo.cn/artist/content?name=率智&HaNi[EXID]/";
		Matcher matcher = pattern.matcher(string);
		while (matcher.find()) {
			// System.out.println(matcher.group());
			// System.out.println(URLEncoder.encode(matcher.group(), "utf-8"));
			String group = matcher.group();
			try {
				System.out.println(string.replaceFirst(matcher.group(), URLEncoder.encode(group, "utf-8")));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("http://www.kuwo.cn/artist/content?name=率智&HaNi[EXID]/".replaceFirst("[\u4e00-\u9fa5]+",
			// "assad"));
		}
	}
}

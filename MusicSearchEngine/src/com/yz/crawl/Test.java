package com.yz.crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {
	public static void main(String[] args) throws IOException {
			Map<String,String> map = new LinkedHashMap<String , String>();
			map.put("c","3" );
			map.put("d","2" );
			map.put("s","4" );
			map.put("a","1" );
			System.out.println(map);
			System.out.println(map.keySet());
	}


}
package com.yz.readpagebean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.yz.readpage.ReadPage;

public class BaiduReadPage implements ReadPage{

	@Override
	public String solveEmptyAttr(String context) {
		Document doc = Jsoup.parse(context);
		Elements eles = doc.select("a[class=mv-icon]");
		if (!eles.isEmpty()) {
			for (Element element : eles) {
				element.attr("title", element.firstElementSibling().attr("title"));
			}
			context=doc.toString();
		}
		return context;
	}

}

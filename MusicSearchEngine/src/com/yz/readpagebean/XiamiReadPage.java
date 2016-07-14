package com.yz.readpagebean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.yz.readpage.ReadPage;

public class XiamiReadPage implements ReadPage{

	@Override
	public String solveEmptyAttr(String context) {
		Document doc = Jsoup.parse(context);
		Elements eles = doc.select("div[class=info]");
		if (!eles.isEmpty()) {
			for (Element element : eles) {
				Element aWithDesc = element.select("a[href~=/album/[0-9]+.*]").first();
				if (aWithDesc!=null) {
					Element divImg = element.firstElementSibling();
					if (divImg!=null) {
						Element aWithoutDesc = divImg.select("a[href~=/album/[0-9]+.*]").first();
						aWithoutDesc.attr("title", aWithDesc.text());
					}
					
				}
			}
			context=doc.toString();
		}
		return context;
	}

}

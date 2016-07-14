package com.yz.lucene;

import java.util.Map;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

public class MapToDocument {
	public static Document objectToDocument(Map<String,String> map){
		Document document=new Document();
		Set<String> set = map.keySet();
		if (set.contains("album")) {
			
			TextField albumField=new TextField("album", map.get("album"),Store.YES);
//		contentField.setBoost(3f);
			StringField al_urlField=new StringField("al_url", map.get("al_url"),Store.YES);
			document.add(albumField);
			document.add(al_urlField);
		}else if (set.contains("artist")) {
			TextField artistField=new TextField("artist",map.get("artist"),Store.YES);
//		contentField.setBoost(3f);
			StringField ar_urlField=new StringField("ar_url", map.get("ar_url"), Store.YES);
			document.add(artistField);
			document.add(ar_urlField);
		}else if (set.contains("music")) {
			TextField musicField=new TextField("music", map.get("music"),Store.YES);
//		contentField.setBoost(3f);
			StringField mu_urlField=new StringField("mu_url", map.get("mu_url"), Store.YES);
			document.add(musicField);
			document.add(mu_urlField);
			
		}else if (set.contains("mv")) {
			TextField mvField=new TextField("mv", map.get("mv"),Store.YES);
//		contentField.setBoost(3f);
			StringField mv_urlField=new StringField("mv_url", map.get("mv_url"), Store.YES);
			document.add(mvField);
			document.add(mv_urlField);
			
		}
		return document;
	}

}

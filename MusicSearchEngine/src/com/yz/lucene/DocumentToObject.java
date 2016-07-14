package com.yz.lucene;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;

import com.yz.javabean.Album;
import com.yz.javabean.Artist;
import com.yz.javabean.Music;
import com.yz.javabean.Mv;

/*8
 * 对象与索引库document 之间的转化
 * 
 */
public class DocumentToObject {
	
	
	public static Object documentToObject(Document document){
		Map<String, String> map = new HashMap<String, String>();
		List<IndexableField> listIndexableFields =  document.getFields();
		for (IndexableField indexableField : listIndexableFields) {
			String fieldName = indexableField.name();
			String fieldValue = indexableField.stringValue();
			map.put(fieldName, fieldValue);
		}
		if (map.keySet().contains("music")) {
			Music music = new Music();
			music.setMusic(map.get("music"));
			music.setMu_url(map.get("mu_url"));
			return music;
		
		}else if (map.keySet().contains("album")) {
			Album album = new Album();
			album.setAlbum(map.get("album"));
			album.setAl_url(map.get("al_url"));
			return album;
		}else if (map.keySet().contains("mv")) {
			Mv mv = new Mv();
			mv.setMv(map.get("mv"));
			mv.setMv_url(map.get("mv_url"));
			return mv;
		}else if (map.keySet().contains("artist")) {
			Artist artist = new Artist();
			artist.setArtist(map.get("artist"));
			artist.setAr_url(map.get("ar_url"));
			return artist;
		}
		return null;
	}

}

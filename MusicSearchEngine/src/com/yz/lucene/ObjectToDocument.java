package com.yz.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.yz.javabean.Album;
import com.yz.javabean.Artist;
import com.yz.javabean.Music;
import com.yz.javabean.Mv;

/*8
 * 对象与索引库document 之间的转化
 * 
 */
public class ObjectToDocument {
	
	
	public static Document objectToDocument(Object object){
		Document document=new Document();
		if (object instanceof Album) {
			Album album = (Album)object;
			TextField albumField=new TextField("album", album.getAlbum(),Store.YES);
//		contentField.setBoost(3f);
			StringField al_urlField=new StringField("al_url", album.getAl_url(), Store.YES);
			document.add(albumField);
			document.add(al_urlField);
		}else if (object instanceof Artist) {
			Artist artist= (Artist)object;
			TextField artistField=new TextField("artist", artist.getArtist(),Store.YES);
//		contentField.setBoost(3f);
			StringField ar_urlField=new StringField("ar_url", artist.getAr_url(), Store.YES);
			document.add(artistField);
			document.add(ar_urlField);
		}else if (object instanceof Music) {
			Music music= (Music)object;
			TextField musicField=new TextField("music", music.getMusic(),Store.YES);
//		contentField.setBoost(3f);
			StringField mu_urlField=new StringField("mu_url", music.getMu_url(), Store.YES);
			document.add(musicField);
			document.add(mu_urlField);
			
		}else if (object instanceof Mv) {
			Mv mv= (Mv)object;
			TextField mvField=new TextField("mv", mv.getMv(),Store.YES);
//		contentField.setBoost(3f);
			StringField mv_urlField=new StringField("mv_url", mv.getMv_url(), Store.YES);
			document.add(mvField);
			document.add(mv_urlField);
			
		}
		return document;
	}

}

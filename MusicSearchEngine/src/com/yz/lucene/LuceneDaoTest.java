package com.yz.lucene;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Test;

import com.yz.javabean.Album;
import com.yz.javabean.Artist;
import com.yz.javabean.Music;
import com.yz.javabean.Mv;

public class LuceneDaoTest {
	
	private LuceneDao luceneDao=new LuceneDao();
	@Test
	public void testCreate() throws IOException{
/*		for(int i=0;i<=22;i++){
			Album album=new Album();
			album.setAlbum("album啊"+i);
			album.setAl_url("al_url啊"+i);
			luceneDao.addIndex(album);
		}
		for(int i=0;i<=22;i++){
			Artist artist=new Artist();
			artist.setArtist("artist啊"+i);
			artist.setAr_url("ar_url啊"+i);
			luceneDao.addIndex(artist);
		}
		for(int i=0;i<=22;i++){
			Music music=new Music();
			music.setMusic("let it go"+i);
			music.setMu_url("let it go mu_url"+i);
			luceneDao.addIndex(music);
		}
		for(int i=0;i<=22;i++){
			Mv mv=new Mv();
			mv.setMv("mv啊"+i);
			mv.setMv_url("mv_url啊"+i);
			luceneDao.addIndex(mv);
		}*/
		Artist mv1=new Artist();
		mv1.setArtist("徐佳莹");
		mv1.setAr_url("http://www.kuwo.cn/mingxing/%E5%BE%90%E4%BD%B3%E8%8E%B9/");
		luceneDao.addIndex(mv1);
//		Music mv2=new Music();
//		mv2.setMusic("东风");
//		mv2.setMu_url("2");
//		luceneDao.addIndex(mv2);
//		Music mv3=new Music();
//		mv3.setMusic("东风破");
//		mv3.setMu_url("3");
//		luceneDao.addIndex(mv3);
//		Music mv4=new Music();
//		mv4.setMusic("西风");
//		mv4.setMu_url("4");
//		luceneDao.addIndex(mv4);
//		Music mv5=new Music();
//		mv5.setMusic("西风破");
//		mv5.setMu_url("5");
//		luceneDao.addIndex(mv5);
//		IndexWriter iw = LuceneUtils.getIndexWriter();
//		Document document = new Document();
//		IntField idfield=new IntField("a", 11, Store.YES);
//		//StringField 对应的值不分词，textField 分词..
//		TextField titleField=new TextField("name", "xxx",Store.YES);
//		TextField contentField=new TextField("class", "aaa",Store.YES);
//		document.add(idfield);
//		document.add(titleField);
//		document.add(contentField);
//		iw.addDocument(document);
//		iw.close();
//		
		
	}
	@Test
	public void testdelByParse() throws Exception{
//		article.setTitle("一定要梦想，万一实现了勒");   textfield   分词     标准分词器      
//		article.setContent("我觉得这句话太矫情了");   textfield   分词    标准分词器
		luceneDao.delPharse("更多");
		
	}
	@Test
	public void testdelByUrl() throws Exception{
//		article.setTitle("一定要梦想，万一实现了勒");   textfield   分词     标准分词器      
//		article.setContent("我觉得这句话太矫情了");   textfield   分词    标准分词器
		luceneDao.delTermIndex("let it go3");
		
	}
	@Test
	public void testUpadteByUrl() throws Exception{
//		article.setTitle("一定要梦想，万一实现了勒");   textfield   分词     标准分词器      
//		article.setContent("我觉得这句话太矫情了");   textfield   分词    标准分词器
		Music music = new Music();
		music.setMu_url("let it go10");
		music.setMusic("let it go10");
		luceneDao.updateByUrl("let it go9", music);
		
	}
	@Test
	public void testfindByUrl() throws Exception{
//		article.setTitle("一定要梦想，万一实现了勒");   textfield   分词     标准分词器      
//		article.setContent("我觉得这句话太矫情了");   textfield   分词    标准分词器
		System.out.println(luceneDao.findByUrl("http://www.kuwo.cn/mingxing/%E5%BE%90%E4%BD%B3%E8%8E%B9/"));
		
	}
	@Test
	public void testPhraseQuery() throws Exception{
//		article.setTitle("一定要梦想，万一实现了勒");   textfield   分词     标准分词器      
//		article.setContent("我觉得这句话太矫情了");   textfield   分词    标准分词器
		luceneDao.findByPhrase("徐佳莹",0,50);
		
	}
	@Test
	public void testPhraseCatagoryKindWithHighLightMap() throws Exception{
//		article.setTitle("一定要梦想，万一实现了勒");   textfield   分词     标准分词器      
		List<Map<String, String>> list = luceneDao.findByPhraseCatagoryKindWithHighLightMap("徐佳莹","music","kuwo", 0,200);
		System.out.println(list.size());
		for (Map<String, String> map : list) {
			System.out.println(map);
		}
	}
	@Test
	public void testPhraseKindWithHighLightMap() throws Exception{
//		article.setTitle("一定要梦想，万一实现了勒");   textfield   分词     标准分词器      
		List<Map<String, String>> list1 = luceneDao.findByPhraseKindWithHighLightMap("周杰伦","xiami", 0,200);
		System.out.println(list1.size());
		for (Map<String, String> map : list1) {
			System.out.println(map);
		}
		
	}
	@Test
	public void testPhraseCatagoryWithHighLightMap() throws Exception{
//		article.setTitle("一定要梦想，万一实现了勒");   textfield   分词     标准分词器      
		List<Map<String, String>> list1 = luceneDao.findByPhraseCatagoryWithHighLightMap("徐佳莹","album", 0,2);
//		List<Map<String, String>> list1 = luceneDao.findByPhraseCatagoryWithHighLightMap("http://www.kuwo.cn/mingxing/%E5%BE%90%E4%BD%B3%E8%8E%B9/","ar_url", 0,200);

			for (Map<String, String> map : list1) {
				System.out.println(map);
			}
		
	}
	@Test
	public void testPhraseWithHighLightMap() throws Exception{
//		article.setTitle("一定要梦想，万一实现了勒");   textfield   分词     标准分词器      
		List<Map<String, String>> list = luceneDao.findByPhraseWithHighLightMap("林俊杰",0,200);
		System.out.println(list.size());
		for (Object object : list) {
			System.out.println(object);
			
		}
		
	}
	@Test
	public void testPhraseWithMap() throws Exception{
//		article.setTitle("一定要梦想，万一实现了勒");   textfield   分词     标准分词器      
//		article.setContent("我觉得这句话太矫情了");   textfield   分词    标准分词器
		List<Map<String, String>> list = luceneDao.findByPhraseWithMap("上一页",0,1000);
		System.out.println(list.size());
		for (Object object : list) {
			System.out.println(object);
			
		}
		
	}
	@Test
	public void testPhraseWithObject() throws Exception{
//		article.setTitle("一定要梦想，万一实现了勒");   textfield   分词     标准分词器      
//		article.setContent("我觉得这句话太矫情了");   textfield   分词    标准分词器
		List<Object> list = luceneDao.findByPhraseWithObject("更多",0,800);
		System.out.println(list.size());
		for (Object object : list) {
			System.out.println(object);
			
		}
		
	}
	@Test
	public void testsearcherWithObject() throws Exception{
		List<Object> list = luceneDao.findIndexWithObject("let it go",0,800);
		System.out.println(list.size());
		for (Object object : list) {
			System.out.println(object);
			
		}
		
	}
	@Test
	public void testsearcherWithMap() throws Exception{
		List<Map<String, String>> list = luceneDao.findIndexWithMap("徐佳莹",0,1000);
		System.out.println(list.size());
		for (Map<String, String> map : list) {
			System.out.println(map);
		}
		
	}
	@Test
	public void testfindallByKinds() throws Exception{
		Map<String, List<Map<String, String>>> map = luceneDao.findallByKinds(200);
		for (Map.Entry<String, List<Map<String, String>>> entry: map.entrySet()) {
			for (Map<String, String> map2 : entry.getValue()) {
				System.out.println(entry.getKey()+"\t"+map2);
			}
		}
	}
	@Test
	public void testfindallCountByKinds() throws Exception{
		Map<String, Integer> map = luceneDao.findallCountByKinds(500000);
		System.out.println(map);
	}
	@Test
	public void testfindall() throws Exception{
		luceneDao.findall(500000);
	}
	@Test
	public void testfindCountByCatagory() throws Exception{
		System.out.println(luceneDao.findCountByPhraseWithObject("林俊杰", 0, 2322));
	}
	@Test
	public void testdelete() throws IOException, ParseException{
//		String fieldName="music";
		String fieldValue="东风破";
		luceneDao.delIndex(fieldValue);
	}
	@Test
	public void testdeleteAll() throws IOException{
		luceneDao.delAll();
	}

	@Test
	public void testUpdate() throws IOException{
		String fieldName="music";
		String fieldValue="let it go";
		Music music = new Music();
		music.setMusic("let it go3");
		music.setMu_url("let it go3");
		luceneDao.updateIndex("mu", "let it go2", music);
		
	}
	
	
}

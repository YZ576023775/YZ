package com.yz.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.hamcrest.core.IsInstanceOf;

import com.yz.crawl.NewMusicCrawlStart;
import com.yz.javabean.Album;
import com.yz.javabean.Artist;
import com.yz.javabean.Music;
import com.yz.javabean.Mv;
import com.yz.urlkind.UrlTools;

/**
 * 使用lucene 的API 来操作索引库..
 * @author Administrator
 *
 */
public class LuceneDao {
	
	public  void  addIndex(Object object) throws IOException{
		synchronized (LuceneDao.class) {
			
			IndexWriter indexWriter=LuceneUtils.getIndexWriter();
			Document doc=ObjectToDocument.objectToDocument(object);
			indexWriter.addDocument(doc);
			indexWriter.close();
		}
	}
	
	/**
	 * 删除符合条件的记录...
	 * @param fieldName
	 * @param fieldValue
	 * @throws IOException
	 * @throws ParseException 
	 */
	public void delTermIndex(String url) throws IOException, ParseException{
		IndexWriter indexWriter=LuceneUtils.getIndexWriter();
		List<String> list =Arrays.asList(new String[]{"ar_url","mu_url","al_url","mv_url"});
		for (String string : list) {
			
			Query query=new TermQuery(new Term(string, url));
			indexWriter.deleteDocuments(query);
		}
		
		indexWriter.close();
	}
	public void delPharse(/*String fieldName,*/String keywords) throws IOException, ParseException{
		IndexWriter indexWriter=LuceneUtils.getIndexWriter();
		List<String> beanList= new ArrayList<String>();
		beanList.add("album");
		beanList.add("artist");
		beanList.add("music");
		beanList.add("mv");
		for (String string : beanList) {
			List<String> list =  AnalyzerTool.analyzeToList(keywords);
			
			PhraseQuery query=new PhraseQuery();
			for (String afterAnalyzeWords : list) {
				query.add(new Term(string, afterAnalyzeWords));
			}
			query.setSlop(0);
//		Term term=new Term(fieldName, fieldValue);
		
		indexWriter.deleteDocuments(query);
	}
		indexWriter.close();
	}
	public void delIndex(/*String fieldName,*/String fieldValue) throws IOException, ParseException{
		IndexWriter indexWriter=LuceneUtils.getIndexWriter();
		String fields []={"music","artist","mv","album"};

		QueryParser queryParser=new MultiFieldQueryParser(LuceneUtils.getVersion(),fields,LuceneUtils.getAnalyzer());
		Query query = queryParser.parse(fieldValue);
//		Term term=new Term(fieldName, fieldValue);
		
		indexWriter.deleteDocuments(query);
		
		indexWriter.close();
	}
	public void delAll() throws IOException{
		IndexWriter indexWriter=LuceneUtils.getIndexWriter();
		
		indexWriter.deleteAll();
		
		indexWriter.close();
	}
	/**
	 * 
	 * 更新
	 * 
	 * update table set ?  where condtion
	 * @throws IOException 
	 * @throws ParseException 
	 * 
	 * 
	 */
	public void updateByUrl(String url,Object object) throws IOException, ParseException{
		synchronized (LuceneDao.class) {
		
		IndexWriter indexWriter = LuceneUtils.getIndexWriter();
		List<String> list =Arrays.asList(new String[]{"ar_url","mu_url","al_url","mv_url"});
//		int size=0;
		for (String string : list) {
			
			
//			Query query=new TermQuery(new Term(string, url));
//			TopDocs topDocs = indexSearcher.search(query,999999999);
//			int cur = topDocs.totalHits;
//			size+=cur;
//			if (cur>0) {
			if ( (object instanceof Artist &&string.equals("ar_url")) ||
					(object instanceof Music &&string.equals("mu_url")) ||
					(object instanceof Album &&string.equals("al_url")) ||
					(object instanceof Mv &&string.equals("mv_url"))) {
				Term term=new Term(string,url);
				Document doc=ObjectToDocument.objectToDocument(object);
				
				indexWriter.updateDocument(term, doc);
			}
				
			}
//		int count = findByUrl(url);
//		if (count>0) {
//			IndexWriter indexWriter=LuceneUtils.getIndexWriter();
//			Query query=new TermQuery(new Term("ar_url", url));
//			TopDocs topDocs = indexSearcher.search(query,999999999);
//			ScoreDoc[] scoreDocs =topDocs.scoreDocs;
//			Term term=new Term("mu_url","let it go1");
//			Document doc=ObjectToDocument.objectToDocument(object);
//			/**
//			 * 
//			 * 在lucene 里面是先删除符合这个条件term 的记录，在创建一个doc 记录...
//			 * 
//			 */
//			indexWriter.updateDocument(term, doc);
//			
//		} else {
//			
//			addIndex(object);
//		}
		indexWriter.close();
		
		}
	}
	public void updateIndex(String fieldName,String fieldValue,Object object) throws IOException{
		IndexWriter indexWriter=LuceneUtils.getIndexWriter();
		/**
		 * 1:term 设置更新的条件...
		 * 
		 * 2:设置更新的内容的对象..
		 * 
		 */
		Term term=new Term(fieldName,fieldValue);
		Document doc=ObjectToDocument.objectToDocument(object);
		/**
		 * 
		 * 在lucene 里面是先删除符合这个条件term 的记录，在创建一个doc 记录...
		 * 
		 */
		indexWriter.updateDocument(term, doc);
		indexWriter.close();
	}
	public int findByUrl(String url) throws IOException, ParseException{
			List<String> list =Arrays.asList(new String[]{"ar_url","mu_url","al_url","mv_url"});
			IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
			int size=0;
			for (String string : list) {
				
				
				Query query=new TermQuery(new Term(string, url));
				TopDocs topDocs = indexSearcher.search(query,999999999);
				size+=topDocs.totalHits;
			}
			return size;
			
		}
	public void findByPhrase(String keywords,int firstResult,int maxResult) throws IOException{
		List<String> beanList= new ArrayList<String>();
		beanList.add("album");
		beanList.add("artist");
		beanList.add("music");
		beanList.add("mv");
		for (String string : beanList) {
			IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
			List<String> list =  AnalyzerTool.analyzeToList(keywords);
			
			PhraseQuery query=new PhraseQuery();
			for (String afterAnalyzeWords : list) {
				query.add(new Term(string, afterAnalyzeWords));
			}
			query.setSlop(0);
			TopDocs topDocs = indexSearcher.search(query,firstResult+maxResult);
			ScoreDoc[] scoreDocs =topDocs.scoreDocs;
			for(int i=firstResult;i<Math.min(scoreDocs.length, firstResult+maxResult);i++){
//			/取出来的是docID,这个id 是lucene 自己来维护。
				int docID=scoreDocs[i].doc;
				Document document=indexSearcher.doc(docID);
				List<IndexableField> lists =  document.getFields();
				System.out.println(lists.size());
				for (IndexableField indexableField : lists) {
					System.out.println(indexableField.name()+"	:	"+indexableField.stringValue());
				}
			}
			
		}
	}
	public int  findallCount(int count) throws IOException{
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		Query query = new MatchAllDocsQuery();
		TopDocs topDocs = indexSearcher.search(query,count);
		ScoreDoc[] scoreDocs =topDocs.scoreDocs;
		return scoreDocs.length;
		}
	public void findall(int count) throws IOException{
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		Query query = new MatchAllDocsQuery();
		TopDocs topDocs = indexSearcher.search(query,count);
		ScoreDoc[] scoreDocs =topDocs.scoreDocs;
		for(int i=0;i<Math.min(scoreDocs.length, count);i++){
//			/取出来的是docID,这个id 是lucene 自己来维护。
			int docID=scoreDocs[i].doc;
			Document document=indexSearcher.doc(docID);
			Object object = DocumentToObject.documentToObject(document);
			System.out.println(object);
		}
	}
	public Map<String, List<Map<String, String>>> findallByKinds(int count) throws IOException{
		Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> list1 = new ArrayList<Map<String,String>>();
		List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
		List<Map<String, String>> list3 = new ArrayList<Map<String,String>>();
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		Query query = new MatchAllDocsQuery();
		TopDocs topDocs = indexSearcher.search(query,count);
		ScoreDoc[] scoreDocs =topDocs.scoreDocs;
		for(int i=0;i<Math.min(scoreDocs.length, count);i++){
//			/取出来的是docID,这个id 是lucene 自己来维护。
			int docID=scoreDocs[i].doc;
			Document document=indexSearcher.doc(docID);
		Map<String, String> listMap = DocumentToMap.documentToMap(document);
			for (String string : listMap.values()) {
				if (string.contains("http://www.kuwo.cn")) {
					list1.add(listMap);
				} else if (string.contains("http://www.xiami.com")) {
					list2.add(listMap);
				} else if (string.contains("http://music.baidu.com")) {
					list3.add(listMap);
				}
			}
		}
		map.put("酷我", list1);
		map.put("虾米", list2);
		map.put("百度", list3);
		return map;
	}
	public Map<String, Integer> findallCountByKinds(int count) throws IOException{
		Map<String, Integer> map = new HashMap<String, Integer>();
		int a = 0 ,b = 0 ,c =0;
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		Query query = new MatchAllDocsQuery();
		TopDocs topDocs = indexSearcher.search(query,count);
		ScoreDoc[] scoreDocs =topDocs.scoreDocs;
		for(int i=0;i<Math.min(scoreDocs.length, count);i++){
//			/取出来的是docID,这个id 是lucene 自己来维护。
			int docID=scoreDocs[i].doc;
			Document document=indexSearcher.doc(docID);
			List<IndexableField> lists =  document.getFields();
			for (IndexableField indexableField : lists) {
				String value =indexableField.stringValue();
				if (value.contains("http://www.kuwo.cn")) {
					a++;
				} else if(value.contains("http://www.xiami.com")){
					b++;
				}else if (value.contains("http://music.baidu.com")) {
					c++;
				}
			}
		}
		map.put("酷我", a);
		map.put("百度", c);
		map.put("虾米", b);
		return map;
	}
	
	/**
	 * 0,10
	 * 10,10
	 * 20,10
	 * @param keywords
	 * @throws Exception
	 */
	public List<Object> findIndexWithObject(String keywords,int firstResult,int maxResult) throws Exception{
		
		List<Object> list = new ArrayList<Object>();
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		
		
		String fields []={"music","artist","mv","album"};
		
		//第二种条件：使用查询解析器，多字段。。。 我们需要重新导入一个jar queryParser 的jar...
		QueryParser queryParser=new MultiFieldQueryParser(LuceneUtils.getVersion(),fields,LuceneUtils.getAnalyzer());
		
//		/这个事一个条件..
		Query query=queryParser.parse(keywords);
		
		//query 它是一个查询条件，query 是一个抽象类，不同的查询规则构造部同的子类即可
		//检索符合query 条件的前面N 条记录...
		//检索的是索引目录... (总记录数，socreDOC (docID))
		//使用lucene 提供的api 进行操作...
		TopDocs topDocs=indexSearcher.search(query,firstResult+maxResult);
//		/存放的是docID
		ScoreDoc scoreDocs []=topDocs.scoreDocs;
		//判断:scoreDocs 的length  (实际取出来的数量..) 与 firstResult+maxResult 的值取小值...
		
		//在java jdk 里面提供了一个api
		int endResult=Math.min(scoreDocs.length, firstResult+maxResult);
		
		
		for(int i=firstResult;i<endResult;i++){
//			/取出来的是docID,这个id 是lucene 自己来维护。
			int docID=scoreDocs[i].doc;
			Document document=indexSearcher.doc(docID);
			list.add(DocumentToObject.documentToObject(document));
			
			
		}
		return list;
		
	}
	
	
	public List<Map<String,String>> findIndexWithMap(String keywords,int firstResult,int maxResult) throws Exception{
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		
		
		String fields []={"music","artist","mv","album"};
		
		//第二种条件：使用查询解析器，多字段。。。 我们需要重新导入一个jar queryParser 的jar...
		QueryParser queryParser=new MultiFieldQueryParser(LuceneUtils.getVersion(),fields,LuceneUtils.getAnalyzer());
		
//		/这个事一个条件..
		Query query=queryParser.parse(keywords);
		
		//query 它是一个查询条件，query 是一个抽象类，不同的查询规则构造部同的子类即可
		//检索符合query 条件的前面N 条记录...
		//检索的是索引目录... (总记录数，socreDOC (docID))
		//使用lucene 提供的api 进行操作...
		TopDocs topDocs=indexSearcher.search(query,firstResult+maxResult);
//		/存放的是docID
		ScoreDoc scoreDocs []=topDocs.scoreDocs;
		//判断:scoreDocs 的length  (实际取出来的数量..) 与 firstResult+maxResult 的值取小值...
		
		//在java jdk 里面提供了一个api
		int endResult=Math.min(scoreDocs.length, firstResult+maxResult);
		
		
		for(int i=firstResult;i<endResult;i++){
//			/取出来的是docID,这个id 是lucene 自己来维护。
			int docID=scoreDocs[i].doc;
			Document document=indexSearcher.doc(docID);
			list.add(DocumentToMap.documentToMap(document));
			
//			List<IndexableField> listIndexableFields =  document.getFields();
//			for (IndexableField indexableField : listIndexableFields) {
//				String fieldName = indexableField.name();
//				String fieldValue = indexableField.stringValue();
//				System.out.println(fieldName+"	:	"+fieldValue);
//			}
			
		}
		return list;
		
	}
	
	public List<Map<String,String>> findByPhraseCatagoryKindWithHighLightMap(String keywords,String catagory,String kind,int firstResult,int maxResult) 
			throws Exception{
		List<Map<String,String>> listWithMap = new ArrayList<Map<String,String>>();
		Map<String, String> mapString = new HashMap<String, String>();
		mapString.put("baidu", "http://music.baidu.com");
		mapString.put("kuwo", "http://www.kuwo.cn");
		mapString.put("xiami", "http://www.xiami.com");
		PhraseQuery query=null;
		Map<Integer,Query> docId = new LinkedHashMap<Integer,Query>();
		List<String> beanList= new ArrayList<String>();
		beanList.add(catagory);
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		for (String string : beanList) {
			List<String> list =  AnalyzerTool.analyzeToList(keywords);
			
			query=new PhraseQuery();
			for (String afterAnalyzeWords : list) {
				query.add(new Term(string, afterAnalyzeWords));
			}
			query.setSlop(0);
			TopDocs topDocs = indexSearcher.search(query,firstResult+maxResult);
			ScoreDoc[] scoreDocs =topDocs.scoreDocs;
			for(int i=0;i<scoreDocs.length;i++){
//			/取出来的是docID,这个id 是lucene 自己来维护。
				int docID=scoreDocs[i].doc;
				docId.put(docID, query);
			}
		}
//		System.out.println(docId.size());
//		for (Map.Entry<Integer,Query> entry : docId.entrySet()) {
//			
//			System.out.println(entry.getKey()+" : "+entry.getValue());
//		}
		Set<Integer> set= docId.keySet();
		for (Object object : set) {
			Query query2 = docId.get(object);
			Highlighter highlighter =HighLighter.getHighLighter(query2);
			Document document = indexSearcher.doc((Integer)object);
			
			Map<String, String> listMap = DocumentToHighlightMap.documentToHighlightMap(document,highlighter);
			for (String string : listMap.values()) {
				if (string.contains(mapString.get(kind.trim().toLowerCase()))) {
					listWithMap.add(listMap);
				} 
			}
		}
		return listWithMap;
	}
	public List<Map<String, String>> findByPhraseKindWithHighLightMap(String keywords,String kind,int firstResult,int maxResult) 
			throws Exception{
		List<Map<String,String>> listWithMap = new ArrayList<Map<String,String>>();
		Map<String, String> mapString = new HashMap<String, String>();
		mapString.put("baidu", "http://music.baidu.com");
		mapString.put("kuwo", "http://www.kuwo.cn");
		mapString.put("xiami", "http://www.xiami.com");
		PhraseQuery query=null;
		Map<Integer,Query> docId = new LinkedHashMap<Integer,Query>();
		List<String> beanList= Arrays.asList(new String[]{"artist","music","album","mv"});
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		for (String string : beanList) {
			List<String> list =  AnalyzerTool.analyzeToList(keywords);
			
			query=new PhraseQuery();
			for (String afterAnalyzeWords : list) {
				query.add(new Term(string, afterAnalyzeWords));
			}
			query.setSlop(0);
			TopDocs topDocs = indexSearcher.search(query,firstResult+maxResult);
			ScoreDoc[] scoreDocs =topDocs.scoreDocs;
			for(int i=0;i<scoreDocs.length;i++){
//			/取出来的是docID,这个id 是lucene 自己来维护。
				int docID=scoreDocs[i].doc;
				docId.put(docID, query);
			}
		}
//		System.out.println(docId.size());
//		for (Map.Entry<Integer,Query> entry : docId.entrySet()) {
//			
//			System.out.println(entry.getKey()+" : "+entry.getValue());
//		}
		Set<Integer> set= docId.keySet();
		for (Object object : set) {
			Query query2 = docId.get(object);
			Highlighter highlighter =HighLighter.getHighLighter(query2);
			Document document = indexSearcher.doc((Integer)object);
			
			Map<String, String> listMap = DocumentToHighlightMap.documentToHighlightMap(document,highlighter);
			for (String string : listMap.values()) {
				if (string.contains(mapString.get(kind.trim().toLowerCase()))) {
					listWithMap.add(listMap);
				} 
			}
		}
		return listWithMap;
	}
	public List<Map<String,String>> findByPhraseCatagoryWithHighLightMap(String keywords,String catagory,int firstResult,int maxResult) 
			throws Exception{
		PhraseQuery query=null;
		Map<Integer,Query> docId = new LinkedHashMap<Integer,Query>();
		List<Map<String,String>> listWithMap = new ArrayList<Map<String,String>>();
		List<String> beanList= new ArrayList<String>();
		beanList.add(catagory);
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		for (String string : beanList) {
			List<String> list =  AnalyzerTool.analyzeToList(keywords);
			
			query=new PhraseQuery();
			for (String afterAnalyzeWords : list) {
				query.add(new Term(string, afterAnalyzeWords));
			}
			query.setSlop(0);
			TopDocs topDocs = indexSearcher.search(query,firstResult+maxResult);
			ScoreDoc[] scoreDocs =topDocs.scoreDocs;
			for(int i=0;i<scoreDocs.length;i++){
//			/取出来的是docID,这个id 是lucene 自己来维护。
				int docID=scoreDocs[i].doc;
				docId.put(docID, query);
			}
		}
//		System.out.println(docId.size());
//		for (Map.Entry<Integer,Query> entry : docId.entrySet()) {
//			
//			System.out.println(entry.getKey()+" : "+entry.getValue());
//		}
		Set<Integer> set= docId.keySet();
		for (Object object : set) {
			Query query2 = docId.get(object);
			Highlighter highlighter =HighLighter.getHighLighter(query2);
			Document document = indexSearcher.doc((Integer)object);
			
			Map<String, String> map = DocumentToHighlightMap.documentToHighlightMap(document,highlighter);
			listWithMap.add(map);
		}
		
		return listWithMap;
	}
	public List<Map<String,String>> findByPhraseWithHighLightMap(String keywords,int firstResult,int maxResult) 
			throws Exception{
		PhraseQuery query=null;
		Map<Integer,Query> docId = new LinkedHashMap<Integer,Query>();
		List<Map<String,String>> listWithMap = new ArrayList<Map<String,String>>();
		List<String> beanList= new ArrayList<String>();
		beanList.add("artist");
		beanList.add("music");
		beanList.add("album");
		beanList.add("mv");
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		for (String string : beanList) {
			List<String> list =  AnalyzerTool.analyzeToList(keywords);
			
			query=new PhraseQuery();
			for (String afterAnalyzeWords : list) {
				query.add(new Term(string, afterAnalyzeWords));
			}
			query.setSlop(0);
			TopDocs topDocs = indexSearcher.search(query,firstResult+maxResult);
			ScoreDoc[] scoreDocs =topDocs.scoreDocs;
			for(int i=0;i<scoreDocs.length;i++){
//			/取出来的是docID,这个id 是lucene 自己来维护。
				int docID=scoreDocs[i].doc;
				docId.put(docID, query);
			}
		}
//		System.out.println(docId.size());
//		for (Map.Entry<Integer,Query> entry : docId.entrySet()) {
//			
//			System.out.println(entry.getKey()+" : "+entry.getValue());
//		}
			Set<Integer> set= docId.keySet();
			int i=-1;
			for (Object object : set) {
				i++;
				if (i>=firstResult&&i<Math.min(docId.size(), firstResult + maxResult)) {
				Query query2 = docId.get(object);
				Highlighter highlighter =HighLighter.getHighLighter(query2);
				Document document = indexSearcher.doc((Integer)object);
				Map<String, String> map = DocumentToHighlightMap.documentToHighlightMap(document,highlighter);
				listWithMap.add(map);
				}
			}
			
		return listWithMap;
	}
	public List<Map<String,String>> findByPhraseWithMap(String keywords,int firstResult,int maxResult) 
			throws Exception{
		List<Integer> docId = new ArrayList<Integer>();
		List<Map<String,String>> listWithMap = new ArrayList<Map<String,String>>();
		List<String> beanList= new ArrayList<String>();
		beanList.add("artist");
		beanList.add("music");
		beanList.add("album");
		beanList.add("mv");
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		for (String string : beanList) {
			List<String> list =  AnalyzerTool.analyzeToList(keywords);
			
			PhraseQuery query=new PhraseQuery();
			for (String afterAnalyzeWords : list) {
				query.add(new Term(string, afterAnalyzeWords));
			}
			query.setSlop(0);
			TopDocs topDocs = indexSearcher.search(query,firstResult+maxResult);
			ScoreDoc[] scoreDocs =topDocs.scoreDocs;
			for(int i=0;i<scoreDocs.length;i++){
//			/取出来的是docID,这个id 是lucene 自己来维护。
				int docID=scoreDocs[i].doc;
				docId.add(docID);
			}
		}
		for (int i = firstResult; i < Math.min(docId.size(), firstResult + maxResult); i++) {
			int docID = docId.get(i);
			Document document = indexSearcher.doc(docID);
			Map<String, String> map = DocumentToMap.documentToMap(document);
			listWithMap.add(map);

		}
			return listWithMap;
}
	public List<Object> findByPhraseWithObject(String keywords,int firstResult,int maxResult) 
			throws Exception{
		List<Object> listWithObject= new ArrayList<Object>();
		List<String> beanList= new ArrayList<String>();
		beanList.add("artist");
		beanList.add("music");
		beanList.add("album");
		beanList.add("mv");
		for (String string : beanList) {
			IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
			List<String> list =  AnalyzerTool.analyzeToList(keywords);
			
			PhraseQuery query=new PhraseQuery();
			for (String afterAnalyzeWords : list) {
				query.add(new Term(string, afterAnalyzeWords));
			}
			query.setSlop(0);
			TopDocs topDocs = indexSearcher.search(query,firstResult+maxResult);
			ScoreDoc[] scoreDocs =topDocs.scoreDocs;
			for(int i=firstResult;i<Math.min(scoreDocs.length, firstResult+maxResult);i++){
//			/取出来的是docID,这个id 是lucene 自己来维护。
				int docID=scoreDocs[i].doc;
				Document document=indexSearcher.doc(docID);
				Object object = DocumentToObject.documentToObject(document);
				listWithObject.add(object);
			}
			
		}
		return listWithObject;
	}
public int findCountByPhraseWithObject(String keywords,int firstResult,int maxResult) 
		throws Exception{
	int listWithObject=0;
	List<String> beanList= new ArrayList<String>();
	beanList.add("artist");
	beanList.add("music");
	beanList.add("album");
	beanList.add("mv");
	for (String string : beanList) {
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		List<String> list =  AnalyzerTool.analyzeToList(keywords);
		
		PhraseQuery query=new PhraseQuery();
		for (String afterAnalyzeWords : list) {
			query.add(new Term(string, afterAnalyzeWords));
		}
		query.setSlop(0);
		TopDocs topDocs = indexSearcher.search(query,firstResult+maxResult);
		listWithObject+=topDocs.totalHits;
	}
	return listWithObject;
}
}

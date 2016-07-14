package com.yz.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

import cn.itcast.bean.Article;
import cn.itcast.uitls.LuceneUtils;

/**
 * 
 * 测试高亮，
 * 
 * 使用高亮的时候我们需要导入两个jar
 * lucene-highlighter-4.4.0.jar
 * lucene-memory-4.4.0.jar
 * 
 * 
 * 对查询出来的结果当中包含的搜索关键字进行高亮...
 */
public class TestHighlighter {
	
	public static void main(String[] args) throws Exception {
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		String keywords="全文检索服务器";
		String fields []={"music","title"};
		QueryParser queryParser=new MultiFieldQueryParser(LuceneUtils.getVersion(),fields,LuceneUtils.getAnalyzer());
		Query query=queryParser.parse(keywords);
		TopDocs topDocs=indexSearcher.search(query, 1);
		ScoreDoc scoreDocs[]=topDocs.scoreDocs;
		
		//高亮显示的格式...
//		solr 是基于lucene 的<font color='blue'>全</font><font color='blue'>文</font><font color='blue'>检</font>索服务器
		Formatter formatter=new SimpleHTMLFormatter("<font color='red'>","</font>");
		
		//与query 查询条件进行关联，因为query 包含了搜索的关键字
		//只有知道了搜索的关键字，高亮显示的格式，我才能把一段文本进行高亮...
		Scorer scorer=new QueryScorer(query);
		
		//创建一个高亮器，我们使用lucene 自带的高亮器进行高亮..
		Highlighter highlighter=new Highlighter(formatter,scorer);
		
		List<Article> articles=new ArrayList<Article>();
		
		Article article=null;
		
		for(ScoreDoc scoreDoc :scoreDocs){
			article=new Article();
			Document document=indexSearcher.doc(scoreDoc.doc);
			String title=document.get("title");
			String content=document.get("content");
			System.out.println("id=="+document.get("id"));
			System.out.println("title==="+title);
			System.out.println("content==="+content);
			System.out.println("没有高亮之前的结果....----------------------------------------------------");
			
			
			if(content!=null){
				//返回高亮过后的文本...
				String highcontent=highlighter.getBestFragment(LuceneUtils.getAnalyzer(), "content", content);
				System.out.println("高亮过后的highcontent="+highcontent);
				if(highcontent==null){
					article.setContent(content);
				}else{
					article.setContent(highcontent);
				}
			}
			
			
			if(title!=null){
				String hightitle=highlighter.getBestFragment(LuceneUtils.getAnalyzer(), "title", title);
				
				//假设我们对一段文本进行高亮，如果这段文本当中不包含搜索关键字，对这段文本高亮，返回的结果为null
				System.out.println("高亮过后的hightitle="+hightitle);
				
				//不能把null 返回到客户端，所以我们需要进行判断，如果为null值，就返回没有高亮之前的文本，
				if(hightitle==null){
					article.setTitle(title);
				}else{
					article.setTitle(hightitle);
				}
				
				
			}
			
			
			
			//title 当中有没有可能不包含搜索的关键字...
		
			
		
			
			
			
		}		
	}

}

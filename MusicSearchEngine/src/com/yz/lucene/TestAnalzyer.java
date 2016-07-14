package com.yz.lucene;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

/**
 * 
 * 对应的分词器的切分词的规则...
 * @author Administrator
 *
 */
public class TestAnalzyer {
	
	public static void main(String[] args) throws IOException {
		String text="东风破";
		//单字切分...
		Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_44);
		
		//二分法分词..
//		Analyzer analyzer=new CJKAnalyzer(Version.LUCENE_44);
		
//		Analyzer 抽象类，切分词的具体规则由子类实现，我们可不可以改造它，定义自己的分词器规则..
		//往上有第三方的分词器。 支持中文的分词器..
		//庖丁分词器，定义扩展词，定义停用词...  词库进行分词...
		
//		Analyzer analyzer=new IKAnalyzer();
		
		testAnalyzer(analyzer, text);
		
		
	}
	
	
	public static void testAnalyzer(Analyzer analyzer,String text) throws IOException{
		System.out.println("当前使用的分词器：" + analyzer.getClass().getSimpleName());
//		/切分关键字，切分的关键字都放在tokenStream 里面...
		TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(text));
		
	    tokenStream.addAttribute(CharTermAttribute.class);
	    tokenStream.reset();
	    while (tokenStream.incrementToken()) {
	       CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
	          System.out.println(new String(charTermAttribute.toString()));
	    }
	   tokenStream.close();
		
	}

}

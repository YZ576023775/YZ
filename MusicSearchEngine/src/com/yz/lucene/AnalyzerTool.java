package com.yz.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

public class AnalyzerTool {
	public static List<String> analyzeToList(String keywords)
			throws IOException {
		List<String> list = new ArrayList<String>();
		Analyzer analyzer = LuceneUtils.getAnalyzer();
		TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(
				keywords));

		tokenStream.addAttribute(CharTermAttribute.class);
		tokenStream.reset();
		while (tokenStream.incrementToken()) {
			CharTermAttribute charTermAttribute = tokenStream
					.getAttribute(CharTermAttribute.class);
			list.add(new String(charTermAttribute.toString()));
		}
		tokenStream.close();
		return list;

	}

	public static void main(String[] args) throws IOException {
		System.out.println(analyzeToList("let it go"));
	}

}

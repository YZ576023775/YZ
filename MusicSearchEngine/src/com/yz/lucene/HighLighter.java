package com.yz.lucene;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

public class HighLighter {
	public static Highlighter getHighLighter(Query query){
		Formatter formatter=new SimpleHTMLFormatter("<b class='text-danger'>","</b>");
		Scorer scorer=new QueryScorer(query);
		return new Highlighter(formatter,scorer);
		
	}

}

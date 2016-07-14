package com.yz.lucene;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

public class DocumentToHighlightMap {
	public static Map<String, String> documentToHighlightMap(Document document,Highlighter highlighter) throws ParseException, IOException, InvalidTokenOffsetsException{
		String[] strings = new String[]{"artist","music","album","mv"};
		List<String> list = Arrays.asList(strings);
		Map<String, String> map = new HashMap<String, String>();
		List<IndexableField> listIndexableFields =  document.getFields();
		for (IndexableField indexableField : listIndexableFields) {
			String fieldName = indexableField.name();
			String fieldValue = indexableField.stringValue();
			if (list.contains(fieldName)) {
				
				String highFieldValue=highlighter.getBestFragment(LuceneUtils.getAnalyzer(), fieldName, fieldValue);
				map.put(fieldName, highFieldValue);
			}else {
				map.put(fieldName, fieldValue);
				
			}
		}
		return map;
		
		
	}

}

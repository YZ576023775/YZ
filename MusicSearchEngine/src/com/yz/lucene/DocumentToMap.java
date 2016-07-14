package com.yz.lucene;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;

/*8
 * 对象与索引库document 之间的转化
 * 
 */
public class DocumentToMap {
	
	
	public static Map<String, String> documentToMap(Document document){
		Map<String, String> map = new HashMap<String, String>();
		List<IndexableField> listIndexableFields =  document.getFields();
		for (IndexableField indexableField : listIndexableFields) {
			String fieldName = indexableField.name();
			String fieldValue = indexableField.stringValue();
			map.put(fieldName, fieldValue);
		}
		return map;
	}

}

package com.yz.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * lucene 工具类...
 * @author Administrator
 *
 */
/**
 * 提炼规则，假设这段代码可以完成一个功能，把这个代码提炼到一个方法里面去，假设这个方法在某个业务罗继承可以共用，那么往上抽取，
 * 假设在其它逻辑层也可以用，提炼到工具类里面去。
 * 
 */
public class LuceneUtils {
	private static IndexWriter indexWriter=null;
	private static IndexSearcher indexSearcher=null;
	
	
	//索引存放目录..
	private static Directory directory=null;
	
	private static IndexWriterConfig indexWriterConfig=null;
	
	private static Version version=null;
	
	
	private static Analyzer analyzer=null;
	
	static {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(LuceneUtils.class.getClassLoader().getResourceAsStream("crawl.cfg.xml"));
			Element root = document.getRootElement();
			String indexUrl = root.elementTextTrim("indexUrl");
			String ramDirectory = root.elementTextTrim("RAMDirectory");
			String analyzerString = root.elementTextTrim("Analyzer");
			String emptyStopWords = root.elementTextTrim("EmptyStopWords");
			directory=FSDirectory.open(new File(indexUrl));
			if (ramDirectory.equals("true")) {
				IOContext ioContext=new IOContext();
				//相办法吧directory 的索引读取到内存当中来...
				directory = new RAMDirectory(directory, ioContext);
			}
//			directory=FSDirectory.open(new File(Constants.URL));
			version=Version.LUCENE_44;
			analyzer=new StandardAnalyzer(version);
			if (emptyStopWords.equals("true")) {
				Collection<String> collection = new ArrayList<String>();
				CharArraySet charArraySet = new CharArraySet(version, collection, true);
				
				analyzer = (Analyzer) ClazzToAnalyzer.newInstance(analyzerString,Version.LUCENE_44,charArraySet);
			}else {
				
				analyzer = (Analyzer) ClazzToAnalyzer.newInstance(analyzerString);
			}
			
			indexWriterConfig=new IndexWriterConfig(version, analyzer);
			indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @return 返回用于操作索引的对象...
	 * @throws IOException
	 */
	public static  IndexWriter getIndexWriter() throws IOException{
			
			indexWriter=new IndexWriter(directory, indexWriterConfig);
			return indexWriter;
	}
	/**
	 * 返回用于搜索索引的对象...
	 * @return
	 * @throws IOException 
	 */
	public static IndexSearcher  getIndexSearcher() throws IOException{
		
		IndexReader indexReader=DirectoryReader.open(directory);
		indexSearcher=new IndexSearcher(indexReader);
		
		return indexSearcher;
	}
	/**
	 * 
	 * 返回lucene 当前的版本...
	 * @return
	 */
	public static Version getVersion() {
		return version;
	}
	/**
	 * 
	 * 返回lucene 当前使用的分词器..
	 * @return
	 */
	public static Analyzer getAnalyzer() {
		return analyzer;
	}
	
}

package com.yz.lucene;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

public class ClazzToAnalyzer {
	public static Object newInstance(String className, Object... args) throws Exception {      
	    Class<Object> newoneClass = (Class<Object>) Class.forName(className);                                  
	    Class<Object>[] argsClass = new Class[args.length];                                    
	    for (int i = 0, j = args.length; i < j; i++) {                                 
	        argsClass[i] = (Class<Object>) args[i].getClass();                                         
	    }	   
	    Constructor<Object> cons = newoneClass.getConstructor(argsClass);	     
	    return cons.newInstance(args);                                                  
	}
	public static Object newInstance(String className) throws Exception {      
		Class<Object> newoneClass = (Class<Object>) Class.forName(className);                                  
		return newoneClass.newInstance();                                                  
	}
	public static void main(String[] args) throws Exception {
		Collection<String> collection = new ArrayList<String>();
		CharArraySet charArraySet = new CharArraySet(Version.LUCENE_44, collection, true);
		System.out.println(newInstance("org.apache.lucene.analysis.standard.StandardAnalyzer",Version.LUCENE_44,(Object)charArraySet));
	}

}

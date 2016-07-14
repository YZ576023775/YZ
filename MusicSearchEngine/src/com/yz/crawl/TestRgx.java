package com.yz.crawl;

import java.io.File;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TestRgx {
	public static void main(String[] args) throws DocumentException {
		System.out.println(TestRgx.class.getResource("/").getPath().replaceAll("%20", ""));
		System.out.println(TestRgx.class.getResource("").getPath().replaceAll("%20", ""));
//		System.out.println(isArtistUrl("http://music.163.com/#/song?id=55"));
		SAXReader reader = new SAXReader();               
		Document document = null;
			try {
				document = reader.read(new File("E:/Myeclipse文档/MusicSearchEngine/src/xpath.xml"));
//				System.out.println(document.asXML());
//				Element e = (Element) document.selectSingleNode("bookstore");
//				getNodes(e);
				Element element = (Element) document.selectNodes("//title[starts-with(@lang,\"en\")]").get(1);
				Document document2 = reader.read(TestRgx.class.getClassLoader().getResourceAsStream("crawl_baidu.xml"));
				System.out.println(document2.asXML());
				System.out.println(document.selectNodes("string(.)"));
//				Element element = (Element) document.selectNodes("//title[starts-with(@lang,\"en\")]").get(1);
//				System.out.println(element.selectNodes("string(.)"));
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Document document3 = reader.read(TestRgx.class.getClassLoader().getResourceAsStream("crawl_kuwo.xml"));
			Document document4 = reader.read(TestRgx.class.getClassLoader().getResourceAsStream("crawl_xiami.xml"));
			System.out.println(document4.getRootElement().elementTextTrim("MvUrlRgx"));
			System.out.println(document3.asXML());
			System.out.println(document4.asXML());
//			System.out.println(document.selectSingleNode("bookstore"));
//		System.out.println(isArtistUrl("http://www.xiami.com/artist/asd"));
			System.out.println("Base	"+new String(Base64.encodeBase64("http://music.baidu.com/song/s/8907e727a700856309596/asdasdas/adfasf/asd".getBytes())));
	}
	 public  static  boolean isArtistUrl(String url){
		 if (url.matches("")) {
				return true;
			}else {
				return false;
			}
		 
	 }
	 public static void getNodes(Element node){
			System.out.println("--------------------");
			
			//当前节点的名称、文本内容和属性
			System.out.println("当前节点名称："+node.getName());//当前节点名称
			System.out.println("当前节点的内容："+node.getTextTrim());//当前节点名称
			List<Attribute> listAttr=node.attributes();//当前节点的所有属性的list
			for(Attribute attr:listAttr){//遍历当前节点的所有属性
				String name=attr.getName();//属性名称
				String value=attr.getValue();//属性的值
				System.out.println("属性名称："+name+"属性值："+value);
			}
			
			//递归遍历当前节点所有的子节点
			List<Element> listElement=node.elements();//所有一级子节点的list
			for(Element e:listElement){//遍历所有一级子节点
				getNodes(e);//递归
			}
		}
}

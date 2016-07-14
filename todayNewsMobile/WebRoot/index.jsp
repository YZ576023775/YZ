<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
 
  <body background="img/img/6.jpg">
  <a href="http://172.16.14.63:8080/javaweb/">电脑版</a>
  <a href="news/findAll.html">新闻列表</a>
  <!--<a href="http://172.16.14.63:8080/javaweb/servlet/NewsServlet?methodName=findAll">新闻列表</a>
  --> <!--<a href="javascript:window.open('news/findAll.html','','height=360,width=400,toolbar=no,scrollbars=yes,menubar=no,resizeable=no,location=no,ststus=no');window.close();">新闻列表</a>
  --><!--<a href="news/findAll.html">新闻列表</a>-->
  <!--<a href="'news/findAll.html></a>
  <a href="javascript:window.open('news/findAll.html','','height=400,width=400,toolbar=no,menubar=no,resizeable=no,location=no,ststus=no');window.close();">新闻列表</a>
  "javascript:window.open('news/updateNews.jsp?id=${news.id}&title=${news.title}&news_desc=${news.news_desc}','','height=400,width=400,toolbar=no,menubar=no,resizeable=no,location=no,ststus=no');window.close();">编辑</a>-->
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login_success.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <body background="img/img/5.jpg">
  <p align="center"> <b>${username },welcome </b></p>
  <ol>
 
  <li><a href="servlet/NewsServlet?methodName=findAll">显示所有新闻</a></li>
  <li><a href="news/saveNews.jsp">发布新闻</a></li>
  <!--<li><input type="text" /><a href="${pageContext.request.contextPath}/servlet/NewsServlet?methodName=find&"></a>
  -->
  </ol>
  
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加新闻信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <script type="text/javascript">
  window.onload=function(){
  var form = document.getElementById("f");
  var tittle = document.getElementById("tittle");
  var news_desc = document.getElementById("news_desc");
  var button = document.getElementById("but");
  button.onclick = function(){
  if(tittle.value==""){
  	alert('请输入标题');
  	return;
  }
  if(news_desc.value==""){
  	alert('请输入描述');
  	return;
  }
  form.submit();
  }
  }
  </script>
  
  <body background="img/img/3.jpg">
	<c:if test="${empty  username}">
	<c:redirect url="../index.jsp"></c:redirect>
	<c:out value="<script>alert('请登录后保存新闻！')</script>"></c:out>
	</c:if>
    
     <form method="post" action="servlet/NewsServlet?methodName=save" id="f">
     <table align="center">
    <tr><td>标题</td><td><input type="text" name="title" id="tittle"></td></tr>
    <tr><td>描述</td><td><textarea rows="10" name="news_desc" id="news_desc"></textarea></td></tr>   
    <tr><td></td><td><input type="button" value="确定" id="but" ></td></tr>
     </table>
    </form>
  </body>
</html>

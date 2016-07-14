<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>来自寻你千百度的错误页面</title>
	<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="js/zzsc.js"></script>
	<style type="text/css">
	#zzsc{
	width:920px;
	margin:42px auto;
	}
	</style>
  </head>
  
  <body style="background:#eee;">
		<h1 style="text-align:center;margin-top:60px;">很抱歉，未能找到相关搜索结果!<h1>
		<div id="zzsc"></div>
		<div style="text-align:center;clear:both;margin-top:20px">
 	</body>
</html>

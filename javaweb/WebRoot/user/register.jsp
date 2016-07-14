<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'register.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  	<script type="text/javascript" src="js/jquery-1.6.4.min.js"></script>  
  </head>
  
  <script type="text/javascript">
 window.onload = function() {
		var form = document.getElementById("f");

		var username = document.getElementById("username");
		var userpwd = document.getElementById("userpwd");
		var button = document.getElementById("button");
		button.onclick = function() {
			var name = username.value;

			var pwd = userpwd.value;

			if (name == "") {
				alert('用户名不能为空');
				return;
			}
			if (pwd == "") {
				alert('密码不能为空');
				return;
			}
			form.submit();
		}
		username.onblur=function(){
		$.get("servlet/UserServlet?methodName=findByName&username="+username.value,
		function(result){
		if(result.indexOf('true')>-1){
		$('#result').html('用户名以注册');
		username.value="";
		}else{
		$('#result').html('用户名可用');
			
		}
		}
		)
		}
		}
		</script>
	
<body background="img/img/8.jpg">
<form action="servlet/UserServlet?methodName=save" method="post" id="f"><!--*********************************
--><table align="center">
		<tr>
		<td>用户名</td><td><input type="text" name="username" id="username"></td>
		<td><span id="result"></span></td>
		</tr>
		<tr>
		<td>密码</td><td><input type="text" name="userpwd" id="userpwd"></td>
		
		</tr>
		<tr>
		<td></td><td><input type="button" value="提交" id="button"></td>
		
		</tr>
		</table>
		
		
		</form>
</body>
</html>
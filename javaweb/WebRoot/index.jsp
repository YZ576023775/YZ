<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	
	<script type="text/javascript" src="js/jquery-1.6.4.min.js"></script>
	</head>
	<script type="text/javascript">
//	window.onload = function() {
//		var form = document.getElementById("f");

//		var username = document.getElementById("user");
//		var userpwd = document.getElementById("pwd");
//		var button1 = document.getElementById("button1");
//		var button2 = document.getElementById("button2");
//		button1.onclick = function() {
//			var name = username.value;

	//		var pwd = userpwd.value;

	//		if (name == "") {
		///		alert('用户名不能为空');
	//			return;
	//		}
	//		if (pwd == "") {
//				alert('密码不能为空');
///				return;
//			}
//			form.submit();
//		}
//		button2.onclick =function(){
//		window.location.href="../user/register.jsp";
//		window.location.href="user/register.jsp";//*******************************
			
//		}
		
//	}

	$(function(){
	$('#button1').click(function(){
	var username=$('#user').val();
	var userpwd=$('#pwd').val();
	
	if(username=""){
	alert('用户名不能为空！');
	return;
	}
	if(userpwd=""){
	alert('密码不能为空！');
	return;
	}
	$('#f').submit();
	
	}
	)
	$('#button2').click(function(){
	window.location.href="user/register.jsp";
	})
	
	});
	



</script>
	<body background="img/Winter.jpg">
		<!--<a href="../user/saveNews.jsp">添加新闻信息</a>
		<a href="saveNews.jsp">添加新闻信息</a>
		--><!--
		<form method="post" action="servlet/UserServlet?methodName=find" id="f">
			<table align="center">
				<tr>
					<td>
						用户名
					</td>
					<td colspan="2">
						<input type="text" name="username" id="user">
					</td>
				</tr>
				<tr>
					<td>
						密码
					</td>
					<td colspan="2">
						<input type="text" name="userpwd" id="pwd">
					</td>
				</tr>
				<tr>
				<td colspan="1">
				</td>
					<td colspan="1">
						<input type="submit" value="登录" id="button1"">
					</td>
					<td colspan="1">
						<input type="button" value="注册" id="button2">
					</td>
					
				</tr>
			</table>
		</form>
	
	
	-->
	<a href="../todayNewsMobile/">手机版</a>
	<div class="container" style=height:200px >
	
	<div id="free" style=height: 200px></div>
	<form  role="form" class="form-signin" action="servlet/UserServlet?methodName=find" method="post" id="f">
	<h2 class="form-signin-heading">请登陆</h2>
	<input name="username" type="text" class="form-control" id="user" placeholder="用户名" required autofocus>
	<input name="userpwd" id="pwd" type="text" class="form-control" placeholder="密码"  required>
	<input class="btn btn-lg btn-primary btn-block " type="button" id="button1" value="登录" >
	<input class="btn btn-lg btn-primary btn-block " type="button" id="button2" value="注册" >
	</form>
	</div>
	
	
	
	
	
	
	
	
	
	
	
	
	
	</body>
</html>

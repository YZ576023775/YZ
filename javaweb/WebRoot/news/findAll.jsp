<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
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

		<title>My JSP 'findAll.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery-1.6.4.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.css" type="text/css"></link>



</head>

<style type="text/css">
 .datalist {
	border: 3px groove #000fff;
	font-family: Arial, Helvetica, sans-serif;
	border-collapse: collapse;
	background-color: #0ffff0;
	color:#000;
	font-size: 20px;
	margin-left: 500px;
	margin-right: 500px;
	width: 440px;
}


.datalist th { /*包含选择器 表中的列 设置表头*/
	border: 1px double #FF0000;
	background-color: #999;
	color: #FFFFFF;
	font-weight: bold;
	padding-top: 4px;
	padding-bottom: 4px; /*内边距*/
	padding-left: 12px;
	padding-right: 12px;
	text-align: center;
}

  .datalist td { /*包含选择器 表中单元格*/
	border: 1px dotted #007180;
	text-align: left;
	padding-top: 4px;
	padding-bottom: 4px;
	padding-left: 10px;
	padding-right: 10px;
}
  .datalist tr.altrow { /*包含选择器*/
	background-color: #000;
	color:#FFF;
}
  #search{
	width:440px;
	margin-left: 500px;
	
	}
#keywords{
	width:360px;
	border:groove #63C 1px;
	background-color:#CCC;
}
div#leader count{
margin-left: 400px;


}

ul li{
list-style:none;
float:left;
}

</style>


	<script language="javascript">
	$(function(){
		$("table.datalist tr:nth-child(odd)").addClass("altrow");
		 $("table").mouseover(function() { //滑入时执行匿名函数
			$("tr:gt(0)").toggleClass("altrow"); //toggleClass方法交替类别
		});
		$("table").mouseout(function() { //滑出时执行匿名函数
			$("tr:gt(0)").toggleClass("altrow"); //$("tr:gt(0)")是过滤选择器
		}); 

	});
</script>
	
	<body background="img/Winter.jpg">

		<!--<c:if test="${empty  username}">
			<script>alert('请登陆！')</script>
			<c:redirect url="../index.jsp"></c:redirect>
			
		</c:if>


		--><div id="search" >
		<form action="servlet/NewsServlet?methodName=findAll" method="post">
		<input id="keywords" name="keywords" placeholder="关键字" type="text"/>
		<input  type="submit" value="搜索"/>
		</form>
		</div>



		<table align="center" class="datalist" id="oTable">
		<tr>
			<th>
				
					新闻标题
				
			</th>
			<th colspan="2">
				
					新闻操作
				
			</th>
		</tr>
			<c:forEach items="${list}" var="news">
				<tr>
					<td>
						<a href="servlet/NewsServlet?methodName=find&id=${news.id }">${news.title}</a>
					</td>
					<td>
						<a id="a" href="servlet/NewsServlet?methodName=delete&id=${news.id }" onclick="return(confirm('确认删除！'))">删除</a>
					</td>
					<td>
					<!--
						<a href="news/updateNews.jsp?&id=${news.id }">编辑</a>
						-->
						<a href="javascript:window.open('news/updateNews.jsp?id=${news.id}&title=${news.title}&news_desc=${news.news_desc}','','height=400,width=400,toolbar=no,menubar=no,resizeable=no,location=no,ststus=no');window.close();">编辑</a>
						
					</td>
				</tr>

			</c:forEach>
		</table>

		<div id="count">当前页：【${currentPageIndex+1} 】总页数: ${pages }</div>
	

		<div id="leader" style="margin-left: 550px">
		<nav>
			<ul id="page" class="pager" style="float:left">
				<li id="last">

					<c:if test="${currentPageIndex==0}">
						<span>上一页</span>
						<li class="previous disabled"><a href="#"><span aria-hidden="true"></span></a></li>
					</c:if>
					<c:if test="${currentPageIndex!=0}">
						<li class="previous active"><a
							href="servlet/NewsServlet?methodName=findAll&currentPageIndex=${currentPageIndex-1}&keywords=${keywords}">上一页</a></li>
					</c:if>


					<c:if test="${currentPageIndex==pages-1}">
						<li class="next diasbled"><a href="#"><span>下一页</span></a></li>
					</c:if>
					<c:if test="${currentPageIndex!=pages-1}">
						<li class="next active"><a href="servlet/NewsServlet?methodName=findAll&currentPageIndex=${currentPageIndex+1}&keywords=${keywords}">下一页</a></li>
					</c:if> 



				
				<!--<c:choose>
						<c:when test="${currentPageIndex==0}">
							<span>上一页</span>
							<a href="servlet/NewsServlet/methodName=findAll&currentPageIndex=${currentPageIndex+1}&keywords=${keywords}">下一页</a>
							
						</c:when>
						<c:when test="${currentPageIndex==(pages-1)}">
						<a href="servlet/NewsServlet/methodName=findAll&currentPageIndex=${currentPageIndex-1}&keywords=${keywords}">上一页</a>
							<span>下一页</span>
						</c:when>
						<c:otherwise >
							<a href="servlet/NewsServlet/methodName=findAll&currentPageIndex=${currentPageIndex-1}&keywords=${keywords}">上一页</a>
							<a href="servlet/NewsServlet/methodName=findAll&currentPageIndex=${currentPageIndex+1}&keywords=${keywords}">下一页</a>
						</c:otherwise>
						
					</c:choose>
				
				--></li>
			</ul>
			</nav>
			
		<nav>
			<ul id="pageindex" class="pager" style="float:left">
				<c:forEach items="${pageIndexList}" var="index">
					<c:if test="${index==currentPageIndex+1}">
						<li class="disabled active">
						
							<span>${index}</span> 
						</li>
					</c:if>

					<c:if test="${index!=(currentPageIndex+1)}">
						<li class="active">
							<a href="servlet/NewsServlet?methodName=findAll&currentPageIndex=${index-1}&keywords=${keywords}">${index}</a>
						</li>
					</c:if>
				</c:forEach>
			</ul>
			</nav>
		</div>


	</body>
</html>

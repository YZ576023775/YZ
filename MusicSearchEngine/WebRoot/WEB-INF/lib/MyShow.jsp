<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyShow.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet"/>
   <script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
   <script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
  </head>
  
  <body>
	<div id="paging" class="pagination pagination-right" >
		<ul>
			<c:choose>
				<c:when test="${pageBean.currentPage > 1}">
					<li><a
						href="javascript:changePage(${pageBean.currentPage-1 });"><i
							class="icon-fast-backward"></i></a></li>
				</c:when>
			</c:choose>


			<c:choose>
				<c:when test="${pageBean.pageCounts != 1}">
					<c:if test="${pageBean.currentPage==1}">
						<li><a style="background-color: #FFFADA;">1</a></li>
					</c:if>
					<c:if
						test="${pageBean.pageCounts!=null and pageBean.currentPage!=1}">
						<li><a href="javascript:changePage(1);">1</a></li>
					</c:if>
				</c:when>
				<c:when test="${pageBean.pageCounts == 1}">
					<li><a style="background-color: #FFFADA;");">${pageBean.pageCounts}</a></li>
				</c:when>
			</c:choose>



			<c:forEach begin="1" end="${pageBean.pageCounts}" varStatus="i">
				<c:choose>
					<c:when
						test="${pageBean.currentPage ==i.index and pageBean.currentPage!=pageBean.pageCounts and pageBean.currentPage!=1}">
						<li><a style="background-color: #FFFADA;">${i.index } </a></li>
					</c:when>
					<c:otherwise>
						<!-- 左侧 -->
						<c:if
							test="${pageBean.currentPage > 4 and pageBean.currentPage - i.index<4 and pageBean.currentPage >i.index}">
							<li><a href="javascript:changePage(${i.index });">${i.index }
							</a></li>
						</c:if>
						<c:if
							test="${pageBean.currentPage < 5 and pageBean.currentPage - i.index<3 and pageBean.currentPage > i.index and  i.index > 1}">
							<li><a href="javascript:changePage(${i.index });">${i.index }
							</a></li>
						</c:if>
						<!-- 右侧 -->
						<c:if
							test="${pageBean.pageCounts-pageBean.currentPage>4 and i.index - pageBean.currentPage <4 and i.index> pageBean.currentPage }">
							<li><a href="javascript:changePage(${i.index });">${i.index }
							</a></li>
						</c:if>
						<c:if
							test="${pageBean.pageCounts-pageBean.currentPage<5 and i.index - pageBean.currentPage <3 and i.index> pageBean.currentPage and i.index<pageBean.pageCounts }">
							<li><a href="javascript:changePage(${i.index });">${i.index }
							</a></li>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:choose>
				<c:when test="${pageBean.pageCounts != 1}">
					<c:if
						test="${pageBean.pageCounts!=null and pageBean.currentPage==pageBean.pageCounts}">
						<li><a style="background-color: #FFFADA;">${pageBean.pageCounts}</a></li>
					</c:if>
					<c:if
						test="${pageBean.pageCounts != 0 and pageBean.currentPage!=pageBean.pageCounts}">
						<li><a href="javascript:changePage(${pageBean.pageCounts});">${pageBean.pageCounts}</a></li>
					</c:if>
				</c:when>
			</c:choose>


			<c:if
				test="${pageBean.pageCounts != 0 and pageBean.currentPage != pageBean.pageCounts}">
				<li><a
					href="javascript:changePage(${pageBean.currentPage+1 });"><i
						class="icon-fast-forward"></i></a></li>
			</c:if>
		</ul>
	</div>
  </body>
</html>

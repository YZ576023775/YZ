<%@page import="com.yz.pagetool.PageBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
  <title>来自寻你千百度</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
   <script src="js/jquery-2.1.4.min.js"></script>
 	<link href="css/bootstrap.min.css" rel="stylesheet"/>
 	<link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/style.css" media="screen" type="text/css" />
   <script src="js/bootstrap.min.js"></script>
   <script src="js/autoComplete1.js"></script>
      <style type="text/css">
	.content{
	float:left;
	text-align:left;
	}
	.right{
	float:right;
	margin-top:120px;
	margin-right:150px;
	padding:25px;
	padding-left:45px;
	}
	img{
	width:13px;
	height:13px;
	}
	/* .container */ h3{
	margin:35px 0;
	}
body{
	background: #eee;
	}
</style>
   <script type="text/javascript">
	function get(){
		this.href="SearchEngineServlet?intent=search&keywords=";
		var keywords = $("#keywords").val();
		this.href=this.href+keywords;

	}
	$(function() {
		$("#keywords").focus();
		$("#a").click(get);
		$("#keywords").keydown(function(event){
			if(event.which == "13"){
				$("#a").attr("href","SearchEngineServlet?intent=search&keywords=");
				var keywords = $("#keywords").val();
				$("#a").attr("href",$("#a").attr("href")+keywords);
				/* window.open($("#a").attr("href")); */
				location.href=$("#a").attr("href");
			} 
		});
	});
   </script>

</head>
	

<body>
  <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" xml:space="preserve" xmlns:xml="http://www.w3.org/XML/1998/namespace" class="svg-defs">
  <defs>

    <pattern id='image' width="1" height="1" viewBox="0 0 100 100" preserveAspectRatio="none">
      <image xlink:href="https://s3-us-west-2.amazonaws.com/s.cdpn.io/78881/pattern_141.gif" width="100" height="100" preserveAspectRatio="none"></image>
    </pattern>

    <g id="shape-butterfly-1">
      <path class="path" fill="" clip-rule="evenodd" d="M8.65,2.85c0.934-0.2,2.15-0.333,3.65-0.4c1.534-0.1,2.667-0.083,3.4,0.05
		c1.533,0.267,3.45,0.767,5.75,1.5c2.466,0.8,4.35,1.617,5.65,2.45c2.066,1.2,3.883,2.383,5.45,3.55c2.567,2.1,4.35,3.8,5.35,5.1
		l2.1,2.5c0.933,1.167,1.517,1.983,1.75,2.45c0.333,0.767,1.083,2.117,2.25,4.05c0.233,0.467,0.717,1.683,1.45,3.65
		c0.733,2.067,1.2,3.45,1.4,4.15c0.467,1.733,0.917,3.767,1.35,6.1l0.4,3.85l-0.25-3.4c-0.6-5.967-1.267-10.25-2-12.85
		c-0.733-2.434-2.167-5.467-4.3-9.1c-0.966-1.667-1.566-3-1.8-4c-0.233-0.933-0.1-1.267,0.4-1c1.3,0.733,2.917,3.867,4.85,9.4
		c1.667,4.7,2.85,11.2,3.55,19.5c0.567,6.934,0.667,11.917,0.3,14.95l0.2,0.05c0.231,0,0.348-0.05,0.35-0.15v0.05l0.1,0.05v27.4
		c-0.032-0.018-0.065-0.035-0.1-0.05v-0.05c-0.7,0.267-0.983,0.117-0.85-0.45c0.067-0.333,0.017-0.817-0.15-1.45
		c-0.2-0.6-0.316-0.983-0.35-1.15l-0.5-1.65c-0.533-2.967-0.833-5.034-0.9-6.2c-0.1-1.533-0.133-2.4-0.1-2.6
		c0-0.933,0.167-1.667,0.5-2.2c0.567-0.9,0.684-1.75,0.35-2.55c-0.167-0.367-0.367-0.6-0.6-0.7c-0.333-0.133-0.517,0.283-0.55,1.25
		c-0.033,1.533-0.167,2.9-0.4,4.1c-0.1,2.3-0.267,3.684-0.5,4.15c-0.333,0.667-1.25,2.95-2.75,6.85c-1.167,2.8-2.233,4.817-3.2,6.05
		c-0.9,1.2-1.583,2.1-2.05,2.7c-0.8,1-1.434,1.667-1.9,2c-2.067,1.333-3.633,2.067-4.7,2.2c-3.033,0.267-4.95,0.317-5.75,0.15
		c-0.8-0.167-1.383-0.217-1.75-0.15c-0.533,0.1-1.033,0.45-1.5,1.05c-0.5,0.667-1.217,1.284-2.15,1.85
		c-0.934,0.567-1.85,0.934-2.75,1.1c-2.467,0.433-4.45,0.25-5.95-0.55c-0.7-0.4-1.467-1.15-2.3-2.25c-0.6-0.867-1.033-1.567-1.3-2.1
		c-0.267-0.667-0.483-1.483-0.65-2.45c-0.3-1.467-0.383-2.717-0.25-3.75c0.267-1.9,0.45-3.05,0.55-3.45
		c0.233-1.233,0.566-2.333,1-3.3C9.25,77.45,9.767,76.4,10,76c0.667-1.233,1.55-2.583,2.65-4.05c1.1-1.434,2.184-2.583,3.25-3.45
		c0.367-0.3,1.15-0.867,2.35-1.7c0.767-0.566,1.917-1.25,3.45-2.05c1.733-0.933,3.267-1.633,4.6-2.1
		c2.133-0.733,4.534-1.467,7.2-2.2c0.467-0.1,1.517-0.3,3.15-0.6c0.967-0.233,0.4-0.4-1.7-0.5c-2.434-0.1-4.534-0.3-6.3-0.6
		c-1.566-0.267-3.383-0.7-5.45-1.3c-2.8-0.8-4.467-1.317-5-1.55c-1.567-0.667-3.2-1.75-4.9-3.25c-1.733-1.533-3-3.1-3.8-4.7
		c-0.533-1.067-0.967-2.434-1.3-4.1c-0.233-1.067-0.3-2.133-0.2-3.2c0.133-0.833,0.183-1.3,0.15-1.4v-0.6
		c-2.467-3.233-3.983-5.433-4.55-6.6c-0.533-1.033-0.883-1.833-1.05-2.4c-0.3-0.867-0.466-1.85-0.5-2.95
		c-0.033-2.367,0.034-4.117,0.2-5.25c0.3-1.034,0.483-1.8,0.55-2.3c0.167-0.867,0.034-1.533-0.4-2c-0.6-0.7-1.133-1.517-1.6-2.45
		c-0.566-1.133-0.833-2.117-0.8-2.95c0.033-1.333,0.167-2.367,0.4-3.1c0.367-1.267,1.05-2.267,2.05-3
		C4.417,4.25,6.483,3.317,8.65,2.85z"/>
    <g>

  </defs>
</svg>
<div style="text-align:center;clear:both;position:absolute;top:30px;left:260px;z-index:-9999">
<script src="/gg_bd_ad_720x90.js" type="text/javascript"></script>
<script src="/follow.js" type="text/javascript"></script>
</div>
<section class="background"></section>

<section class="scene3d">

  <div class="cube skybox">
    <var class="scale">
    <figure class="face front"></figure>
    <figure class="face back"></figure>
    <figure class="face right"></figure>
    <figure class="face left"></figure>
    <figure class="face top"></figure>
    <figure class="face bottom"></figure>
    </var>
  </div>

  <div class="butterfly_container">
    <var class="rotate3d">
    <var class="scale">
    <var class="translate3d">
    <var class="translate3d-1">
    <figure class="butterfly">
      <svg class="wing left" viewBox="0 0 50 100" class="icon shape-codepen">
        <use class="left" xlink:href="#shape-butterfly-1"></use>
      </svg> 
      <svg class="wing right" viewBox="0 0 50 100" class="icon shape-codepen">
        <use class="left" xlink:href="#shape-butterfly-1"></use>
      </svg> 
    </figure>
    </var>
    </var>
    </var>
    </var>
  </div>

  <div class="butterfly_container" 
       style="margin-top: -150px; margin-left: 140px;
              -webkit-animation-duration: 5s;
              -moz-animation-duration: 5s;
              -ms-animation-duration: 5s;
              -o-animation-duration: 5s;
              animation-duration: 5s;">
    <var class="rotate3d">
    <var class="scale">
    <var class="translate3d">
    <var class="translate3d-1">
    <figure class="butterfly">
      <svg class="wing left" viewBox="0 0 50 100"
           style="-webkit-animation-duration: .75s;
                  -moz-animation-duration: .75s;
                  -ms-animation-duration: .75s;
                  -o-animation-duration: .75s;
                  animation-duration: .75s;">
        <use class="left" xlink:href="#shape-butterfly-1"></use>
      </svg> 
      <svg class="wing right" viewBox="0 0 50 100"
           style="-webkit-animation-duration: .75s;
                  -moz-animation-duration: .75s;
                  -ms-animation-duration: .75s;
                  -o-animation-duration: .75s;
                  animation-duration: .75s;">
        <use class="left" xlink:href="#shape-butterfly-1"></use>
      </svg> 
    </figure>
    </var>
    </var>
    </var>
    </var>
  </div>

  <div class="butterfly_container scale_half" 
       style="margin-top: -10px; margin-left: 50px;
              -webkit-animation-duration: 5s;
              -moz-animation-duration: 5s;
              -ms-animation-duration: 5s;
              -o-animation-duration: 5s;
              animation-duration: 5s;">
    <var class="rotate3d">
    <var class="scale">
    <var class="translate3d">
    <var class="translate3d-1">
    <figure class="butterfly">
      <svg class="wing left" viewBox="0 0 50 100"
           style="-webkit-animation-duration: .75s;
                  -moz-animation-duration: .75s;
                  -ms-animation-duration: .75s;
                  -o-animation-duration: .75s;
                  animation-duration: .75s;">
        <use class="left" xlink:href="#shape-butterfly-1"></use>
      </svg> 
      <svg class="wing right" viewBox="0 0 50 100"
           style="-webkit-animation-duration: .75s;
                  -moz-animation-duration: .75s;
                  -ms-animation-duration: .75s;
                  -o-animation-duration: .75s;
                  animation-duration: .75s;">
        <use class="left" xlink:href="#shape-butterfly-1"></use>
      </svg> 
    </figure>
    </var>
    </var>
    </var>
    </var>
  </div>

  <div class="butterfly_container scale_half" 
       style="margin-top: 20px;
              -webkit-animation-duration: 20s;
              -moz-animation-duration: 20s;
              -ms-animation-duration: 20s;
              -o-animation-duration: 20s;
              animation-duration: 20s;">
    <var class="rotate3d">
    <var class="scale">
    <var class="translate3d">
    <var class="translate3d-1">
    <figure class="butterfly">
      <svg class="wing left" viewBox="0 0 50 100"
           style="-webkit-animation-duration: 1s;
                  -moz-animation-duration: 1s;
                  -ms-animation-duration: 1s;
                  -o-animation-duration: 1s;
                  animation-duration: 1s;">
        <use class="left" xlink:href="#shape-butterfly-1"></use>
      </svg> 
      <svg class="wing right" viewBox="0 0 50 100"
           style="-webkit-animation-duration: 1s;
                  -moz-animation-duration: 1s;
                  -ms-animation-duration: 1s;
                  -o-animation-duration: 1s;
                  animation-duration: 1s;">
        <use class="left" xlink:href="#shape-butterfly-1"></use>
      </svg> 
    </figure>
    </var>
    </var>
    </var>
    </var>
  </div>

  <div class="butterfly_container scale_half" 
       style="margin-top: 100px; margin-left: 50px;
              -webkit-animation-duration: 20s;
              -moz-animation-duration: 20s;
              -ms-animation-duration: 20s;
              -o-animation-duration: 20s;
              animation-duration: 20s;">
    <var class="rotate3d">
    <var class="scale">
    <var class="translate3d">
    <var class="translate3d-1">
    <figure class="butterfly">
      <svg class="wing left" viewBox="0 0 50 100"
           style="-webkit-animation-duration: 1.2s;
                  -moz-animation-duration: 1.2s;
                  -ms-animation-duration: 1.2s;
                  -o-animation-duration: 1.2s;
                  animation-duration: 1.2s;">
        <use class="left" xlink:href="#shape-butterfly-1"></use>
      </svg> 
      <svg class="wing right" viewBox="0 0 50 100"
           style="-webkit-animation-duration: 1.2s;
                  -moz-animation-duration: 1.2s;
                  -ms-animation-duration: 1.2s;
                  -o-animation-duration: 1.2s;
                  animation-duration: 1.2s;">
        <use class="left" xlink:href="#shape-butterfly-1"></use>
      </svg> 
    </figure>
    </var>
    </var>
    </var>
    </var>
  </div>

  <div class="butterfly_container scale_third" 
       style="margin-top: 150px;
              -webkit-animation-duration: 20s;
              -moz-animation-duration: 20s;
              -ms-animation-duration: 20s;
              -o-animation-duration: 20s;
              animation-duration: 20s;">
    <var class="rotate3d">
    <var class="scale">
    <var class="translate3d">
    <var class="translate3d-1">
    <figure class="butterfly">
      <svg class="wing left" viewBox="0 0 50 100"
           style="-webkit-animation-duration: .35s;
                  -moz-animation-duration: .35s;
                  -ms-animation-duration: .35s;
                  -o-animation-duration: .35s;
                  animation-duration: .35s;">
        <use class="left" xlink:href="#shape-butterfly-1"></use>
      </svg> 
      <svg class="wing right" viewBox="0 0 50 100"
           style="-webkit-animation-duration: .35s;
                  -moz-animation-duration: .35s;
                  -ms-animation-duration: .53s;
                  -o-animation-duration: .35s;
                  animation-duration: .35s;">
        <use class="left" xlink:href="#shape-butterfly-1"></use>
      </svg> 
    </figure>
    </var>
    </var>
    </var>
    </var>
  </div>

  <div class="butterfly_container scale_third" 
       style="margin-top: -250px; margin-left: 300px; 
              -webkit-animation-duration: 4s;
              -moz-animation-duration: 4s;
              -ms-animation-duration: 4s;
              -o-animation-duration: 4s;
              animation-duration: 4s;">
    <var class="rotate3d">
    <var class="scale">
    <var class="translate3d">
    <var class="translate3d-1">
    <figure class="butterfly">
      <svg class="wing left" viewBox="0 0 50 100"
           style="-webkit-animation-duration: .45s;
                  -moz-animation-duration: .45s;
                  -ms-animation-duration: .45s;
                  -o-animation-duration: .45s;
                  animation-duration: .45s;">
        <use class="left" xlink:href="#shape-butterfly-1"></use>
      </svg> 
      <svg class="wing right" viewBox="0 0 50 100"
           style="-webkit-animation-duration: .45s;
                  -moz-animation-duration: .45s;
                  -ms-animation-duration: .45s;
                  -o-animation-duration: .45s;
                  animation-duration: .45s;">
        <use class="left" xlink:href="#shape-butterfly-1"></use>
      </svg> 
    </figure>
    </var>
    </var>
    </var>
    </var>
  </div>

</section>
		<div class="container" style="position:relative;">
			<div style="padding: 40px 145px 10px;">
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<input id="keywords" type="text" placeholder="单曲/歌手/专辑/MV" title="搜索" autocomplete="off" class="form-control"> <span
								class="input-group-btn"> <a id="a" target="_blank"
								href="SearchEngineServlet?intent=search&keywords="
								class="btn btn-primary">搜索一下</a>

							</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div id="divAutoList"></div>
						</div>
					</div>
				</div>
			</div>
			
			<div style="padding: 0 145px 0;max-width:960px;">
				<div class="content">
					<%
				List<Map<String, String>> list = ((PageBean) request
						.getAttribute("pageBean")).getDatas();
				for (Map<String, String> map : list) {
					if (map.keySet().contains("music")) {
						request.setAttribute("music", map.get("music"));
						request.setAttribute("mu_url", map.get("mu_url"));
			%>
<!--  music-->					
					<%
			
					if(map.get("mu_url").startsWith("http://music.baidu.com")){
			%>
		
		<h3><img src="img/baidu_logo.png">
		<strong>
			<b class="text-success">单曲</b><a target="_blank" href='${mu_url }'> ${music} </a>
		</strong>
		</h3>
		
		<%	
					}else if(map.get("mu_url").startsWith("http://www.kuwo.cn")){
			%>
			
		<h3><img src="img/kuwo_logo.png">
		<strong>
			<b class="text-success">单曲</b><a target="_blank" href='${mu_url }'> ${music} </a>
		</strong>
		</h3>
		<%	
					}else if(map.get("mu_url").startsWith("http://www.xiami.com")){
			%>
			
		<h3><img src="img/xiami_logo.png">
		<strong>
			<b class="text-success">单曲</b><a target="_blank" href='${mu_url }'> ${music} </a>
		</strong>
		</h3>
		
		<%
		}
			%>

<!--album  -->

			<%
				} else if (map.keySet().contains("album")) {
						request.setAttribute("album", map.get("album"));
						request.setAttribute("al_url", map.get("al_url"));
			%>

					<%
			
					if(map.get("al_url").startsWith("http://music.baidu.com")){
			%>
		
		<h3><img src="img/baidu_logo.png">
		<strong>
			<b class="text-primary">专辑</b><a target="_blank" href='${al_url }'> ${album} </a>
		</strong>
		</h3>
		
		<%	
					}else if(map.get("al_url").startsWith("http://www.kuwo.cn")){
			%>
			
		<h3><img src="img/kuwo_logo.png">
		<strong>
			<b class="text-primary">专辑</b><a target="_blank" href='${al_url }'> ${album} </a>
		</strong>
		</h3>
		<%	
					}else if(map.get("al_url").startsWith("http://www.xiami.com")){
			%>
			
		<h3><img src="img/xiami_logo.png">
		<strong>
			<b class="text-primary">专辑</b><a target="_blank" href='${al_url }'> ${album} </a>
		</strong>
		</h3>
		
		<%
		}
			%>
			
<!-- mv -->

		<%
		} else if (map.keySet().contains("mv")) {
				request.setAttribute("mv", map.get("mv"));
				request.setAttribute("mv_url", map.get("mv_url"));
			%>
					<%
			
					if(map.get("mv_url").startsWith("http://music.baidu.com")){
			%>
		
		<h3><img src="img/baidu_logo.png">
		<strong>
			<b class="text-warning">MV</b><a target="_blank" href='${mv_url }'> ${mv} </a>
		</strong>
		</h3>
		
		<%	
					}else if(map.get("mv_url").startsWith("http://www.kuwo.cn")){
			%>
			
		<h3><img src="img/kuwo_logo.png">
		<strong>
			<b class="text-warning">MV</b><a target="_blank" href='${mv_url }'> ${mv} </a>
		</strong>
		</h3>
		<%	
					}else if(map.get("mv_url").startsWith("http://www.xiami.com")){
			%>
			
		<h3><img src="img/xiami_logo.png">
		<strong>
			<b class="text-warning">MV</b><a target="_blank" href='${mv_url }'> ${mv} </a>
		</strong>
		</h3>
		
		<%
		}
			%>
			
<!--artist  -->

		<%
		}else if (map.keySet().contains("artist")) {
				request.setAttribute("artist", map.get("artist"));
				request.setAttribute("ar_url", map.get("ar_url"));
 		 	%>
					<%
			
					if(map.get("ar_url").startsWith("http://music.baidu.com")){
			%>
		
		<h3><img src="img/baidu_logo.png">
		<strong>
			<b class="text-info">歌手</b><a target="_blank" href='${ar_url }'> ${artist} </a>
		</strong>
		</h3>
		
		<%	
					}else if(map.get("ar_url").startsWith("http://www.kuwo.cn")){
			%>
			
		<h3><img src="img/kuwo_logo.png">
		<strong>
			<b class="text-info">歌手</b><a target="_blank" href='${ar_url }'> ${artist} </a>
		</strong>
		</h3>
		<%	
					}else if(map.get("ar_url").startsWith("http://www.xiami.com")){
			%>
			
		<h3><img src="img/xiami_logo.png">
		<strong>
			<b class="text-info">歌手</b><a target="_blank" href='${ar_url }'> ${artist} </a>
		</strong>
		</h3>
		
		<%
		}
			%>

		<%
		}
  	}
  			%>

		</div>

		</div>
			<div class="right" style="border-left:solid #337ab7 1px;max-width: 250px;">
			<h1>其他人还搜</h1>
				<c:forEach var="item" items="${otherList}">
					<a  style="cursor:pointer;margin:15px;"
						href="SearchEngineServlet?intent=search&keywords=${item}"><h1 class="text-danger">${item}</h1></a>
				</c:forEach>
			</div>


		<div class="row">
			<%-- <div id="count" class="col-md-5 col-md-offset-1">
					<h2  class="text-info">
						<strong>当前页:【${pageBean.page.currentPage}】总页数:${pageBean.page.totalPage }</strong>
					</h2>
				</div> --%>
		<div class="col-md-10 col-md-offset-1" style="margin-left:145px;">
			<ul id="pageindex" class="pagination pagination-md">

				<c:if test="${pageBean.page.currentPage==1}">
					<li class="previous disabled " ><a href="#"><span>上一页</span></a></li>

				</c:if>
				<c:if test="${pageBean.page.currentPage!=1}">
					<li class="previous active" ><a  style="cursor:pointer"
						href="SearchEngineServlet?intent=search&keywords=${keywords}&cp=${pageBean.page.prevPage}">上一页</a></li>
				</c:if>

				<c:forEach var="i" begin="${pageBean.page.paginationBegin}" step="1"
					end="${pageBean.page.paginationEnd}">
					<c:if test="${i==pageBean.page.currentPage}">
						<li class="disabled"><span>${i}</span></li>
					</c:if>

					<c:if test="${i!=pageBean.page.currentPage}">
						<li class="active"><a style="cursor:pointer"
							href="SearchEngineServlet?intent=search&keywords=${keywords}&cp=${i}">${i}</a>
						</li>
					</c:if>
				</c:forEach>

				<c:if test="${pageBean.page.currentPage==pageBean.page.totalPage}">
					<li class="next disabled"><a href="#"><span>下一页</span></a></li>
				</c:if>
				<c:if test="${pageBean.page.currentPage!=pageBean.page.totalPage}">
					<li class="next active"><a style="cursor:pointer"
						href="SearchEngineServlet?intent=search&keywords=${keywords}&cp=${pageBean.page.nextPage}">下一页</a></li>
				</c:if>
			</ul>
			</div>
		</div>
 	 </div>
</body>
</html>

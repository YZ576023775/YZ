<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
	<link rel="stylesheet" href="css/foundation.min.css">
	<script src="js/jquery-2.1.4.min.js"></script>
	<script src="js/foundation.min.js"></script>
	<script src="js/modernizr.js"></script>
	<script src="js/autoComplete.js"></script>
</head>
<style>
	body {
	margin:0;
	padding:0;
	text-align:center;
  background: #eee;
	}

	.content{
	text-align:left;
	}
/* 	#keywords{
	position:relative;
	} */
</style>
<script type="text/javascript">

	$(window).resize(function() {
		$(".container").css({
			"position" : "absolute",
			"z-index" : 9999,
			left : ($(window).width() - $(".container").outerWidth()) / 2,
			top : ($(window).height() - $(".container").outerHeight()) / 2
		});
	});
	function get(){
		this.href="SearchEngineServlet?intent=search&keywords=";
		var keywords = $("#keywords").val();
		this.href=this.href+keywords;

	}
	$(function() {
		$(window).resize();
		$("#keywords").focus();
		$("#a").click(get);
		$("#keywords").keydown(function(event){
			if(event.which == "13"){
				$("#a").attr("href","SearchEngineServlet?intent=search&keywords=");
				var keywords = $("#keywords").val();
				$("#a").attr("href",$("#a").attr("href")+keywords);
				window.open($("#a").attr("href"));
			} 
		});
	});
</script>


<body>

 <canvas id="text" width="1300" height="300" style="margin-top:-300px;"></canvas>
<canvas id="stage" width="500" height="100"></canvas>
<div class="row container" >
	<div class="row ">
		<div class="large-12 columns">
			<div class="row collapse">
				<div class="small-10 columns ">
					<input id="keywords" type="text" autocomplete="off" placeholder="单曲/歌手/专辑/MV">
				</div>
				<div class="small-2 columns ">
					<a id="a" target="_blank" href="SearchEngineServlet?intent=search&keywords=" class="button postfix">搜索一下</a>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="large-12 columns">
			<div class="row collapse">
				<div class="small-10 columns ">
					<div id="divAutoList" class="content"></div>
				</div>
			</div>
		</div>
	</div>
</div>
  <script src='js/EasePack.min.js'></script><script src='js/TweenLite.min.js'></script><script src='js/easeljs-0.7.1.min.js'></script><script src='js/requestAnimationFrame.js'></script>

  <script src="js/index.js"></script>
</body>
</html>
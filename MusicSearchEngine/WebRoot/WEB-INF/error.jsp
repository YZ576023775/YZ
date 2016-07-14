<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>来自寻你千百度的错误页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<script src="js/easeljs-NEXT.combined.js"></script>

 <script id="editable">
	var canvas;
	var stage;

	var imgSeq = new Image();        // The image for the sparkle animation
	var sprite;                        // The animated sparkle template to clone
	var fpsLabel;

	function init() {
		// create a new stage and point it at our canvas:
		canvas = document.getElementById("testCanvas");
		canvas.setAttribute("width", window.innerWidth);
		canvas.setAttribute("height",  window.innerHeight);
		stage = new createjs.Stage(canvas);
		// attach mouse handlers directly to the source canvas.
		// better than calling from canvas tag for cross browser compatibility:
		stage.addEventListener("stagemousemove", moveCanvas);
		stage.addEventListener("stagemousedown", clickCanvas);

		// define simple sprite sheet data specifying the image(s) to use, the size of the frames,
		// and the registration point of the frame
		// it will auto-calculate the number of frames from the image dimensions and loop them
		var data = {
			images: ["img/spritesheet_sparkle.png"],
			frames: {width: 21, height: 23, regX: 10, regY: 11}
		};

		// set up an animation instance, which we will clone
		sprite = new createjs.Sprite(new createjs.SpriteSheet(data));

		// add a text object to output the current FPS:
		fpsLabel = new createjs.Text("-- fps", "bold 14px Arial", "#FFF");
		stage.addChild(fpsLabel);
		fpsLabel.x = 10;
		fpsLabel.y = 20;

		// start the tick and point it at the window so we can do some work before updating the stage:
		createjs.Ticker.timingMode = createjs.Ticker.RAF;
		createjs.Ticker.addEventListener("tick", tick);
	}

	function tick(event) {
		// loop through all of the active sparkles on stage:
		var l = stage.getNumChildren();
		var m = event.delta / 16;
		for (var i = l - 1; i > 0; i--) {
			var sparkle = stage.getChildAt(i);

			// apply gravity and friction
			sparkle.vY += 0.8 * m;
			sparkle.vX *= 0.99;

			// update position, scale, and alpha:
			sparkle.x += sparkle.vX * m;
			sparkle.y += sparkle.vY * m;
			sparkle.scaleX = sparkle.scaleY = sparkle.scaleX + sparkle.vS * m;
			sparkle.alpha += sparkle.vA * m;

			//remove sparkles that are off screen or not invisble
			if (sparkle.alpha <= 0 || sparkle.y > canvas.height) {
				stage.removeChildAt(i);
			}
		}

		fpsLabel.text = Math.round(createjs.Ticker.getMeasuredFPS()) + " fps";

		// draw the updates to stage
		stage.update(event);
	}

	//sparkle explosion
	function clickCanvas(evt) {
		addSparkles(Math.random() * 200 + 100 | 0, stage.mouseX, stage.mouseY, 1);
	}

	//sparkle trail
	function moveCanvas(evt) {
		addSparkles(Math.random() * 6 + 3 | 0, stage.mouseX, stage.mouseY, 0.5);
	}

	function addSparkles(count, x, y, speed) {
		//create the specified number of sparkles
		for (var i = 0; i < count; i++) {
			// clone the original sparkle, so we don't need to set shared properties:
			var sparkle = sprite.clone();

			// set display properties:
			sparkle.x = x;
			sparkle.y = y;
			//sparkle.rotation = Math.random()*360;
			sparkle.alpha = Math.random() * 0.5 + 0.5;
			sparkle.scaleX = sparkle.scaleY = Math.random() + 0.3;

			// set up velocities:
			var a = Math.PI * 2 * Math.random();
			var v = (Math.random() - 0.5) * 30 * speed;
			sparkle.vX = Math.cos(a) * v;
			sparkle.vY = Math.sin(a) * v;
			sparkle.vS = (Math.random() - 0.5) * 0.2; // scale
			sparkle.vA = -Math.random() * 0.05 - 0.01; // alpha

			// start the animation on a random frame:
			sparkle.gotoAndPlay(Math.random() * sparkle.spriteSheet.getNumFrames());

			// add to the display list:
			stage.addChild(sparkle);
		}
	}

 </script>
 <style type="text/css">
 body{
 margin:0;
 padding:0;
 }
 </style>
 </head>

<body onload="init();" style="background:#444444;">
<h1 style="position:absolute;left:50%;top:50%;margin-left:-270px;margin-top:-20px;z-index:-999">很抱歉，未能找到相关搜索结果！<h1>
<div>
	<canvas id="testCanvas" style="position:absolute;z-index:999"
			></canvas>
</div>
</body>
</html>


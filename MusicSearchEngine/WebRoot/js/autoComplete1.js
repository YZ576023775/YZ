var highlightindex = -1; //定义高亮显示索引,-1代表不高亮任何行 
var timeOutId = null; //定义延迟时间Id
var delayTime = 300; //默认延迟0.5秒
var minPrefix = 0; //定义最小几个字符开始搜索
var mouseOverCss = { background: "white", cursor: " pointer" }; //mouseover时样式
var mouseOutCss = { background: "white" }; //mouseout时样式
var grayCss = { background: "#ccc", cursor: " pointer" };
var upDownGrayCss = { background: "#ccc" };
var upDownWhiteCss = { background: "white" };
var tmp = null;
var ajaxProcessUrl = "SearchEngineServlet"; //发送ajax请求调用处理url

$(document).ready(function() {
	var bind = function(newDivNode){
		
		newDivNode.mouseover(function() {
			if (highlightindex != -1) {
				$("#divAutoList").children("div")
				.eq(highlightindex)
				.css(mouseOverCss);
			}
			highlightindex = $(this).attr("id");
			$(this).css(grayCss);
		});
		
		//添加光标移出事件,取消高亮
		newDivNode.mouseout(function() {
			$(this).css(mouseOutCss);
		});
		
		//添加光标mousedown事件  点击事件newDivNode.click可能不起作用?
		newDivNode.mousedown(function() {
			var comText = $(this).text();
			$("#divAutoList").hide();
			highlightindex = -1;
			$("#keywords").val(comText);
			$("#a").attr("href","SearchEngineServlet?intent=search&keywords=");
			var keywords = $("#keywords").val();
			$("#a").attr("href",$("#a").attr("href")+keywords);
			window.location.href=$("#a").attr("href");
		});
	}
	var req = function (){
		var autoNode = $("#divAutoList"); //可供选择的项
		var wordText = $("#keywords").val();
		wordText=$.trim(wordText);
		if (wordText.length < minPrefix) return;
		//取消上次提交
    window.clearTimeout(timeOutId);
		if(wordText.length>0){
			if(tmp!=wordText){
				timeOutId = setTimeout(function() {
					//ajax请求 接受后自动转成对象 是什么就是什么	eval转对象 是什么就是什么 stringify转字符串 
					//$包装原对象变成加index对象 属性 0 1 。。length
					//遍历jquery对象 this 是 0 1 2.。对应得值 $(this)还是jquery对象
					$.get(ajaxProcessUrl,{intent:'search',flag:'ajax',keywords:wordText}, function(data) {
//						console.log("data"+typeof data+ data+"\n");
//						console.log("dataJSON.stringify"+JSON.stringify(data)+"\n");
//						console.log("dataParse"+eval(data)+"\n");
//						console.log("dataParseJSON.stringify"+JSON.stringify(eval(data))+"\n");
						var jqueryObj = $(data);
//						console.log("jqueryObj"+jqueryObj+"\n");
//						console.log("jqueryObjJSON.stringify"+JSON.stringify(jqueryObj)+"\n");
//						var wordNodes = jqueryObj.find("KeyWord"); //xml节点名
						autoNode.html("");     //清空
						$.each(jqueryObj,function(i) {
//							console.log("this"+JSON.stringify(this));
							var wrodNode = $(this);
//							alert(wrodNode[0]);
//							console.log("wrodNode"+JSON.stringify(wrodNode)+"\n");
							$.each(wrodNode[0], function(key,value){
//								console.log("secondthis"+this)
//								console.log("secondthisstring"+JSON.stringify(this))
//								console.log("secondthisjquery"+$(this))
//								console.log("secondthisstringjquery"+JSON.stringify($(this)))
//								console.log("key"+key+"value"+value);
//								alert("key"+key+"value"+value);
								if(key=='artist'){
									var newDivNode = $("<div>").attr("id", i);
									newDivNode.html(value).appendTo(autoNode); //xml文本内容( wrodNode.text() )
									autoNode.children("div").eq(highlightindex).css(upDownWhiteCss);
									bind(newDivNode);
								}else if(key=='music'){
									var newDivNode = $("<div>").attr("id", i);
									newDivNode.html(value).appendTo(autoNode); //xml文本内容( wrodNode.text() )
									autoNode.children("div").eq(highlightindex).css(upDownWhiteCss);
									bind(newDivNode);
								}else if(key=='album'){
									var newDivNode = $("<div>").attr("id", i);
									newDivNode.html(value).appendTo(autoNode); //xml文本内容( wrodNode.text() )
									autoNode.children("div").eq(highlightindex).css(upDownWhiteCss);
									bind(newDivNode);
								}else if(key=='mv'){
									var newDivNode = $("<div>").attr("id", i);
									newDivNode.html(value).appendTo(autoNode); //xml文本内容( wrodNode.text() )
									autoNode.children("div").eq(highlightindex).css(upDownWhiteCss);
									bind(newDivNode);
								}
								
							
						});
							
					});
						if (jqueryObj.length > 0) {
							autoNode.show();
						}
						else {
							autoNode.hide();
							highlightindex = -1;
						}
						
					}, "json"); //xml结果集
				}, delayTime);
				tmp = wordText;
			}
		}
	}
    var wordInput = $("#keywords");
    var wordInputOffset = wordInput.offset();

    //隐藏自动补全框,并定义css属性
    $("#divAutoList").hide()
              .css("position", "absolute")
              .css("border", "1px solid black") //边框 黑色
              /* .css("top", "0px")*/
               /* .css("top", wordInputOffset.top + wordInput.height() + 5 + "px")*/
              /*.css("left",0+"px")*/
               .css("top",  0+ "px")
              .css("left", 31+ "px")
              .css("z-index",9999)
              .width(wordInput.width() + 22);


    //给文本框添加键盘按下并弹起的事件
    wordInput.keyup(function(event) {
        var myEvent = event || window.event;
        var keyCode = myEvent.keyCode;
       

        //        if (keyCode >= 65 && keyCode <= 90 || keyCode == 8 || keyCode == 46) { //输入字母,退格或删除,显示最新的内容
        if (/*keyCode != 13 &&*/ keyCode != 38 && keyCode != 40) { //不是三个特殊键，可以搜索
        	req();
        }
        else if (keyCode == 38) {//输入向上,选中文字高亮
            var autoNodes = $("#divAutoList").children("div")
            if (highlightindex != -1) {
                autoNodes.eq(highlightindex).css(upDownWhiteCss);
                highlightindex--;
            }
            else {
                highlightindex = autoNodes.length - 1;
            }

            if (highlightindex == -1) {
                highlightindex = autoNodes.length - 1;
            }
            
            var cur = autoNodes.eq(highlightindex);
            $("#keywords").val(cur.text());
            autoNodes.eq(highlightindex).css(upDownGrayCss);
        }
        else if (keyCode == 40) {//输入向下,选中文字高亮
            var autoNodes = $("#divAutoList").children("div")
            if (highlightindex != -1) {
                autoNodes.eq(highlightindex).css(upDownWhiteCss);
            }
            highlightindex++;
            if (highlightindex == autoNodes.length) {
                highlightindex = 0;
            }
            var cur = autoNodes.eq(highlightindex);
            $("#keywords").val(cur.text());
            autoNodes.eq(highlightindex).css(upDownGrayCss);
        }/*
        else if (keyCode == 13) {//输入回车
            if (highlightindex != -1) {
                var comText = $("#divAutoList").hide().children("div").eq(highlightindex).text();
                highlightindex = -1;
                $("#keywords").val(comText);
                return false;
            }
            else {
                alert("文本框中的[" + $("#keywords").val() + "]被提交了！");
                $("#divAutoList").hide();
                $("#keywords").get(0).blur();
                return true;
            }
        }*/
    });

    //给查询框添加blur事件，隐藏提示层
    $("#keywords").blur(function() {
        $("#divAutoList").hide();
    });
    $("#keywords").focus(function() {
    	if($("#keywords").val().length>0){
    		$("#divAutoList").show();
    	}
    });

});


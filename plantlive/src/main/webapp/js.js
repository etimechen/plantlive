
//$(document).ajaxStop($.unblockUI);
$(document).ready(function(){
//	$.ajaxSetup({
//		async : false
//	});
	$(document).ajaxError(function(){
		$.unblockUI();
	});
	
	var commentStart, commentLimit, commentCount;
	commentStart = 0;
	commentLimit = 10;
	commentCount = 0;
	
	//日期格式化
	Date.prototype.format = function(format) {
	       var date = {
	              "M+": this.getMonth() + 1,
	              "d+": this.getDate(),
	              "h+": this.getHours(),
	              "m+": this.getMinutes(),
	              "s+": this.getSeconds(),
	              "q+": Math.floor((this.getMonth() + 3) / 3),
	              "S+": this.getMilliseconds()
	       };
	       if (/(y+)/i.test(format)) {
	              format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
	       }
	       for (var k in date) {
	              if (new RegExp("(" + k + ")").test(format)) {
	                     format = format.replace(RegExp.$1, RegExp.$1.length == 1
	                            ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
	              }
	       }
	       return format;
	}
	
	//获取评论
	function selectcomment(start, limit){
		$.get("/plantlive/comment/selectcomment",
		  {
		    start:start,
		    limit:limit
		  },
		  function(data,status){
		  	if(data.success){
		  		var newDate = new Date();
		  		commentCount = data.resultsCount;
		  		$.each(data.results, function( index, value ) {
		  			newDate.setTime(value.commentdatetime);	  			
				  $("#commentcontainer").append("<div class=\"row comment\"><p class=\"commentp\">" 
				  + value.commentcontent 
				  + "</p><div class=\"pull-right votefontdiscribe\">"
				  + value.commentcity
				  + " "
				  + newDate.format('yy-MM-dd hh:mm:ss')
				  + "</div></div>")
				});
				$(window).scroll(scrollPage);
				$("#commentloading").remove();
			}
		  });
	}
	
	//滚动条分页事件
	function scrollPage(){
		if($(window).scrollTop() + 1 >= ($(document).height() - $(window).height())) {
        	commentStart += 10;
        	if(commentCount > commentStart){
        		$("#commentcontainer").append("<div id=\"commentloading\" class=\"row  text-center\">---------------------------- 数据加载中 ----------------------------</div>");
        		$ (window).unbind ('scroll');
        		$(document).scrollTop($(document).height() - $(window).height());
        		selectcomment(commentStart, commentLimit);
        	}
        	$ (window).unbind ('scroll');           
        }
	}
	
	//发送评论
	function insertcomment(){
		var commentcontent = $("#commentcontent").val();
		$.post("/plantlive/comment/insertcomment",
		  {
		    commentcontent:commentcontent
		  },
		  function(data,status){
		  	$.unblockUI();
		  	if(data.success){
		  		$("#commentcontent").val('');
		  		$("#commentcontainer").empty();
		  		commentStart = 0;
		  		selectcomment(commentStart, commentLimit);
		  	}
		  });
	}
	
	//投票
	function plantVote(voteyesorno){
		$.post("/plantlive/vote/insertvote",
		  {
		    voteyesorno:voteyesorno
		  },
		  function(data,status){
		  	$.unblockUI();
		  	if(data.success){
		  		$("#yesresult").html(data.results.yes);
		  		$("#noresult").html(data.results.no);
		  	}else{
		  		$.blockUI({message: '一天1票哦',css: {backgroundColor:'#000',lineHeight:'40px',color:'#fff',opacity:0.6,height:'40px',border:0 } });
				setTimeout(function(){$.unblockUI();},2000);
		  	}
		  });
	}
	
	//获取投票结果
	function selectVote(){
		$.get("/plantlive/vote/selectvote",
		  function(data,status){
		  	if(data.success){
		  		$("#yesresult").html(data.results.yes.toString());
		  		$("#noresult").html(data.results.no.toString());  		
		  	}
		  });
	}
	
	//获取天气数据
	$.getJSON("http://api.k780.com:88/?app=weather.today&weaid=101280601&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json&&jsoncallback=?",function(json){
		var weather = json.result;
		if(json.result!=null){
			$("#weather").html(weather.citynm + " " + weather.weather_curr + " " + weather.temperature);
		}else{
			$("#weather").html("无法获取天气数据");
		}
	});
	
	selectVote();
	$("#commentcontainer").empty();
	selectcomment(commentStart, commentLimit);
	
	$.blockUI.defaults.overlayCSS =  {
		backgroundColor: '#fff',
		opacity: 0.6
	},
	$('#yes').click(function() {
		$.blockUI({message: '<img src="images/loading.gif" />',css: {backgroundColor:null,border:0 } });
		plantVote(1);
	});
	$('#no').click(function() {
		$.blockUI({message: '<img src="images/loading.gif" />',css: {backgroundColor:null,border:0 } });
		plantVote(0);
	});
	$('#commentbtn').click(function() {
		var content = $("#commentcontent").val();
		if(content.trim()==''){
			$.blockUI({message: '说点什么吧',css: {backgroundColor:'#000',lineHeight:'40px',color:'#fff',opacity:0.6,height:'40px',border:0 } });
			setTimeout(function(){$.unblockUI();},2000);
		}else{
			$.blockUI({message: '<img src="images/loading.gif" />',css: {backgroundColor:null,border:0 } });
			insertcomment();
		}
	})
});
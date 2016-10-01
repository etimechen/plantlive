$(document).ready(function(){
	$(document).ajaxError(function(){
		$.unblockUI();
	});
	
	var commentStart, commentLimit, commentCount, excuteTime, floor, siv;
	commentStart = 0;
	commentLimit = 10;
	commentCount = 0;
	excuteTime = "17:00";
	timeDifferent = 0;
	floor = 0;
	
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
	
	//获取执行时间
	function getExcuteTime(){
		$.get("/plantlive/irrigation/getconfigtime",
		  function(data,status){
		  	if(data.success){
		  		excuteTime = data.excutetime.toString();
		  		timeDifferent = Math.floor(data.timedifferent/1000);
		  		$(".excutetime").html(excuteTime);
		  		timeJudge();
		  		countDown();
		  		siv = setInterval(countDown,1000);
		  	}
		 });
	}
	
	function timeJudge(){
		var nowDate, nowTime, exString, exDate, exTime;
		nowDate = new Date();
		nowTime = nowDate.getTime();
		exString = nowDate.format("yyyy/MM/dd") + " " + excuteTime + ":00";
		exDate = new Date(exString);
		exTime = exDate.getTime();
		if(nowTime >= exTime){
			$("#todayover").removeClass("hide");
			$("#tomorrow").removeClass("hide");
		}else{
			$("#today").removeClass("hide");
		}
	}
	
	//倒计时
	function countDown(){
		if(timeDifferent > 1){
			timeDifferent -= 1;
		   var h=Math.floor(timeDifferent/60/60%24);
		   var m=Math.floor(timeDifferent/60%60);
		   var s=Math.floor(timeDifferent%60);
			$(".countdown").html("还剩"+h+"小时"+m+"分"+s+"秒"+"执行");
		}else{
			$("#todayover").removeClass("hide");
			$("#tomorrow").removeClass("hide");
			$("#today").addClass("hide");
			$(".countdown").addClass("hide");
			clearInterval(siv); 
		}		
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
		  			floor++;
		  			newDate.setTime(value.commentdatetime);	  			
				  $("#commentcontainer").append("<div class=\"row comment\"><span class=\"votefontdiscribe\">"
				  + floor
				  + "楼</span><p class=\"commentp\">" 
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
        		$("#commentcontainer").append("<div id=\"commentloading\" class=\"row  text-center\">数据加载中</div>");
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
		  		floor = 0;
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
		  		$("#today").children().children("#yesresult").html(data.results.today.yes);
		  		$("#today").children().children("#noresult").html(data.results.today.no);
		  		$("#todayover").children().children("#yesresult").html(data.results.today.yes);
		  		$("#todayover").children().children("#noresult").html(data.results.today.no);
		  		$("#tomorrow").children().children("#yesresult").html(data.results.tomorrow.yes);
		  		$("#tomorrow").children().children("#noresult").html(data.results.tomorrow.no);
		  	}else{
		  		$.blockUI({message: '一天1票哦',css: {backgroundColor:'#000',lineHeight:'40px',color:'#fff',opacity:0.8,height:'40px',border:0 } });
				setTimeout(function(){$.unblockUI();},2000);
		  	}
		  });
	}
	
	//获取投票结果
	function selectVote(){
		$.get("/plantlive/vote/selectvote",
		  function(data,status){
		  	if(data.success){
		  		$("#today").children().children("#yesresult").html(data.results.today.yes);
		  		$("#today").children().children("#noresult").html(data.results.today.no);
		  		$("#todayover").children().children("#yesresult").html(data.results.today.yes);
		  		$("#todayover").children().children("#noresult").html(data.results.today.no);
		  		$("#tomorrow").children().children("#yesresult").html(data.results.tomorrow.yes);
		  		$("#tomorrow").children().children("#noresult").html(data.results.tomorrow.no);		
		  	}
		  });
	}
	
	getExcuteTime();
	
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
	$('#today').children('#yes').click(function() {
		$.blockUI({message: '<img src="images/loading.gif" />',css: {backgroundColor:null,border:0 } });
		plantVote(1);
	});
	$('#tomorrow').children('#yes').click(function() {
		$.blockUI({message: '<img src="images/loading.gif" />',css: {backgroundColor:null,border:0 } });
		plantVote(1);
	});
	$('#today').children('#no').click(function() {
		$.blockUI({message: '<img src="images/loading.gif" />',css: {backgroundColor:null,border:0 } });
		plantVote(0);
	});
	$('#tomorrow').children('#no').click(function() {
		$.blockUI({message: '<img src="images/loading.gif" />',css: {backgroundColor:null,border:0 } });
		plantVote(0);
	});
	$('#commentbtn').click(function() {
		var content = $("#commentcontent").val();
		if(content.trim()==''){
			$.blockUI({message: '说点什么吧',css: {backgroundColor:'#000',lineHeight:'40px',color:'#fff',opacity:0.8,height:'40px',border:0 } });
			setTimeout(function(){$.unblockUI();},2000);
		}else{
			$.blockUI({message: '<img src="images/loading.gif" />',css: {backgroundColor:null,border:0 } });
			insertcomment();
		}
	})
});
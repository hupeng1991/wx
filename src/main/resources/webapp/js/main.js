//打开聊天窗口
function openchat(){
  	 $.ajax({
  	        url: "/tochat",
  	        type: "GET"
  	    }).done(function(data) {
  	        layer.open({
  	            type: 1,
  	            skin: "layui-layer-rim",
  	            area: ["725px", "630px"],
  	            closeBtn: 0,
  	            title: false,
  	            shade: 0,
  	            move: "#title",
  	            scrollbar: false,
  	            moveType: 1,
  	            content: data,
  	            success: function() {
  	            
  	            }
  	        });
  	    });
  }       

function init_frame(){
	  var hmain = document.getElementById("contenFrame");
		var bheight = document.documentElement.clientHeight;
		hmain .style.width = '100%';
		hmain .style.height = (bheight  - 60) + 'px';
 }
 
var old_element;
function toindex(url,element){
	  var pointer="<div class=\"pointer\"><div class=\"arrow\"></div><div class=\"arrow_border\"></div></div>";
	  $("#contenFrame").attr("src",url);
	  if(!old_element){
		 old_element=$(".active").get(0);
	  }
	  if(old_element&&old_element.parentNode.tagName=="LI"&&!$(old_element.parentNode.parentNode).hasClass("submenu")){
		  $(old_element.parentNode).removeClass("active");
		  $(".pointer").remove();
	  }else if(old_element&&old_element.tagName=="LI"){
		  $(old_element).removeClass("active");
		  $(".pointer").remove();
	  }else if(old_element&&old_element.parentNode.tagName=="LI"){
		  $(old_element).removeClass("active");
		  $(old_element.parentNode.parentNode).removeClass("active");
		  $(".pointer").remove();
	  }
	 
	  if(element.parentNode.tagName=="LI"&&!$(element.parentNode.parentNode).hasClass("submenu")){
		  $(element.parentNode).attr("class","active");
		  $(element).before(pointer);
	  }else if(element.parentNode.tagName=="LI"){
		  $(element).addClass("active");
		  if(element.parentNode.parentNode.tagName=="UL"){
		  	$(element.parentNode.parentNode).addClass("active");
		  	$(element.parentNode.parentNode).before(pointer);
		  }
	  }
	  old_element=element;
  }
  
 //提示信息 
  function commonTip(msg){
	  layer.msg(msg);
  }

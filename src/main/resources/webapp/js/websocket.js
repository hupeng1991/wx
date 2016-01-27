var username=$("#currentuserid").val();
var toUser="";
var isQj=true;
var websocket;
var person_num=0;
function toUserMsg(toU){
	if((!isQj && toUser == toU) || toU == username){
		$("#title").html(username + "&nbsp;&nbsp;(已连接)   【现在全局对话】");
		isQj = true;
		toUser = "";
	}else{
		$("#title").html(username + "&nbsp;&nbsp;(已连接)   【现在单独与"+toU+"对话】");
		isQj = false;
		toUser = toU;
	}
}
initWebSocket();
//初始话WebSocket
function initWebSocket() {
	if (window.WebSocket) {
		$("#title").html(username+ '&nbsp;&nbsp;连接中...');
		var hostname=window.location.hostname;
		websocket = new WebSocket(encodeURI('ws://'+hostname+':8897'));
		
		websocket.onopen = function() {
			//连接成功
			$("#title").html(username+ '&nbsp;&nbsp;(已连接)   【现在全局对话】');
			websocket.send('link'+username);
			$("#messagelist").append(getCookie(username));
		}
		websocket.onerror = function() {
			//连接失败
			$("#title").html(username + '&nbsp;&nbsp;(连接发生错误)');
		}
		websocket.onclose = function() {
			//连接断开
			$("#title").html(username + '&nbsp;&nbsp;(已经断开连接)');
		}
		//消息接收
		websocket.onmessage = function(message) {
			var message =$.parseJSON(message.data);
			//接收用户发送的消息
			if (message.type == 'message') {
				setMessage(message.content,message.from,null,username==message.from?true:false);
			} else if (message.type == 'get_online_user') {
				//获取在线用户列表
				for(var i=0;i<message.list.length;i++){
					setUser(message.list[i],null);
					person_num++;
				}
				
				$("#person_num").html(person_num);
			} else if (message.type == 'user_join') {
				//用户上线
				var user = message.user;
				person_num++;
				$("#person_num").html(person_num);
				setUser(user,null);
			} else if (message.type == 'user_leave') {
				//用户下线
				$("#"+message.user).remove();	
			}else if(message.type=='user_joined'){
				layer.msg("用户已经在线！");
				layer.closeAll('page');
			}
		}
	}else{
		layer.msg("浏览器不支持即时通讯！");
		layer.closeAll('page');
	}
}
//追加用户
function setUser(name,img){
	if(!img){
		img="img14.png";
	}
	var userhtml="<li id=\""+name+"\"><a href=\"#\" onclick=\"toUserMsg('"+name+"')\"><img width=\"30\" height=\"30\" src=\"/images/"+img+"\">"+name+"</a></li>";
	$("#userlist").append(userhtml);
}
//追加消息
function setMessage(message,user,img,iscurrentuser){
	if(!img){
		img="img14.png";
	}
	var messagehtml="<li class='"+(iscurrentuser?"current-user":"")+"'><img width=\"30\" height=\"30\" src=\"/images/"+img+"\">"
                    +"<div class=\"bubble\">"
                    +"<a class=\"user-name\" href=\"#\">"+user+"&nbsp;&nbsp;"+new Date().format("hh:mm:ss")+"</a>"
                    + "<p class=\"message\">"+message+"</p></div></li>";
	$("#messagelist").append(messagehtml);
	setCookie(username,getCookie(username)+messagehtml);
}

Date.prototype.format = function(format) {  
    var o = {  
        "M+" : this.getMonth() + 1, // month  
        "d+" : this.getDate(), // day  
        "h+" : this.getHours(), // hour  
        "m+" : this.getMinutes(), // minute  
        "s+" : this.getSeconds(), // second  
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S" : this.getMilliseconds()  
        // millisecond  
    }; 
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4  
                        - RegExp.$1.length));  
    }  
    for (var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1  
                            ? o[k]  
                            : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
};
//发送消息
function send() {
	var content = $("#sendmessage").val();
	if($.trim(content)==""){
		layer.msg("请输入发送内容！");
		return false;
	}
	if(toUser != ""){content = "s886"+toUser+"e886" + content;}
	var message = {"from":username,"content":content,"type":'message'};
	if (websocket != null) {
		//setMessage(content,username,null,true);
		websocket.send(JSON.stringify( message ));
		$("#sendmessage").val("");
	} else {
		layer.msg('您已经掉线，无法发送消息!');
	}
}
 function closelayer(){
	 if (websocket != null) {
		 websocket.send("leave"+username);
	 }
	 layer.closeAll('page');
 }
//写cookies 

 function setCookie(name,value) 
 { 
     var Days = 1; 
     var exp = new Date(); 
     exp.setTime(exp.getTime() + Days*24*60*60*1000); 
     document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
 } 

 //读取cookies 
 function getCookie(name) 
 { 
     var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
  
     if(arr=document.cookie.match(reg))
  
         return unescape(arr[2]); 
     else 
         return ""; 
 } 
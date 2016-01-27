package com.hp.wx.websocket;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.java_websocket.WebSocket;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.alibaba.fastjson.JSONObject;
import com.hp.wx.until.Tools;


/**
 * 即时通讯
 * @author phu
 * 2015-12-25
 */
public class ChatServer extends WebSocketServer{

	public ChatServer(int port) throws UnknownHostException {
		super(new InetSocketAddress(port));
	}

	public ChatServer(InetSocketAddress address) {
		super(address);
	}

	/**
	 * 触发连接事件
	 */
	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake ) {
		//this.sendToAll( "new connection: " + handshake.getResourceDescriptor() );
		//System.out.println("===" + conn.getRemoteSocketAddress().getAddress().getHostAddress());
	}

	/**
	 * 触发关闭事件
	 */
	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
		userLeave(conn);
	}

	/**
	 * 客户端发送消息到服务器时触发事件
	 */
	@Override
	public void onMessage(WebSocket conn, String message){
		message = message.toString();
		if(null != message && message.indexOf("link")==0){
			String user=message.replaceFirst("link", "");
			//判断用户是否已经在线
			if(ChatServerPool.getWebSocketByUser(user)==null){
				 this.userjoin(user,conn);
			}else{
				JSONObject result = new JSONObject();
				result.put("type", "user_joined");
				result.put("user", user);
				conn.send(result.toString());
			}
		}else if(null != message && message.indexOf("leave")==0){
			this.userLeave(conn);
		}else if(null != message && message.contains("s886")){
			String toUser = message.substring(message.indexOf("s886")+4, message.indexOf("e886"));
			if(!Tools.isEmpty(toUser)){
				message = message.substring(0, message.indexOf("s886")) +"[私信]  "+ message.substring(message.indexOf("e886")+4, message.length());
				ChatServerPool.sendMessageToUser(ChatServerPool.getWebSocketByUser(toUser),message);//向所某用户发送消息
				ChatServerPool.sendMessageToUser(conn, message);//同时向本人发送消息
			}else{
				ChatServerPool.sendMessage(message.toString());//向所有在线用户发送消息
			}
		}else{
			ChatServerPool.sendMessage(message.toString());//向所有在线用户发送消息
		}
	}

	public void onFragment( WebSocket conn, Framedata fragment ) {
	}

	/**
	 * 触发异常事件
	 */
	@Override
	public void onError( WebSocket conn, Exception ex ) {
		ex.printStackTrace();
		if( conn != null ) {
			//some errors like port binding failed may not be assignable to a specific websocket
		}
	}

	
	/**
	 * 用户加入处理
	 * @param user
	 */
	public void userjoin(String user, WebSocket conn){
		JSONObject result = new JSONObject();
		result.put("type", "user_join");
		result.put("user", user);
		ChatServerPool.sendMessage(result.toString());				//把当前用户加入到所有在线用户列表中
		String joinMsg = "{\"from\":\"[系统]\",\"content\":\""+user+"上线了\",\"timestamp\":"+new Date().getTime()+",\"type\":\"message\"}";
		ChatServerPool.sendMessage(joinMsg);						//向所有在线用户推送当前用户上线的消息
		result = new JSONObject();
		result.put("type", "get_online_user");
		ChatServerPool.addUser(user,conn);							//向连接池添加当前的连接对象
		result.put("list", ChatServerPool.getOnlineUser());
		ChatServerPool.sendMessageToUser(conn, result.toString());	//向当前连接发送当前在线用户的列表
	}
	
	/**
	 * 用户下线处理
	 * @param user
	 */
	public void userLeave(WebSocket conn){
		String user = ChatServerPool.getUserByKey(conn);
		boolean b = ChatServerPool.removeUser(conn);				//在连接池中移除连接
		if(b){
			JSONObject result = new JSONObject();
			result.put("type", "user_leave");
			result.put("user", user);
			ChatServerPool.sendMessage(result.toString());			//把当前用户从所有在线用户列表中删除
			String joinMsg = "{\"from\":\"[系统]\",\"content\":\""+user+"下线了\",\"timestamp\":"+new Date().getTime()+",\"type\":\"message\"}";
			ChatServerPool.sendMessage(joinMsg);					//向在线用户发送当前用户退出的消息
		}
	}
}


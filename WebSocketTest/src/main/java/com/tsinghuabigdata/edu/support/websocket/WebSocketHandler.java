package com.tsinghuabigdata.edu.support.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @Description: WebSocket鍩烘湰澶勭悊绫� * @author malin@tsinghuabigdata.com
 * @date 2014-8-21涓嬪崍1:18:05 version 1.0
 */
@ServerEndpoint(value = "/websocket/{clientId}")
public class WebSocketHandler {
	private String userId;
	private Session sessionUser;

	public String getUserId() {
		return userId;
	}

	public String getSessionId() {
		return sessionUser.getId();
	}

	/**
	 * 鏀跺埌娑堟伅
	 * 
	 * @author malin@tsinghuabigdata.com
	 * @param message
	 *            娑堟伅
	 * @param session
	 *            浼氳瘽
	 * @date 2014-8-21涓嬪崍1:18:05
	 */
	@OnMessage
	public void onMessage(String message) throws IOException,
			InterruptedException {
		System.out.println(sessionUser.getId());
		// 鏀跺埌瀹㈡埛绔殑娑堟伅
		System.out.println("Received: " + message);

		// 鍙戦�娑堟伅缁欏鎴风
		sessionUser.getBasicRemote().sendText(
				"this is reply message from " + sessionUser.getId());
		WebSocketMessagePoolUtil.sendMessage("");
	}

	/**
	 * 瀹㈡埛绔繛鎺�	 * 
	 * @author malin@tsinghuabigdata.com
	 * @param session
	 *            浼氳瘽
	 * @param clientId
	 *            瀹㈡埛绔爣蹇�	 * @date 2014-8-21涓嬪崍1:18:05
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("clientId") String clientId) {
		userId = clientId;
		sessionUser = session;
		WebSocketMessagePoolUtil.addMessageWSH(this);
		System.out.println("Client connected:" + sessionUser.getId());
		System.out.println("Client connented:" + clientId);
	}

	/**
	 * 瀹㈡埛绔柇寮�繛鎺�	 * 
	 * @author malin@tsinghuabigdata.com
	 * @date 2014-8-21涓嬪崍1:18:05
	 */
	@OnClose
	public void onClose() {
		// System.out.println("Connection closed");
		WebSocketMessagePoolUtil.removeMessageWSH(this);
	}

	/**
	 * 鍚戝鎴风鍙戦�娑堟伅
	 * 
	 * @author malin@tsinghuabigdata.com
	 * @param message
	 *            娑堟伅
	 * @date 2014-8-21涓嬪崍1:18:05
	 */
	public void sendMessage(String message) throws IOException,
			InterruptedException {
		sessionUser.getBasicRemote().sendText(message);
	}

}

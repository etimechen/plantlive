package com.etimechen.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class MyWebSocketHandle implements WebSocketHandler {

	private static final Logger logger = Logger.getLogger(MyWebSocketHandle.class);
	private static final Map<String, WebSocketSession> userSocketSessionMap;
	static {
		userSocketSessionMap = new HashMap<String, WebSocketSession>();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus arg1) throws Exception {
		// TODO Auto-generated method stub
		Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 移除Socket会话
		while (it.hasNext()) {
			Entry<String, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				userSocketSessionMap.remove(entry.getKey());
				logger.debug("Socket会话已经移除:用户ID" + entry.getKey());
				break;
			}
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("connect to the websocket success......");
		String id = session.getId();
		logger.debug("uid为:" + id.toString());
		if (userSocketSessionMap.get(id) == null) {
			userSocketSessionMap.put(id, session);
		}
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.put("jsmp".getBytes());
		bb.putShort((short) 320);
		bb.putShort((short) 240);
		BinaryMessage message = new BinaryMessage(bb.array());
		session.sendMessage(message);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable arg1) throws Exception {
		// TODO Auto-generated method stub
		if (session.isOpen()) {
			session.close();
		}
		Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 移除Socket会话
		while (it.hasNext()) {
			Entry<String, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				userSocketSessionMap.remove(entry.getKey());
				logger.debug("Socket会话已经移除:用户ID" + entry.getKey());
				break;
			}
		}
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 *
	 * @param message
	 */
	public void sendMessageToUsers(BinaryMessage message) {
		Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		synchronized (this) {
			while (it.hasNext()) {
				final Entry<String, WebSocketSession> entry = it.next();
				if (entry.getValue().isOpen()) {
					// entry.getValue().sendMessage(message);
					try {
						if (entry.getValue().isOpen()) {
							entry.getValue().sendMessage(message);
						}
					} catch (IOException e) {
						logger.debug("客户端关闭");
						e.printStackTrace();
					}
				}
			}
		}
	}

}

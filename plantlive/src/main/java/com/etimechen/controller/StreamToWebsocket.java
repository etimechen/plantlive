package com.etimechen.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.BinaryMessage;

import com.etimechen.websocket.MyWebSocketHandle;

@Controller
@RequestMapping("/")
public class StreamToWebsocket extends BaseController {

	@Resource
	private MyWebSocketHandle myWebSocketHandle;
	//private static final Logger logger = Logger.getLogger(StreamToWebsocket.class);

	@RequestMapping("/streamconvert")
	@ResponseBody
	public void streamConvert(HttpServletRequest request, HttpServletResponse response) throws IOException {

		BinaryMessage bm;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		is = request.getInputStream();
		byte[] byteChunk = new byte[512]; // Or whatever size you want to
											// read in at a time.
		int n;
		int i = 1;
		while (i == 1) {
			n = is.read(byteChunk);
			if(n > 0){
				baos.write(byteChunk, 0, n);
				//logger.debug(n);
				if(n < 512){
					bm = new BinaryMessage(baos.toByteArray());
					myWebSocketHandle.sendMessageToUsers(bm);
					baos.reset();
				}
			}
			
		}

//		while (i == 1) {
//			is.read(byteChunk);
//			bm = new BinaryMessage(byteChunk);
//			myWebSocketHandle.sendMessageToUsers(bm);
//		}

	}
}

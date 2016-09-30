package com.etimechen.component.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

public class GetCityByIp {

	private static final String HX = "火星";
	
	//private static final Random random = new Random();

	/**
	 * 
	 * @param IP
	 * @return
	 */
	public static String GetAddressByIp(String IP) {
		String resout = "";
		try {
			String str = getJsonContent("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip=" + IP);
			JSONObject obj = JSONObject.fromObject(str.substring(str.indexOf("{"), str.indexOf("}")+1));
//			JSONObject obj2 = (JSONObject) obj.get("data");
			String code = obj.get("ret").toString();
			if (code.equals("1")) {
				resout = obj.get("city").toString();
				if (resout.isEmpty()) {
					resout = HX;
				}
			} else {
				resout = HX;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resout = HX;
		}
		return resout;

	}

	public static String getJsonContent(String urlStr) {
		try {// 获取HttpURLConnection连接对象
			URL url = new URL(urlStr);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			// 设置连接属性
			httpConn.setConnectTimeout(3000);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod("GET");
			// 获取相应码
			int respCode = httpConn.getResponseCode();
			if (respCode == 200) {
				return ConvertStream2Json(httpConn.getInputStream());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String ConvertStream2Json(InputStream inputStream) {
		String jsonStr = "";
		// ByteArrayOutputStream相当于内存输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// 将输入流转移到内存输出流中
		try {
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// 将内存流转换为字符串
			jsonStr = new String(out.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}
}
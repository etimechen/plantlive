package com.etimechen.component.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.StringUtils;
import com.etimechen.component.constant.ConfigConstant;

import net.sf.json.JSONObject;

public class CommonUtil {

	/**
	 * 获取当前投票日期 将当前日期时间和执行日期时间比较, 早于执行日期为当天, 晚于执行日期天数+1
	 * 
	 * @param excuteTime
	 * @return String
	 */
	public static Date getCurrentVoteDate(String excuteTime) {
		Date nowDate, excuteDate;
		SimpleDateFormat sdfDate, sdfDateTime;
		Calendar calendar;

		nowDate = new Date();
		sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		sdfDateTime = new SimpleDateFormat("yyyy-MM-ddHH:mm");

		try {
			excuteDate = sdfDateTime.parse(sdfDate.format(nowDate).concat(excuteTime));
			if (excuteDate.before(nowDate)) {
				calendar = Calendar.getInstance();
				calendar.setTime(nowDate);
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				return calendar.getTime();
			} else {
				return nowDate;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nowDate;
	}
	
	/**
	 * 获取时间差
	 * 
	 * @param excuteTime
	 * @return long
	 */
	public static long getTimeDifferent(String excuteTime) {
		Date nowDate, excuteDate;
		SimpleDateFormat sdfDate, sdfDateTime;
		Calendar excuteCalendar;

		nowDate = new Date();
		sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		sdfDateTime = new SimpleDateFormat("yyyy-MM-ddHH:mm");

		try {
			excuteDate = sdfDateTime.parse(sdfDate.format(nowDate).concat(excuteTime));
			if (excuteDate.before(nowDate)) {
				excuteCalendar = Calendar.getInstance();
				excuteCalendar.setTime(excuteDate);
				excuteCalendar.add(Calendar.DAY_OF_YEAR, 1);
				return excuteCalendar.getTimeInMillis() - nowDate.getTime();
			} else {
				return excuteDate.getTime() - nowDate.getTime();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * response返回JSON
	 * 
	 * @param response
	 * @param context
	 */
	public static void responseOutWithJSON(HttpServletResponse response, Object context) {
		// 将实体转换为JSON对象
		JSONObject result = JSONObject.fromObject(context);

		response.setCharacterEncoding(ConfigConstant.CHARSET);
		response.setContentType("application/json; charset=" + ConfigConstant.CHARSET);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 获取当前网络ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (!StringUtils.isEmpty(ipAddress) && ipAddress.length() > 15) {
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	
	/**
	 * 
	 * @param IP
	 * @return
	 */
	public static String getAddressByIp(String IP) {
		String resout = "";
		try {
			String str = getJsonContent("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip=" + IP);
			JSONObject obj = JSONObject.fromObject(str.substring(str.indexOf("{"), str.indexOf("}")+1));
//			JSONObject obj2 = (JSONObject) obj.get("data");
			String code = obj.get("ret").toString();
			if (code.equals("1")) {
				resout = obj.get("city").toString();
				if (resout.isEmpty()) {
					resout = ConfigConstant.HX;
				}
			} else {
				resout = ConfigConstant.HX;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resout = ConfigConstant.HX;
		}
		return resout;

	}

	/**
	 * 
	 * @param IP
	 * @return
	 */
	public static String getWeather() {
		String resout = "";
		try {
			String str = getJsonContent("http://api.k780.com:88/?app=weather.today&weaid=101280601&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json");
			JSONObject obj = JSONObject.fromObject(str);
			JSONObject obj2 = (JSONObject) obj.get("result");
			String code = obj.get("success").toString();
			if (code.equals("1")) {
				resout = obj2.get("weather").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
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
				return convertStream2Json(httpConn.getInputStream());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String convertStream2Json(InputStream inputStream) {
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

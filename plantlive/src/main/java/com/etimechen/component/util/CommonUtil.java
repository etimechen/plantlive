package com.etimechen.component.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
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
}

package com.etimechen.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etimechen.component.Configurator;
import com.etimechen.component.Irrigation;
import com.etimechen.component.constant.MessageConstant;
import com.etimechen.component.util.CommonUtil;

@Controller
@RequestMapping("/irrigation")
public class IrrigationController extends BaseController {
	
	@ResponseBody
	@RequestMapping(value="/doirrigate", method=RequestMethod.POST)
	public void doIrrigate(@RequestParam Map<String, Object> paramMap){
		String checkCode = paramMap.get("checkcode").toString();
		if("19811111".equals(checkCode)){
			Integer startorstop = Integer.valueOf(paramMap.get("startorstop").toString());
			if(startorstop == 1){
				Irrigation.start();
			}else if(startorstop == 0){
				Irrigation.stop();
			}else if(startorstop == 2){
				Irrigation.dooneperiod();
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/getconfigtime", method=RequestMethod.GET)
	public Object doIrrigate(){
		String excuteTime = Configurator.EXCUTE_TIME;
		if (StringUtils.isEmpty(excuteTime)) {
			// 不设置值默认为下午5点
			excuteTime = "17:00";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(MessageConstant.SUCCESS, true);
		map.put("excutetime", excuteTime);
		map.put("timedifferent", CommonUtil.getTimeDifferent(excuteTime));
		return map;
	}
}

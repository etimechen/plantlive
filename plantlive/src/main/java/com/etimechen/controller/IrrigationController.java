package com.etimechen.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etimechen.component.Irrigation;

@Controller
@RequestMapping("/irrigation")
public class IrrigationController extends BaseController {
	
	@ResponseBody
	@RequestMapping(value="/doirrigate", method=RequestMethod.POST)
	public void doIrrigate(@RequestParam Map<String, Object> paramMap){
		String checkCode = paramMap.get("checkcode").toString();
		if("liang2439".equals(checkCode)){
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

}

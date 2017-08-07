package org.stockws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test.do")
public class TestController {

	@RequestMapping("/haha.do")
	public String haha(@RequestParam("name") String name){
		return "haha,"+ name;
	}
}

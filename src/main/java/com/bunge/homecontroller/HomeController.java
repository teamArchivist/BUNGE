package com.bunge.homecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/error/403")
	public String error_403() {
		return "error/403";
	}
}
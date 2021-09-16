package com.zkteco.bionest.autoconfigure.security;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContextUtils;

@RequestMapping("/test")
@RestController
public class TestController {

	@Autowired
	MessageSource messageSource;

	@GetMapping("/language")
	public String test(HttpServletRequest httpServletRequest){
		return messageSource.getMessage("sss",null, RequestContextUtils.getLocale(httpServletRequest));
	}

}

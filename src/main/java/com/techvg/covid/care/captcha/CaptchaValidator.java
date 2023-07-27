package com.techvg.covid.care.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class CaptchaValidator {
	
	@Value("${captcha.google.secret.key}")
	private String googleSecretKey;
	
	@Value("${captcha.google.secret.url}")
	private String recaptcha_validator_url;
	
	@Autowired
	private RestTemplate restTemplate;
 
	public boolean isValidCaptcha(String captcha) {
		
		
		String params="?secret="+this.googleSecretKey+"&response="+captcha;
		String completeUrl=this.recaptcha_validator_url+params;
		CaptchaResponse resp= restTemplate.postForObject(completeUrl, null, CaptchaResponse.class);
		return resp.isSuccess();
	}
}

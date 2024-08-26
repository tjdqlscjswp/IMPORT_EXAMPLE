package com.kosta.KOSTA_3_final.service.subscribe;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@Service
public class ImportPay {
	public String getToken() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); // 서버로 요청할 Header
		Map<String, Object> map = new HashMap<>();
		map.put("imp_key", "5945712414472844");
		map.put("imp_secret", "92c551f52382ba2dfa3e77e87c83dd75250f4906f917d271ba391197849fb8a4a63d6be5d72783fd");
		Gson var = new Gson();
		String json = var.toJson(map);
		HttpEntity<String> entity = new HttpEntity<>(json, headers); // 서버로 요청할 Body
		return restTemplate.postForObject("https://api.iamport.kr/users/getToken", entity, String.class);
	}
}

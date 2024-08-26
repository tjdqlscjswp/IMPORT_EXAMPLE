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
		map.put("imp_key", "발급받은 키 정보");
		map.put("imp_secret", "발급받은 키 정보");
		Gson var = new Gson();
		String json = var.toJson(map);
		HttpEntity<String> entity = new HttpEntity<>(json, headers); // 서버로 요청할 Body
		return restTemplate.postForObject("https://api.iamport.kr/users/getToken", entity, String.class);
	}
}

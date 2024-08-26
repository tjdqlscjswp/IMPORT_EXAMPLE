package com.kosta.KOSTA_3_final.service.subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kosta.KOSTA_3_final.model.subscribe.GetTokenVO;

import lombok.Setter;

@JsonIgnoreProperties
@Service
public class GetPayementStatus {
	@Setter(onMethod_ = @Autowired)
	private ImportPay pay;

	public String paymentStatus(long merchantUid) throws JsonMappingException, JsonProcessingException {
		String token = pay.getToken();
		String merchant_uid = Long.toString(merchantUid);

		// getToken에서 얻은 response에서 필요한 값만 추출
		Gson str = new Gson();
		token = token.substring(token.indexOf("response") + 10);
		token = token.substring(0, token.length() - 1);
		GetTokenVO vo = str.fromJson(token, GetTokenVO.class);
		String access_token = vo.getAccess_token();

		String url = "https://api.iamport.kr/subscribe/payments/schedule/" + merchant_uid;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(access_token);

		HttpEntity<JsonObject> entity = new HttpEntity<>(headers);

		ResponseEntity<String> respEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode;
		jsonNode = mapper.readTree(respEntity.getBody());
		String res = str.toJson(jsonNode.findValue("payment_status"));

		if (res.equals("{\"_value\":\"paid\"}"))
			res = "paid";

		return res;

	}

}

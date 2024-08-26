package com.kosta.KOSTA_3_final.model.subscribe;

import lombok.Data;
@Data
public class GetTokenVO {
	private String access_token;
	private long now;
	private long expired_at;
}

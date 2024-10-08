package com.kosta.KOSTA_3_final.model.page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.Getter;

@Getter//lombok이 자동생성....여기서 setter는 직접구현함
public class PageVO {
	private static final int DEFAULT_SIZE = 10;
	private static final int DEFAULT_MAX_SIZE = 50;
	
	int page;
	int size;
	String type; //title or content or writer
	String keyword;
	
	
	public PageVO() {
		this.page = 1;
		this.size = DEFAULT_SIZE;
	}
	
	
	
	public void setPage(int page) {
		this.page = page<0?1:page;
	}
	public void setSize(int size) {
		this.size = size < DEFAULT_SIZE||size>DEFAULT_MAX_SIZE?DEFAULT_SIZE:size;
	}
	public Pageable makePaging(int direction, String...props) {
		Sort.Direction dir = direction==0?Direction.DESC:Direction.ASC;
		return PageRequest.of(this.page-1, this.size, dir, props );
	}



	public void setType(String type) {
		this.type = type;
	}



	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}

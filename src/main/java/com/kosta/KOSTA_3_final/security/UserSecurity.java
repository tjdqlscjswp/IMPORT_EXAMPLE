package com.kosta.KOSTA_3_final.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;


import com.kosta.KOSTA_3_final.model.user.Member;


import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class UserSecurity extends User{
	private static final long serialVersionUID = 1L;
	private static final String ROLE_PREFIX="ROLE_";

    private Member member;   
	public UserSecurity(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	public UserSecurity(Member member) {	
		super(member.getEmail(),member.getPassword(),makeRole(member));
		this.member = member;
	}
	//Role을 여러개 가질수 있도록 되어있음 
	private static List<GrantedAuthority> makeRole(com.kosta.KOSTA_3_final.model.user.Member member) {
		List<GrantedAuthority> roleList = new ArrayList<>();
		roleList.add(new SimpleGrantedAuthority(ROLE_PREFIX + member.getAuth()));

		return roleList;
	}

}
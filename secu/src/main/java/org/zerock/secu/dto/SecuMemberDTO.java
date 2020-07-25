package org.zerock.secu.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;

@Data
public class SecuMemberDTO extends User { //실제로 화면에서 로그인 체크할 때 사용할 객체.

    /* 사용자 이름 등도 시큐리티에 보관하면 화면 상에서 유리함 => 어떻게 가져올까?
     1. 생성자 하나 더 추가 <<=
     2. SecuMember 를 패러미터로 받는 생성자 더 추가 => SecuMember 의 범위가 넓어지는 단점.
     */

    private String mname;
    private String mid;

    public SecuMemberDTO(String mid, String password,String mname, Collection<? extends GrantedAuthority> authorities) {
        super(mid, password, authorities);
        this.mname = mname;
        this.mid = mid;
    }
    
}
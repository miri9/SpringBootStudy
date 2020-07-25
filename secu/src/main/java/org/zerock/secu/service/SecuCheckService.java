package org.zerock.secu.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zerock.secu.domain.SecuMember;
import org.zerock.secu.dto.SecuMemberDTO;
import org.zerock.secu.repository.SecuMemberRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SecuCheckService implements UserDetailsService {
    // @서비스로 빼지 않고 후에 컨피그 파일에서 @빈으로 뺄 예정

    @Autowired
    private SecuMemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("load user.." + username);

        Optional<SecuMember> result = repository.findById(username);

        if(result.isPresent()){
            SecuMember member = result.get();
            log.info(member);

            /* 이슈 : role 을 SimpleGrantedAuthority 로 변환해서 넣어주어야 함.
             SecuMemberDTO 의 생성자 param Collection<? extends GrantedAuthority> authorities 때문.
            */
            List<SimpleGrantedAuthority> list = member.getRoleSets().stream()
            .map(role -> new SimpleGrantedAuthority(role.getRoleName()) )
            .collect(Collectors.toList());

            SecuMemberDTO dto = new SecuMemberDTO(member.getMid(), member.getMpw(), member.getMname(), list);
            return dto;
        }
        return null;
    }
    
}
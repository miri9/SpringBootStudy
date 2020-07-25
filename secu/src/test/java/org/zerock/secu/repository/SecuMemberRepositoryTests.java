package org.zerock.secu.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.secu.domain.SecuMember;
import org.zerock.secu.domain.SecuMemberRole;

@SpringBootTest
public class SecuMemberRepositoryTests {
    
    @Autowired
    private SecuMemberRepository memberRepository;
    @Autowired
    private PasswordEncoder pwencoder;

    @Commit
    @Transactional // 테스트 코드는 트랜잭션 걸려있을 경우 기본적으로 커밋하지 않음.
    @Test
    public void insertDummies(){

        IntStream.rangeClosed(1, 100).forEach(i->{

            String str = "M"+i;
            String enStr = pwencoder.encode(str);

            SecuMember member = SecuMember.builder().mid(str).mpw(enStr).mname("MEMBER"+i).build();


            SecuMemberRole role = SecuMemberRole.builder().roleName("ROLE_MEMBER").build();
            member.addRole(role);

            if(i>= 80){
                member.addRole(SecuMemberRole.builder().roleName("ROLE_ADMIN").build());
            }

            memberRepository.save(member);

        });

        /*
        java.lang.IllegalStateException: org.hibernate.TransientObjectException:
        object references an unsaved transient instance - save the transient instance before flushing:
        org.zerock.secu.domain.SecuMemberRole
        : => cascade.all 넣어서 해결
         */

    }
}
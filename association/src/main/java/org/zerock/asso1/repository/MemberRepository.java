package org.zerock.asso1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.asso1.domain.Member;

public interface MemberRepository extends JpaRepository<Member,String>{
    
}
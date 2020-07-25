package org.zerock.secu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.secu.domain.SecuMember;

public interface SecuMemberRepository extends JpaRepository<SecuMember,String> {

    
}
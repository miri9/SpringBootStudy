package org.zerock.secu.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecuMember {
    // 원래대로라면 양방향 걸겠지만, 이 예제는 단방향으로 갈 것임.
    
    @Id
    private String mid; 

    private String mpw;

    private String mname;

    
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "secuMember") // 별도 테이블 생기지 않도록.
    private Set<SecuMemberRole> roleSets;

    /*
    시큐리티 : load by name => pw 안맞으면 bad credential,  pw 맞되 권한 안맞으면 denied.
    한번에 회원과 그 권한을 로딩해오기 위해 이거 로딩 선택.
    */

    // setter 대신하는 메서드
    public void addRole(SecuMemberRole role){
        if(roleSets == null){
            roleSets = new HashSet<>(); //초기화
        }
        roleSets.add(role);
    }
}
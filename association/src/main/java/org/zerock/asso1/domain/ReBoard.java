package org.zerock.asso1.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class ReBoard {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "writer")
    private Member writer;

    /* mappedBy 속성 : 외래키로 기존의 테이블을 참조하겠다는 뜻. =>
     "AttachFile 엔티티의 board 필드를 외래키 관계로 묶어줘."
     중간에 별개 테이블 따로 생기는 현상 방지.
     */
    /*
    cascade 속성 : board 가 테이블에서 삭제되면, board 의 키를 가진 = 외래키 관계인 테이블도 없애줘.
    */

    // mappedBy 가 기술되는 건 OneToMany 쪽이다.
    @OneToMany(mappedBy = "board",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AttachFile> files;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, 
    cascade = CascadeType.ALL)
    private Set<Reply> replies;
}
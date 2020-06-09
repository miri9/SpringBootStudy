package org.zerock.sboard.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString // 얘도 앞으로는 쓰지 않는게 좋다.
public class SingleBoard extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long sno;

    private String title, content, writer;

    // 사실 이걸 쓰면 완전한 읽기 전용은 아니긴 하다..
    public void changeTitle(String title){
        this.title = title;
    }
}
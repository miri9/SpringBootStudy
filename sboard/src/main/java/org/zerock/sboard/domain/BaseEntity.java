package org.zerock.sboard.domain;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;


@MappedSuperclass // 상속시켜 사용.
@EntityListeners(AuditingEntityListener.class) // audit : 감시하다. for 시간 처리 설정.
@Getter
abstract class BaseEntity {
    
    @CreatedDate
    protected LocalDateTime regdate;

    @LastModifiedDate
    protected LocalDateTime moddate;
}
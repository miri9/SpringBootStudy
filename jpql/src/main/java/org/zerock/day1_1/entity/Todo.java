package org.zerock.day1_1.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_todo")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Todo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//identitiy oracle sequence, mysql ai
    private Long tno;

    @Column(length = 500, nullable = false)
    private String title;

    private String writer;
    
}

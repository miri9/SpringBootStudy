
package org.zerock.ex2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SampleDTO {

    private Long sno;
    private String title;
    private String writer;
    private LocalDateTime regdate;
}
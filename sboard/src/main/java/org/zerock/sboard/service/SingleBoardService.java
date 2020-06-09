package org.zerock.sboard.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.sboard.domain.SingleBoard;
import org.zerock.sboard.dto.SingleBoardDTO;

@Transactional
public interface SingleBoardService {
    
    //  dto => entity
    Long register(SingleBoardDTO dto);

    // entity => dto
    default SingleBoard bindToEntity (SingleBoardDTO dto){

        SingleBoard entity = SingleBoard.builder()
        .sno(dto.getSno())
        .title(dto.getTitle())
        .content(dto.getContent())
        .writer(dto.getWriter())
        .build();

        return entity;
    }

    // entity => dto
    default SingleBoardDTO bindToDTO (SingleBoard entity){

        SingleBoardDTO dto = SingleBoardDTO.builder()
        .sno(entity.getSno())
        .title(entity.getTitle())
        .content(entity.getContent())
        .writer(entity.getWriter())
        .build();

        return dto;
    }



}
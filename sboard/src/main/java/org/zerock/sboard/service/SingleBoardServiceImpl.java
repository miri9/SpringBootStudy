package org.zerock.sboard.service;

import org.springframework.stereotype.Service;
import org.zerock.sboard.domain.SingleBoard;
import org.zerock.sboard.dto.SingleBoardDTO;
import org.zerock.sboard.repository.SingleBoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SingleBoardServiceImpl implements SingleBoardService {

    /* autowired 대신 @RequiredArgsConstructor
    이 어노테이션은 초기화 되지않은 final 혹은 @NonNull 필드의 생성자 만들어줌.
    의존성 주입의 또다른 방법.
    */
    private final SingleBoardRepository repository;


    // dto => entity
    @Override
    public Long register(final SingleBoardDTO dto) {
        SingleBoard entity = bindToEntity(dto);

        repository.save(entity);

        return entity.getSno();
    }
}
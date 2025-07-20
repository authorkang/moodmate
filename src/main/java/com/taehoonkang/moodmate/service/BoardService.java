package com.taehoonkang.moodmate.service;

import com.taehoonkang.moodmate.dto.BoardDTO;
import com.taehoonkang.moodmate.entity.BoardEntity;
import com.taehoonkang.moodmate.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
// lombok 기능 중 하나로, final 변수에 대한 생성자를 자동으로 생성한다.

public class BoardService {

    private final BoardRepository boardRepository;
    // 생성자 주입(DI)

    public void save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        // BoardDTO -> BoardEntity
        boardRepository.save(boardEntity);
        // repository의 save 메서드 호출 (BoardDTO가 아닌 boardEntity 객체를 넘겨줘야 함)
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity: boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public boolean deleteWithPass(Long id, String boardPass) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            if (boardEntity.getBoardPass().equals(boardPass)) {
                boardRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

}

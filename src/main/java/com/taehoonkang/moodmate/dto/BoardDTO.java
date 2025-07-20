package com.taehoonkang.moodmate.dto;

import com.taehoonkang.moodmate.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor // Default constructor
@AllArgsConstructor // Constructor with all fields as parameters
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardRequest;
    private String boardResponse;
    private Integer writerAge;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    public BoardDTO(Long id, String boardWriter, String boardTitle, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardRequest(boardEntity.getBoardRequest());
        boardDTO.setBoardResponse(boardEntity.getBoardResponse());
        boardDTO.setWriterAge(boardEntity.getWriterAge());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        return boardDTO;
    }
}

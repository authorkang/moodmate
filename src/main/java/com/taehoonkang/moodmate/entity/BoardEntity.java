package com.taehoonkang.moodmate.entity;

import com.taehoonkang.moodmate.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity{
    // Time-related columns are managed separately in BaseEntity.java
    @Id // Specifies the primary key column
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;
    @Column(length = 20, nullable = false)
    private String boardWriter;
    @Column
    private String boardPass;
    @Column
    private String boardTitle;
    @Column(length = 800)
    private String boardRequest;
    @Column(length = 800)
    private String boardResponse;
    @Column
    private Integer writerAge;


    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
        // Converts BoardDTO to BoardEntity (for insert operation)
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardRequest(boardDTO.getBoardRequest());
        boardEntity.setBoardResponse(boardDTO.getBoardResponse());
        boardEntity.setWriterAge(boardDTO.getWriterAge());
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
        // Converts BoardDTO to BoardEntity (for update operation)
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardRequest(boardDTO.getBoardRequest());
        boardEntity.setBoardResponse(boardDTO.getBoardResponse());
        boardEntity.setWriterAge(boardDTO.getWriterAge());
        return boardEntity;
    }

}
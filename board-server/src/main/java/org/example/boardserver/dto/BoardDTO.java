package org.example.boardserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.example.boardserver.entity.BoardEntity;

import java.time.LocalDateTime;

//DTO 데이터 전송할 때 사용하는 객체
@Getter
@Setter
@ToString
@NoArgsConstructor //기본생성자
@AllArgsConstructor // 모든 필드를 가지는 생성자
public class BoardDTO {
    private Long id;
    private String title;
    private String email;
    private String contents;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;
    private int count;


    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setTitle(boardEntity.getTitle());
        boardDTO.setCreateDate(boardEntity.getCreatedTime());
        boardDTO.setUpdateTime(boardEntity.getUpdatedTime());
        boardDTO.setCount(boardEntity.getCount());
        boardDTO.setContents(boardEntity.getContents());
        boardDTO.setEmail(boardEntity.getUserEntity().getEmail());

        return boardDTO;
    }
}

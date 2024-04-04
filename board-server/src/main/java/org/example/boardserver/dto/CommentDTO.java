package org.example.boardserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jdk.jshell.Snippet;
import lombok.*;
import org.example.boardserver.entity.CommentEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String contents;
    private String reComment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;
    private String email;
    private Long boardId;

    public static CommentDTO toCommentsDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setBoardId(commentEntity.getBoardEntity().getId());
        commentDTO.setContents(commentEntity.getContents());
        commentDTO.setEmail(commentEntity.getUserEntity().getEmail());
        commentDTO.setCreateTime(commentEntity.getCreateTime());
        commentDTO.setUpdateTime(commentEntity.getUpdateTime());
        commentDTO.setReComment(commentEntity.getReComment());

        return commentDTO;
    }
}

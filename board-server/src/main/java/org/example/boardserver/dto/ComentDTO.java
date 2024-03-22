package org.example.boardserver.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ComentDTO {
    private Long id;
    private String contents;
    private String reComment;

}

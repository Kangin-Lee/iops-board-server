package org.example.boardserver.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardAndUserDTO {
//    private BoardDTO boardDTO;
//    private UserDTO userDTO;

    private String title;
    private String contents;
    private String writer;
}

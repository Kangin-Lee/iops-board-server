package org.example.boardserver.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="board_item")
public class BoardEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String title;

    @Column(length = 500, nullable = false)
    private String contents;

//    @Column(length = 20, nullable = false)
//    private String writer;

    @Column(columnDefinition = "int default 0")
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="email", referencedColumnName = "email", nullable = false)
    private UserEntity userEntity;

}

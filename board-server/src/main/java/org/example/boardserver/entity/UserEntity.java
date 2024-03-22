package org.example.boardserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="user_info")
public class UserEntity {
    @Id
    private String email;

    @Column
    private String name;

    @Column
    private String tel;

    @Column
    private String password;

    @Column
    private String confirmPassword;
}

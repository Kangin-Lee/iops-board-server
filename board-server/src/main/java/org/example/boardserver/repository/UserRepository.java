package org.example.boardserver.repository;

import org.apache.catalina.User;
import org.example.boardserver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByEmail(String email);

    UserEntity findByEmail(String email);

//    Optional<UserEntity> findByUserEmail(String email);
}

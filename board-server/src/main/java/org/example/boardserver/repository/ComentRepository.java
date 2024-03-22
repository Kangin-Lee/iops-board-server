package org.example.boardserver.repository;

import org.example.boardserver.entity.ComentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentRepository extends JpaRepository<ComentEntity, Long> {
}

package org.example.boardserver.repository;

import org.example.boardserver.entity.CommentEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByBoardEntityId(Long boardId, Sort sort);
}

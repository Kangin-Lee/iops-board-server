package org.example.boardserver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.boardserver.dto.CommentDTO;
import org.example.boardserver.entity.BoardEntity;
import org.example.boardserver.entity.CommentEntity;
import org.example.boardserver.entity.UserEntity;
import org.example.boardserver.repository.BoardRepository;
import org.example.boardserver.repository.CommentRepository;
import org.example.boardserver.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentDTO createdComment(Long id, CommentDTO commentDTO) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Board not found"));
        UserEntity userEntity = userRepository.findByEmail(commentDTO.getEmail());

        if(userEntity == null){
            throw  new IllegalArgumentException("User not found");
        }

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContents(commentDTO.getContents());
        commentEntity.setUserEntity(userEntity);
        commentEntity.setBoardEntity(boardEntity);
        commentEntity.setCreateTime(commentDTO.getCreateTime());
        commentEntity.setUpdateTime(commentDTO.getUpdateTime());

        CommentEntity createComment= commentRepository.save(commentEntity);
        boardEntity.addComment(createComment);

        return commentDTO;

    }

    // 댓글 가져오기--------------------------------------------------
    @Transactional
    public List<CommentDTO> getAllCommentsData(Long boardId, Sort sort) {
        List<CommentEntity> commentList = commentRepository.findAllByBoardEntityId(boardId, sort);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity : commentList){
            commentDTOList.add(CommentDTO.toCommentsDTO(commentEntity));
        } 
        return commentDTOList;
    }
 // 댓글 삭제----------------------------------------------------------------
    public void deleteComment(Long id) {
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("댓글을 찾을 수 없습니다. ID : "+id));

        commentRepository.delete(commentEntity);
    }

    //댓글 수정----------------------------------------------------------------------------------
    public void updateComment(Long id, CommentDTO commentDTO) {
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("댓글을 찾을 수 없습니다. ID : "+id));

            commentEntity.setContents(commentDTO.getContents());
            commentEntity.setUpdateTime(commentDTO.getUpdateTime());
        commentRepository.save(commentEntity);
    }

//    public CommentDTO findById(Long id) {
//        CommentEntity commentEntity = commentRepository.findById(id)
//                .orElseThrow(()->new EntityNotFoundException("게시물을 찾을 수 없습니다 ID :"+ id));
//
//        return CommentDTO.toCommentsDTO(commentEntity);
//    }
}

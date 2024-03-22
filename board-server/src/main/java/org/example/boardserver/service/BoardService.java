package org.example.boardserver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.boardserver.dto.BoardDTO;
import org.example.boardserver.dto.UserDTO;
import org.example.boardserver.entity.BaseEntity;
import org.example.boardserver.entity.BoardEntity;
import org.example.boardserver.entity.UserEntity;
import org.example.boardserver.repository.BoardRepository;
import org.example.boardserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

//    게시판 리스트====================================================
    @Transactional
    public List<BoardDTO> getAllBoardData(Sort sort) {
        List<BoardEntity> boardList = boardRepository.findAll(sort);
        List <BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    //조회수 증가------------------------------------------------------------
    public void increaseCount(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("게시물을 찾을 수 없습니다. ID: "+id));

        //조회수를 증가 시킨다.
        int currentCount = boardEntity.getCount();
        boardEntity.setCount(currentCount+1);

        //변경된 내용을 저장한다.
        boardRepository.save(boardEntity);
    }

    // 게시물 상세 조회
    public BoardDTO findById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다. ID: " + id));

        return BoardDTO.toBoardDTO(boardEntity);
    }

    public void updateContents(Long id, BoardDTO boardDTO) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("게시물을 찾을 수 없습니다. ID: "+id));

        boardEntity.setTitle(boardDTO.getTitle()); // 제목 업데이트
        boardEntity.setContents(boardDTO.getContents()); // 내용 업데이트
        boardEntity.setUpdatedTime(boardDTO.getCreateDate()); //시간 업데이트

        boardRepository.save(boardEntity);
    }

    public void deleteContents(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                        .orElseThrow(()->new RuntimeException("게시물을 찾을 수 없습니다. ID: "+id));

        boardRepository.delete(boardEntity);
    }

    // 글 생성 ----------------------------------------------------
    public void saveContents(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setId(boardDTO.getId());
        boardEntity.setContents(boardDTO.getContents());
        boardEntity.setUpdatedTime(boardDTO.getUpdateTime());
        boardEntity.setCount(0);

        // UserRepository를 사용하여 해당 이메일을 가진 사용자를 찾아옵니다.
        UserEntity userEntity = userRepository.findByEmail(boardDTO.getEmail());
        if(userEntity != null){
            boardEntity.setUserEntity(userEntity);
        } else {
            // 사용자를 찾을 수 없는 경우 예외 처리 또는 로그를 남기는 등의 작업을 수행할 수 있습니다.
            System.out.println("해당 이메일을 가진 사용자를 찾을 수 없습니다.");
        }

        boardRepository.save(boardEntity);
    }
}

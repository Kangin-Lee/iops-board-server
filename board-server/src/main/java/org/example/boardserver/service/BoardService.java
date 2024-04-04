package org.example.boardserver.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.boardserver.dto.BoardAndUserDTO;
import org.example.boardserver.dto.BoardDTO;
import org.example.boardserver.entity.BoardEntity;
import org.example.boardserver.entity.UserEntity;
import org.example.boardserver.repository.BoardRepository;
import org.example.boardserver.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<BoardDTO> getAllBoardData(Pageable pageable) {
//        List<BoardEntity> boardList = boardRepository.findAll(sort);
//        List <BoardDTO> boardDTOList = new ArrayList<>();
//        for (BoardEntity boardEntity : boardList){
//            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
//        }
//        return boardDTOList;
        Page<BoardEntity> boardPage = boardRepository.findAll(pageable);
        return boardPage.map(BoardDTO::toBoardDTO);
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
        boardEntity.setUpdatedTime(boardDTO.getUpdateTime()); //시간 업데이트

        boardRepository.save(boardEntity);
    }

    public void deleteContents(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                        .orElseThrow(()->new RuntimeException("게시물을 찾을 수 없습니다. ID: "+id));

        boardRepository.delete(boardEntity);
    }

    // 글 생성 ----------------------------------------------------
    public void saveContents(BoardAndUserDTO boardAndUserDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle(boardAndUserDTO.getTitle());
        boardEntity.setContents(boardAndUserDTO.getContents());

        boardEntity.setCount(0);

        UserEntity userEntity = userRepository.findByEmail(boardAndUserDTO.getWriter());
        if(userEntity != null){
            boardEntity.setUserEntity(userEntity);
        }else{
            System.out.println("해당 이메일을 가진 사용자를 찾을 수 없습니다.");
        }

        System.out.println("d");

        boardRepository.save(boardEntity);
    }


}

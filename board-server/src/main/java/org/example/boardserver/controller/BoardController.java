package org.example.boardserver.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.example.boardserver.dto.BoardAndUserDTO;
import org.example.boardserver.dto.BoardDTO;
import org.example.boardserver.dto.ComentDTO;
import org.example.boardserver.dto.UserDTO;
import org.example.boardserver.service.BoardService;
import org.example.boardserver.service.ComentService;
import org.example.boardserver.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final ComentService comentService;

//    게시판 리스트  ------------------------------------------------------------------------
    @GetMapping("/board")
    public ResponseEntity<List<BoardDTO>> findAll(){
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        List<BoardDTO> boardDTOList = boardService.getAllBoardData(sort);
        return ResponseEntity.ok(boardDTOList);
    }

    //회원가입--------------------------------------------------------------------------------
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO userDTO){
        userService.signUp(userDTO);

        return new ResponseEntity<>("회원가입이 완료되었습니다.",HttpStatus.CREATED);
    }

    //조회 수 증가-----------------------------------------------------------------------------
    @PutMapping("/board/{id}")
    public ResponseEntity<String> increaseCount(@PathVariable Long id){
        boardService.increaseCount(id);
        return ResponseEntity.ok("조회수 증가");
    }

    //게시물 업데이트--------------------------------------------------------------------------
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateContents(@PathVariable Long id, @RequestBody BoardDTO boardDTO){
        boardService.updateContents(id, boardDTO);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteContents(@PathVariable Long id){
        try {
            boardService.deleteContents(id);
            return ResponseEntity.ok("delete");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시물 삭제 중 오류가 발생했습니다.");
        }

    }

    //로그인---------------------------------------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO){
        boolean success = userService.login(userDTO);

        if (success) {
            return ResponseEntity.ok("loginSuccess");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    //게시물 상세 조회----------------------------------------------------------------------------
    @GetMapping("/board/{id}")
    public ResponseEntity<BoardDTO> getPostById(@PathVariable Long id){
        BoardDTO boardDTO = boardService.findById(id);
        return ResponseEntity.ok(boardDTO);
    }

    //게시물 작성---------------------------------------------------------------------------------
    @PostMapping("/create")
    public ResponseEntity<String> createContents(@RequestBody BoardDTO boardDTO){

        boardService.saveContents(boardDTO);

        return new ResponseEntity<>("글작성 완료",HttpStatus.CREATED);
    }

    //로그인한 유저 정보 가져오기
//    @GetMapping("/user-info")
//    public ResponseEntity<UserDTO> userInfo(){
//        UserDTO userDTO = userService.getLoginUserInfo();
//        return ResponseEntity.ok(userDTO);
//    }

    // 댓글 작성------------------------------------------------------------------------------
    @PostMapping("/comment/{id}")
    public ResponseEntity<String> coment(@PathVariable Long id, @RequestBody ComentDTO comentDTO){
        BoardDTO boardDTO = boardService.findById(id);
        comentService.saveComent(comentDTO);

        return new ResponseEntity<>("댓글 작성 완료", HttpStatus.CREATED);
    }
}

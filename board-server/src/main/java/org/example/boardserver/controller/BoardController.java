package org.example.boardserver.controller;

import lombok.RequiredArgsConstructor;
import org.example.boardserver.dto.BoardAndUserDTO;
import org.example.boardserver.dto.BoardDTO;
import org.example.boardserver.dto.CommentDTO;
import org.example.boardserver.dto.UserDTO;
import org.example.boardserver.service.BoardService;
import org.example.boardserver.service.CommentService;
import org.example.boardserver.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final CommentService commentService;

//    게시판 리스트  ------------------------------------------------------------------------
    @GetMapping("/board")
    public ResponseEntity<Page<BoardDTO>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<BoardDTO> boardPage = boardService.getAllBoardData(pageable);
        return ResponseEntity.ok(boardPage);
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

    //댓글 삭제
    @DeleteMapping("comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id){
        try{
            commentService.deleteComment(id);
            return  ResponseEntity.ok("삭제");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제 중 오류 발생");
        }
    }

    // 댓글 수정
    @PutMapping("update/comment/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO){
//        try{
//            commentService.updateComment(id, commentDTO);
//            return ResponseEntity.ok("수정");
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 수정 중 오류 발생");
//        }
            commentService.updateComment(id, commentDTO);
            return ResponseEntity.ok("수정");
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
    public ResponseEntity<String> createContents(@RequestBody BoardAndUserDTO boardAndUserDTO){

        String title = boardAndUserDTO.getTitle();
        String contents = boardAndUserDTO.getContents();
        String email = boardAndUserDTO.getWriter();

        // 여기서 필요한 로직을 수행하고 적절한 응답을 반환합니다.
        if (title == null || contents == null || title.isEmpty() || contents.isEmpty()) {
            return ResponseEntity.badRequest().body("제목 혹은 내용을 입력해 주세요.");
        }

        boardService.saveContents(boardAndUserDTO);

        // 정상적으로 처리되었을 때의 응답
        return ResponseEntity.status(HttpStatus.CREATED).body("글이 정상적으로 작성되었습니다.");
    }


    // 댓글 작성------------------------------------------------------------------------------
    @PostMapping("board/{id}/comments")
    public ResponseEntity<String> comment(@PathVariable Long id, @RequestBody CommentDTO commentDTO){
        commentService.createdComment(id, commentDTO);

        return ResponseEntity.ok("success");
    }

    //댓글 가져오기
    @GetMapping("board/{id}/comments")
    public ResponseEntity<List<CommentDTO>> commentsFindAll(@PathVariable Long id){
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        List<CommentDTO> commentDTOList = commentService.getAllCommentsData(id, sort);

        return ResponseEntity.ok(commentDTOList);
    }

    //수정한 댓글 가져오기
//    @GetMapping("board/{id}/comment")
//    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id){
//        CommentDTO commentDTO = commentService.findById(id);
//        return  ResponseEntity.ok(commentDTO);
//    }

}

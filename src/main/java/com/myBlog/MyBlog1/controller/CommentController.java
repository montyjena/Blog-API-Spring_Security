package com.myBlog.MyBlog1.controller;
import com.myBlog.MyBlog1.entity.CommentDto;
import com.myBlog.MyBlog1.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/comments?postId=1
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestParam long postId,
     @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
    //http://localhost:8080/api/comments?postId=2&commentId=2
    @DeleteMapping
    public ResponseEntity<String> deleteCommentById(@RequestParam long postId,@RequestParam long commentId) {
        commentService.deleteCommentById(postId,commentId);
        return new ResponseEntity<>("Comment is Deleted",HttpStatus.OK);
    }

        //http://localhost:8080/api/comments/1/update
        @PutMapping("/{commentId}/update")
        public ResponseEntity<CommentDto> updateComment(@PathVariable long commentId, @RequestBody CommentDto commentDto) {
            CommentDto dtos1 = commentService.updateCommentById(commentId, commentDto);
            return  new ResponseEntity<>(dtos1,HttpStatus.OK);

        }
        //http://localhost:8080/api/comments?postId=2
        @GetMapping
        public List<CommentDto> getCommentsByPostId(@RequestParam long postId) {
            List<CommentDto> commentsDtos = commentService.getCommentsByPostId(postId);
            return commentsDtos;

        }


}

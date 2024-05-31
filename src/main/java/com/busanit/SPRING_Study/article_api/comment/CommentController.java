package com.busanit.SPRING_Study.article_api.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

//  CREATE
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO createComment) {

        CommentDTO commentDTO = commentService.createComment(createComment);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentDTO);
    }

//  READ ALL
    @GetMapping
    public ResponseEntity<List<CommentDTO>> readAllComments() {

        List<CommentDTO> commentList = commentService.readAllComments();

        return ResponseEntity.ok(commentList);
    }

//  READ SPECIFIC COMMENT
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> readCommentById(@PathVariable Long id) {

        CommentDTO comment = commentService.readCommentById(id);

        if (comment == null) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(comment);
    }

//  UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO updateComment) {

        CommentDTO comment = commentService.updateComment(id, updateComment);

        if (comment == null) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(comment);
    }

//  DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {

        if (!commentService.deleteComment(id)) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
}

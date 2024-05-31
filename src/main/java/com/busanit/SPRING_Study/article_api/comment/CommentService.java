package com.busanit.SPRING_Study.article_api.comment;

import com.busanit.SPRING_Study.article_api.article.Article;
import com.busanit.SPRING_Study.article_api.article.ArticleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

//  CREATE
    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO) {

//      Article ID가 존재하지 않는 경우
        Article article = articleRepository.findById(commentDTO.getArticleId()).orElse(null);

        if (article == null) {
            throw new RuntimeException("존재하지 않는 Article");
        }
//      DTO → 엔터티
//      1. 생성 메서드 사용 DTO
//      Comment comment = Comment.toEntity(commentDTO);

//      2. 빌더 변환
        Comment comment = commentDTO.toEntityBuilder(article);

        Comment saved = commentRepository.save(comment);

        return saved.toDTO();
    }

//  Entity → DTO 로 변환하여 전달
//  READ ALL
    public List<CommentDTO> readAllComments() {

        List<Comment> comments = commentRepository.findAll();

        return comments.stream().map(Comment::toDTO).toList();
    }

//  READ SPECIFIC  COMMENT
    public CommentDTO readCommentById(Long id) {

        Comment comment = commentRepository.findById(id).orElse(null);

        return comment.toDTO();
    }

//  UPDATE
    @Transactional
    public CommentDTO updateComment(Long id, CommentDTO updateComment) {

        Comment comment = commentRepository.findById(id).orElse(null);

        if (comment != null) {

            if (updateComment.getContent() != null) {
                comment.setContent(updateComment.getContent());
            }

            if (updateComment.getAuthor() != null) {
                comment.setAuthor(updateComment.getAuthor());
            }

//          댓글의 게시글까지 변경하고 싶은 경우 (로직 추가)

            return commentRepository.save(comment).toDTO();

        } else {

            return null;
        }
    }

//  DELETE
    @Transactional
    public Boolean deleteComment(Long id) {

        Comment comment = commentRepository.findById(id).orElse(null);

        if (comment != null) {

            commentRepository.delete(comment);

            return true;

        } else {

            return false;
        }
    }
}

package com.busanit.SPRING_Study.article_api.comment;

import com.busanit.SPRING_Study.article_api.article.Article;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String author;

//  1:N 관계에서 외래 키를 갖는 엔터티는 (N)
//  외래 키 필드 지정
//  댓글은 Many에 해당
    @ManyToOne    // 관계 설정
    @JoinColumn(name = "article_id")    // 외래 키로 사용될 컬럼 이름
//  @JoinColumn(name = "article_id", nullable = false)  → 애초에 이렇게 설정해 주어도 됨
    private Article article;
//  PostMan 입력 시 필드는 Article 클래스의 Long 타입의 id가 아닌
//  Article 객체 자체이기 때문에 JSON 형식에 맞춰 Article 객체를 입력해 주어야 한다.

//  엔터티 → DTO 변환 메서드
    public CommentDTO toDTO() {

//      댓글에 게시글 ID가 없는 경우 예외처리
        Long articleId = 0L;

        if (article != null) {
            articleId = article.getId();
        }

        return new CommentDTO(id, content, author, articleId);
    }

//  DTO → 엔터티 변환 메서드
    public static Comment toEntity(CommentDTO commentDTO) {

        Comment comment = new Comment();

        comment.setContent(commentDTO.getContent());
        comment.setAuthor(commentDTO.getAuthor());

        Article article = new Article();

        article.setId(commentDTO.getArticleId());
        comment.setArticle(article);

        return comment;
    }
}

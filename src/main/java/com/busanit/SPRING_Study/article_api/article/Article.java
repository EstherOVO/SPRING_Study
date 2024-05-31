package com.busanit.SPRING_Study.article_api.article;

import com.busanit.SPRING_Study.article_api.comment.Comment;
import com.busanit.SPRING_Study.article_api.comment.CommentDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// 롬복 애노테이션
// @Data
// @Getter  롬복 : 게터만 생성
// @Setter  롬복 : 세터만 생성
// @ToString    롬복 : ToString만 생성
// @NoArgsConstructor   롬복 : 기본 생성자만 생성
// @AllArgsConstructor  롬복 : 모든 필드 인자로 갖는 생성자
// @RequiredArgsConstructor     롬복 : final 또는 @nonnull 이 붙은 필드만 매개변수로 갖는 생성자(final 키워드가 없을 경우 기본 생성자)
// @EqualsAndHashCode   롬복 : Equals, HashCode

@Data   // 롬복 : 게터, 세터, ToString, Equals, HashCode, @RequiredArgsConstructor를 한 번에 적용
@AllArgsConstructor // 전체 필드를 갖는 생성자
@NoArgsConstructor  // 필드를 갖지 않는 기본 생성자
@Entity
@Builder    // 롬복 : 빌더 패턴
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String author;

//  양방향 관계 : 부모 객체가 자식을 알고 있음
//  1:N 관계 → 하나의 게시글을 여러 개의 댓글을 가질 수 있다.
//  mappedBy : Comment 엔터티의 article 필드와 매핑이 된다.
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
//  cascade = CascadeType.ALL : 부모 수정(삭제) 시 자식도 수정(삭제) → Article이 저장되거나 삭제될 때 관련된 상태를 모두 전이 : 영속성 전이
//  orphanRemoval = true : 부모 엔터티에서 자식 엔터티의 관계가 끊어질 때 자동으로 DB에서 제거

//  Entity → DTO 변환 메서드
    public ArticleDTO toDTO() {

        List<CommentDTO> commentDTOList = new ArrayList<>();

        if (comments != null) {
            commentDTOList = comments.stream().map(Comment::toDTO).toList();
        }

        return new ArticleDTO(id, title, content, author, commentDTOList);
    }

//  DTO → 엔터티 변환 메서드
    public static Article toEntity(ArticleDTO articleDTO) {

        Article article = new Article();

        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setAuthor(articleDTO.getAuthor());

        return article;
    }
}

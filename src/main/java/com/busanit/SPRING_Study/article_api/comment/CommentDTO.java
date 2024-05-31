package com.busanit.SPRING_Study.article_api.comment;

import com.busanit.SPRING_Study.article_api.article.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;
    private String content;
    private String author;
    private Long articleId;

//  DTO → 엔터티(엔터티에 @Builder 적용, 빌더 패턴 사용)
    public Comment toEntityBuilder(Article article) {

        return Comment.builder()
                .id(id)
                .content(content)
                .author(author)
                .article(article)
                .build();
    }
}

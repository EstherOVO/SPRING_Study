package com.busanit.SPRING_Study.article_api.article;

import com.busanit.SPRING_Study.article_api.comment.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

//  CREATE
    @Transactional
    public ArticleDTO createArticle(ArticleDTO createArticle) {

        Article saved = articleRepository.save(createArticle.toEntityBuilder());

        return saved.toDTO();
    }

//  READ ALL
    public List<ArticleDTO> readAllArticles() {

        List<Article> articles = articleRepository.findAll();

//        List<ArticleDTO> articleDTOList = new ArrayList<>();    // ArticleDTO 를 담을 빈 객체
//
////      조회된 전체 엔터티를 순회하며 Article → ArticleDTO 변경
//        for (Article article : articles) {
//
//            List<Comment> comments = article.getComments();         // Article에서 커멘트를 가져옴
//            List<CommentDTO> commentDTOList = new ArrayList<>();    // CommentDTO 를 담을 빈 객체
//
////          게시글을 참조하는 댓글 전체 순회하며 Comment → CommentDTO 변경
//            for (Comment comment : comments) {
//
//                CommentDTO commentDTO = new CommentDTO(comment.getId(), comment.getContent(), comment.getAuthor(), comment.getArticle().getId());   // CommentDTO 생성
//                commentDTOList.add(commentDTO);
//            }
//
//            ArticleDTO articleDTO = new ArticleDTO(article.getId(), article.getContent(), article.getTitle(), article.getAuthor(), commentDTOList); // ArticleDTO 생성
//            articleDTOList.add(articleDTO);
//        }

        return articles.stream().map(Article::toDTO).toList();
    }

//  READ SPECIFIC ARTICLE
    public ArticleDTO readArticleById(Long id) {

        Article article = articleRepository.findById(id).orElse(null);

//        List<Comment> comments = article.getComments();
//        List<CommentDTO> commentDTOList = new ArrayList<>();
//
//        for (Comment comment : comments) {
//
//            CommentDTO commentDTO = new CommentDTO(comment.getId(), comment.getContent(), comment.getAuthor(), comment.getArticle().getId());
//            commentDTOList.add(commentDTO);
//        }
//
//        ArticleDTO articleDTO = new ArticleDTO(article.getId(), article.getContent(), article.getTitle(), article.getAuthor(), commentDTOList);

        return article != null ? article.toDTO() : null;
    }

//  UPDATE
    @Transactional
    public ArticleDTO updateArticle(Long id, ArticleDTO updateArticle) {

        Article article = articleRepository.findById(id).orElse(null);

        if (article != null) {

            if (updateArticle.getTitle() != null) {
                article.setTitle(updateArticle.getTitle());
            }

            if (updateArticle.getContent() != null) {
                article.setContent(updateArticle.getContent());
            }

            if (updateArticle.getAuthor() != null) {
                article.setAuthor(updateArticle.getAuthor());
            }

            return articleRepository.save(article).toDTO();

        } else {

            return null;
        }
    }

//  DELETE
    @Transactional
    public Boolean deleteArticle(Long id) {

        Article article = articleRepository.findById(id).orElse(null);

        if (article != null) {

            articleRepository.delete(article);

            return true;

        } else {

            return false;
        }
    }
}


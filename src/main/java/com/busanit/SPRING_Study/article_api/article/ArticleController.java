package com.busanit.SPRING_Study.article_api.article;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    @Autowired
    final private ArticleService articleService;

//  CREATE
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO createArticle) {

        ArticleDTO article = articleService.createArticle(createArticle);

        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }

//  READ ALL
    @GetMapping
    public ResponseEntity<List<ArticleDTO>> readAllArticles() {

        List<ArticleDTO> articleDTOList = articleService.readAllArticles();

        return ResponseEntity.ok(articleDTOList);
    }

//  READ SPECIFIC ARTICLE
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> readArticleById(@PathVariable Long id) {

        ArticleDTO article = articleService.readArticleById(id);

        if (article == null) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(article);
    }

//  UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody ArticleDTO updateArticle) {

        ArticleDTO article = articleService.updateArticle(id, updateArticle);

        if (article == null) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(article);
    }

//  DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {

        if (!articleService.deleteArticle(id)) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/author/{author}")
    public List<ArticleDTO> readArticleByAuthor(@PathVariable String author) {

        return articleService.readArticleByAuthor(author);
    }

//  커스텀 레포지토리
    @GetMapping("/title/{title}")
    public List<ArticleDTO> getArticleByTitleContaining(@PathVariable String title) {

        return articleService.readArticleByTitleContaining(title);
    }

//  페이징
//  게시글 페이징, 정렬된 상태로 조회하기
    @GetMapping("/page")
    public Page<Article> getArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<Article> articles = articleService.readArticles(page, size, sortBy);
        return articles;
    }

//    특정 저자의 게시글 페이징, 정렬된 상태로 조회
//    @GetMapping("/author")
//    public Page<Article> getArticlesByAuthor(
//            @RequestParam String author,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "id") String sortBy) {
//        Page<Article> articles = articleService.readArticleByAuthor(author, page, size, sortBy);
//        return articles;
//    }
}

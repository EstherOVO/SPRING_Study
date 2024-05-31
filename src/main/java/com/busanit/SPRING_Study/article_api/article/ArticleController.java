package com.busanit.SPRING_Study.article_api.article;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/title/{title}")
    public List<ArticleDTO> getArticleByTitleContaining(@PathVariable String title) {

        return articleService.readArticleByTitleContaining(title);
    }
}

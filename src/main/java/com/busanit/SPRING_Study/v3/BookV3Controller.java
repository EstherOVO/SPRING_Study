package com.busanit.SPRING_Study.v3;

import com.busanit.SPRING_Study.v2.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 표현 계층 : 사용자로부터 요청을 받고, 응답을 해주는 일을 전담
@RestController // 표현 계층임을 선언
@RequestMapping("/api/v3/books")    // 기본 경로
public class BookV3Controller {

//  ★ 컨트롤러 → 서비스 ★
//  스프링의 의존성 주입(DI : Dependency Injection)

/*
    ResponseEntity : HTTP 응답을 표현하는 객체
    ResponseEntity.ok(엔터티) : 200
    ResponseEntity.status(상태코드).body(엔터티) : 해당 상태코드와 데이터 본문을 응답
    ResponseEntity.notFound().build() : 404 Not Found 반환
*/

    @Autowired
    private BookService bookService;

//  CREATE
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {

//      데이터베이스 접근은 서비스 계층에 위임
        Book createdBook = bookService.createBook(book);

//      상태코드 201번(CREATED)에 응답 본문을 담아서 리턴
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

//  READ
    @GetMapping
    public List<Book> readAllBooks() {

        return bookService.readAllBooks();
    }

//  READ 특정 도서
    @GetMapping("/{id}")
    public ResponseEntity<Book> readBookById(@PathVariable Long id) {

        Book book = bookService.readBookById(id);

        if (book == null) {
            return ResponseEntity.notFound().build();   // 책이 없으면 404 Not Found 응답
        }

        return ResponseEntity.ok(book);
    }

//  UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updateBook) {

        Book book = bookService.updateBook(id, updateBook);

        if (book == null) {
            return ResponseEntity.notFound().build();   // 책이 없으면 404 Not Found 응답
        }

        return ResponseEntity.ok(book);
    }

//  DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

        if (!bookService.deleteBook(id)) {
            return ResponseEntity.notFound().build();   // 책이 없으면 404 Not Found 응답
        }

        return ResponseEntity.ok().build();     // 책 객체가 없기 때문에 뒤에 .build() 붙이기
    }
}

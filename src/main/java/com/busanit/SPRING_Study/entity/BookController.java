package com.busanit.SPRING_Study.entity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController     // REST API 표시
@RequestMapping("/api/books")   // HTTP 요청 엔드 포인트의 시작
public class BookController {

//  책 데이터를 저장할 리스트
    List<Book> books = new ArrayList<>();

//  새로운 책 생성 → 사용자에게 본문(body) 입력 요청
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        book.setId((long) books.size() + 1);    // ID 생성
        books.add(book);    // 데이터 리스트에 추가

        return book;
    }

//  모든 책 조회 Read
    @GetMapping()
    public List<Book> getAllBooks() {

        return books;
    }

//  특정 책 조회
    @GetMapping("/{id}")    // 찾을 특정 책(수정할 책)을 경로 변수로 받음
    public Book getByBookById(@PathVariable Long id) { // @경로변수

//      책 리스트에서 ID와 일치하는 것 찾아서 반환
        return books.stream()
                .filter(book -> Objects.equals(book.getId(), id))
                .findFirst()
                .orElse(null);
    }

//  수정 Update
//  변경될 내용을 요청 본문으로 받는다.
    @PutMapping("/{id}")    // 수정할 특정 책(수정할 책)을 경로 변수로 받음
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {

//      1. 수정 요청한 책을 찾음
        Book book = getByBookById(id);

//      2. 책이 있다면 저자와 제목을 수정
        if (book != null) {
//          요청 본문으로 준 데이터 업데이트
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
        }

        return book;
    }

//  삭제 Delete
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {

        boolean removeIf = books.removeIf(book -> book.getId().equals(id));
        if (removeIf) {
            return id + "번 책 삭제 성공";
        } else {
            return "찾을 수 없는 도서입니다.";
        }
    }
}

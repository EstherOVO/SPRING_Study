package com.busanit.SPRING_Study.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // JPA 엔티티임을 선언
public class Book {

    @Id // 기본 키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성
    private Long id;

    private String title;
    private String author;

//  생성자 : 전체 생성자, 기본 생성자
    public Book() {}

    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

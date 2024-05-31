package com.busanit.SPRING_Study.article_api.article;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

/*
    커스텀 메서드
    JPA에서 메서드의 이름을 분석하여 적절한 쿼리를 생성

    ★ 쿼리 메서드 이름 규칙
    - 접두사
        - findBy : 특정 컬럼(필드)에 대한 검색
        - countBy : 특정 컬럼(필드)에 대한 숫자 갯수
        - deleteBy : 특정 컬럼(필드)에 대해 삭제
    - 속성 이름
        - 엔터티 클래스의 필드 이름과 일치해야 한다.
    - 조건
        - Like, Containing, And, Or, Between, OrderBy
*/

//  1. 저자(Author) 기준으로 쿼리를 사용하고 싶은 경우
//  1-1. 기본 쿼리 메서드
    List<Article> findByAuthor(String author);

//  1-2. 명시적 쿼리(JPQL)
//  JPA에서 엔터티 객체를 대상으로 쿼리를 작성하는 객체지향 쿼리
//  JPQL에서 @Query에서 FROM 이하는 자바 객체의 이름(무조건 대문자)
//  :author : 매개변수로 매핑
//  @Param("author") : 매핑되는 매개변수임을 선언
    @Query("SELECT a FROM Article a WHERE a.author = :author")  // SELECT 이하 a는 객체를 의미
    List<Article> findByAuthorJPQL(@Param("author") String author);

//  1-3. 명시적 쿼리(네이티브 쿼리)
//  데이터베이스에 직접 전송되는 SQL 쿼리
//  네이티브 쿼리에서 @Query에서 FROM 이하는 테이블 이름(소문자 가능)
//  :author : 매개변수로 매핑
//  @Param("author") : 매핑되는 매개변수임을 선언
    @Query(value = "SELECT * FROM article WHERE author = :author", nativeQuery = true)
    List<Article> findByAuthorNative(@Param("author") String author);

//  2. 제목에 특정 문자가 포함되는 경우 쿼리
//  2-1. 기본 쿼리 메서드
    List<Article> findByTitleContaining(String title);

    @Query("SELECT a FROM Article a WHERE a.title LIKE %:title%")
    List<Article> findByTitleContainingJPQL(String title);

//  2-3. 명시적 쿼리(네이티브 쿼리)
    @Query(value = "SELECT * FROM article WHERE title LIKE %:title%", nativeQuery = true)
    List<Article> findByTitleContainingNative(String title);

//  ----------------------------------------------------------

//  페이징과 정렬

//  Page, Pageable 임포트(import)하고, 페이징과 정렬을 위한 메서드
    Page<Article> findAll(Pageable pageable);

//  특정 필드(author)로 페이징
    Page<Article> findByAuthor(String author, Pageable pageable);
}

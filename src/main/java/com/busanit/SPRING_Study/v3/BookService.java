package com.busanit.SPRING_Study.v3;

import com.busanit.SPRING_Study.v2.Book;
import com.busanit.SPRING_Study.v2.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service    // 서비스(비지니스) 계층임을 선언
// 서비스 단계에서는 컨트롤러에 정보가 잘 전달될 수 있도록 저장한다. → 따라서 Mapping 하지 않음! (중간 단계라고 생각)
public class BookService {

//  서비스 계층은 Repository (데이터 접근 계층을 의존성 주입 받아 사용)
    @Autowired
    private BookRepository bookRepository;

//  CREATE
    @Transactional  // 해당 서비스 내의 DB 접근을 트랜잭션으로 관리(입출금 등 하나의 트랜잭션으로 생성해야 할 경우가 있음)
//  데이터를 조작하는 메서드는 @Transactional로 트랜잭션을 구현하는 것을 권장한다.
    public Book createBook(Book book) {

        return bookRepository.save(book);
    }

//  READ
    public List<Book> readAllBooks() {

        return bookRepository.findAll();
    }

//  READ 특정 책
    public Book readBookById(Long id) {

        return bookRepository.findById(id).orElse(null);
    }


//  UPDATE
    @Transactional
    public Book updateBook(Long id, Book updateBook) {

        Book book = bookRepository.findById(id).orElse(null);
//      각 필드(컬럼)이 비어있는지 확인하고 업데이트한다.

        if (book != null) {
            if (updateBook.getTitle() == null) {
                book.setTitle(updateBook.getTitle());
            }
            if (updateBook.getAuthor() == null) {
                book.setAuthor(updateBook.getAuthor());
            }

            return bookRepository.save(book);

        } else {

            return null;
        }
    }

//  DELETE
    public boolean deleteBook(Long id) {

        Book book = bookRepository.findById(id).orElse(null);

        if (book != null) {
            bookRepository.delete(book);

            return true;    // 성공적으로 삭제되면 true

        } else {

            return false;   // 아니면 false
        }
    }
}

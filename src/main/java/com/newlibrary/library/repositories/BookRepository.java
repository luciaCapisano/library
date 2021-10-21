package com.newlibrary.library.repositories;

import com.newlibrary.library.entities.Book;
import java.util.List;
//import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    @Query("SELECT b FROM Book b WHERE b.isbn =:i")
    Book searchByISBN(@Param("i") Long i);

    @Query("SELECT b FROM Book b WHERE b.author.id =:id")
    List<Book> getByAuthor(@Param("id") String id);
    
    @Query("SELECT b FROM Book b WHERE b.publisher.id =:id")
    List <Book> getByPublisher(@Param("id") String id);
    
    
//    @Query("SELECT b FROM Book b WHERE b.registered = 1 ORDER BY b.name")
//     List<Book> findRegistered();

    //    @Query("SELECT b FROM Book b WHERE b.registered = 1 ORDER BY b.name")
//     List<Book> findUnregistered();
    
}

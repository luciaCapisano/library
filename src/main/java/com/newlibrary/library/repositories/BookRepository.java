package com.newlibrary.library.repositories;

import com.newlibrary.library.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

//    @Query("SELECT b FROM book b WHERE b.isbn =:i")
//    public Book searchByISBN(@Param("i") Long i);

}

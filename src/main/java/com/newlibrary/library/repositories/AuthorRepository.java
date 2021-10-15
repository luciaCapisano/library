package com.newlibrary.library.repositories;

import com.newlibrary.library.entities.Author;
import java.util.List;
//import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {

    @Query("SELECT a FROM Author a WHERE a.registered = 1 ORDER BY a.name")
    List<Author> searchRegistered();
    
    @Query("SELECT a FROM Author a WHERE a.name LIKE :nameSearch")
    public List<Author> findByName(@Param("nameSearch") String name);

}

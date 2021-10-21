package com.newlibrary.library.repositories;

import com.newlibrary.library.entities.Publisher;
import java.util.List;
//import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, String> {

    @Query ("SELECT p FROM Publisher p WHERE p.registered = 1 ORDER BY p.name")
    public List<Publisher> searchRegistered();
    
     @Query ("SELECT p FROM Publisher p WHERE p.registered = 0 ORDER BY p.name")
    public List<Publisher> searchUnregistered();
    
//    @Query("SELECT p.id FROM Publisher p WHERE p.name LIKE :nameSearch")
//    public String searchByName(@Param("nameSearch") String nameSearch);

     @Query("SELECT p FROM Publisher p WHERE p.name LIKE :nameSearch")
    public List <Publisher> findByName(@Param("nameSearch") String name);
    
}


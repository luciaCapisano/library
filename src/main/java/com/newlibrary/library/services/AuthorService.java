package com.newlibrary.library.services;

import com.newlibrary.library.entities.Author;
import com.newlibrary.library.exceptions.ServiceException;
import com.newlibrary.library.repositories.AuthorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public void consult() {

    }

    @Transactional
    public void save(String name) throws ServiceException {
        validate(name);
         
        if (!authorRepository.findByName(name).isEmpty()) {
            throw new ServiceException("El autor ingresado ya se encuentra en el sistema");
        }
        
        Author author = new Author();
        author.setName(name);
        author.setRegistered(true);
        authorRepository.save(author);
    }

    @Transactional
    public void edit(String id, String name, Boolean registered) throws ServiceException {
        validate(name);
        Optional<Author> result = authorRepository.findById(id);
        if (result.isPresent()) {
            Author author = result.get();
            author.setName(name);
            author.setRegistered(registered);
            authorRepository.save(author);
        } else {
            throw new ServiceException("No se encontró el autor solicitado");
        }
    }
    
    public void delete(String id){
        Author result = authorRepository.getById(id);
            result.setRegistered(false);
            authorRepository.save(result);
    }
    
    public void unregister(String id) throws ServiceException {
        Optional<Author> result = authorRepository.findById(id);
        if (result.isPresent()) {
            Author author = result.get();
            author.setRegistered(false);
            authorRepository.save(author);
        } else {
              throw new ServiceException("No se encontró el autor solicitado");
        }
    }

    public List<Author> listAll(){
        return authorRepository.findAll();
    }
    
    public List<Author> listRegistered(){
        return authorRepository.searchRegistered();
    }
    
        public List<Author> listUnregistered(){
        return authorRepository.searchUnregistered();
    }
    
    public Author findById(String id){
        Author result = authorRepository.getById(id);
        return result;
    }
    
    
    public void validate(String name) throws ServiceException {
        if (name == null || name.isEmpty() || name.equals(" ") || name.contains("  ")) {
            throw new ServiceException("El nombre del autor no puede estar vacío");
        }
        

    }
}

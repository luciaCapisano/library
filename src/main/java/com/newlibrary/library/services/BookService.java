package com.newlibrary.library.services;

import com.newlibrary.library.entities.Author;
import com.newlibrary.library.entities.Book;
import com.newlibrary.library.entities.Publisher;
import com.newlibrary.library.exceptions.ServiceException;
import com.newlibrary.library.repositories.AuthorRepository;
import com.newlibrary.library.repositories.BookRepository;
import com.newlibrary.library.repositories.PublisherRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public List<Book> listAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public void save(Long isbn, String title, Integer legalYear, Integer totalQuantity, Integer givenQuantity, String idAuthor, String idPublisher) throws ServiceException {
        Publisher publisher = publisherRepository.getById(idPublisher);
        Author author = authorRepository.getById(idAuthor);
        validate(isbn, title, legalYear, totalQuantity, givenQuantity, publisher, author);
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setLegalYear(legalYear);
        book.setTotalQuantity(totalQuantity);
        book.setGivenQuantity(givenQuantity);
        book.setAvailableQuantity(totalQuantity - givenQuantity);
        book.setRegistered(true);
        book.setPublisher(publisher);
        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Transactional
    public void edit(String id, Long isbn, String title, Integer legalYear, Integer totalQuantity, Integer givenQuantity, String idAuthor, String idPublisher) throws ServiceException {
        Optional<Book> result = bookRepository.findById(id);
        if (result.isPresent()) {
            Publisher publisherSet = publisherRepository.getById(idPublisher);
            Author authorSet = authorRepository.getById(idAuthor);
            validate(isbn, title, legalYear, totalQuantity, givenQuantity, publisherSet, authorSet);
            Book book = result.get();
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setLegalYear(legalYear);
            book.setTotalQuantity(totalQuantity);
            book.setGivenQuantity(givenQuantity);
            book.setAvailableQuantity(totalQuantity - givenQuantity);
            bookRepository.save(book);
        } else {
            throw new ServiceException("No se ha podido encontrar el libro solicitado");
        }
    }

    public void unregister(String id) {
        Book book = bookRepository.findById(id).get();
        book.setRegistered(false);
    }

    public void validate(Long isbn, String title, Integer legalYear, Integer totalQuantity, Integer givenQuantity, Publisher publisher, Author author) throws ServiceException {
        if (isbn == null) {
            throw new ServiceException("El ISBN no puede ser nulo ");
        }

//        Book book = bookRepository.searchByISBN(isbn);
//        if (book != null) {
//            throw new ServiceException("El isbn ingresado ya existe");
//        }
        if (title.isEmpty() || title == null) {
            throw new ServiceException("El título del libro no puede ser nulo ");
        }

        if (title.length() < 3) {
            throw new ServiceException("El título no puede ser tan corto");
        }

        int añoActual = new Date().getYear() + 1900;
        if (legalYear < 1000 || legalYear > añoActual) {
            throw new ServiceException("El año de publicación es inválido");
        }

        if (legalYear == null) {
            throw new ServiceException("El año de publicación no puede ser nulo");
        }

        if (totalQuantity < 0) {
            throw new ServiceException("No puede haber existencias nulas");
        }

        if (totalQuantity == null) {
            throw new ServiceException("No puede haber existencias nulas");
        }

        if (givenQuantity < 0) {
            throw new ServiceException("No puede haber una cantidad prestada nula");
        }

        if (givenQuantity == null) {
            throw new ServiceException("No puede haber una cantidad prestada nula");
        }

        if (givenQuantity > totalQuantity) {
            throw new ServiceException("No puede haber mas ejemplares prestados que el total");
        }

        if (publisher == null) {
            throw new ServiceException("No se ha podido acceder a la editorial solicitada");
        }

        if (author == null) {
            throw new ServiceException("No se ha podido acceder al autor solicitado");
        }
    }

}
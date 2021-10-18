package com.newlibrary.library.controllers;

import com.newlibrary.library.entities.Book;
import com.newlibrary.library.services.AuthorService;
import com.newlibrary.library.services.BookService;
import com.newlibrary.library.services.PublisherService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;
    
    @Autowired
    PublisherService publisherService;

    @Autowired
    AuthorService authorService;

    @GetMapping("/list")
    public String list(ModelMap model) {
        List<Book> books = bookService.listAll();
        model.addAttribute("books", books);
        return "book-list.html";
    }

    @GetMapping("/save")
    public String createBook(ModelMap model) {
        model.addAttribute("publishers", publisherService.listRegistered());
        model.addAttribute("authors", authorService.listRegistered());
        return "book-form.html";
    }

    @PostMapping("/save")
    public String saveBook(ModelMap model, @RequestParam Long isbn, @RequestParam String title, @RequestParam Integer legalYear, @RequestParam Integer totalQuantity, @RequestParam Integer givenQuantity, @RequestParam String idAuthor, @RequestParam String idPublisher) {

        try {
            bookService.save(isbn, title, legalYear, totalQuantity, givenQuantity, idAuthor, idPublisher);
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }
        return "redirect:/book/list";
    }
}

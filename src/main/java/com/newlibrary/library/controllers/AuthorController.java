package com.newlibrary.library.controllers;

import com.newlibrary.library.entities.Author;
import com.newlibrary.library.services.AuthorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/save")
    public String save() {
        return "author-form.html";
    }

    @PostMapping("/save")
    public String save(ModelMap model, @RequestParam String name) {
        try {
            authorService.save(name);
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }
        return "redirect:/author/list";
    }

    @GetMapping("/list")
    public String list(ModelMap model) {
        List<Author> RegisteredAuthors = authorService.listRegistered();
        List<Author> UnregisteredAuthors = authorService.listUnregistered();
        model.put("RegisteredAuthors", RegisteredAuthors);
        model.put("UnregisteredAuthors", UnregisteredAuthors);
        return "author-list.html";
    }

    @GetMapping("/delete")
    public String deleteAuthor(@RequestParam String id) {
        authorService.delete(id);
        return "redirect:/author/list";
    }

    @GetMapping("/edit")
    public String editAutor(ModelMap model, @RequestParam String id){
          try {
            Author author = authorService.findById(id);
            model.addAttribute("author", author);
            return "author-form-edit.html";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "redirect:/author/list";
        }
    }
    
    
    @PostMapping("/edit")
    public String editAuthor(ModelMap model, @RequestParam String id, @RequestParam String name, @RequestParam Boolean registered) {
        try {
           Author author = authorService.findById(id);
           model.addAttribute("author", author);
            authorService.edit(id, name, registered);
            return "redirect:/author/list";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "author-form-edit.html";
        }

    }

}

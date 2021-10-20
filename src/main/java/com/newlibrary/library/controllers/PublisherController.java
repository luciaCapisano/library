package com.newlibrary.library.controllers;

import com.newlibrary.library.entities.Publisher;
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
@RequestMapping("/publisher")
public class PublisherController {

    @Autowired
    PublisherService publisherService;

    @GetMapping("/save")
    public String save() {
        return "publisher-form.html";
    }

    @PostMapping("/save")
    public String save(ModelMap model, @RequestParam String name) {
        try {
            publisherService.save(name);
         return "redirect:/publisher/list";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "publisher-form.html";
        }
    }

    @GetMapping("/list")
    public String list(ModelMap model) {
        List<Publisher> publishers = publisherService.listAll();
        model.put("publishers", publishers);
        return "publisher-list.html";
    }

    @GetMapping("/edit")
    public String edit(ModelMap model, @RequestParam String id) {
        try {
            Publisher publisher = publisherService.findById(id);
            model.put("publisher", publisher);
            return "publisher-form-edit.html";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "redirect:/publisher/list";
        }
    }

    @PostMapping("/edit")
    public String edit(ModelMap model, @RequestParam String id, @RequestParam String name, @RequestParam Boolean registered) {
        try {
            Publisher publisher = publisherService.findById(id);
            model.addAttribute("publisher", publisher);
            publisherService.edit(id, name, registered);
            return "redirect:/publisher/list";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "publisher-form-edit.html";
        }
    }

    @GetMapping("/delete")
    public String deletePublisher(ModelMap model, @RequestParam String id) {
        try {
            publisherService.unregister(id);
            return "redirect:/publisher/list";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "publisher-form-edit.html";
        }
    }

}

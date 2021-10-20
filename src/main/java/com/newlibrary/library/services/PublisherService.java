package com.newlibrary.library.services;

import com.newlibrary.library.entities.Publisher;
import com.newlibrary.library.exceptions.ServiceException;
import com.newlibrary.library.repositories.PublisherRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PublisherService {

    @Autowired
    PublisherRepository publisherRepository;

    @Transactional
    public Publisher save(String name) throws ServiceException {
        validate(name);

        if (!publisherRepository.findByName(name).isEmpty()) {
            throw new ServiceException("La editorial ingresada ya se encuentra en el sistema");
        }
        Publisher publisher = new Publisher();
        publisher.setName(name);
        publisher.setRegistered(true);
        return publisherRepository.save(publisher);
    }

    @Transactional
    public void edit(String id, String name, Boolean registered) throws ServiceException {
        validate(name);

        Optional<Publisher> result = publisherRepository.findById(id);
        if (result.isPresent()) {
            Publisher publisher = result.get();
            publisher.setName(name);
            publisher.setRegistered(registered);
            publisherRepository.save(publisher);
        } else {
            throw new ServiceException("No se encontró la editorial solicitada");
        }
    }

    @Transactional
    public Publisher unregister(String id) {
        Publisher publisher = publisherRepository.findById(id).get();
        publisher.setRegistered(false);
        return publisherRepository.save(publisher);
    }

    public List<Publisher> listAll() {
        return publisherRepository.findAll();
    }

    public List<Publisher> listRegistered(){
        return publisherRepository.searchRegistered();
    }
    
    
    public Publisher findById(String id) {
        return publisherRepository.getById(id);
    }

    public void validate(String name) throws ServiceException {
        if (name == null || name.isEmpty() || name.equals(" ")|| name.contains("  ")) {
            throw new ServiceException("El nombre de la editorial no puede estar vacío");
        }

    }

}

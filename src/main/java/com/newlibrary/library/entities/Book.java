package com.newlibrary.library.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Book implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id; 
    private Long isbn; 
    private String title; 
    private Integer legalYear;
    private Integer totalQuantity;
    private Integer givenQuantity; 
    private Integer availableQuantity;
    private Boolean registered;
    @ManyToOne
    private Author author; 
    @ManyToOne
    private Publisher publisher;
}

package com.example.springjpa;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "PERSON")
public class Person {

    private @Id @GeneratedValue Long id ;
    private String firstName ;
    private String lastName ;
    private java.sql.Date birthDate ;
    private java.sql.Date deathDate ;
    private String fooBar ;


}


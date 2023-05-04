
package com.example.springgumball;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;


/*

	https://docs.spring.io/spring-data/data-commons/docs/current/reference/html/#mapping.fundamentals
	https://www.baeldung.com/jpa-indexes

 */

@Entity 
@Table(name = "orders", indexes=@Index(name = "altIndex", columnList = "orderNumber", unique = true))
@Data
@RequiredArgsConstructor
class GumballOrder {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @Column(nullable=false)
    private String orderNumber ;

    private String instructions ;
    private String status ;
    private String color ;
    
}


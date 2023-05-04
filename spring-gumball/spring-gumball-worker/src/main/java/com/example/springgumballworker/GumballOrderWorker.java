package com.example.springgumballworker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
@RabbitListener(queues = "gumball")
public class GumballOrderWorker {

    private static final Logger log = LoggerFactory.getLogger(GumballOrderWorker.class);

    @Autowired
    private GumballOrderRepository orders ;

    @RabbitHandler
    public void processGumballOrders(String orderNumber) {
        log.info( "Received  Order # " + orderNumber) ;

        // Sleeping to simulate buzy work
        try {
            Thread.sleep(60000); // 60 seconds
        } catch (Exception e) {}


        List<GumballOrder> list = orders.findByOrderNumber( orderNumber ) ;
        if ( !list.isEmpty() ) {
            GumballOrder order = list.get(0) ;
            order.setStatus ( "FULFILLED" ) ;
            String[] gumball_colors = {"RED", "BLUE", "WHITE", "GREEN", "YELLOW"};
            int min = 1 ;
            int max = 5 ;
            int ran = (int)(Math.random()*(max-min+1)+min) - 1 ;  
            order.setColor( gumball_colors[ran] ) ;
            orders.save(order) ;
            log.info( "Processed Order # " + orderNumber );
        } else {
            log.info( "[ERROR] Order # " + orderNumber + " Not Found!" );
        } 

    }
}
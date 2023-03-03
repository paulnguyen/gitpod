/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

import java.text.NumberFormat ;

/**
 * Card Class for Managing Card Balance & Transactions
 */
public class Card {

    private double balance = 0.00f ; // default
    private String cardId = "000000000" ;
    private String cardCode = "000" ;
    
    private static Card theCard = null ;
    
    /**
     * New Singleton
     * @return Singleton
     */
    public synchronized static Card getNewInstance() {
        theCard = new Card();
        return theCard;
    }
    
    /**
     * Current Singleton
     * @return Siingleton
     */
    public synchronized static Card getInstance() {
        if (theCard == null) {
            theCard = new Card();
            return theCard;
        }
        else
            return theCard;
    }

    /**
     * Prevent Construction
     * @return NULL
     */
    private Card() { }

    /**
     * Set the Card ID and Code
     * @param id   9-Digit Card Number
     * @param code 3-Digit Card Code
     */
    public void setCard(String id, String code)
    {

        try {

            StarbucksAPI api = new StarbucksAPI() ;
            cardId = id ;
            cardCode = code ;
            api.activateCard(cardId, cardCode) ; 
            StarbucksAPI.Card card = api.getCard(cardId) ; 
            balance = card.balance ;
            
        } catch (Exception e) {
            System.err.println( e ) ;
        }


    }

    /**
     * Get Card Number
     * @return Card Number
     */
    public String getCardId() {
        return cardId ;
    }
    
    /**
     * Get Card Code
     * @return Card Code
     */
    public String getCardCode() {
        return cardCode ;
    }
    
    /**
     * Get Card Balance
     * @return Card Balance in USD
     */
    public String getBalance() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(balance) ;        
        //return Float.toString(balance);
    }

    /**
     * Process a Card Payment
     */
    public void pay() {

        Device theDevice = Device.getInstance() ;
        String regid = theDevice.getProps("register") ;
        System.err.println( "Paying on Register: " + regid ) ;

        try {

            StarbucksAPI api = new StarbucksAPI() ;
            StarbucksAPI.Card card = api.payForOrder(regid, cardId) ;   
            this.balance = card.balance ;
            
        } catch (Exception e) {

            System.err.println( e ) ;
            
        }

    }


    /**
     * Log Card ID and Balance
     */
    public void display()
    {
        System.err.format( "Card ID: %s%n", cardId ) ;
        System.err.format( "Card Balance: $%4.2f%n", balance ) ;
    }
}
 


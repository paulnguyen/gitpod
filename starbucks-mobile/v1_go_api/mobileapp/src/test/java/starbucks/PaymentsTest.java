/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

import java.util.ArrayList ;
import java.text.NumberFormat ;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PaymentsTest
{

    IApp app ;
    Device device ;
    StarbucksTestAPI api ;
    String regid = "5012349" ;

    public PaymentsTest()
    {

    }

    @Before
    public void setUp()
    {
        app = (IApp) Device.getNewInstance("1234") ;
        device =  Device.getInstance() ;
        device.setProps( "apiurl", "http://localhost:3000" ) ;
        device.setProps( "apikey", "2742a237475c4703841a2bf906531eb0" ) ;
        device.setProps( "register", regid ) ;
        api = new StarbucksTestAPI() ;
    }

   private int getX(String number, int index) {
        int x = 0 ;
        char[] chars = number.toCharArray() ;
        char ch = chars[index] ;
        int num = Integer.parseInt(String.valueOf(ch)) ;
        switch (num) {
            case 0: x = 2 ; break ;
            case 1: x = 1 ; break ;
            case 2: x = 2 ; break ;
            case 3: x = 3 ; break ;
            case 4: x = 1 ; break ;
            case 5: x = 2 ; break ;
            case 6: x = 3 ; break ;
            case 7: x = 1 ; break ;
            case 8: x = 2 ; break ;
            case 9: x = 3 ; break ;
            }
        System.err.println( "Get X number: " + number + " [" + String.valueOf(index) + "] ==> " + String.valueOf(num) + " ( x =" + String.valueOf(x) + " ) " ) ;
        return x ;
    }

    private int getY(String number, int index) {
        int y = 0 ;
        char[] chars = number.toCharArray() ;
        char ch = chars[index] ;
        int num = Integer.parseInt(String.valueOf(ch)) ;
        switch (num) {
            case 0: y = 8 ; break ;
            case 1: y = 5 ; break ;
            case 2: y = 5 ; break ;
            case 3: y = 5 ; break ;
            case 4: y = 6 ; break ;
            case 5: y = 6 ; break ;
            case 6: y = 6 ; break ;
            case 7: y = 7 ; break ;
            case 8: y = 7 ; break ;
            case 9: y = 7 ; break ;
            }
        System.err.println( "Get Y number: " + number + " [" + String.valueOf(index) + "] ==> " + String.valueOf(num) + " ( y =" + String.valueOf(y) + " ) " ) ;
        return y ;
    }

    class Receipt {
        public double runningTotal = 0.00f ;
        public ArrayList<StarbucksTestAPI.Order> orders = new ArrayList(20) ;
          public String formatRunningTotal() {
              NumberFormat formatter = NumberFormat.getCurrencyInstance();
              return formatter.format( runningTotal ) ;
          }
        public void addOrder( StarbucksTestAPI.Order order ) {
            if (runningTotal + order.total <= 20.00f) {
                runningTotal += order.total ;
                orders.add( order ) ;
            }
        }
        public void clear() {
            orders.clear() ;
            runningTotal = 0.00f ;
        }
    }

    private String formatTotal(double total) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(total) ;
    }

    @Test
    public void PaymentsTest1()
    {
        String[] lines ;
        StarbucksTestAPI.Card card = null ;
        String cardId = "" ;
        String cardCode = "" ;

        try {
            card = api.newCard() ;
            System.err.println( card ) ;
        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        cardId = card.cardId ;
        app.touch( getX(cardId,0), getY(cardId,0) ) ;
        app.touch( getX(cardId,1), getY(cardId,1) ) ;
        app.touch( getX(cardId,2), getY(cardId,2) ) ;
        app.touch( getX(cardId,3), getY(cardId,3) ) ;
        app.touch( getX(cardId,4), getY(cardId,4) ) ;
        app.touch( getX(cardId,5), getY(cardId,5) ) ;
        app.touch( getX(cardId,6), getY(cardId,6) ) ;
        app.touch( getX(cardId,7), getY(cardId,7) ) ;
        app.touch( getX(cardId,8), getY(cardId,8) ) ;
        app.touch(2,3); // focus on card code
        // Card Code digits
        cardCode = card.cardCode ;
        app.touch( getX(cardCode,0), getY(cardCode,0) ) ;
        app.touch( getX(cardCode,1), getY(cardCode,1) ) ;
        app.touch( getX(cardCode,2), getY(cardCode,2) ) ;
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[4].trim());
        assertEquals("["+cardCode+"]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
        // switch to payment
        app.touch(3,3);
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        // New Register Order
        try { api.newOrder( regid ) ; } catch (Exception e) {}
        // Make Payments
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to balance
        // Get Card Balance & Validate it
        try { card = api.getCard(cardId) ; } catch (Exception e) {}
        String newbal = card.getFormatedBalance() ;
        lines = app.screenContents().split("\n");
        assertEquals(newbal, lines[7].trim());
    }



    @Test
    public void PaymentsTest2()
    {
        String[] lines ;
        StarbucksTestAPI.Card card = null ;
        String cardId = "" ;
        String cardCode = "" ;
        String cardbal = "" ;
        String newbal = "" ;
        Receipt receipt = new Receipt() ;

        try {
            card = api.newCard() ;
            System.err.println( card ) ;
        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        cardId = card.cardId ;
        app.touch( getX(cardId,0), getY(cardId,0) ) ;
        app.touch( getX(cardId,1), getY(cardId,1) ) ;
        app.touch( getX(cardId,2), getY(cardId,2) ) ;
        app.touch( getX(cardId,3), getY(cardId,3) ) ;
        app.touch( getX(cardId,4), getY(cardId,4) ) ;
        app.touch( getX(cardId,5), getY(cardId,5) ) ;
        app.touch( getX(cardId,6), getY(cardId,6) ) ;
        app.touch( getX(cardId,7), getY(cardId,7) ) ;
        app.touch( getX(cardId,8), getY(cardId,8) ) ;
        app.touch(2,3); // focus on card code
        // Card Code digits
        cardCode = card.cardCode ;
        app.touch( getX(cardCode,0), getY(cardCode,0) ) ;
        app.touch( getX(cardCode,1), getY(cardCode,1) ) ;
        app.touch( getX(cardCode,2), getY(cardCode,2) ) ;
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[4].trim());
        assertEquals("["+cardCode+"]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
        // switch to payment
        app.touch(3,3);
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        // Make payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Get Card Balance & Validate it
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(newbal, lines[7].trim());
        // Switch to payment
        app.touch(3,3);
        // Make payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(2,2);  // Pay $1.50
        // New Register Order
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(2,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Get Card Balance & Validate it
        try { card = api.getCard(cardId) ; } catch (Exception e) {}
        cardbal = card.getFormatedBalance() ;
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(cardbal, lines[7].trim());
        assertEquals(newbal, lines[7].trim());
    }

    @Test
    public void PaymentsTest3()
    {
        String[] lines ;
        StarbucksTestAPI.Card card = null ;
        String cardId = "" ;
        String cardCode = "" ;
        String newbal = "" ;
        String cardbal = "" ;
        Receipt receipt = new Receipt() ;

        try {
            card = api.newCard() ;
            System.err.println( card ) ;
        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        cardId = card.cardId ;
        app.touch( getX(cardId,0), getY(cardId,0) ) ;
        app.touch( getX(cardId,1), getY(cardId,1) ) ;
        app.touch( getX(cardId,2), getY(cardId,2) ) ;
        app.touch( getX(cardId,3), getY(cardId,3) ) ;
        app.touch( getX(cardId,4), getY(cardId,4) ) ;
        app.touch( getX(cardId,5), getY(cardId,5) ) ;
        app.touch( getX(cardId,6), getY(cardId,6) ) ;
        app.touch( getX(cardId,7), getY(cardId,7) ) ;
        app.touch( getX(cardId,8), getY(cardId,8) ) ;
        app.touch(2,3); // focus on card code
        // Card Code digits
        cardCode = card.cardCode ;
        app.touch( getX(cardCode,0), getY(cardCode,0) ) ;
        app.touch( getX(cardCode,1), getY(cardCode,1) ) ;
        app.touch( getX(cardCode,2), getY(cardCode,2) ) ;
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[4].trim());
        assertEquals("["+cardCode+"]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
        // switch to payment
        app.touch(3,3);
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        // Make payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // New register order
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Validate total cost of all orders
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(newbal, lines[7].trim());
        // Switch to payment
        app.touch(3,3);
        // New register order
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // New register order
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // New register order
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // New register order
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Get Card Balance & Validate it
        try { card = api.getCard(cardId) ; } catch (Exception e) {}
        cardbal = card.getFormatedBalance() ;
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(cardbal, lines[7].trim());
        assertEquals(newbal, lines[7].trim());
    }

    @Test
    public void PaymentsTest4()
    {
        String[] lines ;
        StarbucksTestAPI.Card card = null ;
        String cardId = "" ;
        String cardCode = "" ;
        String newbal = "" ;
        String cardbal = "" ;
        Receipt receipt = new Receipt() ;

        try {
            card = api.newCard() ;
            System.err.println( card ) ;
        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        cardId = card.cardId ;
        app.touch( getX(cardId,0), getY(cardId,0) ) ;
        app.touch( getX(cardId,1), getY(cardId,1) ) ;
        app.touch( getX(cardId,2), getY(cardId,2) ) ;
        app.touch( getX(cardId,3), getY(cardId,3) ) ;
        app.touch( getX(cardId,4), getY(cardId,4) ) ;
        app.touch( getX(cardId,5), getY(cardId,5) ) ;
        app.touch( getX(cardId,6), getY(cardId,6) ) ;
        app.touch( getX(cardId,7), getY(cardId,7) ) ;
        app.touch( getX(cardId,8), getY(cardId,8) ) ;
        app.touch(2,3); // focus on card code
        // Card Code digits
        cardCode = card.cardCode ;
        app.touch( getX(cardCode,0), getY(cardCode,0) ) ;
        app.touch( getX(cardCode,1), getY(cardCode,1) ) ;
        app.touch( getX(cardCode,2), getY(cardCode,2) ) ;
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[4].trim());
        assertEquals("["+cardCode+"]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
        // switch to payment
        app.touch(3,3);
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        // Make Payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Validate total cost of all orders
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(newbal, lines[7].trim());
        // Switch to payment
        app.touch(3,3);
        // Make Payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(2,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(2,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Get Card Balance & Validate it
        try { card = api.getCard(cardId) ; } catch (Exception e) {}
        cardbal = card.getFormatedBalance() ;
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(cardbal, lines[7].trim());
        assertEquals(newbal, lines[7].trim());
    }

    @Test
    public void PaymentsTest5()
    {
        String[] lines ;
        StarbucksTestAPI.Card card = null ;
        String cardId = "" ;
        String cardCode = "" ;
        String newbal = "" ;
        String cardbal = "" ;
        Receipt receipt = new Receipt() ;

        try {
            card = api.newCard() ;
            System.err.println( card ) ;
        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        cardId = card.cardId ;
        app.touch( getX(cardId,0), getY(cardId,0) ) ;
        app.touch( getX(cardId,1), getY(cardId,1) ) ;
        app.touch( getX(cardId,2), getY(cardId,2) ) ;
        app.touch( getX(cardId,3), getY(cardId,3) ) ;
        app.touch( getX(cardId,4), getY(cardId,4) ) ;
        app.touch( getX(cardId,5), getY(cardId,5) ) ;
        app.touch( getX(cardId,6), getY(cardId,6) ) ;
        app.touch( getX(cardId,7), getY(cardId,7) ) ;
        app.touch( getX(cardId,8), getY(cardId,8) ) ;
        app.touch(2,3); // focus on card code
        // Card Code digits
        cardCode = card.cardCode ;
        app.touch( getX(cardCode,0), getY(cardCode,0) ) ;
        app.touch( getX(cardCode,1), getY(cardCode,1) ) ;
        app.touch( getX(cardCode,2), getY(cardCode,2) ) ;
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[4].trim());
        assertEquals("["+cardCode+"]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
        // switch to payment
        app.touch(3,3);
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        // Make Payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Validate total cost of all orders
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(newbal, lines[7].trim());
        // Switch to payment
        app.touch(3,3);
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(2,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(2,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Validate total cost of all orders
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(newbal, lines[7].trim());
        // Switch to payment
        app.touch(3,3);
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(2,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Get Card Balance & Validate it
        try { card = api.getCard(cardId) ; } catch (Exception e) {}
        cardbal = card.getFormatedBalance() ;
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(cardbal, lines[7].trim());
        assertEquals(newbal, lines[7].trim());
    }

    @Test
    public void PaymentsTest6()
    {
        String[] lines ;
        StarbucksTestAPI.Card card = null ;
        String cardId = "" ;
        String cardCode = "" ;
        String newbal = "" ;
        String cardbal = "" ;
        Receipt receipt = new Receipt() ;

        try {
            card = api.newCard() ;
            System.err.println( card ) ;
        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        cardId = card.cardId ;
        app.touch( getX(cardId,0), getY(cardId,0) ) ;
        app.touch( getX(cardId,1), getY(cardId,1) ) ;
        app.touch( getX(cardId,2), getY(cardId,2) ) ;
        app.touch( getX(cardId,3), getY(cardId,3) ) ;
        app.touch( getX(cardId,4), getY(cardId,4) ) ;
        app.touch( getX(cardId,5), getY(cardId,5) ) ;
        app.touch( getX(cardId,6), getY(cardId,6) ) ;
        app.touch( getX(cardId,7), getY(cardId,7) ) ;
        app.touch( getX(cardId,8), getY(cardId,8) ) ;
        app.touch(2,3); // focus on card code
        // Card Code digits
        cardCode = card.cardCode ;
        app.touch( getX(cardCode,0), getY(cardCode,0) ) ;
        app.touch( getX(cardCode,1), getY(cardCode,1) ) ;
        app.touch( getX(cardCode,2), getY(cardCode,2) ) ;
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[4].trim());
        assertEquals("["+cardCode+"]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
        // switch to payment
        app.touch(3,3);
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        // Make Payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Validate balance
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(newbal, lines[7].trim());
        // Switch to payment
        app.touch(3,3);
        // Make payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(2,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(2,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Validate total cost of all orders
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(newbal, lines[7].trim());
        // Switch to payment
        app.touch(3,3);
        // Make payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(2,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Validate balance
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(newbal, lines[7].trim());
        // Switch to payment
        app.touch(3,3);
        // Make payment
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Get Card Balance & Validate it
        try { card = api.getCard(cardId) ; } catch (Exception e) {}
        cardbal = card.getFormatedBalance() ;
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(cardbal, lines[7].trim());
        assertEquals(newbal, lines[7].trim());
    }

    @Test
    public void PaymentsTest7()
    {
        String[] lines ;
        StarbucksTestAPI.Card card = null ;
        String cardId = "" ;
        String cardCode = "" ;
        String newbal = "" ;
        String cardbal = "" ;
        Receipt receipt = new Receipt() ;

        try {
            card = api.newCard() ;
            System.err.println( card ) ;
        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        cardId = card.cardId ;
        app.touch( getX(cardId,0), getY(cardId,0) ) ;
        app.touch( getX(cardId,1), getY(cardId,1) ) ;
        app.touch( getX(cardId,2), getY(cardId,2) ) ;
        app.touch( getX(cardId,3), getY(cardId,3) ) ;
        app.touch( getX(cardId,4), getY(cardId,4) ) ;
        app.touch( getX(cardId,5), getY(cardId,5) ) ;
        app.touch( getX(cardId,6), getY(cardId,6) ) ;
        app.touch( getX(cardId,7), getY(cardId,7) ) ;
        app.touch( getX(cardId,8), getY(cardId,8) ) ;
        app.touch(2,3); // focus on card code
        // Card Code digits
        cardCode = card.cardCode ;
        app.touch( getX(cardCode,0), getY(cardCode,0) ) ;
        app.touch( getX(cardCode,1), getY(cardCode,1) ) ;
        app.touch( getX(cardCode,2), getY(cardCode,2) ) ;
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[4].trim());
        assertEquals("["+cardCode+"]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
        // switch to payment
        app.touch(3,3);
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        // Make Payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Validate balance
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(newbal, lines[7].trim());
        // Switch to payment
        app.touch(3,3);
        // No Op Touches
        for ( int x = 1; x<=3; x++ )
            for ( int y = 4; y<=8; y++ )
                app.touch(x,y) ;
        // Switch to balance
        app.touch(3,3);
        // Get Card Balance & Validate it
        try { card = api.getCard(cardId) ; } catch (Exception e) {}
        cardbal = card.getFormatedBalance() ;
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(cardbal, lines[7].trim());
        assertEquals(newbal, lines[7].trim());
    }

    @Test
    public void PaymentsTest8()
    {
        String[] lines ;
        StarbucksTestAPI.Card card = null ;
        String cardId = "" ;
        String cardCode = "" ;
        String newbal = "" ;
        String cardbal = "" ;
        Receipt receipt = new Receipt() ;

        try {
            card = api.newCard() ;
            System.err.println( card ) ;
        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        cardId = card.cardId ;
        app.touch( getX(cardId,0), getY(cardId,0) ) ;
        app.touch( getX(cardId,1), getY(cardId,1) ) ;
        app.touch( getX(cardId,2), getY(cardId,2) ) ;
        app.touch( getX(cardId,3), getY(cardId,3) ) ;
        app.touch( getX(cardId,4), getY(cardId,4) ) ;
        app.touch( getX(cardId,5), getY(cardId,5) ) ;
        app.touch( getX(cardId,6), getY(cardId,6) ) ;
        app.touch( getX(cardId,7), getY(cardId,7) ) ;
        app.touch( getX(cardId,8), getY(cardId,8) ) ;
        app.touch(2,3); // focus on card code
        // Card Code digits
        cardCode = card.cardCode ;
        app.touch( getX(cardCode,0), getY(cardCode,0) ) ;
        app.touch( getX(cardCode,1), getY(cardCode,1) ) ;
        app.touch( getX(cardCode,2), getY(cardCode,2) ) ;
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[4].trim());
        assertEquals("["+cardCode+"]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
        // switch to payment
        app.touch(3,3);
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        // Make Payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Validate balance
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(newbal, lines[7].trim());
        // Switch to payment
        app.touch(3,3);
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(2,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(2,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Validate balance
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(newbal, lines[7].trim());
        // Switch to payment
        app.touch(3,3);
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(2,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Validate balance
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(newbal, lines[7].trim());
        // Switch to payment
        app.touch(3,3);
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Get Card Balance & Validate it
        try { card = api.getCard(cardId) ; } catch (Exception e) {}
        cardbal = card.getFormatedBalance() ;
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(cardbal, lines[7].trim());
        assertEquals(newbal, lines[7].trim());
        // Add New Card
        try {
            card = api.newCard() ;
            System.err.println( card ) ;
        } catch (Exception e) {
            System.out.println(e);
        }
        // reset running total
        receipt.clear();
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        cardId = card.cardId ;
        app.touch( getX(cardId,0), getY(cardId,0) ) ;
        app.touch( getX(cardId,1), getY(cardId,1) ) ;
        app.touch( getX(cardId,2), getY(cardId,2) ) ;
        app.touch( getX(cardId,3), getY(cardId,3) ) ;
        app.touch( getX(cardId,4), getY(cardId,4) ) ;
        app.touch( getX(cardId,5), getY(cardId,5) ) ;
        app.touch( getX(cardId,6), getY(cardId,6) ) ;
        app.touch( getX(cardId,7), getY(cardId,7) ) ;
        app.touch( getX(cardId,8), getY(cardId,8) ) ;
        app.touch(2,3); // focus on card code
        // Card Code digits
        cardCode = card.cardCode ;
        app.touch( getX(cardCode,0), getY(cardCode,0) ) ;
        app.touch( getX(cardCode,1), getY(cardCode,1) ) ;
        app.touch( getX(cardCode,2), getY(cardCode,2) ) ;
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[4].trim());
        assertEquals("["+cardCode+"]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
    }

    @Test
    public void PaymentsTest9()
    {
        String[] lines ;
        StarbucksTestAPI.Card card = null ;
        String cardId = "" ;
        String cardCode = "" ;
        String newbal = "" ;
        String cardbal = "" ;
        Receipt receipt = new Receipt() ;

        try {
            card = api.newCard() ;
            System.err.println( card ) ;
        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        cardId = card.cardId ;
        app.touch( getX(cardId,0), getY(cardId,0) ) ;
        app.touch( getX(cardId,1), getY(cardId,1) ) ;
        app.touch( getX(cardId,2), getY(cardId,2) ) ;
        app.touch( getX(cardId,3), getY(cardId,3) ) ;
        app.touch( getX(cardId,4), getY(cardId,4) ) ;
        app.touch( getX(cardId,5), getY(cardId,5) ) ;
        app.touch( getX(cardId,6), getY(cardId,6) ) ;
        app.touch( getX(cardId,7), getY(cardId,7) ) ;
        app.touch( getX(cardId,8), getY(cardId,8) ) ;
        app.touch(2,3); // focus on card code
        // Card Code digits
        cardCode = card.cardCode ;
        app.touch( getX(cardCode,0), getY(cardCode,0) ) ;
        app.touch( getX(cardCode,1), getY(cardCode,1) ) ;
        app.touch( getX(cardCode,2), getY(cardCode,2) ) ;
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[4].trim());
        assertEquals("["+cardCode+"]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
        // switch to payment
        app.touch(3,3);
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        // Make Payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Get Card Balance & Validate it
        try { card = api.getCard(cardId) ; } catch (Exception e) {}
        cardbal = card.getFormatedBalance() ;
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(cardbal, lines[7].trim());
        assertEquals(newbal, lines[7].trim());

        // Second Time
        try {
            card = api.newCard() ;
            System.err.println( card ) ;
        } catch (Exception e) {
            System.out.println(e);
        }
        // reset running total
        receipt.clear() ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        cardId = card.cardId ;
        app.touch( getX(cardId,0), getY(cardId,0) ) ;
        app.touch( getX(cardId,1), getY(cardId,1) ) ;
        app.touch( getX(cardId,2), getY(cardId,2) ) ;
        app.touch( getX(cardId,3), getY(cardId,3) ) ;
        app.touch( getX(cardId,4), getY(cardId,4) ) ;
        app.touch( getX(cardId,5), getY(cardId,5) ) ;
        app.touch( getX(cardId,6), getY(cardId,6) ) ;
        app.touch( getX(cardId,7), getY(cardId,7) ) ;
        app.touch( getX(cardId,8), getY(cardId,8) ) ;
        app.touch(2,3); // focus on card code
        // Card Code digits
        cardCode = card.cardCode ;
        app.touch( getX(cardCode,0), getY(cardCode,0) ) ;
        app.touch( getX(cardCode,1), getY(cardCode,1) ) ;
        app.touch( getX(cardCode,2), getY(cardCode,2) ) ;
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[4].trim());
        assertEquals("["+cardCode+"]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
        // switch to payment
        app.touch(3,3);
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        // Make Payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Get Card Balance & Validate it
        try { card = api.getCard(cardId) ; } catch (Exception e) {}
        cardbal = card.getFormatedBalance() ;
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(cardbal, lines[7].trim());
        assertEquals(newbal, lines[7].trim());
    }


    @Test
    public void PaymentsTest10()
    {
        String[] lines ;
        StarbucksTestAPI.Card card = null ;
        String cardId = "" ;
        String cardCode = "" ;
        String newbal = "" ;
        String cardbal = "" ;
        Receipt receipt = new Receipt() ;

        try {
            card = api.newCard() ;
            System.err.println( card ) ;
        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        cardId = card.cardId ;
        app.touch( getX(cardId,0), getY(cardId,0) ) ;
        app.touch( getX(cardId,1), getY(cardId,1) ) ;
        app.touch( getX(cardId,2), getY(cardId,2) ) ;
        app.touch( getX(cardId,3), getY(cardId,3) ) ;
        app.touch( getX(cardId,4), getY(cardId,4) ) ;
        app.touch( getX(cardId,5), getY(cardId,5) ) ;
        app.touch( getX(cardId,6), getY(cardId,6) ) ;
        app.touch( getX(cardId,7), getY(cardId,7) ) ;
        app.touch( getX(cardId,8), getY(cardId,8) ) ;
        app.touch(2,3); // focus on card code
        // Card Code digits
        cardCode = card.cardCode ;
        app.touch( getX(cardCode,0), getY(cardCode,0) ) ;
        app.touch( getX(cardCode,1), getY(cardCode,1) ) ;
        app.touch( getX(cardCode,2), getY(cardCode,2) ) ;
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[4].trim());
        assertEquals("["+cardCode+"]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
        // switch to payment
        app.touch(3,3);
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        // Make Payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Get Card Balance & Validate it
        try { card = api.getCard(cardId) ; } catch (Exception e) {}
        cardbal = card.getFormatedBalance() ;
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(cardbal, lines[7].trim());
        assertEquals(newbal, lines[7].trim());

        // Second Time
        try {
            card = api.newCard() ;
            System.err.println( card ) ;
        } catch (Exception e) {
            System.out.println(e);
        }
        // reset running total
        receipt.clear() ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        cardId = card.cardId ;
        app.touch( getX(cardId,0), getY(cardId,0) ) ;
        app.touch( getX(cardId,1), getY(cardId,1) ) ;
        app.touch( getX(cardId,2), getY(cardId,2) ) ;
        app.touch( getX(cardId,3), getY(cardId,3) ) ;
        app.touch( getX(cardId,4), getY(cardId,4) ) ;
        app.touch( getX(cardId,5), getY(cardId,5) ) ;
        app.touch( getX(cardId,6), getY(cardId,6) ) ;
        app.touch( getX(cardId,7), getY(cardId,7) ) ;
        app.touch( getX(cardId,8), getY(cardId,8) ) ;
        app.touch(2,3); // focus on card code
        // Card Code digits
        cardCode = card.cardCode ;
        app.touch( getX(cardCode,0), getY(cardCode,0) ) ;
        app.touch( getX(cardCode,1), getY(cardCode,1) ) ;
        app.touch( getX(cardCode,2), getY(cardCode,2) ) ;
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[4].trim());
        assertEquals("["+cardCode+"]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
        // switch to payment
        app.touch(3,3);
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        // Make Payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Get Card Balance & Validate it
        try { card = api.getCard(cardId) ; } catch (Exception e) {}
        cardbal = card.getFormatedBalance() ;
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(cardbal, lines[7].trim());
        assertEquals(newbal, lines[7].trim());

        // Third Time
        try {
            card = api.newCard() ;
            System.err.println( card ) ;
        } catch (Exception e) {
            System.out.println(e);
        }
        // reset running total
        receipt.clear() ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        cardId = card.cardId ;
        app.touch( getX(cardId,0), getY(cardId,0) ) ;
        app.touch( getX(cardId,1), getY(cardId,1) ) ;
        app.touch( getX(cardId,2), getY(cardId,2) ) ;
        app.touch( getX(cardId,3), getY(cardId,3) ) ;
        app.touch( getX(cardId,4), getY(cardId,4) ) ;
        app.touch( getX(cardId,5), getY(cardId,5) ) ;
        app.touch( getX(cardId,6), getY(cardId,6) ) ;
        app.touch( getX(cardId,7), getY(cardId,7) ) ;
        app.touch( getX(cardId,8), getY(cardId,8) ) ;
        app.touch(2,3); // focus on card code
        // Card Code digits
        cardCode = card.cardCode ;
        app.touch( getX(cardCode,0), getY(cardCode,0) ) ;
        app.touch( getX(cardCode,1), getY(cardCode,1) ) ;
        app.touch( getX(cardCode,2), getY(cardCode,2) ) ;
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[4].trim());
        assertEquals("["+cardCode+"]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
        // switch to payment
        app.touch(3,3);
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        // Make Payments
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        try { receipt.addOrder( api.newOrder( regid ) ) ; } catch (Exception e) {}
        app.touch(3,2);  // Pay $1.50
        // Switch to balance
        app.touch(3,3);
        // Get Card Balance & Validate it
        try { card = api.getCard(cardId) ; } catch (Exception e) {}
        cardbal = card.getFormatedBalance() ;
        newbal = formatTotal( 20.00f - receipt.runningTotal ) ;
        lines = app.screenContents().split("\n");
        assertEquals(cardbal, lines[7].trim());
        assertEquals(newbal, lines[7].trim());

    }

    @Test
    public void PaymentsTest11()
    {
        String[] lines ;
        StarbucksTestAPI.Card card = null ;
        String cardId = "" ;
        String cardCode = "" ;

        try {
            card = api.newCard() ;
            System.err.println( card ) ;
        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        cardId = card.cardId ;
        app.touch( getX(cardId,0), getY(cardId,0) ) ;
        app.touch( getX(cardId,1), getY(cardId,1) ) ;
        app.touch( getX(cardId,2), getY(cardId,2) ) ;
        app.touch( getX(cardId,3), getY(cardId,3) ) ;
        app.touch( getX(cardId,4), getY(cardId,4) ) ;
        app.touch( getX(cardId,5), getY(cardId,5) ) ;
        app.touch( getX(cardId,6), getY(cardId,6) ) ;
        app.touch( getX(cardId,7), getY(cardId,7) ) ;
        app.touch( getX(cardId,8), getY(cardId,8) ) ;
        app.touch(2,3); // focus on card code
        // Card Code digits
        cardCode = card.cardCode ;
        app.touch( getX(cardCode,0), getY(cardCode,0) ) ;
        app.touch( getX(cardCode,1), getY(cardCode,1) ) ;
        app.touch( getX(cardCode,2), getY(cardCode,2) ) ;
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("["+cardId+"]", lines[4].trim());
        assertEquals("["+cardCode+"]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
        // New Order
        try { api.newOrder( regid ) ; } catch (Exception e) {}
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to payment
        app.touch(3,3); // switch to balance
        app.touch(3,3); // switch to payment
        app.touch(3,3); // switch to balance
        lines = app.screenContents().split("\n");
        assertEquals("$20.00", lines[7].trim());
    }


    @After
    public void tearDown()
    {
        api.clearOrders() ;
        api.clearCards() ;
    }
}

/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

import java.util.ArrayList ;
import java.util.HashSet ;
import java.text.NumberFormat ;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CashRegisterTest
{

    IApp app ;
    Device device ;
    StarbucksTestAPI api ;
    String regid = "5012349" ;

    class Register {

        public int x ;
        public int y ;
        public String regid ;

        public Register( int x, int y, String regid )
        {
            this.x = x ;
            this.y = y ;
            this.regid = regid ;
        }
    }

    ArrayList<Register> registers = new ArrayList<Register>();

    public CashRegisterTest()
    {
        Register dubc = new Register(3,2,"5012349") ;
        Register ptown = new Register(3,7,"1287612") ;
        Register city = new Register(1,3,"6498234") ;
        Register eso = new Register(2,4,"7812386") ;
        Register dro = new Register(2,5,"8723098") ;
        Register mateo = new Register(1,6,"9621043") ;
        Register deadwood = new Register(1,7,"1393478") ;

        registers.add(dubc) ;
        registers.add(ptown) ;
        registers.add(city) ;
        registers.add(eso) ;
        registers.add(dro) ;
        registers.add(mateo) ;
        registers.add(deadwood) ;
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

    @After
    public void tearDown()
    {
        api.clearOrders() ;
        api.clearCards() ;
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

    private String formatTotal(double total) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(total) ;
    }

    @Test
    public void CashRegisterTest1()
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
        app.touch(1,5) ; // 1
        app.touch(2,5) ; // 2
        app.touch(3,5) ; // 3
        app.touch(1,6) ; // 4
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
        // loop thru all regisgters
        for (Register r : registers) {
            // go to find store screen
            app.execute("D") ;
            // select a store
            app.touch(r.x, r.y) ;
            // New Register Order
            try { api.newOrder( r.regid ) ; } catch (Exception e) {}
            // Make Payments
            app.touch(2,2); // Pay for Order
            app.touch(3,3); // switch to balance
            // Get Card Balance & Validate it
            try { card = api.getCard(cardId) ; } catch (Exception e) {}
            String newbal = card.getFormatedBalance() ;
            lines = app.screenContents().split("\n");
            assertEquals(newbal, lines[7].trim());
        }
    }

    @Test
    public void CashRegisterTest2() {

      String[] lines ;
      StarbucksTestAPI.Card card = null ;
      String cardId = "" ;
      String cardCode = "" ;
      StarbucksTestAPI.Order order = null ;

      try {
         card = api.newCard() ;
         System.err.println( card ) ;
      } catch (Exception e) {
         System.out.println(e);
      }

      assertEquals("", app.screen().trim());
      app.touch(1,5) ; // 1
      app.touch(2,5) ; // 2
      app.touch(3,5) ; // 3
      app.touch(1,6) ; // 4
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
      // go to find store screen
      app.execute("D") ;
      // Create set of starbucks store touch locations
      HashSet<Integer> stores = new HashSet<Integer>();
      for (Register r : registers) {
        stores.add(r.x*10 + r.y) ;
      }
      // No op touches
      for ( int x = 1; x<=3; x++ )
        for ( int y = 1; y<=9; y++ )
          if ( !stores.contains(x*10 + y) )
            app.touch(x, y) ;
      assertEquals("Find Store", app.screen().trim());
      // go to my cards screen
      app.execute("A") ;
      assertEquals("My Cards", app.screen().trim());
      lines = app.screenContents().split("\n");
      assertEquals("$20.00", lines[7].trim());
    }

    @Test
    public void CashRegisterTest3() {

      String[] lines ;
      StarbucksTestAPI.Card card = null ;
      String cardId = "" ;
      String cardCode = "" ;
      StarbucksTestAPI.Order order = null ;

      try {
          card = api.newCard() ;
          System.err.println( card ) ;
      } catch (Exception e) {
          System.out.println(e);
      }

      assertEquals("", app.screen().trim());
      app.touch(1,5) ; // 1
      app.touch(2,5) ; // 2
      app.touch(3,5) ; // 3
      app.touch(1,6) ; // 4
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
      // loop thru all regisgters
      for (Register r : registers) {
          // go to find store screen
          app.execute("D") ;
          // select a store
          app.touch(r.x, r.y) ;
          // New Register Order
          try { api.newOrder( r.regid ) ; } catch (Exception e) {}
      }
      // go to find store screen
      app.execute("D") ;
      // select a store
      Register dubc = registers.get(0);
      app.touch(dubc.x, dubc.y) ;
      // Get order price
      try { order = api.getOrder( dubc.regid ); } catch (Exception e) {}
      // Make Payments
      app.touch(2,2); // Pay for Order
      app.touch(3,3); // switch to balance
      // Get Card Balance & Validate it
      lines = app.screenContents().split("\n");
      assertEquals(formatTotal(20.00f - order.total), lines[7].trim());
    }

    @Test
    public void CashRegisterTest4() {

      String[] lines ;
      StarbucksTestAPI.Card card = null ;
      String cardId = "" ;
      String cardCode = "" ;
      StarbucksTestAPI.Order order = null ;

      try {
          card = api.newCard() ;
          System.err.println( card ) ;
      } catch (Exception e) {
          System.out.println(e);
      }

      assertEquals("", app.screen().trim());
      app.touch(1,5) ; // 1
      app.touch(2,5) ; // 2
      app.touch(3,5) ; // 3
      app.touch(1,6) ; // 4
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
      // loop thru all regisgters
      for (Register r : registers) {
          // go to find store screen
          app.execute("D") ;
          // select a store
          app.touch(r.x, r.y) ;
          // New Register Order
          try { api.newOrder( r.regid ) ; } catch (Exception e) {}
      }
      // go to find store screen
      app.execute("D") ;
      // select a store
      Register ptown = registers.get(1);
      app.touch(ptown.x, ptown.y) ;
      // Get order price
      try { order = api.getOrder( ptown.regid ); } catch (Exception e) {}
      // Make Payments
      app.touch(2,2); // Pay for Order
      app.touch(3,3); // switch to balance
      // Get Card Balance & Validate it
      lines = app.screenContents().split("\n");
      assertEquals(formatTotal(20.00f - order.total), lines[7].trim());
    }

    @Test
    public void CashRegisterTest5() {

      String[] lines ;
      StarbucksTestAPI.Card card = null ;
      String cardId = "" ;
      String cardCode = "" ;
      StarbucksTestAPI.Order order = null ;

      try {
          card = api.newCard() ;
          System.err.println( card ) ;
      } catch (Exception e) {
          System.out.println(e);
      }

      assertEquals("", app.screen().trim());
      app.touch(1,5) ; // 1
      app.touch(2,5) ; // 2
      app.touch(3,5) ; // 3
      app.touch(1,6) ; // 4
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
      // loop thru all regisgters
      for (Register r : registers) {
          // go to find store screen
          app.execute("D") ;
          // select a store
          app.touch(r.x, r.y) ;
          // New Register Order
          try { api.newOrder( r.regid ) ; } catch (Exception e) {}
      }
      // go to find store screen
      app.execute("D") ;
      // select a store
      Register city = registers.get(2);
      app.touch(city.x, city.y) ;
      // Get order price
      try { order = api.getOrder( city.regid ); } catch (Exception e) {}
      // Make Payments
      app.touch(2,2); // Pay for Order
      app.touch(3,3); // switch to balance
      // Get Card Balance & Validate it
      lines = app.screenContents().split("\n");
      assertEquals(formatTotal(20.00f - order.total), lines[7].trim());
    }

    @Test
    public void CashRegisterTest6() {

      String[] lines ;
      StarbucksTestAPI.Card card = null ;
      String cardId = "" ;
      String cardCode = "" ;
      StarbucksTestAPI.Order order = null ;

      try {
          card = api.newCard() ;
          System.err.println( card ) ;
      } catch (Exception e) {
          System.out.println(e);
      }

      assertEquals("", app.screen().trim());
      app.touch(1,5) ; // 1
      app.touch(2,5) ; // 2
      app.touch(3,5) ; // 3
      app.touch(1,6) ; // 4
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
      // loop thru all regisgters
      for (Register r : registers) {
          // go to find store screen
          app.execute("D") ;
          // select a store
          app.touch(r.x, r.y) ;
          // New Register Order
          try { api.newOrder( r.regid ) ; } catch (Exception e) {}
      }
      // go to find store screen
      app.execute("D") ;
      // select a store
      Register eso = registers.get(3);
      app.touch(eso.x, eso.y) ;
      // Get order price
      try { order = api.getOrder( eso.regid ); } catch (Exception e) {}
      // Make Payments
      app.touch(2,2); // Pay for Order
      app.touch(3,3); // switch to balance
      // Get Card Balance & Validate it
      lines = app.screenContents().split("\n");
      assertEquals(formatTotal(20.00f - order.total), lines[7].trim());
    }

    @Test
    public void CashRegisterTest7() {

      String[] lines ;
      StarbucksTestAPI.Card card = null ;
      String cardId = "" ;
      String cardCode = "" ;
      StarbucksTestAPI.Order order = null ;

      try {
          card = api.newCard() ;
          System.err.println( card ) ;
      } catch (Exception e) {
          System.out.println(e);
      }

      assertEquals("", app.screen().trim());
      app.touch(1,5) ; // 1
      app.touch(2,5) ; // 2
      app.touch(3,5) ; // 3
      app.touch(1,6) ; // 4
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
      // loop thru all regisgters
      for (Register r : registers) {
          // go to find store screen
          app.execute("D") ;
          // select a store
          app.touch(r.x, r.y) ;
          // New Register Order
          try { api.newOrder( r.regid ) ; } catch (Exception e) {}
      }
      // go to find store screen
      app.execute("D") ;
      // select a store
      Register dro = registers.get(4);
      app.touch(dro.x, dro.y) ;
      // Get order price
      try { order = api.getOrder( dro.regid ); } catch (Exception e) {}
      // Make Payments
      app.touch(2,2); // Pay for Order
      app.touch(3,3); // switch to balance
      // Get Card Balance & Validate it
      lines = app.screenContents().split("\n");
      assertEquals(formatTotal(20.00f - order.total), lines[7].trim());
    }

    @Test
    public void CashRegisterTest8() {

      String[] lines ;
      StarbucksTestAPI.Card card = null ;
      String cardId = "" ;
      String cardCode = "" ;
      StarbucksTestAPI.Order order = null ;

      try {
          card = api.newCard() ;
          System.err.println( card ) ;
      } catch (Exception e) {
          System.out.println(e);
      }

      assertEquals("", app.screen().trim());
      app.touch(1,5) ; // 1
      app.touch(2,5) ; // 2
      app.touch(3,5) ; // 3
      app.touch(1,6) ; // 4
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
      // loop thru all regisgters
      for (Register r : registers) {
          // go to find store screen
          app.execute("D") ;
          // select a store
          app.touch(r.x, r.y) ;
          // New Register Order
          try { api.newOrder( r.regid ) ; } catch (Exception e) {}
      }
      // go to find store screen
      app.execute("D") ;
      // select a store
      Register mateo = registers.get(5);
      app.touch(mateo.x, mateo.y) ;
      // Get order price
      try { order = api.getOrder( mateo.regid ); } catch (Exception e) {}
      // Make Payments
      app.touch(2,2); // Pay for Order
      app.touch(3,3); // switch to balance
      // Get Card Balance & Validate it
      lines = app.screenContents().split("\n");
      assertEquals(formatTotal(20.00f - order.total), lines[7].trim());
    }

    @Test
    public void CashRegisterTest9() {

      String[] lines ;
      StarbucksTestAPI.Card card = null ;
      String cardId = "" ;
      String cardCode = "" ;
      StarbucksTestAPI.Order order = null ;

      try {
          card = api.newCard() ;
          System.err.println( card ) ;
      } catch (Exception e) {
          System.out.println(e);
      }

      assertEquals("", app.screen().trim());
      app.touch(1,5) ; // 1
      app.touch(2,5) ; // 2
      app.touch(3,5) ; // 3
      app.touch(1,6) ; // 4
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
      // loop thru all regisgters
      for (Register r : registers) {
          // go to find store screen
          app.execute("D") ;
          // select a store
          app.touch(r.x, r.y) ;
          // New Register Order
          try { api.newOrder( r.regid ) ; } catch (Exception e) {}
      }
      // go to find store screen
      app.execute("D") ;
      // select a store
      Register deadwood = registers.get(6);
      app.touch(deadwood.x, deadwood.y) ;
      // Get order price
      try { order = api.getOrder( deadwood.regid ); } catch (Exception e) {}
      // Make Payments
      app.touch(2,2); // Pay for Order
      app.touch(3,3); // switch to balance
      // Get Card Balance & Validate it
      lines = app.screenContents().split("\n");
      assertEquals(formatTotal(20.00f - order.total), lines[7].trim());
    }

    @Test
    public void CashRegisterTest10() {
      String[] lines ;
      StarbucksTestAPI.Card card = null ;
      String cardId = "" ;
      String cardCode = "" ;
      StarbucksTestAPI.Order order = null ;
      double cardbal = 20.00f ;

      try {
          card = api.newCard() ;
          System.err.println( card ) ;
      } catch (Exception e) {
          System.out.println(e);
      }

      assertEquals("", app.screen().trim());
      app.touch(1,5) ; // 1
      app.touch(2,5) ; // 2
      app.touch(3,5) ; // 3
      app.touch(1,6) ; // 4
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
      // loop thru all regisgters and create an order
      for (Register r : registers) {
          // go to find store screen
          app.execute("D") ;
          // select a store
          app.touch(r.x, r.y) ;
          // New Register Order
          try { api.newOrder( r.regid ) ; } catch (Exception e) {}
      }
      // loop through all registers and pay
      for (int i : new int[]{0,6,2,5,4,1,3}) {
        // Grab the register at index i
        Register r = registers.get(i);
        // go to find store screen
        app.execute("D") ;
        // select a store
        app.touch(r.x, r.y) ;
        // Get Register Order
        try { order = api.getOrder( r.regid ) ; } catch (Exception e) {}
        // Make Payments
        app.touch(2,2); // Pay for Order
        app.touch(3,3); // switch to balance
        // Get Card Balance & Validate it
        cardbal -= order.total;
        lines = app.screenContents().split("\n");
        assertEquals(formatTotal(cardbal), lines[7].trim());
      }
    }
}

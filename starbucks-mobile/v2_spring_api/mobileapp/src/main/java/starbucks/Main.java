/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

import java.io.Console;

/**
 * Main Entry Point.
 */
final class Main {

    /**
     * Prevent Construction.
     */
    private Main() {
        // Utility Class
        return ;
    }

    /**
     * Main App Entry Point.
     * @param args No args expected.
     */
    public static void main(final String[] args) {
        StarbucksAPI api = new StarbucksAPI() ;
        System.err.println( "Args: " + args ) ;
        Device d = Device.getNewInstance("1234") ;
        IApp app = (IApp) d ;
        Console c = System.console();
        String msg = "" ;
        for (;;) {
            System.out.print("\033[H\033[2J") ; // clear the screen
            System.out.flush() ;
            System.out.println(app.screenContents()) ;
            System.out.println( msg ) ;
            System.out.print("=> ") ;
            String ch = c.readLine() ;       // get user command
            String cmd = ch.toLowerCase() ;  // convert to lower case
            String cmdsave = cmd ;
            cmd = cmd.replaceAll("\\s","") ; // remove all whitespaces
                   
            /* process commands */
            msg = cmd ;
            if ( cmd.startsWith("touch") ) {
                String parms = cmd.replaceFirst("touch", "") ;
                parms = parms.substring(1) ;
                parms = parms.substring(0, parms.length() - 1) ;
                String[] values = parms.split(",") ;
                System.err.println( "Value: " + values ) ;
                String x = values[0] ;
                String y = values[1] ;
                msg = "touch: x="+x + " y="+y ; 
                app.touch( Integer.parseInt(x), Integer.parseInt(y) ) ;
            } else if ( cmd.equals("a") || cmd.equals("b") 
                        || cmd.equals("c") || cmd.equals("d")
                        || cmd.equals("e")
                ) {
                String selection = cmd.toUpperCase() ;
                msg = "selected: " + selection ;
                app.execute( selection ) ;

            } else if ( cmd.startsWith("prev") ) {
                msg = "cmd: previous" ;
                app.prev() ;
            } else if ( cmd.startsWith("next") ) {
                msg = "cmd: next" ;
                app.next() ;
            } else if (cmd.equalsIgnoreCase( "portrait" ) || cmd.equalsIgnoreCase( "p" )) {
                app.portrait() ;
            } else if (cmd.equalsIgnoreCase( "landscape" ) || cmd.equalsIgnoreCase( "l" )) {
                app.landscape() ;
            } else if ( cmd.startsWith("login6") ) {
                app.touch(1,5) ;  // 1
                app.touch(2,5) ;  // 2
                app.touch(3,5) ;  // 3
                app.touch(1,6) ;  // 4
                app.touch(2,6) ;  // 5
                app.touch(3,6) ;  // 6                
            } else if ( cmd.startsWith("login4") ) {
                app.touch(1,5) ;  // 1
                app.touch(2,5) ;  // 2
                app.touch(3,5) ;  // 3
                app.touch(1,6) ;  // 4
            } else if ( cmd.startsWith("payment") ) {
                app.touch(1,5) ;  // 1
                app.touch(2,5) ;  // 2
                app.touch(3,5) ;  // 3
                app.touch(1,6) ;  // 4
                app.execute("E") ; // Settings Page
                app.touch(1,1) ; // Add New Card
                // Card Id digits - 123456789
                app.touch(1,5); 
                app.touch(2,5);
                app.touch(3,5);
                app.touch(1,6);
                app.touch(2,6);
                app.touch(3,6);
                app.touch(1,7);
                app.touch(2,7);
                app.touch(3,7);
                app.touch(2,3); // focus on card code
                // Card Code digits - 999
                app.touch(3,7);
                app.touch(3,7);
                app.touch(3,7);
                app.next() ;    // add card - see balance
                app.touch(3,3); // switch to payment    
            } else if ( cmd.equals("ping") ) {
                api.ping() ;
            } else if ( cmd.equals("getcards") ) {
                api.getCards() ;
            } else if ( cmd.equals("getorders") ) {
                api.getOrders() ;
            } else if ( cmd.equals("clearorders") ) {
                api.clearOrders() ;
            } else if ( cmd.equals("clearcards") ) {
                api.clearCards() ;
             } else if ( cmd.equals("newcard") ) {
                try {
                    StarbucksAPI.Card card = api.newCard() ;    
                    System.err.println( card ) ;
                } catch (Exception e) { System.out.println(e); }
            } else if ( cmd.startsWith("getcard") ) {
                String[] values = cmdsave.split(" ") ; 
                String cardnum = values[1] ;
                msg = cmdsave ;
                try {
                    StarbucksAPI.Card card = api.getCard(cardnum) ;    
                    System.err.println( card ) ;
                } catch (Exception e) { System.out.println(e); }
            } else if ( cmd.startsWith("activate") ) {
                String[] values = cmdsave.split(" ") ; 
                String cardnum = values[1] ;
                String cardcode = values[2] ;
                msg = cmdsave ;
                try {
                    api.activateCard(cardnum, cardcode) ;    
                } catch (Exception e) { System.out.println(e); }
            } else if ( cmd.startsWith("neworder") ) {
                String[] values = cmdsave.split(" ") ; 
                String regid = values[1] ;  
                msg = cmdsave ;              
                api.newOrder( regid ) ;
            } else if ( cmd.startsWith("getorder") ) {
                String[] values = cmdsave.split(" ") ; 
                String regid = values[1] ;  
                msg = cmdsave ;              
                api.getOrder( regid ) ;
            } else if ( cmd.startsWith("clearorder") ) {
                String[] values = cmdsave.split(" ") ; 
                String regid = values[1] ;  
                msg = cmdsave ;              
                api.clearOrder( regid ) ;
            } else if ( cmd.startsWith("pay") ) {
                String[] values = cmdsave.split(" ") ; 
                String regid = values[1] ;
                String cardnum = values[2] ;
                msg = cmdsave ;
                try {
                    StarbucksAPI.Card card = api.payForOrder(regid, cardnum) ;   
                    System.err.println( card ) ; 
                } catch (Exception e) { System.out.println(e); }
             } else {
                msg = "" ;  
            }
        }
    }
}



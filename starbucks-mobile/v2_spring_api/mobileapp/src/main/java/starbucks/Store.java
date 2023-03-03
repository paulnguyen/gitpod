/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

/** Store Screen */
public class Store extends Screen {

    /**
     * Constructor
     */
    public Store() {

    }

    /**
     * Return Screen Contents
     * @return Map Screen Sample Content
     */
    public String display() {
        return "         X\n   X\n       X\n      X\n  X\n           X\n  X" ;
    }

    /**
     * Handle Touch Event -- Not Used.
     * @param x Touch X
     * @param y Touch Y
     */
    public void touch(int x, int y) {
        System.err.println( "X: " + x + " Y: " + y ) ;

        /*
            Stores      Touch Cords       Register   
            ======      ===========       ========
            Dub-C       touch(3,2)        5012349         
            P-Town      touch(3,7)        1287612         
            The City    touch(1,3)        6498234         
            Eso         touch(2,4)        7812386         
            The Dro     touch(2,5)        8723098         
            Mateo       touch(1,6)        9621043         
            Deadwood    touch(1,7)        1393478                 
         */
        
        String regid = "" ;

        if ( x == 3 && y == 2 ) regid = "5012349" ; // Dub-C
        if ( x == 3 && y == 7 ) regid = "1287612" ; // P-Town 
        if ( x == 1 && y == 3 ) regid = "6498234" ; // The City
        if ( x == 2 && y == 4 ) regid = "7812386" ; // Eso
        if ( x == 2 && y == 5 ) regid = "8723098" ; // The Dro
        if ( x == 1 && y == 6 ) regid = "9621043" ; // Mateo
        if ( x == 1 && y == 7 ) regid = "1393478" ; // Deadwood

        if ( !regid.equals("") ) {
            Device theDevice = Device.getInstance() ;
            AppController app = AppController.getInstance() ;
            theDevice.setProps( "register", regid ) ;
            System.err.println( "Selected Register: " + regid ) ;
            app.setScreen( AppController.SCREENS.MY_CARDS_PAY ) ;         
        }
 

    }

}

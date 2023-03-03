/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

/** Settings Screen */
public class Settings extends Screen
{
   
    /** Constructor */
    public Settings() {}

    /**
     * Return Screen Contents
     * @return Screen Contents
     */
    public String display() {
        String result = "Add Card\nDelete Card\nBilling\nPasscode\n\nAbout|Terms\nHelp" ;
        return result ;
    }

    /**
     * Handle Touch Events.
     * Mainly to Add a New Card.
     * 
     * @param x Touch X
     * @param y Touch Y
     */
   public void touch(int x, int y) {
        AppController app = AppController.getInstance() ;
        if ( (x == 1 && y == 1) ||
             (x == 2 && y == 1) ||
             (x == 3 && y == 1 )
            ) {
            app.setScreen( AppController.SCREENS.ADD_CARD ) ;
        }            
    }    
     
    
}

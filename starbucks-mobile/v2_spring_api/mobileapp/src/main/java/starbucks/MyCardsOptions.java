/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

/** My Cards Options Screen */
public class MyCardsOptions extends Screen
{
   
    public MyCardsOptions()
    {
    }

    /**
     * Get Screen Display Contents
     * @return Screen Contents
     */
    public String display() {
        return "Reload\nRefresh\nMore Options\nCancel" ;
    }    
 
     /**
     * Touch (X,Y) Event
     * @param x X Coord
     * @param y Y Coord
     */
    public void touch(int x, int y) {
        AppController app = AppController.getInstance() ;
        if ( (x == 1 && y == 7) ||
             (x == 2 && y == 7) ||
             (x == 3 && y == 7 )
            ) {
            app.setScreen( AppController.SCREENS.MY_CARDS_MORE_OPTIONS ) ;
        }            
    }    
    
}

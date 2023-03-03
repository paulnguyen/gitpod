/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

/** My Card Pay Screen */
public class MyCardsPay extends Screen
{

    public MyCardsPay() {
    }

    /**
     * Touch (X,Y) Event
     * @param x X Coord
     * @param y Y Coord
     */
    public void touch(int x, int y) {
        Card card = Card.getInstance() ;
        if ( x == 3 && y == 3 ) {
            AppController app = AppController.getInstance() ;
            app.setScreen( AppController.SCREENS.MY_CARDS_MAIN ) ;
        }   
        if ( (x == 2 && y == 2) ||
             (x == 3 && y == 2) 
        ) {
            card.pay() ;
        }                   
    }

    /**
     * Get Screen Display Contents
     * @return Screen Contents
     */
    public String display() {
        Card card = Card.getInstance() ;
        String result = "" ;
        result = "[" + card.getCardId() + "]\n" + "\n\n" + "Scan Now" ;
        return result ;
    }

    /** Added Fall 2019 */
    @Override
    public boolean supportsLandscape() {
        return true ;
    }


}

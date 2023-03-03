/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

/** My Card More Options Screen */
public class MyCardsMoreOptions extends Screen {

    public MyCardsMoreOptions() {
        return ;
    }

    /**
     * Get Screen Display Contents
     * @return Screen Contents
     */
    public String display() {
        return "Refresh\nReload\nAuto Reload\nTransactions" ;
    }

    /**
      * Touch (X,Y) Event
      * @param x X Coord
      * @param y Y Coord
      */
    public void touch(int x, int y) { 
        System.err.println( "X: " + x + " Y: " + y ) ;
        return ;
    }

}

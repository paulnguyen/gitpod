/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

/** Payments Screen */
public class Payments extends Screen
{

    /** No Constructor Needed */
    public Payments()
    {
    }

    /**
     * Return Payments Screen
     * @return Contents from Payments Screen
     */
    public String display() {
        return "Find Store\nEnable Payments" ;
    }

    /**
     * No Touch Events for This Component (i.e. Display Only)
     * @param x X Coord
     * @param y Y Coord
     */
    public void touch(int x, int y) {
        System.err.println( "X: " + x + " Y: " + y ) ; 
        return ;
    }
    
}

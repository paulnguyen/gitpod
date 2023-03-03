/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

import java.util.* ;

/**
 * Base Class for Screens.
 * 
 * Provides Common Functionality
 * For Setting Up the Composite and 
 * Chain of Responsibility Patterns.
 * 
 */
public class Screen implements IScreen, IDisplayComponent
{
    /** Display Components */
    private ArrayList<IDisplayComponent> components = new ArrayList<IDisplayComponent>() ;

    /** Front of Event Chain */
    private ITouchEventHandler chain ;

    /** Constructor */
    public Screen()
    {
    }

    /**
     * Send Touch Events to the Chain
     * @param x Touch X Coord.
     * @param y Touch Y Coord.
     */
    public void touch(int x, int y) {
        chain.touch(x, y) ;
    }
    
    /** Next Screen - Not Used */
    public void next() {
        // add code here
    }
    
    /** Previous Screen - Not Used */
    public void prev()  {
        // add code here
    }
        
    /**
     * Set Next Screen - Not Used 
     * @param s Next Screen Object
     * @param n Next Screen Label
     */
    public void setNext(IScreen s, String n )  {
        // add code here
    }
    
    /**
     * Send Previous Screen - Not Used
     * @param s Previous Screen Object
     * @param n Previous Screen Label
     */
    public void setPrev(IScreen s, String n )  {
        // add code here
    }    

    /**
     * Add Display Component to Screen
     * @param c Display Component
     */
    public void addSubComponent( IDisplayComponent c )
    {
        components.add( c ) ;
        if (components.size() == 1 )
        {
            chain = (ITouchEventHandler) c ;
        }
        else
        {
            ITouchEventHandler prev = (ITouchEventHandler) components.get(components.size()-2) ;
            prev.setNext( (ITouchEventHandler) c ) ;
        }
    }
    
    /**
     * Get Display Contents
     * @return Display Contents
     */
    public String display() { 
        String value = "" ;
        for (IDisplayComponent c : components )
        {
            System.err.println( "Screen: " + c.getClass().getName() ) ;
            value = value + c.display() + "\n" ;
        }
        return value ; 
    }

    /**
     * Get Class Name of Current Screen
     * @return Class Name of Current Screen
     */
    public String name() {
        String class_name = (this.getClass().getName()).split("\\.")[1] ;
        String screen_name = SCREEN_NAMES.get(class_name) ;
        return screen_name ; 
    }

    private static Map<String, String> SCREEN_NAMES;
    static {
        SCREEN_NAMES = new HashMap<>();
        SCREEN_NAMES.put("PinScreen", "");
        SCREEN_NAMES.put("MyCards", "My Cards");
        SCREEN_NAMES.put("MyCardsPay", "My Cards");
        SCREEN_NAMES.put("MyCardsOptions", "My Cards");
        SCREEN_NAMES.put("MyCardsMoreOptions", "My Cards");
        SCREEN_NAMES.put("Payments", "Payments");
        SCREEN_NAMES.put("Rewards", "Rewards");
        SCREEN_NAMES.put("Store", "Find Store");
        SCREEN_NAMES.put("Settings", "Settings");
        SCREEN_NAMES.put("AddCard", "Add Card");
    }

    /** Added Fall 2019 */
    public boolean supportsLandscape() {
        return false ;
    }

}

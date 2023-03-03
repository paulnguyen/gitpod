/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

/** Card ID Display Component */
public class CardID implements IDisplayComponent, ITouchEventHandler, IKeyPadObserver, ICardFocus
{
    private ITouchEventHandler nextHandler ;
    private boolean hasFocus = true ;
    private StringBuffer cardId ;

    /**
     * Get Card ID Value for Screen
     * @return  Card ID Value between brackets.  I.E.:  [123456789]
     */
    public String display() { return "[" + cardId.toString() + "]" ; }
    
    public CardID() {
        cardId = new StringBuffer() ;
    }

    /**
     * Composite Leaf - No Func!
     * @param c [description]
     */
    public void addSubComponent( IDisplayComponent c ) {}

    /**
     * Process Touch
     * @param x X Coord
     * @param y Y Coord
     */
    public void touch(int x, int y) 
    {
        if ( nextHandler != null )
            nextHandler.touch(x,y) ;
    }


   /**
     * Set Next in Event Chain
     * @param next Next Event Chain Object
     */
     public void setNext( ITouchEventHandler next) 
    { 
        nextHandler = next ;
    }

    /**
     * Receive Key Event Updates as Observer
     * @param c   Count of Digits
     * @param key Key Pressed
     */
    public void keyEventUpdate( int c, String key ) 
    {
        if ( hasFocus )         
            System.err.println( "Key Event CardID: [" + c + " | " + key + "]" ) ;

        if ( hasFocus && key.equals(" ") ) 
                return ;

        if ( hasFocus && !key.equals("X") ) {
            if ( cardId.length() < 9 )
                cardId.append(key);
        }
        
        if ( hasFocus && key.equals("X") && cardId.length() > 0 ) {
            cardId.deleteCharAt(cardId.length()-1) ;
        }        
    }     

    /**
     * Clear String Buffer
     */
    public void reset() {
        cardId.setLength(0);
    }

    /**
     * Set Focus to this Component
     */    
    public void setFocus() { hasFocus = true ; }
    
    /**
     * De Focus away from this Component
     */
    public void unsetFocus() { hasFocus = false ;}
}
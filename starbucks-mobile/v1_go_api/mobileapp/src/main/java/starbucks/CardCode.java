/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

/**
 * Card Code UI Component for Card Screen
 */
public class CardCode implements IDisplayComponent, ITouchEventHandler, IKeyPadObserver, ICardFocus
{
    private ITouchEventHandler nextHandler ;
    private boolean hasFocus = false ;
    private StringBuffer cardCode ;

    /**
     * Get Card Code Value for Screen
     * @return Card Code Value between brackets.  I.E.:  [123]
     */
    public String display() { return "[" + cardCode.toString() + "]" ; }

    public CardCode() {
        cardCode = new StringBuffer() ;
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
            System.err.println( "Key Event CardCode: [" + c + " | " + key + "]" ) ;

        if ( hasFocus && key.equals(" ") ) 
                return ;

        if ( hasFocus && !key.equals("X") ) {
            if ( cardCode.length() < 3 )
                cardCode.append(key);
        }

        if ( hasFocus && key.equals("X") && cardCode.length() > 0 ) {
            cardCode.deleteCharAt(cardCode.length()-1) ;
        }        
    }    

    /**
     * Clear String Buffer
     */
    public void reset() {
        cardCode.setLength(0);
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


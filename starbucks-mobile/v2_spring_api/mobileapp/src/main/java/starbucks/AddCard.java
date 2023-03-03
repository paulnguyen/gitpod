/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

/**
 * Add New Card Screen
 */
public class AddCard extends Screen
{
    private AppController app = AppController.getInstance() ;
    private ICardFocus cardId ;
    private ICardFocus cardCode ;

    public AddCard(ICardFocus cardid, ICardFocus cardcode){
        this.cardId = cardid ;
        this.cardCode = cardcode ;
        this.cardId.setFocus() ;
        this.cardCode.unsetFocus() ;
    }
    
    /**
     * Screen Touch C
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void touch(int x, int y) {
        if ( x == 2 && y == 3 ) {
             System.err.println( "Focus Set on Card Code");
            this.cardId.unsetFocus() ;
            this.cardCode.setFocus() ;            
        }
        if ( ( x == 1 && y == 2 ) || 
             ( x == 2 && y == 2 ) ||
             ( x == 3 && y == 2 )
        ) {
            System.err.println( "Focus Set on Card ID");
            this.cardId.setFocus() ;
            this.cardCode.unsetFocus() ;          
        }
        super.touch(x, y) ;
    }
    
    /**
     * Frame Next Screen
     */
    public void next() {
        Card card = Card.getInstance() ;
        String card_id = cardId.display().replace("[", "").replace("]", "") ;  
        String card_code = cardCode.display().replace("[", "").replace("]", "") ;
        if ( card_id.equals("000000000") ) {
            cardId.reset() ;
            cardCode.reset() ;  
            this.cardId.setFocus() ; 
            this.cardCode.unsetFocus() ;
        } else if ( card_id.length() == 9 && card_code.length() == 3 ) {
            card.setCard( card_id, card_code ) ;
            cardId.reset() ;
            cardCode.reset() ;  
            app.setScreen( AppController.SCREENS.MY_CARDS_MAIN ) ;           
        } else {
            cardId.reset() ;
            cardCode.reset() ;  
            this.cardId.setFocus() ; 
            this.cardCode.unsetFocus() ;
        }
    }

    /**
     * Reset Card
     */
    public void reset() {
        cardId.reset() ;
        cardCode.reset() ;  
        cardId.setFocus() ;
        cardCode.unsetFocus() ; 
    }

    /**
     * Frame Previous Screen
     */
    public void prev()  {
        cardId.reset() ;
        cardCode.reset() ;  
        app.setScreen( AppController.SCREENS.SETTINGS ) ;
    }
}

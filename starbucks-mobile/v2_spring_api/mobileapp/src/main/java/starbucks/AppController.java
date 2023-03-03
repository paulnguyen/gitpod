/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

/**
 * Main App Controller Class
 */
public class AppController implements IApp
{

    /** Enums for Screen Types */
    public enum SCREENS {
        MY_CARDS_MAIN, MY_CARDS_PAY, MY_CARDS_OPTIONS,
        MY_CARDS_MORE_OPTIONS, STORE, REWARDS, PAYMENTS, 
        SETTINGS, ADD_CARD
    }
    
    /**
     * Change the Current Active Screen
     * @param s Enum Identifying Screen
     */
    public void setScreen( SCREENS s ) {
        switch( s ) {
            case MY_CARDS_MAIN: frame.setCurrentScreen( mycards ) ; break ;
            case MY_CARDS_PAY: frame.setCurrentScreen( mycardsPay ) ; break ;
            case STORE: frame.setCurrentScreen( store ) ; break ;
            case REWARDS: frame.setCurrentScreen( rewards ) ; break ;
            case PAYMENTS: frame.setCurrentScreen( payments ) ; break ;
            case SETTINGS: frame.setCurrentScreen( settings ) ; break ;
            case MY_CARDS_OPTIONS: frame.setCurrentScreen( mycardsOptions ) ; break ;
            case MY_CARDS_MORE_OPTIONS: frame.setCurrentScreen( mycardsMoreOptions ) ; break ;
            case ADD_CARD:
                addCardScreen.reset() ; 
                frame.setCurrentScreen( addCard ) ; 
                break ;
        }
    }

    private IScreen mycards ;
    private IScreen mycardsPay ;
    private IScreen mycardsOptions ;
    private IScreen mycardsMoreOptions ;
    private IScreen store ;
    private IScreen rewards ;
    private IScreen payments ;
    private IScreen settings ;
    private IScreen addCard ;
    
    private IMenuCommand displayMyCards ;
    private IMenuCommand displayPayments ;
    private IMenuCommand displayRewards ;
    private IMenuCommand doStore ;   
    private IMenuCommand doSettings ;
    private IFrame frame ;

    private AddCard addCardScreen ;
    private CardID cardId = new CardID() ;
    private CardCode cardCode = new CardCode() ;
    private Spacer sp = new Spacer() ;
    private KeyPad kp = new KeyPad() ;

    private static AppController theController = null;    

    private AppController() { }

    /**
     * Get New Instance 
     * @return Reference to App Controller (Create New Singleton)
     */
    public synchronized static AppController getNewInstance() {
        theController = new AppController();
        theController.startUp();
        return theController;
    }

    /**
     * Get Singleton Instance
     * @return Reference to Current App Controller Singleton (Create if none exists)
     */
    public synchronized static AppController getInstance() {
        if (theController == null) {
            theController = new AppController();
            theController.startUp();
            return theController;
        }
        else
            return theController;
    }

    /**
     * App Starup Process.  
     * Configures all the Screens and Patterns.
     */
    public void startUp()
    {
        // app screens
        mycards = new ScreenCenterDecorator( new MyCards() ) ;
        mycardsPay = new ScreenCenterDecorator( new MyCardsPay() ) ;
        store =  new ScreenLeftDecorator( new Store() );
        rewards = new ScreenLeftDecorator( new Rewards() ) ;
        payments = new ScreenLeftDecorator( new Payments() ) ;
        settings = new ScreenCenterDecorator( new Settings() ) ;
        mycardsOptions = new ScreenLeftDecorator( new MyCardsOptions() ) ;
        mycardsMoreOptions = new ScreenLeftDecorator( new MyCardsMoreOptions() ) ;  

        // set up frame
        frame = new Frame( mycards ) ;
        
        // setup the add card composite pattern
        addCardScreen = new AddCard(cardId, cardCode) ;
        ((IDisplayComponent) addCardScreen).addSubComponent( cardId ) ;
        ((IDisplayComponent) addCardScreen).addSubComponent( cardCode ) ;
        ((IDisplayComponent) addCardScreen).addSubComponent( sp ) ;
        ((IDisplayComponent) addCardScreen).addSubComponent( kp ) ;
        addCard = new ScreenCenterDecorator( addCardScreen );
        
        // setup the observer pattern
        ((IKeyPadSubject)kp).attach( cardId ) ;      
        ((IKeyPadSubject)kp).attach( cardCode ) ; 
        
        // setup command pattern
        displayMyCards  = new MenuCommand() ;
        displayPayments = new MenuCommand() ;
        displayRewards  = new MenuCommand() ;
        doStore         = new MenuCommand() ;
        doSettings      = new MenuCommand() ;
        
        displayMyCards.setReceiver(
            new IMenuReceiver() {
                public void doAction() {
                    frame.setCurrentScreen( mycards ) ;
                }
            }
        ) ;
        displayPayments.setReceiver(
            new IMenuReceiver() {
                public void doAction() {
                    frame.setCurrentScreen( payments ) ;
                }
            }
        ) ;
        displayRewards.setReceiver(
            new IMenuReceiver() {
                public void doAction() {
                    frame.setCurrentScreen( rewards ) ;
                }
            }
        ) ;
        doStore.setReceiver(
            new IMenuReceiver() {
                public void doAction() {
                    frame.setCurrentScreen( store ) ;
                }
            }
        ) ;  
        doSettings.setReceiver(
            new IMenuReceiver() {
                public void doAction() {
                    frame.setCurrentScreen( settings ) ;
                }
            }
        ) ;         
        frame.setMenuItem ( "A", displayMyCards ) ;
        frame.setMenuItem ( "B", displayPayments ) ;
        frame.setMenuItem ( "C", displayRewards ) ;
        frame.setMenuItem ( "D", doStore ) ;
        frame.setMenuItem ( "E", doSettings ) ;
    }

    /**
     * Switch to Landscape Mode
     */
    public void landscape() 
    { 
        frame.landscape() ; 
    }

    /**
     * Switch to Portait Mode
     */
    public void portrait()  
    { 
        frame.portrait() ; 
    }      

    /**
     * Send In Touch Events
     * @param x X Coord
     * @param y Y Coord
     */
    public void touch(int x, int y)
    {
        frame.touch(x,y) ;
    }

    /**
     * Display Current Screen
     */
    public void display()
    {
        frame.display() ;
    }

    /**
     * Execute Menu Bar Command
     * @param c Menu Bar Option (A, B, C, D or E)
     */
    public void execute( String c )
    {
        frame.cmd( c ) ;
    }

    /**
     * Navigate to Previous Screen
     */
    public void prev() {
        frame.previousScreen() ;
    }

    /**
     * Navigate to Next Screen
     */
    public void next() {
        frame.nextScreen() ;
    }

    /**
     * Get Current Screen Name
     * @return Screen Name
     */
    public String screen() {
        return frame.screen() ;
    }

    /**
     * Get Current Screen Contents
     * @return Current Screen Contents
     */
    public String screenContents() {
        return frame.contents() ;
    }


    /** Added Fall 2019 */
    public boolean supportsLandscape() {
        return frame.supportsLandscape() ;
    }

}

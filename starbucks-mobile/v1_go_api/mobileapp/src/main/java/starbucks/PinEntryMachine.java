/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

/** Pin Entry Machine - Context for State Pattern */
public class PinEntryMachine implements IPinStateMachine, IKeyPadObserver, IPinAuthSubject
{

    // Device Config
    private Device device = Device.getInstance() ;

    /**
     * Get name of current state for unit testing
     * @return Class Name of Current State
     */
    public String getCurrentState()
    {
        return state.getClass().getName() ;
    }

    // Pin Domain Object
    private boolean authenticated = false ;
    private int pinCount=0 ;
    private IPinAuthObserver auth ; // single observer 

    // pin machine states
    private NoPinDigits     pin0 ;
    private OnePinDigit     pin1 ;
    private TwoPinDigits    pin2 ;
    private ThreePinDigits  pin3 ;
    private FourPinDigits   pin4 ;
    private FivePinDigits   pin5 ;
    private SixPinDigits    pin6 ;

    // current state
    private IPinState state ;

    // pin captured so far
    private String d1, d2, d3, d4, d5, d6 ;
    public String d1() { return d1 ; }
    public String d2() { return d2 ; }
    public String d3() { return d3 ; }
    public String d4() { return d4 ; }
    public String d5() { return d5 ; }
    public String d6() { return d6 ; }

    /**
     * Constructor - Set Up State Objects
     * and Set Initial State to "PIN 0"
     */
    public PinEntryMachine( )
    {
        pin0 = new NoPinDigits( this ) ;
        pin1 = new OnePinDigit( this ) ;
        pin2 = new TwoPinDigits( this ) ;
        pin3 = new ThreePinDigits( this ) ;
        pin4 = new FourPinDigits( this ) ;
        pin5 = new FivePinDigits( this ) ; 
        pin6 = new SixPinDigits( this ) ;               

        this.d1 = "" ;
        this.d2 = "" ;
        this.d3 = "" ;
        this.d4 = "" ;   
        this.d5 = "" ;
        this.d6 = "" ;     

        this.state = pin0 ;
    }

    /** Backspace Event */
    public void backspace() {
        this.state.backspace() ;
    }

    /**
     * Number Event
     * @param digit Digit Pressed
     */
    public void number( String digit ) {
        this.state.number( digit ) ;
    }

    /** Valid Pin Event */
    public void validPin() {
        this.state.validPin() ;
    }

    /** Invalid Pin Event */
    public void invalidPin() {
        this.state.invalidPin() ;
    }

    /** Change the State to No Pin State */
    public void setStateNoPinDigits()
    {
        this.pinCount = 0 ;
        this.state = pin0 ;
        this.d1 = "" ;
        this.d2 = "" ;
        this.d3 = "" ;
        this.d4 = "" ;
        this.d5 = "" ;
        this.d6 = "" ;         
        debug() ;
        Device.debug() ;
    }

    /**
     * Change the State to One Pin State
     * @param digit Digit/Number Enterred
     */
    public void setStateOnePinDigit( String digit )
    {
        this.pinCount = 1 ;
        this.state = pin1 ;
        if ( digit != null )
            this.d1 = digit ;
        else {
            this.d2 = "" ;
            this.d3 = "" ;
            this.d4 = "" ;
            this.d5 = "" ;
            this.d6 = "" ;               
        }
        debug() ;
        Device.debug() ;
    }

    /**
     * Change the State to Two Pin State
     * @param digit Digit/Number Enterred
     */
    public void setStateTwoPinDigits( String digit )
    {
        this.pinCount = 2 ;
        this.state = pin2 ;
        if ( digit != null )
            this.d2 = digit ;
        else {
            this.d3 = "" ;
            this.d4 = "" ;
            this.d5 = "" ;
            this.d6 = "" ;               
        }
        debug() ;
        Device.debug() ;
    }

    /**
     * Change the State to Three Pin State
     * @param digit Digit/Number Enterred
     */
    public void setStateThreePinDigits( String digit )
    {
        this.pinCount = 3 ;
        this.state = pin3 ;
        if ( digit != null )
            this.d3 = digit ;
        else {
            this.d4 = "" ;
            this.d5 = "" ;
            this.d6 = "" ;               
        }
        debug() ;
        Device.debug() ;
    }

    /**
     * Change the State to Four Pin State
     * @param digit Digit/Number Enterred
     */
    public void setStateFourPinDigits( String digit )
    {
        this.pinCount = 4 ;
        this.state = pin4 ;
        int pin_option = device.getPinOption() ;
        String pin = device.getPin() ;

        if ( digit != null && pin_option == 4 )
        {
            this.d4 = digit ;
            System.err.println( "Authenticating..." ) ;
            if ( pin.equals( d1+d2+d3+d4 ) )
            {
                System.err.println( "Successful Login!" ) ;
                this.authenticated = true ;
                notifyObserver() ;
            }
            else
            {
                System.err.println( "Login Failed!" ) ;
                this.authenticated = false ;
                setStateNoPinDigits() ;
                notifyObserver() ;
            }
        } else if ( digit != null ) {
            this.d4 = digit ;
        } else {
            this.d5 = "" ;
            this.d6 = "" ;               
        }
        debug() ;
        Device.debug() ;
    }

    /**
     * Change the State to Four Pin State
     * @param digit Digit/Number Enterred
     */
    public void setStateFivePinDigits( String digit )
    {
        this.pinCount = 5 ;
        this.state = pin5 ;
        
        if ( digit != null )
            this.d5 = digit ;
        else {
            this.d6 = "" ;
        }

        debug() ;
        Device.debug() ; 
    }

    /**
     * Change the State to Four Pin State
     * @param digit Digit/Number Enterred
     */
    public void setStateSixPinDigits( String digit )
    {
        this.pinCount = 6 ;
        this.state = pin6 ;
        this.d6 = digit ;
        String pin = device.getPin() ;
        
        if ( digit != null )
        {
            System.err.println( "Authenticating..." ) ;
            if ( pin.equals( d1+d2+d3+d4+d5+d6 ) )
            {
                System.err.println( "Successful Login!" ) ;
                this.authenticated = true ;
                notifyObserver() ;
            }
            else
            {
                System.err.println( "Login Failed!" ) ;
                this.authenticated = false ;
                setStateNoPinDigits() ;
                notifyObserver() ;
            }
        }         

        debug() ;
        Device.debug() ; 
    }

    /**
     * Observer of Key Events
     * @param c   Num Keys So Far
     * @param key Last Key Enterred
     */
    public void keyEventUpdate( int c, String key ) 
    {
        System.err.println( "Key: " + key + " Count: " + c ) ;
        if ( key.equals(" ") )
        /* nothing */ ;
        else if ( key.equals("X") )
            backspace() ;
        else
            number( key ) ;        
    }    

    /**
     * Register Observers for Pin Authentication
     * @param obj Object Observing Pin Auth
     */
    public void registerObserver( IPinAuthObserver obj ) 
    {
        this.auth = obj ;
    }

    /**
     * Remove Pin Auth Observer
     * @param obj Object No Longer Observing Pin Auth
     */
    public void removeObserver( IPinAuthObserver obj ) 
    {
        this.auth = null ;
    }

    /**
     * Notify Pin Auth Observers
     */
    public void notifyObserver( ) 
    {
        if ( this.auth != null ) {
            if (this.authenticated)
                this.auth.authEvent() ;
            else
                this.auth.invalidPin() ;
        }
    }

    /** Debug Dump to Stderr State Machine Changes */
    private void debug()
    {
        System.err.println( "Current State: " + state.getClass().getName() ) ;
        System.err.println( "D1 = " + d1 ) ;
        System.err.println( "D2 = " + d2 ) ;
        System.err.println( "D3 = " + d3 ) ;
        System.err.println( "D4 = " + d4 ) ;
        System.err.println( "D5 = " + d5 ) ;
        System.err.println( "D6 = " + d6 ) ;
    }

}

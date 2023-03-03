/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

import java.util.HashMap;

/** Device Config  */
public class Device implements IApp, IPinAuthObserver
{

    private static Device theDevice = null;   
    private boolean fourPin = true ;
    private boolean sixPin = false ;
    private String pin = "" ; 
    private HashMap<String, String> props = new HashMap<String, String>() ;

    private IApp app ;
    private KeyPad kp ;
    private Passcode4 pc4 ;
    private Passcode6 pc6 ;
    private PinScreen ps ;
    private Spacer sp ;
    private boolean authenticated = false ;
    private boolean showInvalidPin = false ;
    private PinEntryMachine pm ;    

    public static final int screen_frame_header = 3 ;
    public static final int portrait_screen_width = 15 ;
    public static final int portrait_screen_length = 10 ;
    public static final int landscape_screen_width = 32 ;
    public static final int landscape_screen_length = 6 ;

    public enum ORIENTATION_MODE {
        PORTRAIT, LANDSCAPE
    }

    private ORIENTATION_MODE device_orientation_state ;

    public ORIENTATION_MODE getDeviceOrientation() {
        return this.device_orientation_state ;
    }

    public void setPortraitOrientation() {
        this.device_orientation_state = ORIENTATION_MODE.PORTRAIT ;
    }

    public void setLandscapeOrientation() {
        this.device_orientation_state = ORIENTATION_MODE.LANDSCAPE ;
    }

    private Device() { }

    /** Get/Set Device Secured Enclave for Apps **/
    public String getProps(String key) {
        return props.get(key) ;
    }

    public void setProps(String key, String value) {
        props.put(key, value) ;
    }

    /** Debug Device State */
    public static void debug()
    {
    	Device d = Device.getInstance() ;
        System.err.println( "============================================" ) ;
        System.err.println( "--------- D E V I C E  S T A T E  ----------" ) ;
        System.err.println( "============================================" ) ;
        System.err.println( "Pin Option    = " + d.getPinOption() ) ;
        System.err.println( "Stored Pin    = " + d.getPin() ) ;
        System.err.println( "Authenticated = " + d.isAuthenticated() ) ;
        System.err.println( "Orientation   = " + d.getDeviceOrientation() ) ;
        System.err.println( "============================================" ) ;
    }

    /**
     * Get Current Auth State
     * @return Auth T/F
     */
    public String isAuthenticated() {
        return Boolean.toString( authenticated ) ;
    }    

    /**
     * Return the current Pin Option:
     *  0 = User Chosed No Pin
     *  4 = User Chosed 4-digit Pin
     *  6 = User Chosed 6-digit Pin
     * @return Pin Option
     */
    public int getPinOption() {
    	if ( fourPin )
    		return 4 ;
    	else if ( sixPin )
    		return 6 ;
    	else
    		return 0 ;
    }

    /**
     * Get Current Pin
     * @return Pin
     */
    public String getPin() {
    	return pin ;
    }


    /**
     * Set Pin
     * @param p New Pin
     */
    private void setPin( String p ) {
    	pin = p ;
        int len = p.length() ;
        switch ( len ) {
            case 0:
                fourPin = false ;
                sixPin = false ;   
                this.authenticated = true ;           
            case 4:
                fourPin = true ;
                sixPin = false ;
                break ;
            case 6:
                fourPin = false ;
                sixPin = true ;
                break ;
            default:
                fourPin = false ;
                sixPin = false ;
                this.authenticated = true ;
        }
    }

    /**
     * Device Reset Pin  
     */
    private void clearPin()
    {
        this.pin = "" ;
    }

    /**
     * Get Singleton Instance
     * @return Reference to Current Device Config (Create if none exists)
     */
    public synchronized static Device getInstance() {
        if (theDevice == null) {
            return getNewInstance( "1234" ) ;
        }
        else
            return theDevice;
    }

    /**
     * Get New Instance 
     * @return Reference to Device (Create New Singleton)
     */
    public synchronized static Device getNewInstance() {
        return getNewInstance( "1234" ) ;
    }

    public synchronized static Device getNewInstance( String pin ) {
        theDevice = new Device() ;
        theDevice.setPin( pin ) ;
        theDevice.setProps( "apiurl", "http://localhost:3000" ) ;
        theDevice.setProps( "apikey", "2742a237475c4703841a2bf906531eb0" ) ;
        theDevice.setProps( "register", "5012349" ) ;        
        theDevice.startUp() ;
        debug() ;
        return theDevice ;
    }

    public synchronized static Device getNewInstance( String pin, String url ) {
        if (theDevice == null) {
            theDevice = getNewInstance( "1234" ) ;
            theDevice.setProps( "apiurl", url ) ;
        }
        debug() ;
        return theDevice ;
    }

   	/**
     * Device StartUp Process.  
     * Starts Up with Default 4-Pin Option
     */
    public void startUp()
    {

        // setup pin screen
        reset_screen() ;

        // get app controller reference
        app = AppController.getNewInstance() ;

        // startup in portrait
        this.device_orientation_state = ORIENTATION_MODE.PORTRAIT ;

    }

    private void set_screen_4pins() {
        // pin screen ui components
        kp = new KeyPad() ;
        pc4 = new Passcode4() ;
        sp = new Spacer() ;
        ps = new PinScreen() ;
        pm = new PinEntryMachine() ;

        // setup the composite pattern
        ps.addSubComponent( pc4 ) ;
        ps.addSubComponent( sp ) ;
        ps.addSubComponent( kp ) ;

        // setup the observer pattern
        ((IKeyPadSubject)kp).attach( pc4 ) ;
        ((IKeyPadSubject)kp).attach( pm ) ;
        ((IPinAuthSubject)pm).registerObserver(this) ;        
    }

    private void set_screen_6pins() {
        // pin screen ui components
        kp = new KeyPad() ;
        pc6 = new Passcode6() ;
        sp = new Spacer() ;
        ps = new PinScreen() ;
        pm = new PinEntryMachine() ;

        // setup the composite pattern
        ps.addSubComponent( pc6 ) ;
        ps.addSubComponent( sp ) ;
        ps.addSubComponent( kp ) ;

        // setup the observer pattern
        ((IKeyPadSubject)kp).attach( pc6 ) ;
        ((IKeyPadSubject)kp).attach( pm ) ;
        ((IPinAuthSubject)pm).registerObserver(this) ; 
    }

    private void reset_screen() {
        switch ( getPinOption() ) {
            case 4: 
                set_screen_4pins() ;
                pc4.reset() ; 
                break ;
            case 6: 
                set_screen_6pins() ;
                pc6.reset() ; 
                break ;
            default: 
                set_screen_4pins() ;
                pc4.reset() ; 
                break ;
        }
    }




    /**
     * Test Sequence for Test Payment
     */
    public void mock_payment() 
    {
        touch(1,5) ;  // 1
        touch(2,5) ;  // 2
        touch(3,5) ;  // 3
        touch(1,6) ;  // 4
        execute("E") ; // Settings Page
        touch(1,1) ; // Add New Card
        // Card Id digits
        touch(1,5); 
        touch(2,5);
        touch(3,5);
        touch(1,6);
        touch(2,6);
        touch(3,6);
        touch(1,7);
        touch(2,7);
        touch(3,7);
        touch(2,3); // focus on card code
        // Card Code digits
        touch(3,7);
        touch(3,7);
        touch(3,7);
        next() ;    // add card - see balance
        touch(3,3); // switch to payment
    }

    /**
     * Test Sequence for Adding a New Card
     */
    public void mock_addcard() 
    {
        touch(1,5) ;  // 1
        touch(2,5) ;  // 2
        touch(3,5) ;  // 3
        touch(1,6) ;  // 4
        execute("E") ; // Settings Page
        touch(1,1) ; // Add New Card
    }   
    
    /**
     * Test Sequence to Login
     */
    public void mock_login() 
    {
        touch(1,5) ;  // 1
        touch(2,5) ;  // 2
        touch(3,5) ;  // 3
        touch(1,6) ;  // 4
    }
    
    /**
     * Switch to Landscape View
     */
    public void landscape() 
    { 
        if ( authenticated && app.supportsLandscape() ) {
            setLandscapeOrientation() ;
            app.landscape() ; 
        }
    }

    /**
     * Switch to Portait View
     */
    public void portrait()  
    { 
        if ( authenticated ) {
            setPortraitOrientation() ;
            app.portrait() ; 
        }
    }      

    /** Added Fall 2019 */
    public boolean supportsLandscape() {
        return app.supportsLandscape() ;
    }

    /**
     * User Touch at X,Y Coordinates
     * @param x X Coordinate
     * @param y Y Coordinate
     */
    public void touch(int x, int y)
    {
        if ( authenticated ) {
            if ( device_orientation_state != ORIENTATION_MODE.LANDSCAPE )
                app.touch(x,y) ;
        }
        else {
            if (showInvalidPin)
                showInvalidPin = false ;
            ps.touch(x,y) ;
        }
    }

    /**
     * Display Screen Contents to Terminal
     */
    public void display()
    {
        System.out.println( screenContents() ) ;
    }

    /** 
     * Get Class Name of Screen
     * @return Class Name of Current Screen
     */
    public String screen() {
        if ( authenticated )
            return app.screen() ;
        else
            return ps.name() ;
    }

    /**
     * Get Screen Contents as a String
     * @return Screen Contents of Current Screen
     */
    public String screenContents() {
        if ( authenticated ) {
            return app.screenContents() ;
        }
        else {
            String out = "" ;
            out  = "---------------\n" ;
            out += "   " + ps.name() + "  \n" ;
            out += "---------------\n" ;
            if ( showInvalidPin ) {
                out += "  Invalid Pin\n" ;
                out += "\n" ;
            }
            else {
                out += "\n\n" ;
            }
            out += ps.display() ;
            out += "\n\n\n---------------\n" ;
            return out ;
        }
    }

    /**
     * Select a Menu Command
     * @param c Menu Option (A, B, C, E, or E)
     */
    public void execute( String c )
    {
        if ( authenticated )
            app.execute( c ) ;
    }

    /**
     * Navigate to Previous Screen
     */
    public void prev() {
        if ( authenticated )
            app.prev() ;
    }

    /**
     * Navigate to Next Screen
     */
    public void next() {
        if ( authenticated )
            app.next() ;
    }

    /**
     * Receive Authenticated Event from Authenticator
     */
    public void authEvent() 
    {
        this.authenticated = true ;
    }

    /**
     * Receive Invalid Pin Event
     */
    public void invalidPin() 
    {
        this.authenticated = false ;
        this.showInvalidPin = true ;
        reset_screen() ; // note: refactor this to encapsullate key count
        kp.reset() ; // note: refactor this to encapsullate key count
    }    

   /** Utility Functions  - Refactored for Fall 2019 (Moved from Frame.java) */

    public static void dumpLines(String str) {
          String[] lines = str.split("\r\n|\r|\n");
          for ( int i = 0; i<lines.length; i++ ) {
            System.err.println( i + ": " + lines[i] ) ;
          }
    }

    public static int countLines(String str){

        /*
          // this approach doesn't work in cases: "\n\n"
          String lines = str ;
          String[] split = lines.split("\r\n|\r|\n") ;
          return split.length ;
        */

        if (str == null || str.isEmpty()) {
                return 0;
            }

        int lines = 0;
        int pos = 0;
        while ((pos = str.indexOf("\n", pos) + 1) != 0) {
                lines++;
        }

        return lines ;
    }

     public static String padLines(int num) {
        String lines = "" ;
        for ( int i = 0; i<num; i++ ) {
            System.err.print(".") ;
            lines += "\n" ;
        }
        System.err.println("") ;
        return lines ;
    }
    
    public static String padSpaces(int num) {
        String spaces = "" ;
        for ( int i = 0; i<num; i++ )
            spaces += " " ;           
        return spaces ;     
    }            




}



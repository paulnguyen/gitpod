
/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

/** Decorator for Fall 2019  **/

public class ScreenDecorator implements IScreen
{

	protected IScreen screen ;

	public ScreenDecorator( IScreen s ) {
		this.screen = s ;
    }
            
    // decorate - left justified + line pad based on 
    // device orientation
    
    public String display() {
    	// No Implementation
    	return "" ;
    }   

    // always center justify the name + line pad based on
    // device orientation
    
    public String name() {

    	int w = 0 ;
    	int l = 0 ;
    	int h = 0 ;
    	String output = "" ;
    	int nameLen = 0 ;

    	Device d = Device.getInstance() ;
    	Device.ORIENTATION_MODE m = d.getDeviceOrientation() ;

    	switch( m ) {

            case PORTRAIT: 
            	h = Device.screen_frame_header ;
            	w = Device.portrait_screen_width ;
            	l = Device.portrait_screen_length ;
            	break ;
            case LANDSCAPE: 
            	h = Device.screen_frame_header ;
            	w = Device.landscape_screen_width ;
            	l = Device.landscape_screen_length ;
            	break ;
        }

    	output = "" ;
        nameLen = screen.name().length() ;
        if (nameLen < (w)) {
            int pad = (int) Math.ceil( (double)(w - nameLen)/(double)2 ) ;
            output += Device.padSpaces( pad ) ;
        }
        output += screen.name() + "\n" ;

        return output ;

    }                        
 
    // pass these thru
    
    public void touch(int x, int y) { screen.touch(x,y) ; }
    public void next() { screen.next() ; }                      
    public void prev() { screen.prev() ; }    
    public void setNext(IScreen s, String n ) { screen.setNext(s, n) ; }    
    public void setPrev(IScreen s, String n ) { screen.setPrev(s, n) ; }
    public boolean supportsLandscape() { return screen.supportsLandscape() ; }


}




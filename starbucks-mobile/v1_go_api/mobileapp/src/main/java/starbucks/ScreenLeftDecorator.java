
/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

/** Decorator for Fall 2019  **/

public class ScreenLeftDecorator extends ScreenDecorator
{

	public ScreenLeftDecorator( IScreen s ) {
		super(s) ;
    }
            
    // decorate - left justified + line pad based on 
    // device orientation
    
    public String display() {

  		int w = 0 ;
    	int l = 0 ;
    	int h = 0 ;
    	String output = "" ;
    	String s = "" ;
    	String padlines = "" ;
    	int nameLen = 0 ;
    	int cnt1 = 0 ;
    	int cnt2 = 0 ;
    	int pad1 = 0 ;
    	int pad2 = 0 ;

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
        s = screen.display() + "\n" ;
        cnt1 = Device.countLines( s ) ;
        pad1 = (l - cnt1) / 2;
        output += Device.padLines( pad1 ) ;
        output += screen.display()  ;              
        cnt2 = Device.countLines( output ) ;
        pad2 = l - cnt2 ;
        padlines = Device.padLines( pad2 ) ;
        output += padlines ;

        return output ;    	


    }   

                      
}
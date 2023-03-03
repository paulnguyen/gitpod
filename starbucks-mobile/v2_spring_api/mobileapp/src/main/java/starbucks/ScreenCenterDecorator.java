
/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

/** Decorator for Midterm Question  **/

public class ScreenCenterDecorator extends ScreenDecorator
{

	public ScreenCenterDecorator( IScreen s ) {
		super(s) ;
    }
            
    // decorate - center justified + line pad based on 
    // device orientation
    
    public String display() {

		int w = 0 ;
    	int l = 0 ;
    	int h = 0 ;
    	String output = "" ;
    	String s = "" ;
    	String padlines = "" ;
        String padspaces = "" ;
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
        
        System.err.println( "l: " + l ) ;
        System.err.println( "w: " + w ) ;
        System.err.println( "h: " + w ) ;

        s = screen.display() + "\n" ;
        System.err.println( "DEBUG: " + s ) ;
        cnt1 = Device.countLines( s ) ;
        pad1 = (l - cnt1) / 2;
        System.err.println( "cnt1: " + cnt1 ) ;
        System.err.println( "pad1: " + pad1 ) ;
        output += Device.padLines( pad1 ) ;
        // Center Screen Contents Start 
        String[] lines = screen.display().split("\n") ;
        System.err.println( "DEBUG: " + lines + " " + lines.length ) ;
        for (int i = 0; i < lines.length; i++) { 
        	String line = lines[i].trim() ;
        	int len = line.length() ;
            System.err.println( "line (before): " + line + " [len = " + len + "]" ) ;
            int pad = 0 ;
	        if (len < w ) {
	            pad = (int) Math.ceil((double)(w-len)/(double)2);
	            padspaces = Device.padSpaces( pad ) ;
	        }
            System.err.println( "line (after): " + padspaces + line  + " [pad = " + pad + "]" ) ;
	        output += padspaces + line + "\n" ;
        }
        // Center Screen Contents End            
        cnt2 = Device.countLines( output ) ;
        pad2 = l - cnt2 ;
        System.err.println( "cnt2: " + cnt2 ) ;
        System.err.println( "pad2: " + pad2 ) ;
        padlines = Device.padLines( pad2 ) ;
        output += padlines ;

        return output ;    	

    }   

}

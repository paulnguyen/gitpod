/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Passcode4Test
{

    IApp app ;

    public Passcode4Test()
    {
    }

    @Before
    public void setUp()
    {
        app = (IApp) Device.getNewInstance("2468") ; 
    }

    @Test
    public void Passcode4Test1()
    {
        assertEquals("", app.screen().trim());
        app.touch(2,5) ; // 2
        app.touch(1,6) ; // 4
        app.touch(3,6) ; // 6
        app.touch(2,7) ; // 8
        assertEquals("My Cards", app.screen().trim());
    }    

    @Test
    public void Passcode4Test2()
    {
        assertEquals("", app.screen().trim());
        app.touch(2,5) ; // 2
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X        
        assertEquals("", app.screen().trim());
    }    

    @Test
    public void Passcode4Test3()
    {
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(3,5) ; 
        assertEquals("", app.screen().trim());
        String[] lines ;
        lines = app.screenContents().split("\n");   
        assertEquals( "Invalid Pin", lines[3].trim());     
        assertEquals("[_][_][_][_]", lines[5].trim());        
    }

   @Test
    public void Passcode4Test4()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("[_][_][_][_]", lines[5].trim());
        app.touch(1,5) ;
        lines = app.screenContents().split("\n");  
        assertEquals("[*][_][_][_]", lines[5].trim());
        app.touch(2,5) ;
        lines = app.screenContents().split("\n");  
        assertEquals("[*][*][_][_]", lines[5].trim());
        app.touch(3,5) ;
        lines = app.screenContents().split("\n");  
        assertEquals("[*][*][*][_]", lines[5].trim());
        app.touch(3,8) ; // X
        lines = app.screenContents().split("\n");  
        assertEquals("[*][*][_][_]", lines[5].trim());        
        app.touch(3,8) ; // X
        lines = app.screenContents().split("\n");  
        assertEquals("[*][_][_][_]", lines[5].trim());        
        app.touch(3,8) ; // X
        lines = app.screenContents().split("\n");  
        assertEquals("[_][_][_][_]", lines[5].trim());
        app.touch(1,5) ;
        assertEquals("", app.screen().trim());
    }                

    @Test
    public void Passcode4Test5()
    {
        assertEquals("", app.screen().trim());
        app.touch(-1, -9) ;
        app.touch(-1, -100) ;
        app.touch(0, 0) ;
        app.touch(2,5) ; // 2
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X     
        app.touch(-1, -9) ;
        app.touch(-1, -100) ;
        app.touch(0, 0) ;          
        assertEquals("", app.screen().trim());
    } 

    @After
    public void tearDown()
    {
    }
}

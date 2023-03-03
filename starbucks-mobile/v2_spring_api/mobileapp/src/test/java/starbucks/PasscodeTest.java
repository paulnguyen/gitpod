/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PasscodeTest
{

    IApp app ;

    public PasscodeTest()
    {
    }

    @Before
    public void setUp()
    {
        app = (IApp) Device.getNewInstance() ; 
    }

    @Test
    public void PasscodeTest1()
    {
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        assertEquals("My Cards", app.screen().trim());
    }    

    @Test
    public void PasscodeTest2()
    {
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(3,8) ; // X
        app.touch(3,5) ;
        app.touch(1,6) ;
        assertEquals("My Cards", app.screen().trim());
    }

    @Test
    public void PasscodeTest3()
    {
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(3,8) ; // X
        assertEquals("", app.screen().trim());
    }

    @Test
    public void PasscodeTest4()
    {
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(1,7) ;
        app.touch(2,7) ;
        app.touch(3,7) ;
        assertEquals("", app.screen().trim());
    }

    @Test
    public void PasscodeTest5()
    {
        assertEquals("", app.screen().trim());
        app.touch(1,6) ;
        app.touch(3,5) ;
        app.touch(2,5) ;
        app.touch(1,5) ;
        assertEquals("", app.screen().trim());
    }

    @Test
    public void PasscodeTest6()
    {
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(1,6) ;
        assertEquals("", app.screen().trim());
    }

    @Test
    public void PasscodeTest7()
    {
        assertEquals("", app.screen().trim());
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(1,6) ;
        assertEquals("", app.screen().trim());
    }

    @Test
    public void PasscodeTest8()
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
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        lines = app.screenContents().split("\n");  
        assertEquals("[_][_][_][_]", lines[5].trim());
        app.touch(-1,-9) ;
        assertEquals("", app.screen().trim());
    }    

    @Test
    public void PasscodeTest9()
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
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        lines = app.screenContents().split("\n");  
        assertEquals("[_][_][_][_]", lines[5].trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;     
        app.touch(1,6) ;
        assertEquals("My Cards", app.screen().trim());
    }        

    @Test
    public void PasscodeTest10()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("[_][_][_][_]", lines[5].trim());
        app.touch(1,8) ;
        assertEquals("[_][_][_][_]", lines[5].trim());
        app.touch(3,8) ;
        assertEquals("[_][_][_][_]", lines[5].trim());
        assertEquals("", app.screen().trim());
    }     

    @After
    public void tearDown()
    {
    }
}

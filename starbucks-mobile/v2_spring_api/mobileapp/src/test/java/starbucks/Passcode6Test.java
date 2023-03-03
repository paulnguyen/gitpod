/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Passcode6Test
{

    IApp app ;

    public Passcode6Test()
    {
    }

    @Before
    public void setUp()
    {
        app = (IApp) Device.getNewInstance("246843") ; 
    }

    @Test
    public void Passcode6Test1()
    {
        assertEquals("", app.screen().trim());
        app.touch(2,5) ; // 2
        app.touch(1,6) ; // 4
        app.touch(3,6) ; // 6
        app.touch(2,7) ; // 8
        app.touch(1,6) ; // 4
        app.touch(3,5) ; // 3
        assertEquals("My Cards", app.screen().trim());
    }    

    @Test
    public void Passcode6Test2()
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
    public void Passcode6Test3()
    {
        assertEquals("", app.screen().trim());
        app.touch(2,5) ; // 2
        app.touch(1,6) ; // 4
        app.touch(3,6) ; // 6
        app.touch(3,5) ; // 3
        app.touch(2,7) ; // 8
        app.touch(1,6) ; // 4        
        assertEquals("", app.screen().trim());
        String[] lines ;
        lines = app.screenContents().split("\n");   
        assertEquals( "Invalid Pin", lines[3].trim());     
        assertEquals( "_ _ _ _ _ _",  lines[5].trim()); 
    }  

    @Test
    public void Passcode6Test4()
    {
        String[] lines ;
        lines = app.screenContents().split("\n");   
        assertEquals( "_ _ _ _ _ _",  lines[5].trim());         
        assertEquals("", app.screen().trim());
        app.touch(2,5) ; // 2
        lines = app.screenContents().split("\n");   
        assertEquals( "* _ _ _ _ _",  lines[5].trim()); 
        app.touch(1,6) ; // 4
        lines = app.screenContents().split("\n");   
        assertEquals( "* * _ _ _ _",  lines[5].trim()); 
        app.touch(3,6) ; // 6
        lines = app.screenContents().split("\n");   
        assertEquals( "* * * _ _ _",  lines[5].trim()); 
        app.touch(2,7) ; // 8
        lines = app.screenContents().split("\n");   
        assertEquals( "* * * * _ _",  lines[5].trim()); 
        app.touch(1,6) ; // 4
        lines = app.screenContents().split("\n");   
        assertEquals( "* * * * * _",  lines[5].trim()); 
        app.touch(3,8) ; // X 
        lines = app.screenContents().split("\n");   
        assertEquals( "* * * * _ _",  lines[5].trim());         
        app.touch(3,8) ; // X 
        lines = app.screenContents().split("\n");   
        assertEquals( "* * * _ _ _",  lines[5].trim());         
        app.touch(3,5) ; // 3
        assertEquals("", app.screen().trim());
    }    

    @Test
    public void Passcode6Test5()
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

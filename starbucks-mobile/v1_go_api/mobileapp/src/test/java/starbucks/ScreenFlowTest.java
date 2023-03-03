/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ScreenFlowTest
{
    IApp app ;

    public ScreenFlowTest()
    {
    }

    @Before
    public void setUp()
    {
        app = (IApp) Device.getNewInstance() ;
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void ScreenFlowTest1()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("$0.00", lines[7].trim());        
        app.display() ;
        app.touch(3,3) ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("[000000000]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        app.display() ;
    }

    @Test
    public void ScreenFlowTest2()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("$0.00", lines[7].trim());        
        app.display() ;
        app.touch(2,4) ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("Reload",          lines[6].trim()); 
        assertEquals("Refresh",         lines[7].trim()); 
        assertEquals("More Options",    lines[8].trim()); 
        assertEquals("Cancel",          lines[9].trim()); 
    }    

    @Test
    public void ScreenFlowTest3()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("$0.00", lines[7].trim());        
        app.display() ;
        app.touch(2,4) ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("Reload",          lines[6].trim()); 
        assertEquals("Refresh",         lines[7].trim()); 
        assertEquals("More Options",    lines[8].trim()); 
        assertEquals("Cancel",          lines[9].trim()); 
        app.touch(1, 7) ;
        assertEquals("My Cards", app.screen().trim());
    }        

    @Test
    public void ScreenFlowTest4()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("$0.00", lines[7].trim());        
        app.display() ;
        app.touch(2,4) ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("Reload",          lines[6].trim()); 
        assertEquals("Refresh",         lines[7].trim()); 
        assertEquals("More Options",    lines[8].trim()); 
        assertEquals("Cancel",          lines[9].trim()); 
        app.touch(2, 7) ;
        assertEquals("My Cards", app.screen().trim());
    }        

    @Test
    public void ScreenFlowTest5()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("$0.00", lines[7].trim());        
        app.display() ;
        app.touch(2,4) ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("Reload",          lines[6].trim()); 
        assertEquals("Refresh",         lines[7].trim()); 
        assertEquals("More Options",    lines[8].trim()); 
        assertEquals("Cancel",          lines[9].trim()); 
        app.touch(3, 7) ;
        assertEquals("My Cards", app.screen().trim());
    }        

    @Test
    public void ScreenFlowTest6()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("$0.00", lines[7].trim());        
        app.display() ;
        app.touch(3,3) ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("[000000000]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        app.display() ;
        app.touch(3,3) ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("$0.00", lines[7].trim());        
    }    

    @Test
    public void ScreenFlowTest7()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("$0.00", lines[7].trim());        
        app.display() ;
        for ( int x = 1; x<=3; x++ )
            for ( int y = 1; y<=2; y++ )
            {
                app.touch(x,y) ;
                assertEquals("My Cards", app.screen().trim());
            }
        app.touch(1,3) ;
        assertEquals("My Cards", app.screen().trim());
        app.touch(2,3) ;
        assertEquals("My Cards", app.screen().trim());
        app.touch(1,4) ;
        assertEquals("My Cards", app.screen().trim());
        app.touch(3,4) ;
        assertEquals("My Cards", app.screen().trim());
        for ( int x = 1; x<=3; x++ )
            for ( int y = 5; y<=8; y++ )
            {
                app.touch(x,y) ;
                assertEquals("My Cards", app.screen().trim());
            }
    }

    @Test
    public void ScreenFlowTest8()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        app.prev() ;
        assertEquals("My Cards", app.screen().trim());
        app.next() ;
        assertEquals("My Cards", app.screen().trim());
    }    

    @Test
    public void ScreenFlowTest9()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        app.execute("B") ;
        assertEquals("Payments", app.screen().trim());
        app.prev() ;
        assertEquals("Payments", app.screen().trim());
        app.next() ;
        assertEquals("Payments", app.screen().trim());
    }    

    @Test
    public void ScreenFlowTest10()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        app.execute("C") ;
        assertEquals("Rewards", app.screen().trim());
        app.prev() ;
        assertEquals("Rewards", app.screen().trim());
        app.next() ;
        assertEquals("Rewards", app.screen().trim());
    }      

    @Test
    public void ScreenFlowTest11()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        app.execute("D") ;
        assertEquals("Find Store", app.screen().trim());
        app.prev() ;
        assertEquals("Find Store", app.screen().trim());
        app.next() ;
        assertEquals("Find Store", app.screen().trim());
    }      

    @Test
    public void ScreenFlowTest12()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        app.execute("E") ;
        assertEquals("Settings", app.screen().trim());
        app.prev() ;
        assertEquals("Settings", app.screen().trim());
        app.next() ;
        assertEquals("Settings", app.screen().trim());
    }      

    @Test
    public void ScreenFlowTest13()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        // No Op Touches
        for ( int x = 1; x<=3; x++ )
            for ( int y = 1; y<=4; y++ )
                app.touch(x,y) ;
        assertEquals("", app.screen().trim());
    }      

    @Test
    public void ScreenFlowTest14()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        // No Op Touches
        for ( int x = 1; x<=3; x++ )
            for ( int y = 1; y<=4; y++ )
                if ( x != 3 && y != 3 )
                    if ( x != 2 && x!= 4 )
                        app.touch(x,y) ;
        assertEquals("My Cards", app.screen().trim());
    }      

    @Test
    public void ScreenFlowTest15()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        app.execute("B") ;
        assertEquals("Payments", app.screen().trim());
        // No Op Touches
        for ( int x = 1; x<=3; x++ )
            for ( int y = 1; y<=8; y++ )
                app.touch(x,y) ;
        assertEquals("Payments", app.screen().trim());
    }        

    @Test
    public void ScreenFlowTest16()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        app.execute("C") ;
        assertEquals("Rewards", app.screen().trim());
        // No Op Touches
        for ( int x = 1; x<=3; x++ )
            for ( int y = 1; y<=8; y++ )
                app.touch(x,y) ;
        assertEquals("Rewards", app.screen().trim());
    }        

    @Test
    public void ScreenFlowTest17()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        app.execute("D") ;
        assertEquals("Find Store", app.screen().trim());
        // No Op Touches
        app.touch(1,1) ;
        app.touch(2,1) ;
        app.touch(3,1) ;
        app.touch(1,8) ;
        app.touch(2,8) ;
        app.touch(3,8) ;
        assertEquals("Find Store", app.screen().trim());
    }        

    @Test
    public void ScreenFlowTest18()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        app.execute("E") ;
        assertEquals("Settings", app.screen().trim());
        // No Op Touches
        for ( int x = 1; x<=3; x++ )
            for ( int y = 2; y<=8; y++ )
                app.touch(x,y) ;
        assertEquals("Settings", app.screen().trim());
    }            

    @Test
    public void ScreenFlowTest19()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        app.touch(2,4) ;  
        assertEquals("My Cards", app.screen().trim());        
        // No Op Touches
        for ( int x = 1; x<=3; x++ )
            for ( int y = 1; y<=6; y++ )
                app.touch(x,y) ;
        assertEquals("My Cards", app.screen().trim());
    }            


    @Test
    public void ScreenFlowTest20()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        app.touch(2,4) ;  
        assertEquals("My Cards", app.screen().trim());      
        app.touch(1, 7) ;
        assertEquals("My Cards", app.screen().trim());        
        // No Op Touches
        for ( int x = 1; x<=3; x++ )
            for ( int y = 1; y<=8; y++ )
                app.touch(x,y) ;
        assertEquals("My Cards", app.screen().trim());
    }

    @Test
    public void ScreenFlowTest21()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        app.touch(2,4) ;  
        assertEquals("My Cards", app.screen().trim());      
        app.touch(2, 7) ;
        assertEquals("My Cards", app.screen().trim());        
        // No Op Touches
        for ( int x = 1; x<=3; x++ )
            for ( int y = 1; y<=8; y++ )
                app.touch(x,y) ;
        assertEquals("My Cards", app.screen().trim());
    }

    @Test
    public void ScreenFlowTest22()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("My Cards", app.screen().trim());
        app.touch(2,4) ;  
        assertEquals("My Cards", app.screen().trim());      
        app.touch(3, 7) ;
        assertEquals("My Cards", app.screen().trim());        
        // No Op Touches
        for ( int x = 1; x<=3; x++ )
            for ( int y = 1; y<=8; y++ )
                app.touch(x,y) ;
        assertEquals("My Cards", app.screen().trim());
    }
    
    
}

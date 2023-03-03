/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MenuTest
{
     IApp app ;
    
    public MenuTest()
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
    public void MenuTestTest1()
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
        app.execute("B") ;
        assertEquals("Payments", app.screen().trim());
        lines = app.screenContents().split("\n"); 
        assertEquals("Find Store", lines[7].trim());  
        assertEquals("Enable Payments", lines[8].trim());  
        app.display() ;
    }   
    
    @Test
    public void MenuTestTest2()
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
        app.execute("C") ;
        assertEquals("Rewards", app.screen().trim());
        lines = app.screenContents().split("\n"); 
        assertEquals("Make Every", lines[7].trim());  
        assertEquals("Visit Count", lines[8].trim());  
        app.display() ;
    }  
    
    @Test
    public void MenuTestTest3()
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
        app.execute("D") ;
        assertEquals("Find Store", app.screen().trim());
        lines = app.screenContents().split("\n"); 
        assertEquals("         X",   lines[4]);  
        assertEquals("   X",         lines[5]);  
        assertEquals("       X",     lines[6]);  
        assertEquals("      X",      lines[7]);  
        assertEquals("  X",          lines[8]);  
        assertEquals("           X", lines[9]);  
        assertEquals("  X",          lines[10]);  
        app.display() ;
    }  
    
    @Test
    public void MenuTestTest4()
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
        app.execute("E") ;
        assertEquals("Settings", app.screen().trim());
        app.display() ;
    }     
    
    @Test
    public void MenuTestTest5()
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
        
        app.execute("B") ;
        assertEquals("Payments", app.screen().trim());
        lines = app.screenContents().split("\n"); 
        assertEquals("Find Store", lines[7].trim());  
        assertEquals("Enable Payments", lines[8].trim());  
        app.display() ;
        
        app.execute("C") ;
        assertEquals("Rewards", app.screen().trim());
        lines = app.screenContents().split("\n"); 
        assertEquals("Make Every", lines[7].trim());  
        assertEquals("Visit Count", lines[8].trim());  
        app.display() ;
        
        app.execute("D") ;
        assertEquals("Find Store", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("         X",   lines[4]);  
        assertEquals("   X",         lines[5]);  
        assertEquals("       X",     lines[6]);  
        assertEquals("      X",      lines[7]);  
        assertEquals("  X",          lines[8]);  
        assertEquals("           X", lines[9]);  
        assertEquals("  X",          lines[10]);  
        app.display() ;        
        
        app.execute("E") ;
        assertEquals("Settings", app.screen().trim());
        app.display() ;
        
        app.execute("A") ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("$0.00", lines[7].trim());
        app.display() ;

        app.execute("E") ;
        assertEquals("Settings", app.screen().trim());
        app.display() ;
        
        app.execute("C") ;
        assertEquals("Rewards", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("Make Every", lines[7].trim());  
        assertEquals("Visit Count", lines[8].trim());  
        app.display() ;
        
        app.execute("D") ;
        assertEquals("Find Store", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("         X",   lines[4]);  
        assertEquals("   X",         lines[5]);  
        assertEquals("       X",     lines[6]);  
        assertEquals("      X",      lines[7]);  
        assertEquals("  X",          lines[8]);  
        assertEquals("           X", lines[9]);  
        assertEquals("  X",          lines[10]);  
        app.display() ; 
        
        app.execute("B") ;
        assertEquals("Payments", app.screen().trim());
        lines = app.screenContents().split("\n");
        assertEquals("Find Store", lines[7].trim());  
        assertEquals("Enable Payments", lines[8].trim());  
        app.display() ;        
    }  
    
}

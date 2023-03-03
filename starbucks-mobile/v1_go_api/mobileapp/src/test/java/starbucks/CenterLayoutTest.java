/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CenterLayoutTest
{
    IApp app ;

    public CenterLayoutTest()
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
    public void CenterLayoutTest1()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        app.display() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals('$', lines[7].charAt(5));        
        app.touch(3,3) ;
        app.display() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals('[', lines[6].charAt(2));
        assertEquals(']', lines[6].charAt(12));
        assertEquals('S', lines[9].charAt(4));
        assertEquals('N', lines[9].charAt(9));
    }

 
     @Test
    public void CenterLayoutTest2()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        app.display() ;
        assertEquals("My Cards", app.screen().trim());
        app.execute("E") ;
        app.display() ;
        assertEquals("Settings", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals('A', lines[4].charAt(4));
        assertEquals('D', lines[5].charAt(2));
        assertEquals('B', lines[6].charAt(4));
        assertEquals('P', lines[7].charAt(4));        
        assertEquals('A', lines[9].charAt(2));        
        assertEquals('H', lines[10].charAt(6));        
     }


     @Test
    public void CenterLayoutTest3()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        app.display() ;
        assertEquals("My Cards", app.screen().trim());
        app.execute("E") ;
        app.display() ;
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ;
        app.display() ;
        assertEquals("Add Card", app.screen().trim());        
        lines = app.screenContents().split("\n");  
        assertEquals('[', lines[4].charAt(7));
        assertEquals(']', lines[4].charAt(8));
        assertEquals('[', lines[5].charAt(7));
        assertEquals(']', lines[5].charAt(8));             
     }


    @Test
    public void CenterLayoutTest4()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        app.touch(1,5); // 1
        app.touch(2,5); // 2
        app.touch(3,5); // 3
        app.touch(1,6); // 4
        app.touch(2,6); // 5
        app.touch(3,6); // 6
        app.touch(1,7); // 7
        app.touch(2,7); // 8
        app.touch(3,7); // 9
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n"); 
        assertEquals("[123456789]", lines[4].trim());
        assertEquals("[999]", lines[5].trim());
        assertEquals('[', lines[4].charAt(2));
        assertEquals(']', lines[4].charAt(12));
        assertEquals('[', lines[5].charAt(5));
        assertEquals(']', lines[5].charAt(9));              
    }        
 

     @Test
    public void CenterLayoutTest5()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        app.touch(1,5); // 1
        app.touch(2,5); // 2
        app.touch(3,5); // 3
        app.touch(1,6); // 4
        app.touch(2,6); // 5
        app.touch(3,6); // 6
        app.touch(1,7); // 7
        app.touch(2,7); // 8
        app.touch(3,7); // 9
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n"); 
        assertEquals("[123456789]", lines[4].trim());
        assertEquals("[999]", lines[5].trim());
        // add card - see balance
        app.next() ;    
        app.display() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("$20.00", lines[7].trim());  
        assertEquals('$', lines[7].charAt(5));
        assertEquals('2', lines[7].charAt(6));
        assertEquals('0', lines[7].charAt(7));
        assertEquals('.', lines[7].charAt(8));                
        assertEquals('0', lines[7].charAt(9));
        assertEquals('0', lines[7].charAt(10));   
        app.touch(3,3) ;
        app.display() ;
        assertEquals("My Cards", app.screen().trim());
        lines = app.screenContents().split("\n"); 
        assertEquals("[123456789]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        assertEquals('[', lines[6].charAt(2));
        assertEquals('1', lines[6].charAt(3));
        assertEquals('2', lines[6].charAt(4));
        assertEquals('3', lines[6].charAt(5));
        assertEquals('4', lines[6].charAt(6));
        assertEquals('5', lines[6].charAt(7));
        assertEquals('6', lines[6].charAt(8));
        assertEquals('7', lines[6].charAt(9));
        assertEquals('8', lines[6].charAt(10));
        assertEquals('9', lines[6].charAt(11));
        assertEquals(']', lines[6].charAt(12));
        assertEquals('S', lines[9].charAt(4));
        assertEquals('c', lines[9].charAt(5));
        assertEquals('a', lines[9].charAt(6));
        assertEquals('n', lines[9].charAt(7));
        assertEquals(' ', lines[9].charAt(8));
        assertEquals('N', lines[9].charAt(9));
        assertEquals('o', lines[9].charAt(10));
        assertEquals('w', lines[9].charAt(11));
          
    }        
       
}

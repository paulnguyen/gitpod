/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddCardTest
{

    IApp app ;

    public AddCardTest()
    {
    }

    @Before
    public void setUp()
    {
        app = (IApp) Device.getNewInstance() ;
    }

    @Test
    public void AddCardTest1()
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
    }

    @Test
    public void AddCardTest2()
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
        app.touch(2,6); // 5
        app.touch(3,7); // 9
        app.touch(3,6); // 6
        app.touch(2,6); // 5
        app.touch(2,5); // 2
        app.touch(1,7); // 7
        app.touch(1,6); // 4
        app.touch(1,6); // 4
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(1,7); // 7
        app.touch(2,6); // 5
        app.touch(3,5); // 3
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("[159652744]", lines[4].trim());
        assertEquals("[753]", lines[5].trim());
    }

    @Test
    public void AddCardTest3()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        app.display() ;
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        app.display() ;
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        app.touch(1,5); // 1
        app.touch(2,8); // 0
        app.touch(3,7); // 9
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("[109]", lines[4].trim());
        app.touch(3,6); // 6
        app.touch(2,6); // 5
        app.touch(2,5); // 2
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("[109652]", lines[4].trim());
        app.touch(1,7); // 7
        app.touch(1,6); // 4
        app.touch(1,6); // 4
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("[109652744]", lines[4].trim());
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(1,7); // 7
        app.touch(2,6); // 5
        app.touch(3,5); // 3
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("[109652744]", lines[4].trim());
        assertEquals("[753]", lines[5].trim());
    }

    @Test
    public void AddCardTest4()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        app.display() ;
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        app.display() ;
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        app.touch(1,5); // 1
        app.touch(2,6); // 5
        app.touch(3,7); // 9
        app.touch(3,8); // X
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("[15]", lines[4].trim());        
        app.touch(3,6); // 6
        app.touch(2,6); // 5
        app.touch(2,5); // 2
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("[15652]", lines[4].trim());        
        app.touch(3,8); // X
        app.touch(3,8); // X
        app.touch(3,8); // X
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("[15]", lines[4].trim());        
    }

    @Test
    public void AddCardTest5()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        app.display() ;
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        app.display() ;
        assertEquals("Add Card", app.screen().trim());
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,8); // X
        app.touch(3,8); // X
        app.touch(3,8); // X
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("[]", lines[4].trim());
        app.touch(2,2); // focus on card id
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9        
        app.touch(2,3); // focus on card code
        app.touch(1,5); // 1
        app.touch(2,6); // 5
        app.touch(3,7); // 9
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("[159]", lines[5].trim());
    }

    @Test
    public void AddCardTest6()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        app.display() ;
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        app.display() ;
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        app.touch(1,5); // 1
        app.touch(2,5); // 2
        app.touch(3,5); // 3
        app.touch(1,6); // 4
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(2,2); // focus on card id
        // Card Id digits
        app.touch(2,6); // 5
        app.touch(3,6); // 6
        app.touch(1,7); // 7
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(3,8) ; // X
        app.touch(3,2); // focus on card id
        // Card Id digits
        app.touch(2,7); // 8
        app.touch(3,7); // 9
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("[123456789]", lines[4].trim());
        assertEquals("[99]", lines[5].trim());  
    }    

    @Test
    public void AddCardTest7()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        app.display() ;
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        app.display() ;
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        app.touch(1,5); // 1
        app.touch(2,5); // 2
        app.touch(3,5); // 3
        app.touch(1,6); // 4
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(2,2); // focus on card id
        // Card Id digits
        app.touch(2,6); // 5
        app.touch(3,6); // 6
        app.touch(1,7); // 7
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(3,2); // focus on card id
        // Card Id digits
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("[1234]", lines[4].trim());
        assertEquals("[]", lines[5].trim());  
    }    

    @Test
    public void AddCardTest8()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        app.display() ;
        assertEquals("Settings", app.screen().trim());
        app.touch(1,1) ; // Add New Card
        app.display() ;
        assertEquals("Add Card", app.screen().trim());
        // Card Id digits
        app.touch(1,5); // 1
        app.touch(2,5); // 2
        app.touch(3,5); // 3
        app.touch(1,6); // 4
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(2,2); // focus on card id
        // Card Id digits
        app.touch(2,6); // 5
        app.touch(3,6); // 6
        app.touch(1,7); // 7
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(3,2); // focus on card id
        // Card Id digits
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(3,8) ; // X
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("[1234]", lines[4].trim());
        assertEquals("[999]", lines[5].trim());  
    }    
    
    
    @After
    public void tearDown()
    {
    }
}

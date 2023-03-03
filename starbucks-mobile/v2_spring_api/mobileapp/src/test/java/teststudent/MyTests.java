/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package teststudent ;

import starbucks.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyTests
{
    IApp app ;
    
    /**
     * Get Char at Line/Column Position
     * @param  lines  Array of Lines
     * @param  line   Line (Index 0)
     * @param  column Column (Index 0)
     * @return        Char (as String)
     */
    private String getChar( String[] lines, int line, int column ) {
        String line_content = lines[line] ;
        return Character.toString(line_content.charAt(column))  ;
    }

    /**
     * Default constructor for test class ProxyPatternTest
     */
    public MyTests()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        app = (IApp) Device.getNewInstance() ;
    }

    /**
     * Pin Screen
     */
    @Test
    public void MyTests1()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.display() ;
        app.touch(1,5) ;
        lines = app.screenContents().split("\n"); 
        assertEquals("1", getChar(lines,7,3));
        assertEquals("2", getChar(lines,7,7));
        assertEquals("3", getChar(lines,7,11));
        assertEquals("4", getChar(lines,8,3));
        assertEquals("5", getChar(lines,8,7));
        assertEquals("6", getChar(lines,8,11));
        assertEquals("7", getChar(lines,9,3));
        assertEquals("8", getChar(lines,9,7));
        assertEquals("9", getChar(lines,9,11));
        assertEquals("_", getChar(lines,10,3));
        assertEquals("0", getChar(lines,10,7));
        assertEquals("X", getChar(lines,10,11));        
        app.touch(2,5) ;
        app.touch(1,5) ;
        app.touch(1,6) ;
        lines = app.screenContents().split("\n");  
        assertEquals("I", getChar(lines,3,2));
        assertEquals("Invalid Pin", lines[3].trim());
        app.touch(1,5) ;
        lines = app.screenContents().split("\n");  
        assertEquals("", lines[3].trim());
        app.touch(2,5) ;
        app.touch(3,5) ;
        lines = app.screenContents().split("\n");  
        assertEquals("1", getChar(lines,7,3));
        assertEquals("2", getChar(lines,7,7));
        assertEquals("3", getChar(lines,7,11));
        assertEquals("4", getChar(lines,8,3));
        assertEquals("5", getChar(lines,8,7));
        assertEquals("6", getChar(lines,8,11));
        assertEquals("7", getChar(lines,9,3));
        assertEquals("8", getChar(lines,9,7));
        assertEquals("9", getChar(lines,9,11));
        assertEquals("_", getChar(lines,10,3));
        assertEquals("0", getChar(lines,10,7));
        assertEquals("X", getChar(lines,10,11));        
        app.touch(1,6) ;
        assertEquals("My Cards", app.screen().trim());
    }

    /**
     * My Cards Screen
     */
    @Test
    public void MyTests2()
    {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        assertEquals("My Cards", app.screen().trim());
        app.display() ;
        lines = app.screenContents().split("\n"); 
        assertEquals("A", getChar(lines,14,1));
        assertEquals("B", getChar(lines,14,4));
        assertEquals("C", getChar(lines,14,7));
        assertEquals("D", getChar(lines,14,10));        
        assertEquals("E", getChar(lines,14,13));
    } 

    /**
     * My Cards Pay Screen
     */
    @Test
    public void MyTest3()
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
        app.touch(3,3) ;
        assertEquals("My Cards", app.screen().trim());
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("[000000000]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        assertEquals("A", getChar(lines,14,1));
        assertEquals("B", getChar(lines,14,4));
        assertEquals("C", getChar(lines,14,7));
        assertEquals("D", getChar(lines,14,10));        
        assertEquals("E", getChar(lines,14,13));
    }


    /**
     * Add New Card Screen
     */
    @Test
    public void MyTest4()
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
        lines = app.screenContents().split("\n"); 
        assertEquals("[]", lines[4].trim());
        assertEquals("[]", lines[5].trim());        
        // Invalid Card Id digits
        app.touch(1,2); // focus on card id
        app.touch(1,5); // 1
        app.touch(2,5); // 2
        app.touch(3,5); // 3
        app.touch(1,6); // 4
        app.touch(2,6); // 5
        // Good Card Code digits
        app.touch(2,3); // focus on card code
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.next();
        assertEquals("Add Card", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("[]", lines[4].trim());
        assertEquals("[]", lines[5].trim());        
        // Good Card Id digits
        app.touch(1,2); // focus on card id
        app.touch(1,5); // 1
        app.touch(2,5); // 2
        app.touch(3,5); // 3
        app.touch(1,6); // 4
        app.touch(2,6); // 5
        app.touch(3,6); // 6
        app.touch(1,7); // 7
        app.touch(2,7); // 8
        app.touch(3,7); // 9
        // Invalid Card Code digits
        app.touch(2,3); // focus on card code
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.next();
        assertEquals("Add Card", app.screen().trim());
        lines = app.screenContents().split("\n");  
        assertEquals("[]", lines[4].trim());
        assertEquals("[]", lines[5].trim());        
        // Good Card Id digits
        app.touch(1,2); // focus on card id
        app.touch(1,5); // 1
        app.touch(2,5); // 2
        app.touch(3,5); // 3
        app.touch(1,6); // 4
        app.touch(2,6); // 5
        app.touch(3,6); // 6
        app.touch(1,7); // 7
        app.touch(2,7); // 8
        app.touch(3,7); // 9
        app.touch(3,7); // 9 -- extra digit
        app.touch(3,7); // 9 -- extra digit
        app.touch(3,7); // 9 -- extra digit
        app.touch(3,7); // 9 -- extra digit
        app.touch(3,7); // 9 -- extra digit
        app.touch(3,7); // 9 -- extra digit
        app.touch(2,3); // focus on card code
        // Good Card Code digits
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9 -- extra digit
        app.touch(3,7); // 9 -- extra digit
        app.touch(3,7); // 9 -- extra digit
        app.touch(3,7); // 9 -- extra digit
        app.touch(3,7); // 9 -- extra digit
        app.touch(3,7); // 9 -- extra digit
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n"); 
        assertEquals("[123456789]", lines[4].trim());
        assertEquals("[999]", lines[5].trim());
    }

    /**
     * Card Options Screens
     */
    @Test
    public void MyTest5()
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
        app.display() ;
        lines = app.screenContents().split("\n");  
        System.err.println( lines ) ;
        assertEquals("O", getChar(lines,8,5));
        assertEquals("a", getChar(lines,9,1));
        assertEquals("A", getChar(lines,14,1));
        assertEquals("B", getChar(lines,14,4));
        assertEquals("C", getChar(lines,14,7));
        assertEquals("D", getChar(lines,14,10));        
        assertEquals("E", getChar(lines,14,13));
        app.touch(1,7) ;
        lines = app.screenContents().split("\n");  
        assertEquals("My Cards", app.screen().trim());
        app.display() ;
        assertEquals("Refresh",        lines[6].trim()); 
        assertEquals("Reload",         lines[7].trim()); 
        assertEquals("Auto Reload",    lines[8].trim()); 
        assertEquals("Transactions",   lines[9].trim());         
        assertEquals("e", getChar(lines,6,1));   
        assertEquals("a", getChar(lines,9,5));  
        assertEquals("f", getChar(lines,6,2));   
        assertEquals("A", getChar(lines,14,1));
        assertEquals("B", getChar(lines,14,4));
        assertEquals("C", getChar(lines,14,7));
        assertEquals("D", getChar(lines,14,10));        
        assertEquals("E", getChar(lines,14,13));

    }        

    /**
     * Payments Screen
     */
    @Test
    public void MyTest6()
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
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("S", getChar(lines,7,5));
        assertEquals("P", getChar(lines,8,7));
        assertEquals("A", getChar(lines,14,1));
        assertEquals("B", getChar(lines,14,4));
        assertEquals("C", getChar(lines,14,7));
        assertEquals("D", getChar(lines,14,10));        
        assertEquals("E", getChar(lines,14,13));

    }    

    /**
     * Rewards Screen
     */
    @Test
    public void MyTest7()
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
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("i", getChar(lines,8,3));
        assertEquals("a", getChar(lines,7,1));
        assertEquals("A", getChar(lines,14,1));
        assertEquals("B", getChar(lines,14,4));
        assertEquals("C", getChar(lines,14,7));
        assertEquals("D", getChar(lines,14,10));        
        assertEquals("E", getChar(lines,14,13));        
    }      

    /**
     * Find Store Screen
     */
    @Test
    public void MyTest8()
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
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("X", getChar(lines,4,9));
        assertEquals("X", getChar(lines,5,3));
        assertEquals("X", getChar(lines,6,7));
        assertEquals("X", getChar(lines,7,6));
        assertEquals("X", getChar(lines,8,2));
        assertEquals("X", getChar(lines,9,11));
        assertEquals("X", getChar(lines,10,2));
        assertEquals("A", getChar(lines,14,1));
        assertEquals("B", getChar(lines,14,4));
        assertEquals("C", getChar(lines,14,7));
        assertEquals("D", getChar(lines,14,10));        
        assertEquals("E", getChar(lines,14,13));        
    }

    /**
     * Settings Screen
     */
   @Test
    public void MyTest9() {
        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen().trim());
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("Add Card",    lines[4].trim()); 
        assertEquals("Delete Card", lines[5].trim()); 
        assertEquals("Billing",     lines[6].trim()); 
        assertEquals("Passcode",    lines[7].trim());  
        assertEquals("About|Terms", lines[9].trim());  
        assertEquals("Help",        lines[10].trim());             
        assertEquals("A", getChar(lines,14,1));
        assertEquals("B", getChar(lines,14,4));
        assertEquals("C", getChar(lines,14,7));
        assertEquals("D", getChar(lines,14,10));        
        assertEquals("E", getChar(lines,14,13));        
    }

    /**
     * Add Card Screen
     */
    @Test
    public void MyTest10()
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
        lines = app.screenContents().split("\n");  
        assertEquals("1", getChar(lines,7,3));
        assertEquals("2", getChar(lines,7,7));
        assertEquals("3", getChar(lines,7,11));
        assertEquals("4", getChar(lines,8,3));
        assertEquals("5", getChar(lines,8,7));
        assertEquals("6", getChar(lines,8,11));
        assertEquals("7", getChar(lines,9,3));
        assertEquals("8", getChar(lines,9,7));
        assertEquals("9", getChar(lines,9,11));
        assertEquals("_", getChar(lines,10,3));
        assertEquals("0", getChar(lines,10,7));
        assertEquals("X", getChar(lines,10,11));        
        assertEquals("A", getChar(lines,14,1));
        assertEquals("B", getChar(lines,14,4));
        assertEquals("C", getChar(lines,14,7));
        assertEquals("D", getChar(lines,14,10));        
        assertEquals("E", getChar(lines,14,13));        
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
        app.display() ;
        assertEquals("[123456789]", lines[4].trim());
        assertEquals("[999]", lines[5].trim());
        assertEquals("", lines[3].trim());
        assertEquals("", lines[6].trim());
        assertEquals("", lines[11].trim());
        assertEquals("", lines[12].trim());
     }


    // Chirag Arora 
    @Test
    public void MyTest11()
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
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(1,7); // 7
        app.touch(2,6); // 5
        app.touch(3,8); // X
        app.touch(3,8); // X
        app.touch(3,8); // X
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[]", lines[4].trim());
        assertEquals("[]", lines[5].trim());
    }


    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}

/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LandscapeSupportTest
{
    IApp app ;

    public LandscapeSupportTest()
    {
    }

    @Before
    public void setUp()
    {
        app = (IApp) Device.getNewInstance() ;
    }

    @Test
    public void LandscapeSupportTest1()
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
        assertEquals("===============", lines[0].trim() );
        assertEquals('M', lines[1].charAt(4));
        assertEquals("===============", lines[2].trim() );
        app.landscape();
        assertEquals("My Cards", app.screen().trim());
        app.execute( "A" ) ;
        app.execute( "B" ) ;
        app.execute( "C" ) ;
        app.execute( "D" ) ;
        app.execute( "E" ) ;  
        assertEquals("My Cards", app.screen().trim());  
        app.display() ;
        lines = app.screenContents().split("\n"); 
        assertEquals("================================", lines[0].trim() );
        assertEquals('M', lines[1].charAt(12));
        assertEquals('$', lines[5].charAt(14));        
        assertEquals("================================", lines[2].trim() );              
    }


    @Test
    public void LandscapeSupportTest2()
    {

        String[] lines ;
        assertEquals("", app.screen().trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        assertEquals("My Cards", app.screen().trim());

        app.touch(3,3) ;
        app.display() ;
        lines = app.screenContents().split("\n"); 
        assertEquals("===============", lines[0].trim() );
        assertEquals('M', lines[1].charAt(4));
        assertEquals("===============", lines[2].trim() );
        assertEquals("My Cards", app.screen().trim());
        
        assertEquals("[000000000]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        assertEquals('[', lines[6].charAt(2));
        assertEquals('0', lines[6].charAt(3));
        assertEquals('0', lines[6].charAt(4));
        assertEquals('0', lines[6].charAt(5));
        assertEquals('0', lines[6].charAt(6));
        assertEquals('0', lines[6].charAt(7));
        assertEquals('0', lines[6].charAt(8));
        assertEquals('0', lines[6].charAt(9));
        assertEquals('0', lines[6].charAt(10));
        assertEquals('0', lines[6].charAt(11));
        assertEquals(']', lines[6].charAt(12));
        assertEquals('S', lines[9].charAt(4));
        assertEquals('c', lines[9].charAt(5));
        assertEquals('a', lines[9].charAt(6));
        assertEquals('n', lines[9].charAt(7));
        assertEquals(' ', lines[9].charAt(8));
        assertEquals('N', lines[9].charAt(9));
        assertEquals('o', lines[9].charAt(10));
        assertEquals('w', lines[9].charAt(11));

        app.landscape();

        app.execute("A") ;
        app.execute("B") ;
        app.execute("C") ;
        app.execute("D") ;
        app.execute("E") ;

        assertEquals("My Cards", app.screen().trim());
        app.display() ;
        lines = app.screenContents().split("\n"); 
        assertEquals("================================", lines[0].trim() );
        assertEquals('M', lines[1].charAt(12));      
        assertEquals("================================", lines[2].trim() );      

        assertEquals("[000000000]", lines[4].trim());
        assertEquals("Scan Now", lines[7].trim());
        assertEquals('[', lines[4].charAt(11));
        assertEquals('0', lines[4].charAt(12));
        assertEquals('0', lines[4].charAt(13));
        assertEquals('0', lines[4].charAt(14));
        assertEquals('0', lines[4].charAt(15));
        assertEquals('0', lines[4].charAt(16));
        assertEquals('0', lines[4].charAt(17));
        assertEquals('0', lines[4].charAt(18));
        assertEquals('0', lines[4].charAt(19));
        assertEquals('0', lines[4].charAt(20));
        assertEquals(']', lines[4].charAt(21));
        assertEquals('S', lines[7].charAt(12));
        assertEquals('c', lines[7].charAt(13));
        assertEquals('a', lines[7].charAt(14));
        assertEquals('n', lines[7].charAt(15));
        assertEquals(' ', lines[7].charAt(16));
        assertEquals('N', lines[7].charAt(17));
        assertEquals('o', lines[7].charAt(18));
        assertEquals('w', lines[7].charAt(19));

    }

    @Test
    public void LandscapeSupportTest3()
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
        assertEquals("===============", lines[0].trim() );
        assertEquals('M', lines[1].charAt(4));
        assertEquals("===============", lines[2].trim() );
        app.landscape();
        app.display() ;
        lines = app.screenContents().split("\n"); 
        assertEquals("================================", lines[0].trim() );
        assertEquals('M', lines[1].charAt(12));
        assertEquals('$', lines[5].charAt(14));        
        assertEquals("================================", lines[2].trim() );    
        app.portrait() ;

        app.execute("B") ;
        assertEquals("Payments", app.screen().trim());
        app.display() ;
        lines = app.screenContents().split("\n"); 
        assertEquals("===============", lines[0].trim() );
        assertEquals('P', lines[1].charAt(4));
        assertEquals("===============", lines[2].trim() );
        app.landscape();
        app.display() ;
        lines = app.screenContents().split("\n"); 
        lines = app.screenContents().split("\n"); 
        assertEquals("===============", lines[0].trim() );
        assertEquals('P', lines[1].charAt(4));
        assertEquals("===============", lines[2].trim() );
        app.portrait() ;

    }


  @Test
    public void LandscapeSupportTest4()
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
        assertEquals("===============", lines[0].trim() );
        assertEquals('M', lines[1].charAt(4));
        assertEquals("===============", lines[2].trim() );
        app.landscape();
        app.display() ;
        lines = app.screenContents().split("\n"); 
        assertEquals("================================", lines[0].trim() );
        assertEquals('M', lines[1].charAt(12));
        assertEquals('$', lines[5].charAt(14));        
        assertEquals("================================", lines[2].trim() );    
        app.portrait() ;

        app.execute("C") ;
        assertEquals("Rewards", app.screen().trim());
        app.display() ;
        lines = app.screenContents().split("\n"); 
        assertEquals("===============", lines[0].trim() );
        assertEquals('R', lines[1].charAt(4));
        assertEquals("===============", lines[2].trim() );
        app.landscape();
        app.display() ;
        lines = app.screenContents().split("\n"); 
        lines = app.screenContents().split("\n"); 
        assertEquals("===============", lines[0].trim() );
        assertEquals('R', lines[1].charAt(4));
        assertEquals("===============", lines[2].trim() );
        app.portrait() ;

    }

    @Test
    public void LandscapeSupportTest5()
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
        assertEquals("===============", lines[0].trim() );
        assertEquals('M', lines[1].charAt(4));
        assertEquals("===============", lines[2].trim() );
        app.landscape();
        app.display() ;
        lines = app.screenContents().split("\n"); 
        assertEquals("================================", lines[0].trim() );
        assertEquals('M', lines[1].charAt(12));
        assertEquals('$', lines[5].charAt(14));        
        assertEquals("================================", lines[2].trim() );    
        app.portrait() ;

        app.execute("D") ;
        assertEquals("Find Store", app.screen().trim());
        app.display() ;
        lines = app.screenContents().split("\n"); 
        assertEquals("===============", lines[0].trim() );
        assertEquals('F', lines[1].charAt(3));
        assertEquals("===============", lines[2].trim() );
        app.landscape();
        app.display() ;
        lines = app.screenContents().split("\n"); 
        lines = app.screenContents().split("\n"); 
        assertEquals("===============", lines[0].trim() );
        assertEquals('F', lines[1].charAt(3));
        assertEquals("===============", lines[2].trim() );
        app.portrait() ;

        app.execute("E") ;
        assertEquals("Settings", app.screen().trim());
        app.display() ;
        lines = app.screenContents().split("\n"); 
        assertEquals("===============", lines[0].trim() );
        assertEquals('S', lines[1].charAt(4));
        assertEquals("===============", lines[2].trim() );
        app.landscape();
        app.display() ;
        lines = app.screenContents().split("\n"); 
        lines = app.screenContents().split("\n"); 
        assertEquals("===============", lines[0].trim() );
        assertEquals('S', lines[1].charAt(4));
        assertEquals("===============", lines[2].trim() );
        app.portrait() ;

    }

    @After
    public void tearDown()
    {
    }
}

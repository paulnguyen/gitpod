/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

/**
 * Frame Class -- Container for Screens
 * Frame Class Refactored for Fall 2019
 * 
 */
public class Frame implements IFrame
{
    private IScreen current ;
    private IMenuInvoker menuA = new MenuOption() ;
    private IMenuInvoker menuB = new MenuOption() ;
    private IMenuInvoker menuC = new MenuOption() ;
    private IMenuInvoker menuD = new MenuOption() ;
    private IMenuInvoker menuE = new MenuOption() ;
    private IOrientationStrategy portraitStrategy ;
    private IOrientationStrategy landscapeStrategy ;
    private IOrientationStrategy currentStrategy ;

    /**
     * Return Screen Name
     * @return Screen Name
     */
    public String screen() { return current.name() ; }

    /** Switch to Landscape Strategy */
    public void landscape() { 

        System.err.println( "Supports Landscape? " + current.getClass().getName() + " => " + current.supportsLandscape() ) ;

        if ( current.supportsLandscape() ) {
            Device.getInstance().setLandscapeOrientation() ;
            currentStrategy = landscapeStrategy ; 
        } else {
            currentStrategy = portraitStrategy ; 
        }
        
    }

    /** Added Fall 2019 */
    public boolean supportsLandscape() {
        return current.supportsLandscape() ;
    }

    /** Switch to Portrait Strategy */
    public void portrait()  {       
        Device.getInstance().setPortraitOrientation() ;        
        currentStrategy = portraitStrategy ; 
    }  

    /** Nav to Previous Screen */
    public void previousScreen() {
        current.prev() ;
    }

    /** Nav to Next Screen */
    public void nextScreen() {
        current.next() ;
    }
         

    /** Constructor */
    public Frame(IScreen initial)
    {
        current = initial ;

        portraitStrategy = new IOrientationStrategy() 
        {
                        
            /*
            
             *     AddCard
             *  ===============
             *  
             *  [C] AddCard [A]
             *  ===============

             */
            
            /**
             * Display Screen Contents
             * @param s Reference to Screen
             */
            public void display(IScreen s)
            {
                System.out.println( contents(s) ) ;
            }         

            /**
             * Return String / Lines for Frame and Screen
             * @param  s [description]
             * @return   [description]
             */
            public String contents(IScreen s) 
            { 
                String out = "" ;
                out += "===============\n" ;
                out += s.name() ;
                out += "===============\n" ;
                out += s.display() ;
                out +=  "===============\n" ;
                out +=  "[A][B][C][D][E]\n" ;
                Device.dumpLines( out ) ;
                return out ;             
            }

            /** Select Command A */
            public void selectA() { menuA.invoke() ; }

            /** Select Command B */
            public void selectB() { menuB.invoke() ; }

            /** Select Command C */
            public void selectC() { menuC.invoke() ; }

            /** Select Command D */
            public void selectD() { menuD.invoke() ; }

            /** Select Command E */
            public void selectE() { menuE.invoke() ; }

        } ;

        landscapeStrategy = new IOrientationStrategy() 
        {
           /**
             * Display Screen Contents
             * @param s Reference to Screen
             */
             public void display(IScreen s)
            {
                System.out.println( contents(s) ) ;           
            }         

            /**
             * Display Contents of Frame + Screen 
             * @param  s Screen to Display
             * @return   Contents for Screen
             */
            public String contents(IScreen s) 
            { 
                String out = "" ;
                out += "================================\n" ;
                out += s.name() ;
                out += "================================\n" ;
                out += s.display() ;
                out += "================================\n" ;
                Device.dumpLines( out ) ;
                return out ;
            }

            /** Don't Respond in Landscaope Mode */
            public void selectA() {  }

            /** Don't Respond in Landscaope Mode */
            public void selectB() {  }

            /** Don't Respond in Landscaope Mode */
            public void selectC() {  }

            /** Don't Respond in Landscaope Mode */
            public void selectD() {  }

            /** Don't Respond in Landscaope Mode */
            public void selectE() {  }
        } ;     

        /* set default layout strategy */
        currentStrategy = portraitStrategy ;
    }

    /**
     * Change the Current Screen
     * @param s Screen Object
     */
    public void setCurrentScreen( IScreen s )
    {
        current = s ;
    }

    /**
     * Configure Selections for Command Pattern 
     * @param slot A, B, ... E
     * @param c    Command Object
     */
    public void setMenuItem( String slot, IMenuCommand c )
    {
        if ( "A".equals(slot) ) { menuA.setCommand(c) ;  }
        if ( "B".equals(slot) ) { menuB.setCommand(c) ; }
        if ( "C".equals(slot) ) { menuC.setCommand(c) ; }
        if ( "D".equals(slot) ) { menuD.setCommand(c) ; } 
        if ( "E".equals(slot) ) { menuE.setCommand(c) ; }   
    }

    /** 
     * Send Touch Event
     * @param x X Coord
     * @param y Y Coord
     */
    public void touch(int x, int y)
    {

        if ( current != null )
            current.touch(x,y) ;
    }

    /**
     * Get Contents of the Frame + Screen 
     * @return Frame + Screen Contents
     */
    public String contents() 
    { 
        if ( current != null )
        {
            return currentStrategy.contents( current ) ; 
        } 
        else 
        {
            return "" ;
        }
    }

    /** Display Contents of Frame + Screen */
    public void display()
    {
        if ( current != null )
        {
            currentStrategy.display( current ) ;
        }
    }

    /**
     *  Execute a Command 
     * @param c Command
     */
    public void cmd( String c ) 
    {
        if ( "A".equals(c) ) { selectA() ; }
        if ( "B".equals(c) ) { selectB() ; }
        if ( "C".equals(c) ) { selectC() ; }
        if ( "D".equals(c) ) { selectD() ; }        
        if ( "E".equals(c) ) { selectE() ; }        
    }

    /** Select Command A */
    public void selectA() { currentStrategy.selectA() ;  }

    /** Select Command B */
    public void selectB() { currentStrategy.selectB() ;  }

    /** Select Command C */
    public void selectC() { currentStrategy.selectC() ;  }

    /** Select Command D */
    public void selectD() { currentStrategy.selectD() ;  }

    /** Select Command E */
    public void selectE() { currentStrategy.selectE() ;  }    
}


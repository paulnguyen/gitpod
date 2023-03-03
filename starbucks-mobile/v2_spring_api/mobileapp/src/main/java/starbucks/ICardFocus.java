/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

/**
 * Card Focus Interface
 */
public interface ICardFocus
{
    /** Set Focus to Me */
    void setFocus();

    /** Remove Focus on Me */
    void unsetFocus();

    /** Reset Content */
    void reset();

    /**
     * Get Display Component Contents
     * @return Display Conents
     */
    String display() ;
}

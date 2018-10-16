/**	
 * CS A272		
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.builders.JUnit4Builder;

import edu.orangecoastcollege.cs272.model.Armor;

/**
 * The <code>TestArmor</code> class tests the validity and functionality of the <code>Armor</code>
 * class utilizing JUnit.
 *
 * @author Vincent Nguyen
 * @version 1.0
 */
public class TestArmor extends JUnit4Builder {
	private static Armor mArmor;
	static {
		try {
			  mArmor = new Armor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints out a message notifying that the testing is about to commence.
	 * @throws Exception If an error occurs.
	 */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	System.out.println("Commencing Armor Testing...");
    }

	/**
	 * Prints out a message notifying that the testing is completed.
	 * @throws Exception If an error occurs.
	 */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    	System.out.println("Armor Testing Completed!");
    }

    /**
     * Unused
     * @throws Exception Unused
     */
    @Before
    public void setUp() throws Exception {}

    /**
     * Unused
     * @throws Exception Unused
     */
    @After
    public void tearDown() throws Exception { }

	/**
	 * Tests the getArmorRating method, in which it should return 1.
	 * @throws Exception If an error occurs.
	 */
    @Test
    public void testGetArmorRating() {
        assertEquals("Testing the retrieval of armor rating.", 1, mArmor.getArmorRating());
    }
    
	/**
	 * Tests the setArmorRating method, in which it should set the armor rating to 5.
	 * @throws Exception If an error occurs.
	 */
    @Test
    public void testSetArmorRating() {
    	mArmor.setArmorRating(5);
        assertEquals("Testing the setting of armor rating.", 5, mArmor.getArmorRating());
    }
    
	/**
	 * Tests the equals method, in which it should return true.
	 * @throws Exception If an error occurs.
	 */
    @Test
    public void testEquals() {
    	mArmor.setArmorRating(1);
    	final Armor tempArmor = new Armor();
    	assertEquals("Testing the validity of armor equality checking...", mArmor.equals(tempArmor));
    }
    
	/**
	 * Tests the toString method, in which it should not be an empty String.
	 * @throws Exception If an error occurs.
	 */
    @Test
    public void testToString() {
    	assertNotEquals("Testing the validity of armor toString...", "", mArmor.toString());
    }
}
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

import edu.orangecoastcollege.cs272.model.Weapon;

/**
 * The <code>TestWeapon</code> class tests the validity and functionality of the <code>Weapon</code>
 * class utilizing JUnit.
 *
 * @author Vu Nguyen
 * @version 1.0
 */
public class TestWeapon extends JUnit4Builder {
	private static Weapon mWeapon;
	static {
		try {
			mWeapon = new Weapon();
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
    	System.out.println("Commencing Weapon Testing...");
    }

	/**
	 * Prints out a message notifying that the testing is completed.
	 * @throws Exception If an error occurs.
	 */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    	System.out.println("Weapon Testing Completed!");
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
    public void testGetAttackPoints() {
        assertEquals("Testing the retrieval of weapon rating.", 1, mWeapon);
    }
    
	/**
	 * Tests the setArmorRating method, in which it should set the armor rating to 5.
	 * @throws Exception If an error occurs.
	 */
    @Test
    public void testSetAttackPoints() {
    	mWeapon.setAttackPoints(5);
        assertEquals("Testing the setting of weapon rating.", 5, mWeapon.getAttackPoints());
    }
    
	/**
	 * Tests the equals method, in which it should return true.
	 * @throws Exception If an error occurs.
	 */
    @Test
    public void testEquals() {
    	mWeapon.setAttackPoints(1);
    	final Weapon tempWeapon = new Weapon();
    	assertEquals("Testing the validity of weapon equality checking...", mWeapon.equals(tempWeapon));
    }
    
	/**
	 * Tests the toString method, in which it should not be an empty String.
	 * @throws Exception If an error occurs.
	 */
    @Test
    public void testToString() {
    	assertNotEquals("Testing the validity of weapon toString...", "", mWeapon.toString());
    }
}
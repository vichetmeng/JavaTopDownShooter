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

import edu.orangecoastcollege.cs272.model.Role;
import edu.orangecoastcollege.cs272.model.User;

/**
 * The <code>TestUser</code> class tests the validity and functionality of the <code>User</code>
 * class utilizing JUnit.
 *
 * @author Vichet Meng
 * @version 1.0
 */
public class TestUser extends JUnit4Builder {
	private static User mUser;
	static {
		try {
			  mUser = new User(1, "Brother Flab", "1", Role.ADMINISTRATOR);
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
    	System.out.println("Commencing User Testing...");
    }

	/**
	 * Prints out a message notifying that the testing is completed.
	 * @throws Exception If an error occurs.
	 */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    	System.out.println("User Completed!");
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
	 * Tests the getID method, in which it should return 1.
	 * @throws Exception If an error occurs.
	 */
    @Test
    public void testGetID() {
        assertEquals("Testing the retrieval of ID.", 1, mUser.getId());
    }
    
	/**
	 * Tests the getUsername method, in which it should return Brother Flab.
	 * @throws Exception If an error occurs.
	 */
    @Test
    public void testGetUsername() {
        assertEquals("Testing the retrieval of username.", "Brother Flab", mUser.getUserName());
    }
    
	/**
	 * Tests the setUsername method, in which it should set the username to Vincent.
	 * @throws Exception If an error occurs.
	 */
    @Test
    public void testSetUsername() {
    	mUser.setUserName("Vincent");
        assertEquals("Testing the setting of username.", "Vincent", mUser.getUserName());
    }
    
	/**
	 * Tests the getRole method, in which it should return Role.ADMINISTRATOR.
	 * @throws Exception If an error occurs.
	 */
    @Test
    public void testGetRole() {
        assertEquals("Testing the retrieval of ID.", Role.ADMINISTRATOR, mUser.getRole());
    }
    
	/**
	 * Tests the setRole method, in which it should set the role to Role.STANDARD.
	 * @throws Exception If an error occurs.
	 */
    @Test
    public void testSetRole() {
    	mUser.setRole(Role.STANDARD);
        assertEquals("Testing the setting of armor rating.", Role.STANDARD, mUser.getRole());
    }
    
	/**
	 * Tests the equals method, in which it should return true.
	 * @throws Exception If an error occurs.
	 */
    @Test
    public void testEquals() {
    	final User tempUser = new User(1, "Brother Flab", "1", Role.ADMINISTRATOR);
    	assertEquals("Testing the validity of user equality checking...", mUser.equals(tempUser));
    }
    
	/**
	 * Tests the toString method, in which it should not be an empty String.
	 * @throws Exception If an error occurs.
	 */
    @Test
    public void testToString() {
    	assertNotEquals("Testing the validity of armor toString...", "", mUser.toString());
    }
}
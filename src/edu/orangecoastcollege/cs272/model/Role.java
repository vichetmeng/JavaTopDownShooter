/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.model;

/** 
 * The <code>Role</code> enum represents the permissions level a specific <code>User</code> account has.
 * Standard represents a basic/regular <code>User</code>, while Administrator represents the highest level
 * of authority available.
 *
 * @author Vincent Nguyen
 * @version 1.0
*/
public enum Role {
	STANDARD, ADMINISTRATOR, UNLISTED;
}
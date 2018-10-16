/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.model;

/**
 * The <code>Attackable</code> enum is for <code>Player</code> and all related
 * classes to use, in which depending on the type will enable bonus stats/damage
 * when used with <code>Skill</code>s.
 * 
 * @author Vichet Meng
 * @version 1.0
 */
public interface Attackable {
	public double getAttackPower();
}
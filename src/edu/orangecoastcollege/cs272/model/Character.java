/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.model;

import javafx.scene.image.Image;

/**
 * The <code>Character</code> class stores information in regards to a <code>User</code>'s in-game
 * character.
 * 
 * @author Vichet Meng
 * @version 1.0
 */
public abstract class Character extends Object implements Attackable {
	/**
	 * The class constructor that initializes all the instance
	 * variables in the class.
	 * @param The ID to set.
	 * @param x The x-coordinate to set.
	 * @param y The y-coordinate to set.
	 * @param name The name to set.
	 * @param Image The image to set.
	 */
	protected Character(int id, double x, double y, String name, Image image) {
		super(id, x, y, name, image);
	}
	
	/**
	 * The class constructor that initializes all the instance
	 * variables in the class
	 * @param x The x-coordinate to set.
	 * @param y The y-coordinate to set.
	 * @param name The name to set.
	 * @param Image The image to set.
	 */
	protected Character(double x, double y, String name, Image image) {
		super(x, y, name, image);
	}
	
	/**
	 * Default <code>Character</code> constructor, initializing all fields to default values;
	 */
	protected Character() {
		super();
	}
}
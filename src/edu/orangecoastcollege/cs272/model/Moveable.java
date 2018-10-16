/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.model;

import javafx.scene.Scene;

/**
 * The <code>Moveable</code> interface is for character-based classes such as
 * <code>Player</code> and <code>Enemy</code> objects, which could move freely
 * around a given map.
 * 
 * @author Vichet Meng
 * @version 1.0
 */
public interface Moveable {
	public abstract void moveTo(double dx, double dy, Scene parent);
	public abstract void moveBy(double dx, double dy, Scene parent);
}
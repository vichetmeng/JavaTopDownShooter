/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.model;

import javafx.scene.image.Image;

/**
 * The <code>Object</code> class stores information in regards to an object, whether it be
 * an <code>Enemy</code>, a <code>Player</code>, etc.
 * 
 * @author Vichet Meng
 * @version 1.0
 */
public abstract class Object {
	protected int mID;
	protected String mName;
	protected double mX;
	protected double mY;
	protected Image mImage;
	
	/**
	 * Parameterized <code>User</code> constructor that initializes all fields to the given parameters.
	 * @param x The x-coordinate to set.
	 * @param y The y-coordinate to set.
	 * @param name The name to set.
	 * @param image The image to set.
	 */
	protected Object(double x, double y, String name, Image image) {
		mX = x;
		mY = y;
		mName = name;
		mImage = image;
	}
	
	/**
	 * Parameterized <code>User</code> constructor that initializes all fields to the given parameters. ID
	 * is passed as a parameter.
	 * @param id The ID to set.
	 * @param x The x-coordinate to set.
	 * @param y The -coordinate to set.
	 * @param name The name  to set.
	 * @param image The image to set.
	 */
	protected Object(int id, double x, double y, String name, Image image) {
		mID = id;
		mX = x;
		mY = y;
		mName = name;
		mImage = image;
	}
	
	/**
	 * Default <code>Armor</code> constructor, initializing all fields to default values;
	 */
	protected Object() {
		super();
	}

	/**
	 * Returns the iD of the <code>Object</code>
	 * @return iD The iD
	 */
	public final int getID() {
		return this.mID;
	}

	/**
	 * Sets the current iD to the parameter value
	 * @param iD The iD to set
	 */
	@SuppressWarnings("unused")
	private final void setID(int iD) {
		this.mID = iD;
	}

	/**
	 * Accessor method that return the image
	 * @return the image
	 */
	public Image getImage() {
		return mImage;
	}

	/**
	 * Mutator method that sets the image of the class
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.mImage = image;
	}

	/**
	 * Accessor method that return the name
	 * @return the name
	 */
	public String getName() {
		return mName;
	}

	/**
	 * Mutator method that sets the name of the class
	 * @param name the name to set
	 */
	public void setName(String name) {
		mName = name;
	}

	/**
	 * Accessor method that return the x
	 * @return the x
	 */
	public double getX() {
		return mX;
	}

	/**
	 * Mutator method that sets the x of the class
	 * @param x the x to set
	 */
	public void setX(double x) {
		mX = x;
	}

	/**
	 * Accessor method that return the y
	 * @return the y
	 */
	public double getY() {
		return mY;
	}

	/**
	 * Mutator method that sets the y of the class.
	 * @param y The y-coordinate to set
	 */
	public void setY(double y) {
		mY = y;
	}
	
	/**
	 * Mutator method that sets the x and the y of the class.
	 * @param x The x-coordinate to set.
	 * @param y The y-coordinate to set.
	 */
	public void setPos(double x, double y) {
		mX = x;
		mY = y;
	}

	/**
	 * Generates a unique integer prime number/primary key for each <code>User</code> object.
	 * @return The uniquely generated number.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mName == null) ? 0 : mName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(mX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Compares one <code>User</code> object to another, checking for equality between the two.
	 * @param obj The other <code>User</code> object to compare to.
	 * @return True if the same, false if not.
	 */
	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Object other = (Object) obj;
		if (mName == null) {
			if (other.mName != null)
				return false;
		} else if (!mName.equals(other.mName))
			return false;
		if (Double.doubleToLongBits(mX) != Double.doubleToLongBits(other.mX))
			return false;
		if (Double.doubleToLongBits(mY) != Double.doubleToLongBits(other.mY))
			return false;
		return true;
	}
}
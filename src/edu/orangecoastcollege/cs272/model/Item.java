/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.model;

import javafx.scene.image.Image;

/**
 * 
 * @author Vichet Meng
 * @version 1.0
 */
public abstract class Item extends Object {
	
	private double mWeight;
	
	/**
	 * The class constructor that initializes all the instance
	 * variables in the class
	 * @param x
	 * @param y
	 */
	protected Item(double x, double y, String name, double weight, Image image) {
		super(x, y, name, image);
		mWeight = weight;
	}

	/**
	 * Returns the weight of the <code>Item</code>
	 * @return weight The weight
	 */
	public final double getWeight() {
		return this.mWeight;
	}

	/**
	 * Sets the current weight to the parameter value
	 * @param weight The weight to set
	 */
	public final void setWeight(double weight) {
		this.mWeight = weight;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(mWeight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (Double.doubleToLongBits(mWeight) != Double.doubleToLongBits(other.mWeight))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Item [Name=" + mName + ", Weight=" + mWeight + "]";
	}
}
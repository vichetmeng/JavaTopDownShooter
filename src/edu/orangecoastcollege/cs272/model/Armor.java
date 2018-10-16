/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.model;

import javafx.scene.image.Image;

/** 
 * Derived class from the <code>Equipment</code> class, the <code>Armor</code> class stores information in regards
 * to a player's armor equipment. <code>Armor</code> provides a defensive boost to the user's <code>Character</code>,
 * where they may take less damage from enemies if they were to not have any on.
 *
 * @author Vincent Nguyen
 * @version 1.0
*/
public final class Armor extends Equipment {
	private int mArmorRating;
	private static final int DEFAULT_ARMOR_RATING = 1;

	/**
	 * Parameterized <code>Armor</code> constructor that initializes all fields to the given parameters. ID
	 * is passed as a parameter.
	 * @param iD The id/primary key to set.
	 * @param worth The gold value to set.
	 * @param rarity The rarity value to set.
	 * @param name The name to set.
	 * @param description The description to set.
	 * @param armorRating The armor rating/points to set.
	 * @param image The image to set.
	 */
	public Armor(final int iD, final String name, final String description, final int worth,
			final int rarity, final int armorRating, final Image image) {
		super(iD, worth, rarity, name, description, image);
		this.mArmorRating = armorRating;
	}
	
	/**
	 * Parameterized <code>Armor</code> constructor that initializes all fields to the given parameters.
	 * @param worth The gold value to set.
	 * @param rarity The rarity value to set.
	 * @param name The name to set.
	 * @param description The description to set.
	 * @param armorRating The armor rating/points to set.
	 * @param image The image to set.
	 */
	public Armor(final int worth, final int rarity, final String name, final String description,
			final Image image, final int armorRating) {
		this(-1, name, description, worth, rarity, armorRating, image);
	}
	
	/**
	 * <code>Armor</code> copy constructor that copies values from the other <code>Armor</code> object, initializing
	 * all fields to them.
	 * @param other The other <code>Armor</code> to copy values from.
	 */
	public Armor(final Armor other) {
		this(-1, other.mName, other.mDescription, other.mWorth, other.mRarity, other.mArmorRating,
				other.mImage);
	}
	
	/**
	 * Default <code>Armor</code> constructor, initializing all fields to default values;
	 */
	public Armor() {
		super();
		this.mArmorRating = DEFAULT_ARMOR_RATING;
	}

	/**
	 * Returns the armorRating of the <code>Armor</code>
	 * @return armorRating The armorRating
	 */
	public final int getArmorRating() {
		return this.mArmorRating;
	}

	/**
	 * Sets the current armorRating to the parameter value
	 * @param armorRating The armorRating to set
	 */
	public final void setArmorRating(final int armorRating) {
		this.mArmorRating = armorRating;
	}

	/**
	 * Generates a unique integer prime number/primary key for each <code>Armor</code> object.
	 * @return The uniquely generated number.
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.mArmorRating;
		return result;
	}

	/**
	 * Compares one <code>Armor</code> object to another, checking for equality between the two.
	 * @param obj The other <code>Armor</code> object to compare to.
	 * @return True if the same, false if not.
	 */
	@Override
	public final boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final Armor other = (Armor) obj;
		if (this.mArmorRating != other.mArmorRating)
			return false;
		return true;
	}

	/**
	 * Returns a String representation of an <code>Armor</code> object.
	 * @return The String representation of an <code>Armor</code> object.
	 */
	@Override
	public String toString() {
		return "Armor [ID=" + this.mID + ", Name=" + this.mName
				+ ", Description=" + this.mDescription + ", Worth=" + this.mWorth + " gold, "
				+ "Rarity=" + this.mRarity + ", Armor Rating=" + this.mArmorRating
				+ ",\n\tImage URI=" + this.mImage.toString() + "]";
	}
}
/**	
 * CS A272	
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.model;

import javafx.scene.image.Image;

/** 
 * Derived class from the <code>Equipment</code> class, the <code>Weapon</code> class stores information in regards
 * to a player's weapon equipment. <code>Weapon</code> provides an offensive boost to the user's <code>Character</code>,
 * where they may deal more damage to enemies if they were to not have any on.
 *
 * @author Vincent Nguyen
 * @version 1.0
*/
public final class Weapon extends Equipment {
	private int mAttackPoints;
	private static final int DEFAULT_ATTACK_POINTS = 1;
	
	/**
	 * Parameterized <code>Armor</code> constructor that initializes all fields to the given parameters. ID
	 * is passed as a parameter.
	 * @param iD The id/primary key to set.
	 * @param worth The gold value to set.
	 * @param rarity The rarity value to set.
	 * @param name The name to set.
	 * @param description The description to set.
	 * @param attackPoints The attack points to set.
	 * @param image The image to set.
	 */
	public Weapon(final int iD, final String name, final String description, final int worth,
			final int rarity, final int attackPoints, final Image image) {
		super(iD, worth, rarity, name, description, image);
		this.mAttackPoints = attackPoints;
	}
	
	/**
	 * Parameterized <code>Armor</code> constructor that initializes all fields to the given parameters.
	 * @param worth The gold value to set.
	 * @param rarity The rarity value to set.
	 * @param name The name to set.
	 * @param description The description to set.
	 * @param attackPoints The attack points to set.
	 * @param imageUri The image URI to set.
	 */
	public Weapon(final int worth, final int rarity, final String name, final String description,
			final Image image, final int attackPoints) {
		this(-1, name, description, worth, rarity, attackPoints, image);
	}
	
	/**
	 * <code>Weapon</code> copy constructor that copies values from the other <code>Weapon</code> object, initializing
	 * all fields to them.
	 * @param other The other <code>Weapon</code> to copy values from.
	 */
	public Weapon(final Weapon other) {
		this(-1, other.mName, other.mDescription, other.mWorth, other.mRarity, other.mAttackPoints,
				other.mImage);
	}
	
	/**
	 * Default <code>Weapon</code> constructor, initializing all fields to default values;
	 */
	public Weapon() {
		super();
		this.mAttackPoints = DEFAULT_ATTACK_POINTS;
	}

	/**
	 * Returns the attackPoints of the <code>Weapon</code>
	 * @return attackPoints The attackPoints
	 */
	public final int getAttackPoints() {
		return this.mAttackPoints;
	}

	/**
	 * Sets the current attackPoints to the parameter value
	 * @param attackPoints The attackPoints to set
	 */
	public final void setAttackPoints(final int attackPoints) {
		this.mAttackPoints = attackPoints;
	}

	/**
	 * Generates a unique integer prime number/primary key for each <code>Weapon</code> object.
	 * @return The uniquely generated number.
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.mAttackPoints;
		return result;
	}

	/**
	 * Compares one <code>Weapon</code> object to another, checking for equality between the two.
	 * @param obj The other <code>Weapon</code> object to compare to.
	 * @return True if the same, false if not.
	 */
	@Override
	public final boolean equals(java.lang.Object other) {
		if (this == other)
			return true;
		if (!super.equals(other))
			return false;
		if (this.getClass() != other.getClass())
			return false;
		final Weapon o = (Weapon) other;
		if (this.mAttackPoints != o.mAttackPoints)
			return false;
		return true;
	}

	/**
	 * Returns a String representation of an <code>Weapon</code> object.
	 * @return The String representation of an <code>Weapon</code> object.
	 */
	@Override
	public String toString() {
		return "Weapon [ID=" + this.mID + ", Name=" + this.mName
				+ ", Description=" + this.mDescription + ", Worth=" + this.mWorth + " gold, "
				+ "Rarity=" + this.mRarity + ", Attack Points=" + this.mAttackPoints
				+ ",\n\tImage URI=" + this.mImage.toString() + "]";
	}
}
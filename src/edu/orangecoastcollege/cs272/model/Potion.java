/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.model;

import java.io.File;

import javafx.scene.image.Image;

/**
 * The <code>Potion</code> class stores information in regards to a <code>Potion</code>, in which a <code>Player</code>
 * may use to gain bonuses to their stats.
 * 
 * @author Vichet Meng
 * @version 1.0
 */
public class Potion extends Item {
	private PotionType mType;
	private String mId;
	private double mPower;
	private String mDescription;
	private boolean mUsable;
	private double mWeight;
	
	/**
	 * Parameterized <code>Potion</code> constructor that initializes all fields to the given parameters.
	 * @param id The ID to set.
	 * @param name The name to set.
	 * @param description The description to set.
	 * @param weight The weight to set.
	 * @param type The <code>PotionType</code> to set.
	 * @param power The power to set.
	 * @param usability The usability to set.
	 * @param image_path The image path to set.
	 */
	public Potion(String id, String name, String description, double weight, PotionType type, double power, boolean usability, String image_path) {
		super(0, 0, name, weight, new Image(new File(image_path).toURI().toString()));
		mId = id;
		mType = type;
		mPower = power;
		mDescription = description;
		mWeight = weight;
		mUsable = usability;
	}

	/**
	 * Accessor method that return the type
	 * @return the type
	 */
	public PotionType getType() {
		return mType;
	}
	/**
	 * Accessor method that return the id
	 * @return the id
	 */
	public String getId() {
		return mId;
	}
	/**
	 * Accessor method that return the power
	 * @return the power
	 */
	public double getPower() {
		return mPower;
	}
	/**
	 * Accessor method that return the description
	 * @return the description
	 */
	public String getDescription() {
		return mDescription;
	}
	/**
	 * Accessor method that return the usability
	 * @return the usability
	 */
	public boolean isUsable() {
		return mUsable;
	}

	/**
	 * Sets the current type to the parameter value
	 * @param type The type to set
	 */
	public final void setType(PotionType type) {
		this.mType = type;
	}

	/**
	 * Sets the current id to the parameter value
	 * @param id The id to set
	 */
	public final void setId(String id) {
		this.mId = id;
	}

	/**
	 * Sets the current power to the parameter value
	 * @param power The power to set
	 */
	public final void setPower(double power) {
		this.mPower = power;
	}

	/**
	 * Sets the current description to the parameter value
	 * @param description The description to set
	 */
	public final void setDescription(String description) {
		this.mDescription = description;
	}

	/**
	 * Sets the current usable to the parameter value
	 * @param usable The usable to set
	 */
	public final void setUsable(boolean usable) {
		this.mUsable = usable;
	}

	/**
	 * Generates a unique integer prime number/primary key for each <code>Armor</code> object.
	 * @return The uniquely generated number.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((mDescription == null) ? 0 : mDescription.hashCode());
		result = prime * result + ((mId == null) ? 0 : mId.hashCode());
		long temp;
		temp = Double.doubleToLongBits(mPower);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((mType == null) ? 0 : mType.hashCode());
		result = prime * result + (mUsable ? 1231 : 1237);
		temp = Double.doubleToLongBits(mWeight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Compares one <code>Armor</code> object to another, checking for equality between the two.
	 * @param obj The other <code>Armor</code> object to compare to.
	 * @return True if the same, false if not.
	 */
	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Potion other = (Potion) obj;
		if (mDescription == null) {
			if (other.mDescription != null)
				return false;
		} else if (!mDescription.equals(other.mDescription))
			return false;
		if (mId == null) {
			if (other.mId != null)
				return false;
		} else if (!mId.equals(other.mId))
			return false;
		if (Double.doubleToLongBits(mPower) != Double.doubleToLongBits(other.mPower))
			return false;
		if (mType != other.mType)
			return false;
		if (mUsable != other.mUsable)
			return false;
		if (Double.doubleToLongBits(mWeight) != Double.doubleToLongBits(other.mWeight))
			return false;
		return true;
	}

	/**
	 * Returns a String representation of an <code>Player</code> object.
	 * @return The String representation of an <code>Player</code> object.
	 */
	@Override
	public String toString() {
		return "Potion [mId=" + mId + ",  Type=" + mType + ", Power=" + mPower + ", mDescription=" + mDescription
				+ ",\nmUsable=" + mUsable + ", mWeight=" + mWeight + "]";
	}
}
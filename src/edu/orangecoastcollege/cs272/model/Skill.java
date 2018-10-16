/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.model;

/**
 * The <code>Skill</code> stores information in regards to the usable skills/abilities a
 * <code>Player</code> can use in-game to enhance their gameplay.
 * 
 * @author Vu Nguyen
 * @version 1.0
 */
public class Skill {
	private double mCostToUse;
	private SkillType mType;
	
	/**
	 * Parameterized <code>Skill</code> constructor that initializes all fields to the given parameters.
	 * @param cost The MP cost to set.
	 * @param type The <code>SkillType</code> to set.
	 */
	public Skill(final double cost, final SkillType type) {
		mCostToUse = cost;
		mType = type;
	}
	/**
	 * Accessor method that returns the costToUse
	 * @return the costToUse
	 */
	public double getCostToUse() {
		return mCostToUse;
	}
	/**
	 * Accessor method that returns the type
	 * @return the type
	 */
	public SkillType getType() {
		return mType;
	}
	/**
	 * Sets the current costToUse to the parameter value
	 * @param costToUse The costToUse to set
	 */
	public final void setCostToUse(double costToUse) {
		this.mCostToUse = costToUse;
	}
	/**
	 * Sets the current type to the parameter value
	 * @param type The type to set
	 */
	public final void setType(SkillType type) {
		this.mType = type;
	}
	
	/**
	 * Generates a unique integer prime number/primary key for each <code>Skill</code> object.
	 * @return The uniquely generated number.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(mCostToUse);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((mType == null) ? 0 : mType.hashCode());
		return result;
	}
	
	/**
	 * Compares one <code>Skill</code> object to another, checking for equality between the two.
	 * @param obj The other <code>Skill</code> object to compare to.
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
		Skill other = (Skill) obj;
		if (Double.doubleToLongBits(mCostToUse) != Double.doubleToLongBits(other.mCostToUse))
			return false;
		if (mType != other.mType)
			return false;
		return true;
	}
	
	/**
	 * Returns a String representation of an <code>Skill</code> object.
	 * @return The String representation of an <code>Skill</code> object.
	 */
	@Override
	public String toString() {
		return "Skill [MP Cost=" + mCostToUse + ", Skill Type=" + mType + "]";
	}
}
/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.model;

/**
 * The <code>Score</code> class stores information in regards to a <code>Player</code>'s total earned score.
 * Score is accumulated from defeating enemies and winning levels.
 * 
 * @author Vu Nguyen
 * @version 1.0
 */
public class Score {
	private int mScoreID;
	private String mName;
	private int mScore;
	private double mTime;
	
	/**
	 * Parameterized <code>Score</code> constructor that initializes all fields to the given parameters.
	 * @param scoreID The ID to set.
	 * @param name The name to set.
	 * @param score The score to set.
	 * @param time The time to set.
	 */
	public Score(int scoreID, String name, int score, double time) {
		super();
		mScoreID = scoreID;
		mName = name;
		mScore = score;
		mTime = time;
	}
	
	/**
	 * Parameterized <code>Score</code> constructor that initializes all fields to the given parameters.
	 * @param name The name to set.
	 * @param score The score to set.
	 * @param time The time to set.
	 */
	public Score(String name, int score, double time) {
		super();
		mName = name;
		mScore = score;
		mTime = time;
	}

	/**
	 * Returns the scoreID of the <code>Score</code>
	 * @return scoreID The scoreID
	 */
	public final int getScoreID() {
		return this.mScoreID;
	}

	/**
	 * Sets the current scoreID to the parameter value
	 * @param scoreID The scoreID to set
	 */
	public final void setScoreID(int scoreID) {
		this.mScoreID = scoreID;
	}

	/**
	 * Returns the name of the <code>Score</code>
	 * @return name The name
	 */
	public final String getName() {
		return this.mName;
	}

	/**
	 * Sets the current name to the parameter value
	 * @param name The name to set
	 */
	public final void setName(String name) {
		this.mName = name;
	}

	/**
	 * Returns the score of the <code>Score</code>
	 * @return score The score
	 */
	public final int getScore() {
		return this.mScore;
	}

	/**
	 * Sets the current score to the parameter value
	 * @param score The score to set
	 */
	public final void setScore(int score) {
		this.mScore = score;
	}

	/**
	 * Returns the time of the <code>Score</code>
	 * @return time The time
	 */
	public final double getTime() {
		return this.mTime;
	}

	/**
	 * Sets the current time to the parameter value
	 * @param time The time to set
	 */
	public final void setTime(double time) {
		this.mTime = time;
	}
	
	/**
	 * Adds points to the current <code>Score</code>.
	 * @param points The points to add.
	 */
	public final void addPoints(final int points) {
		this.mScore += points;
	}
	
	/**
	 * Subtracts points to the current <code>Score</code>.
	 * @param points The points to subtract.
	 */
	public final void subtract(final int points) {
		this.mScore -= points;
	}
	
	/**
	 * Generates a unique integer prime number/primary key for each <code>Score</code> object.
	 * @return The uniquely generated number.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mName == null) ? 0 : mName.hashCode());
		result = prime * result + mScore;
		long temp;
		temp = Double.doubleToLongBits(mTime);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Compares one <code>Score</code> object to another, checking for equality between the two.
	 * @param obj The other <code>Score</code> object to compare to.
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
		Score other = (Score) obj;
		if (mName == null) {
			if (other.mName != null)
				return false;
		} else if (!mName.equals(other.mName))
			return false;
		if (mScore != other.mScore)
			return false;
		if (Double.doubleToLongBits(mTime) != Double.doubleToLongBits(other.mTime))
			return false;
		return true;
	}

	/**
	 * Returns a String representation of an <code>Score</code> object.
	 * @return The String representation of an <code>Score</code> object.
	 */
	@Override
	public String toString() {
		return "Player: " + mName + "     Score" + mScore;
	}
}
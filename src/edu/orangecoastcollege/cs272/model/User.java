/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.model;

/**
 * The <code>User</code> class stores information in regards to a user's account, so that they may use
 * the full functionality of the program. Password is not stored in the class, and is therefore private.
 *
 * @author Vincent Nguyen
 * @version 1.0
 */
public final class User {
	private int mId;
	private String mUserName;
	private Role mRole;
	private String mPlayerId;
	
	/**
	 * Parameterized <code>User</code> constructor that initializes all fields to the given parameters. ID
	 * is passed as a parameter.
	 * @param iD The id/primary key to set.
	 * @param userName The username to set
	 * @param playeerID The player ID to set
	 * @param role The permissions role of the account.
	 */
	public User(final int id, final String userName, final String playerId, final Role role) {
		this.mId = id;
		this.mUserName = userName;
		this.mRole = role;
		mPlayerId = playerId;
	}
	
	/**
	 * Returns the id of the <code>User</code>
	 * @return id The id
	 */
	public final int getId() {
		return this.mId;
	}

	/**
	 * Returns the userName of the <code>User</code>
	 * @return userName The userName
	 */
	public final String getUserName() {
		return this.mUserName;
	}

	/**
	 * Sets the current userName to the parameter value
	 * @param userName The userName to set
	 */
	public final void setUserName(final String userName) {
		this.mUserName = userName;
	}

	/**
	 * Returns the role of the <code>User</code>
	 * @return role The role
	 */
	public final Role getRole() {
		return this.mRole;
	}

	/**
	 * Sets the current role to the parameter value
	 * @param role The role to set
	 */
	public final void setRole(final Role role) {
		this.mRole = role;
	}

	/**
	 * Sets the current playerId to the parameter value
	 * @param playerId The playerId to set
	 */
	public final void setPlayerId(String playerId) {
		this.mPlayerId = playerId;
	}


	/**
	 * Accessor method that return the player
	 * @return the player
	 */
	public String getPlayerId() {
		return mPlayerId;
	}

	/**
	 * Mutator method that sets the player of the class
	 * @param player the player to set
	 */
	public void setPlayer(String playerId) {
		mPlayerId = playerId;
	}

	/**
	 * Generates a unique integer prime number/primary key for each <code>User</code> object.
	 * @return The uniquely generated number.
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.mId;
		result = prime * result + ((this.mUserName == null) ? 0 : this.mUserName.hashCode());
		result = prime * result + ((this.mRole == null) ? 0 : mRole.hashCode());
		return result;
	}

	/**
	 * Compares one <code>User</code> object to another, checking for equality between the two.
	 * @param obj The other <code>User</code> object to compare to.
	 * @return True if the same, false if not.
	 */
	@Override
	public final boolean equals(final java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (this.mId != other.mId)
			return false;
		if (this.mUserName == null) {
			if (other.mUserName != null)
				return false;
		} else if (!this.mUserName.equals(other.mUserName))
			return false;
		if (this.mRole == null) {
			if (other.mRole != null)
				return false;
		} else if (!this.mRole.equals(other.mRole))
			return false;
		return true;
	}

	/**
	 * Returns a String representation of an <code>User</code> object.
	 * @return The String representation of an <code>User</code> object.
	 */
	@Override
	public String toString() {
		return "User [ID=" + mId + ", Username=" + mUserName + ", Role=" + mRole + ", Player ID=" + mPlayerId + "]";
	}

		
}
/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.model;

import javafx.scene.image.Image;

/**
 * The <code>Enemy</code> class extends the <code>Character</code> class, storing information in regards to
 * an enemy NPC in the game.
 * 
 * @author Vichet Meng
 * @version 1.0
 */
public class Enemy extends Character {
    private static final double DEFAULT_ATTACK_POWER = 20;
	private static final double DEFAULT_SPEED = 0.5;
	private static final double DEFAULT_CURR_HP = 100.0;
	private static final double DEFAULT_MAX_HP = 100.0;
	private static final int DEFAULT_LEVEL = 1;
	private static final double DEFAULT_BASE_DEFENSE = 5.0;
	private static final boolean DEFAULT_WOUNDED = false;
	private static int DEFAULT_ATTACK_SPEED = 3;

	private String mDescription;
	private int mExperienceReward;
	private double mAttackPower;
	private double mBaseDefense;
	private double mSpeed;
	private int mLevel;
	private double mCurrHP;
	private double mMaxHP;
	private boolean mWounded;
	private Image mWoundedImage;
	private Image mDeathImage;
	private Image mCodexImage;
	private int mAttackSpeed;

	/**
	 * Parameterized <code>Enemy</code> constructor that initializes fields to the specified parameters.
	 * @param id The ID to set.
	 * @param x The x-coordinate to set.
	 * @param y The y-coordinate to set.
	 * @param name The name to set.
	 * @param image The image to set.
	 * @param codexImage The codex image to set.
	 */
	public Enemy(int id, double x, double y, String name, Image image, Image codexImage) {
		super(id, x, y, name, image);
		mAttackPower = DEFAULT_ATTACK_POWER;
		mExperienceReward = 1;
		mSpeed = DEFAULT_SPEED;
		mCurrHP = DEFAULT_CURR_HP;
		mMaxHP = DEFAULT_MAX_HP;
		mLevel = DEFAULT_LEVEL;
		mBaseDefense = DEFAULT_BASE_DEFENSE;
		mWounded = DEFAULT_WOUNDED;
		mAttackSpeed = DEFAULT_ATTACK_SPEED;
		mCodexImage = codexImage;
	}
	
	/**
	 * Parameterized <code>Enemy</code> constructor that initializes fields to the specified parameters.
	 * @param x The x-coordinate to set.
	 * @param y The y-coordinate to set.
	 * @param name The name to set.
	 * @param image The image to set.
	 * @param codexImage The codex image to set.
	 */
	public Enemy(double x, double y, String name, Image image, Image codexImage) {
		super(-1, x, y, name, image);
		mAttackPower = DEFAULT_ATTACK_POWER;
		mExperienceReward = 1;
		mSpeed = DEFAULT_SPEED;
		mCurrHP = DEFAULT_CURR_HP;
		mMaxHP = DEFAULT_MAX_HP;
		mLevel = DEFAULT_LEVEL;
		mBaseDefense = DEFAULT_BASE_DEFENSE;
		mWounded = DEFAULT_WOUNDED;
		mAttackSpeed = DEFAULT_ATTACK_SPEED;
		mCodexImage = codexImage;
	}
	
	/**
	 * Parameterized <code>Enemy</code> constructor that initializes fields to the specified parameters.
	 * @param id The ID to set.
	 * @param name The name to set.
	 * @param description The description to set.
	 * @param attack The attack to set.
	 * @param defense The defense to set.
	 * @param speed The speed to set.
	 * @param maxHP The max HP to set.
	 * @param moveImage The movement image to set.
	 * @param woundedImage The wounded image to set.
	 * @param deathImage The death image to set.
	 * @param codexImage The codex image to set.
	 * @param level The level to set.
	 */
	public Enemy(final int id, final String name, final String description, final double attack, final double defense,
			final double speed, final double maxHP, final Image moveImage, final Image woundedImage, final Image deathImage,
			final Image codexImage, final int level) {
		mID = id;
		mName = name;
		mDescription = description;
		mAttackPower = attack;
		mBaseDefense = defense;
		mSpeed = speed;
		mMaxHP = maxHP;
		mImage = moveImage;
		mWoundedImage = woundedImage;
		mDeathImage = deathImage;
		mCodexImage = codexImage;
		mLevel = level;
	}
	
	/**
	 * Parameterized <code>Enemy</code> constructor that initializes fields to the specified parameters.
	 * ID is specified as a parameter.
	 * @param id The ID to set.
	 * @param name The name to set.
	 * @param description The description to set.
	 * @param expReward The EXP reward to set.
	 * @param attackPower The attack to set.
	 * @param baseDefense The defense to set.
	 * @param speed The speed to set.
	 * @param level The level to set.
	 * @param currHP The current HP to set.
	 * @param maxHP The max HP to set.
	 * @param isWounded The wounded state to set.
	 * @param attackSpeed The attack speed to set.
	 * @param moveImage The movement image to set.
	 * @param woundedImage The wounded image to set.
	 * @param deathImage The death image to set.
	 * @param codexImage The codex image to set.
	 */
	public Enemy(final int id, final String name, final String description, final int expReward, final double attackPower,
			final double baseDefense,final double speed, final int level,final double currHP,
			final double maxHP, final boolean isWounded, final int attackSpeed, final Image movingImage,
			final Image woundedImage, final Image deathImage, final Image codexImage) {
		mID = id;
		mName = name;
		mDescription = description;
		mExperienceReward = expReward;
		mAttackPower = attackPower;
		mBaseDefense = baseDefense;
		mSpeed = speed;
		mLevel = level;
		mCurrHP = currHP;
		mMaxHP = maxHP;
		mWounded = isWounded;
		mAttackSpeed = attackSpeed;
		mImage = movingImage;
		mWoundedImage = woundedImage;
		mDeathImage = deathImage;
		mCodexImage = codexImage;	
	}
	
	/**
	 * Parameterized <code>Enemy</code> constructor that initializes fields to the specified parameters.
	 * @param name The name to set.
	 * @param description The description to set.
	 * @param expReward The EXP reward to set.
	 * @param attackPower The attack to set.
	 * @param baseDefense The defense to set.
	 * @param speed The speed to set.
	 * @param level The level to set.
	 * @param currHP The current HP to set.
	 * @param maxHP The max HP to set.
	 * @param isWounded The wounded state to set.
	 * @param attackSpeed The attack speed to set.
	 * @param moveImage The movement image to set.
	 * @param woundedImage The wounded image to set.
	 * @param deathImage The death image to set.
	 * @param codexImage The codex image to set.
	 */
	public Enemy(final String name, final String description, final int expReward, final double attackPower,
			final double baseDefense,final double speed, final int level,final double currHP,
			final double maxHP, final boolean isWounded, final int attackSpeed, final Image movingImage,
			final Image woundedImage, final Image deathImage, final Image codexImage) {
		this(-1, name, description, expReward, attackPower, baseDefense, speed, level, currHP, maxHP, isWounded,
				attackSpeed, movingImage, woundedImage, deathImage, codexImage);
	}
	
	/**
	 * <code>Enemy</code> copy constructor that copies values from the other <code>Enemy</code> object, initializing
	 * all fields to them.
	 * @param other The other <code>Enemy</code> to copy values from.
	 */
	public Enemy(final Enemy other) {
		this(-1, other.mName, other.mDescription, other.mExperienceReward, other.mAttackPower, other.mBaseDefense, other.mSpeed,
				other.mLevel, other.mCurrHP, other.mMaxHP, other.mWounded, other.mAttackSpeed, other.mImage,
				other.mWoundedImage, other.mDeathImage, other.mCodexImage);
	}
	
	/**
	 * Default <code>Enemy</code> constructor, initializing all fields to default values;
	 */
	public Enemy() {
		super();
		mAttackPower = DEFAULT_ATTACK_POWER;
		mSpeed = DEFAULT_SPEED;
		mCurrHP = DEFAULT_CURR_HP;
		mMaxHP = DEFAULT_MAX_HP;
		mLevel = DEFAULT_LEVEL;
		mBaseDefense = DEFAULT_BASE_DEFENSE;
		mWounded = DEFAULT_WOUNDED;
	}

	/**
	 * Returns the speed of the <code>Enemy</code>
	 * @return speed The speed
	 */
	public final double getSpeed() {
		return this.mSpeed;
	}

	/**
	 * Sets the current speed to the parameter value
	 * @param speed The speed to set
	 */
	public final void setSpeed(double speed) {
		this.mSpeed = speed;
	}

	/**
	 * Returns the wounded of the <code>Enemy</code>
	 * @return wounded The wounded
	 */
	public final boolean isWounded() {
		return this.mWounded;
	}

	/**
	 * Sets the current wounded to the parameter value
	 * @param wounded The wounded to set
	 */
	public final void setWounded(boolean wounded) {
		this.mWounded = wounded;
	}

	/**
	 * Returns the currHP of the <code>Enemy</code>
	 * @return currHP The currHP
	 */
	public final double getCurrHP() {
		return this.mCurrHP;
	}

	/**
	 * Returns the attackSpeed of the <code>Enemy</code>
	 * @return attackSpeed The attackSpeed
	 */
	public final int getAttackSpeed() {
		return this.mAttackSpeed;
	}

	/**
	 * Accessor method that return the experienceReward
	 * 
	 * @return the experienceReward
	 */
	public int getExperienceReward() {
		return mExperienceReward;
	}

	/**
	 * Accessor method that return the baseDefense
	 * 
	 * @return the baseDefense
	 */
	public double getBaseDefense() {
		return mBaseDefense;
	}

	/**
	 * Accessor method that return the level
	 * 
	 * @return the level
	 */
	public int getLevel() {
		return mLevel;
	}

	/**
	 * Accessor method that return the maxHP
	 * 
	 * @return the maxHP
	 */
	public double getMaxHP() {
		return mMaxHP;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.orangecoastcollege.cs272.model.Attackable#attack()
	 */
	@Override
	public double getAttackPower() {
		return mAttackPower;
	}
	
	/**
	 * Returns the description of the <code>Enemy</code>
	 * @return description The description
	 */
	public final String getDescription() {
		return this.mDescription;
	}

	/**
	 * Sets the current description to the parameter value
	 * @param description The description to set
	 */
	public final void setDescription(String description) {
		this.mDescription = description;
	}

	/**
	 * Returns the woundedImage of the <code>Enemy</code>
	 * @return woundedImage The woundedImage
	 */
	public final Image getWoundedImage() {
		return this.mWoundedImage;
	}

	/**
	 * Sets the current woundedImage to the parameter value
	 * @param woundedImage The woundedImage to set
	 */
	public final void setWoundedImage(Image woundedImage) {
		this.mWoundedImage = woundedImage;
	}

	/**
	 * Returns the deathImage of the <code>Enemy</code>
	 * @return deathImage The deathImage
	 */
	public final Image getDeathImage() {
		return this.mDeathImage;
	}

	/**
	 * Sets the current deathImage to the parameter value
	 * @param deathImage The deathImage to set
	 */
	public final void setDeathImage(Image deathImage) {
		this.mDeathImage = deathImage;
	}

	/**
	 * Sets the current experienceReward to the parameter value
	 * @param experienceReward The experienceReward to set
	 */
	public final void setExperienceReward(int experienceReward) {
		this.mExperienceReward = experienceReward;
	}

	/**
	 * Sets the current attackPower to the parameter value
	 * @param attackPower The attackPower to set
	 */
	public final void setAttackPower(double attackPower) {
		this.mAttackPower = attackPower;
	}

	/**
	 * Sets the current baseDefense to the parameter value
	 * @param baseDefense The baseDefense to set
	 */
	public final void setBaseDefense(double baseDefense) {
		this.mBaseDefense = baseDefense;
	}

	/**
	 * Sets the current level to the parameter value
	 * @param level The level to set
	 */
	public final void setLevel(int level) {
		this.mLevel = level;
	}

	/**
	 * Sets the current currHP to the parameter value
	 * @param currHP The currHP to set
	 */
	public final void setCurrHP(double currHP) {
		this.mCurrHP = currHP;
	}

	/**
	 * Sets the current maxHP to the parameter value
	 * @param maxHP The maxHP to set
	 */
	public final void setMaxHP(double maxHP) {
		this.mMaxHP = maxHP;
	}

	/**
	 * Sets the current attackSpeed to the parameter value
	 * @param attackSpeed The attackSpeed to set
	 */
	public final void setAttackSpeed(int attackSpeed) {
		this.mAttackSpeed = attackSpeed;
	}

	/**
	 * Returns the codexImage of the <code>Enemy</code>
	 * @return codexImage The codexImage
	 */
	public final Image getCodexImage() {
		return this.mCodexImage;
	}

	/**
	 * Sets the current codexImage to the parameter value
	 * @param codexImage The codexImage to set
	 */
	public final void setCodexImage(Image codexImage) {
		this.mCodexImage = codexImage;
	}

	/**
	 * Updates the current position.
	 * @param player The <code>Player</code> in the game.
	 */
	public void update(Player player) {
		if (isAlive()) {
			double dY = (double) (mY - player.getY());
			double dX = (double) (mX - player.getX());
			double angle = Math.atan(dY / dX);
			if ((dX > 0 && dY > 0) || (dX > 0 && dY < 0))
				angle += Math.PI;
			double mSpeedY = Math.sin(angle) * mSpeed;
			double mSpeedX = Math.cos(angle) * mSpeed;
			this.setX(mX + mSpeedX);
			this.setY(mY + mSpeedY);
		}

	}

	/**
	 * Registers the damage dealt.
	 * @param attackDamage The amount of damage dealt.
	 */
	public void gotAttacked(double attackDamage) {
		double actualDamage = attackDamage - mBaseDefense;
		if (actualDamage > 0) {
			mCurrHP -= actualDamage;
		}
	}

	/**
	 * Checks to see if the <code>Enemy</code> is still alive.
	 * @return True if alive, false if not.
	 */
	public boolean isAlive() {
		return mCurrHP > 0;
	}

	/**
	 * Generates a unique integer prime number/primary key for each <code>Armor</code> object.
	 * @return The uniquely generated number.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(mAttackPower);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + mAttackSpeed;
		temp = Double.doubleToLongBits(mBaseDefense);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mCurrHP);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((mDescription == null) ? 0 : mDescription.hashCode());
		result = prime * result + mExperienceReward;
		result = prime * result + mLevel;
		temp = Double.doubleToLongBits(mMaxHP);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mSpeed);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (mWounded ? 1231 : 1237);
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
		Enemy other = (Enemy) obj;
		if (Double.doubleToLongBits(mAttackPower) != Double.doubleToLongBits(other.mAttackPower))
			return false;
		if (mAttackSpeed != other.mAttackSpeed)
			return false;
		if (Double.doubleToLongBits(mBaseDefense) != Double.doubleToLongBits(other.mBaseDefense))
			return false;
		if (Double.doubleToLongBits(mCurrHP) != Double.doubleToLongBits(other.mCurrHP))
			return false;
		if (mDescription == null) {
			if (other.mDescription != null)
				return false;
		} else if (!mDescription.equals(other.mDescription))
			return false;
		if (mExperienceReward != other.mExperienceReward)
			return false;
		if (mLevel != other.mLevel)
			return false;
		if (Double.doubleToLongBits(mMaxHP) != Double.doubleToLongBits(other.mMaxHP))
			return false;
		if (Double.doubleToLongBits(mSpeed) != Double.doubleToLongBits(other.mSpeed))
			return false;
		if (mWounded != other.mWounded)
			return false;
		return true;
	}

	/**
	 * Returns a String representation of an <code>Armor</code> object.
	 * @return The String representation of an <code>Armor</code> object.
	 */
	@Override
	public String toString() {
		return "Enemy [ID=" + mID + ", Name=" + mName + ", Description=" + mDescription + ", Attack Power=" + mAttackPower
				+ ",\n\tBase Defense=" + mBaseDefense + ", Speed=" + mSpeed + ", Max HP=" + mMaxHP;
	}
}

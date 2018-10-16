/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.orangecoastcollege.cs272.view.GameScene;
import javafx.scene.image.Image;

/**
 * The <code>Player</code> class extends the <code>Object</code> class, where it stores information
 * in regards to a <code>User</code>'s in-game avatar.
 * 
 * @author Vichet Meng
 * @version 1.0
 */
public class Player extends Character {
	/**
	 * The default base blue wizard defense.
	 */
	public static final double DEFAULT_BASE_DEFENSE_BLUE = 2;
	/**
	 * The default base blue wizard attack.
	 */
	public static final double DEFAULT_BASE_ATTACK_BLUE = 15;
	/**
	 * The default base blue wizard movement speed.
	 */
	public static final double DEFAULT_BASE_MOVESPEED_BLUE = 1.5;
	/**
	 * The default base purple wizard defense.
	 */
	public static final double DEFAULT_BASE_DEFENSE_PURPLE = 4;
	/**
	 * The default base purple wizard attack.
	 */
	public static final double DEFAULT_BASE_ATTACK_PURPLE = 10;
	/**
	 * The default base purple wizard movement speed.
	 */
	public static final double DEFAULT_BASE_MOVESPEED_PURPLE = 1;
	/**
	 * The default <code>Player</code> boost.
	 */
	public static final double DEFAULT_BOOST = 1;
	/**
	 * The default <code>Player</code> defense.
	 */
	public static final double DEFAULT_BASE_DEFENSE = 5;
	/**
	 * The default <code>Player</code> <code>Equipment</code> cap.
	 */
	public static final double DEFAULT_BASE_EQUIPMENTCAP = 100;
	/**
	 * The default <code>Player</code> movement speed.
	 */
	public static final double DEFAULT_BASE_MOVESPEED = 1.5;
	/**
	 * The default <code>Player</code> skill cost..
	 */
	public static final double DEFAULT_SKILL_COST = 10;
	
	// Instance variables
	private List<Item> mInventory;
	private double mBaseDefense;
	private double mEquipmentCap;
	private int mLevel;
	private int mExperience;
	private double mMovespeed;
	private double mNextLevelExp;
	private double mMaxHP;
	private double mCurrentHP;
	private double mMaxMP;
	private double mCurrentMP;
	private double mAttackPower;
	private Skill mCurrentSkill;
	private Skill mSkill;
	private List<Item> mAllItems;
	private String mPlayerId;
	private double mDefense;
	private int mScore;
	private int mPowerPoint;
	private int mDefensePoint;
	private int mSpeedPoint;
	private String mClassId;
	private Weapon mWeapon;
	private Armor mArmor;
	private boolean mSkillActive = false;
	
	/**
	 * Parameterized <code>Player</code> constructor that initializes all fields to the given parameters. ID
	 * is passed as a parameter.
	 * @param id The ID to set.
	 * @param name The name to set.
	 * @param power The attack to set.
	 * @param defense The defense to set.
	 * @param speed The speed to set.
	 * @param weapon The weapon to set.
	 * @param armor The armor to set.
	 * @param cClass The <code>CharacterClass</code> to set.
	 * @param imagePath The image path to set.
	 */
	public Player(String id, String name, int power, int defense, int speed, Weapon weapon, Armor armor,
			CharacterClass cClass, String imagePath) {
		super(GameScene.WINDOW_WIDTH / 2, GameScene.WINDOW_HEIGHT / 2, name,
				new Image(new File(imagePath).toURI().toString()));
		// TODO Auto-generated constructor stub
		mPlayerId = id;
		mPowerPoint = power;
		mDefensePoint = defense;
		mSpeedPoint = speed;
		mInventory = new ArrayList<>();
		if (cClass == CharacterClass.BLUE_MAGE) {
			mClassId = "1";
			mDefense = DEFAULT_BASE_DEFENSE_BLUE + mDefensePoint;
			mMovespeed = DEFAULT_BASE_MOVESPEED_BLUE + mSpeedPoint * 0.15;
			mAttackPower = DEFAULT_BASE_ATTACK_BLUE + mPowerPoint;
		} else {
			mClassId = "2";
			mDefense = DEFAULT_BASE_DEFENSE_PURPLE + mDefensePoint;
			mMovespeed = DEFAULT_BASE_MOVESPEED_PURPLE + mSpeedPoint * 0.15;
			mAttackPower = DEFAULT_BASE_ATTACK_PURPLE + mPowerPoint;
		}
		mWeapon = weapon;
		mArmor = armor;
		mScore = 0;

		mNextLevelExp = 1000;
		mMaxHP = 100;
		mCurrentHP = mMaxHP;
		mMaxMP = 100;
		mCurrentMP = mMaxMP;
		mCurrentSkill = new Skill(0, SkillType.Passive);
		mSkill = new Skill(0, SkillType.Passive);
		// mAllItems.add(new Item)
	}
	
	/**
	 * The class constructor that initializes previously existed Player (from
	 * save).
	 * 
	 * @param id The ID to set.
	 * @param name The name to set.
	 * @param level The level to set.
	 * @param power The power to set.
	 * @param defense The defense to set.
	 * @param speed The speed to set.
	 * @param weaponId The weapon ID to set.
	 * @param cClass The <code>CharacterClass</code> to set.
	 * @param path The image path to set.
	 */
	public Player(String id, String name, int power, int defense, int speed, Weapon weapon, Armor armor, int score,
			CharacterClass cClass, String imagePath, List<Item> items) {
		super(GameScene.WINDOW_WIDTH / 2, GameScene.WINDOW_HEIGHT / 2, name,
				new Image(new File(imagePath).toURI().toString()));
		// TODO Auto-generated constructor stub
		mPlayerId = id;
		mPowerPoint = power;
		mDefensePoint = defense;
		mSpeedPoint = speed;
		mInventory = new ArrayList<>(items);
		if (cClass == CharacterClass.BLUE_MAGE) {
			mClassId = "1";
			mDefense = DEFAULT_BASE_DEFENSE_BLUE + mDefensePoint;
			mMovespeed = DEFAULT_BASE_MOVESPEED_BLUE + mSpeedPoint * 0.15;
			mAttackPower = DEFAULT_BASE_ATTACK_BLUE + mPowerPoint;
		} else {
			mClassId = "2";
			mDefense = DEFAULT_BASE_DEFENSE_PURPLE + mDefensePoint;
			mMovespeed = DEFAULT_BASE_MOVESPEED_PURPLE + mSpeedPoint * 0.15;
			mAttackPower = DEFAULT_BASE_ATTACK_PURPLE + mPowerPoint;
		}
		mScore = score;
		mWeapon = weapon;
		mArmor = armor;
		mNextLevelExp = 1000;
		mMaxHP = 100;
		mCurrentHP = mMaxHP;
		mMaxMP = 100;
		mCurrentMP = mMaxMP;
		mCurrentSkill = new Skill(0, SkillType.Passive);
		mSkill = new Skill(0, SkillType.Passive);
		// mAllItems.add(new Item)
	}
	
	/**
	 * The class constructor that initializes all the instance
	 * variables in the class
	 * @param id The ID to set.
	 * @param x The x-coordinate to set.
	 * @param y The y-coordinate to set.
	 * @param name The name to set.
	 * @param image The image to set.
	 */
	public Player(int id, double x, double y, String name, Image image) {
		super(id, x, y , name, image);
		mInventory = new ArrayList<>();
		mBaseDefense = DEFAULT_BASE_DEFENSE;
		mEquipmentCap = DEFAULT_BASE_EQUIPMENTCAP;
		mLevel = 1;
		mExperience = 0;
		mMovespeed = DEFAULT_BASE_MOVESPEED;
		mNextLevelExp = 1000;
		mMaxHP = 100;
		mCurrentHP = mMaxHP;
		mMaxMP = 100;
		mCurrentMP = mMaxMP;
		mAttackPower = 10;
		mSkill = new Skill(0, SkillType.Passive);
		mCurrentSkill = new Skill(0, SkillType.Passive);
	}

	/**
	 * Adds a new <code>Item</code> to the player's inventory.
	 * @param i
	 */
	public void addItem(Item i) {
		if (mInventory.size() < 11)
			mInventory.add(i);
	}
	
	/**
	 * Returns a List of all the <code>Item</code>s.
	 * @return The List of all the <code>Items</code>.
	 */
	public List<Item> getAllItems() {
		return mAllItems;
	}

	/**
	 * Accessor method that return the skill
	 * @return the skill
	 */
	public Skill useSkill() {
		if (mCurrentSkill.getType() == SkillType.Active)
            mCurrentMP -= DEFAULT_SKILL_COST;
        return mCurrentSkill;
	}


	/**
	 * Mutator method that sets the skill of the class
	 * @param skill the skill to set
	 */
	public void setSkill(Skill skill) {
		this.mCurrentSkill = skill;
	}
	


	/**
	 * Accessor method that return the inventory
	 * @return the inventory
	 */
	public List<Item> getInventory() {
        return new ArrayList<Item>(mInventory);
    }

	/**
	 * Mutator method that sets the inventory of the class
	 * @param inventory the inventory to set
	 */
	public void setInventory(ArrayList<Item> inventory) {
		mInventory = inventory;
	}

	/**
	 * Accessor method that return the baseDefense
	 * @return the baseDefense
	 */
	public double getBaseDefense() {
		return mBaseDefense;
	}

	/**
	 * Mutator method that sets the baseDefense of the class
	 * @param baseDefense the baseDefense to set
	 */
	public void setBaseDefense(double baseDefense) {
		mBaseDefense = baseDefense;
	}

	/**
	 * Accessor method that return the equipmentCap
	 * @return the equipmentCap
	 */
	public double getEquipmentCap() {
		return mEquipmentCap;
	}

	/**
	 * Mutator method that sets the equipmentCap of the class
	 * @param equipmentCap the equipmentCap to set
	 */
	public void setEquipmentCap(double equipmentCap) {
		mEquipmentCap = equipmentCap;
	}

	/**
	 * Accessor method that return the level
	 * @return the level
	 */
	public int getLevel() {
		return mLevel;
	}

	/**
	 * Mutator method that sets the level of the class
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		mLevel = level;
	}

	/**
	 * Accessor method that return the experience
	 * @return the experience
	 */
	public int getExperience() {
		return mExperience;
	}

	/**
	 * Mutator method that sets the experience of the class
	 * @param experience the experience to set
	 */
	public void setExperience(int experience) {
		mExperience = experience;
	}

	/**
	 * Accessor method that return the movespeed
	 * @return the movespeed
	 */
	public double getMovespeed() {
		double actualMoveSpeed = mMovespeed;
		if (mCurrentSkill.getType() == SkillType.Passive) {
            System.out.println("passive");
            actualMoveSpeed += 1;
        }
        return actualMoveSpeed;
	}

	/**
	 * Mutator method that sets the movespeed of the class
	 * @param movespeed the movespeed to set
	 */
	public void setMovespeed(double movespeed) {
		mMovespeed = movespeed;
	}

	/**
	 * Accessor method that return the nextLevelExp
	 * @return the nextLevelExp
	 */
	public double getNextLevelExp() {
		return mNextLevelExp;
	}

	/**
	 * Mutator method that sets the nextLevelExp of the class
	 * @param nextLevelExp the nextLevelExp to set
	 */
	public void setNextLevelExp(double nextLevelExp) {
		mNextLevelExp = nextLevelExp;
	}

	/**
	 * Accessor method that return the maxHP
	 * @return the maxHP
	 */
	public double getMaxHP() {
		return mMaxHP;
	}

	/**
	 * Accessor method that return the currentHP
	 * @return the currentHP
	 */
	public double getCurrentHP() {
		return mCurrentHP;
	}
	
	

	/**
	 * Accessor method that returns the maxMP
	 * @return the maxMP
	 */
	public double getMaxMP() {
		return mMaxMP;
	}

	/**
	 * Accessor method that returns the currentMP
	 * @return the currentMP
	 */
	public double getCurrentMP() {
		return mCurrentMP;
	}

	/**
	 * Mutator method that sets the currentHP of the class
	 * @param currentHP the currentHP to set
	 */
	public void setCurrentHP(double currentHP) {
		mCurrentHP = currentHP;
	}
	
	/**
	 * Returns the currentSkill of the <code>Player</code>
	 * @return currentSkill The currentSkill
	 */
	public final Skill getCurrentSkill() {
		return this.mCurrentSkill;
	}

	/**
	 * Sets the current currentSkill to the parameter value
	 * @param currentSkill The currentSkill to set
	 */
	public final void setCurrentSkill(Skill currentSkill) {
		this.mCurrentSkill = currentSkill;
	}

	/**
	 * Returns the playerId of the <code>Player</code>
	 * @return playerId The playerId
	 */
	public final String getPlayerId() {
		return this.mPlayerId;
	}

	/**
	 * Sets the current playerId to the parameter value
	 * @param playerId The playerId to set
	 */
	public final void setPlayerId(String playerId) {
		this.mPlayerId = playerId;
	}

	/**
	 * Returns the defense of the <code>Player</code>
	 * @return defense The defense
	 */
	public final double getDefense() {
		return this.mDefense;
	}

	/**
	 * Sets the current defense to the parameter value
	 * @param defense The defense to set
	 */
	public final void setDefense(double defense) {
		this.mDefense = defense;
	}

	/**
	 * Returns the score of the <code>Player</code>
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
	 * Returns the powerPoint of the <code>Player</code>
	 * @return powerPoint The powerPoint
	 */
	public final int getPowerPoint() {
		return this.mPowerPoint;
	}

	/**
	 * Sets the current powerPoint to the parameter value
	 * @param powerPoint The powerPoint to set
	 */
	public final void setPowerPoint(int powerPoint) {
		this.mPowerPoint = powerPoint;
	}

	/**
	 * Returns the defensePoint of the <code>Player</code>
	 * @return defensePoint The defensePoint
	 */
	public final int getDefensePoint() {
		return this.mDefensePoint;
	}

	/**
	 * Sets the current defensePoint to the parameter value
	 * @param defensePoint The defensePoint to set
	 */
	public final void setDefensePoint(int defensePoint) {
		this.mDefensePoint = defensePoint;
	}

	/**
	 * Returns the speedPoint of the <code>Player</code>
	 * @return speedPoint The speedPoint
	 */
	public final int getSpeedPoint() {
		return this.mSpeedPoint;
	}

	/**
	 * Sets the current speedPoint to the parameter value
	 * @param speedPoint The speedPoint to set
	 */
	public final void setSpeedPoint(int speedPoint) {
		this.mSpeedPoint = speedPoint;
	}

	/**
	 * Returns the classId of the <code>Player</code>
	 * @return classId The classId
	 */
	public final String getClassId() {
		return this.mClassId;
	}

	/**
	 * Sets the current classId to the parameter value
	 * @param classId The classId to set
	 */
	public final void setClassId(String classId) {
		this.mClassId = classId;
	}

	/**
	 * Returns the weapon of the <code>Player</code>
	 * @return weapon The weapon
	 */
	public final Weapon getWeapon() {
		return this.mWeapon;
	}

	/**
	 * Sets the current weapon to the parameter value
	 * @param weapon The weapon to set
	 */
	public final void setWeapon(Weapon weapon) {
		this.mWeapon = weapon;
	}

	/**
	 * Returns the armor of the <code>Player</code>
	 * @return armor The armor
	 */
	public final Armor getArmor() {
		return this.mArmor;
	}

	/**
	 * Sets the current armor to the parameter value
	 * @param armor The armor to set
	 */
	public final void setArmor(Armor armor) {
		this.mArmor = armor;
	}

	/**
	 * Returns the skillActive of the <code>Player</code>
	 * @return skillActive The skillActive
	 */
	public final boolean isSkillActive() {
		return this.mSkillActive;
	}

	/**
	 * Sets the current skillActive to the parameter value
	 * @param skillActive The skillActive to set
	 */
	public final void setSkillActive(boolean skillActive) {
		this.mSkillActive = skillActive;
	}

	/**
	 * Returns the skill of the <code>Player</code>
	 * @return skill The skill
	 */
	public final Skill getSkill() {
		return this.mSkill;
	}

	/**
	 * Sets the current inventory to the parameter value
	 * @param inventory The inventory to set
	 */
	public final void setInventory(List<Item> inventory) {
		this.mInventory = inventory;
	}

	/**
	 * Sets the current maxHP to the parameter value
	 * @param maxHP The maxHP to set
	 */
	public final void setMaxHP(double maxHP) {
		this.mMaxHP = maxHP;
	}

	/**
	 * Sets the current maxMP to the parameter value
	 * @param maxMP The maxMP to set
	 */
	public final void setMaxMP(double maxMP) {
		this.mMaxMP = maxMP;
	}

	/**
	 * Sets the current currentMP to the parameter value
	 * @param currentMP The currentMP to set
	 */
	public final void setCurrentMP(double currentMP) {
		this.mCurrentMP = currentMP;
	}

	/**
	 * Sets the current attackPower to the parameter value
	 * @param attackPower The attackPower to set
	 */
	public final void setAttackPower(double attackPower) {
		this.mAttackPower = attackPower;
	}

	/**
	 * Sets the current allItems to the parameter value
	 * @param allItems The allItems to set
	 */
	public final void setAllItems(List<Item> allItems) {
		this.mAllItems = allItems;
	}

	/**
	 * Register damage onto a <code>Player</code>.
	 * @param damage The amount of damage taken.
	 */
	public void wasHit(double damage) {
		double damageDealt = damage - mBaseDefense;
		if (mCurrentHP > 0) {
			if (mCurrentHP - damageDealt > 0)
				mCurrentHP -= damageDealt;
			else
				mCurrentHP = 0;
		}
	}
	
	/**
	 * Checks to see if the <code>Player</code> is still alive.
	 * @return True if alive, false if not.
	 */
	public boolean isAlive() {
		return mCurrentHP > 0;
	}
	
	/**
	 * Resets the <code>Player</code> base HP/MP.
	 */
	public void reset() {
		mCurrentHP = mMaxHP;
		mCurrentMP = mMaxMP;
	}
	
	/**
	 * Returns the modified attack power from the skill.
	 */
	@Override
	public double getAttackPower() {
		double actualAttackPower = mAttackPower;
		if (mSkill.getType() == SkillType.Active && mSkillActive && hasEnoughMana())
			actualAttackPower *= 1.6;
		return actualAttackPower;
	}

	/**
	 * Checks to see if the <code>Player</code> has enough mana for a skill.
	 * @return
	 */
	public boolean hasEnoughMana() {
		return mCurrentMP - mSkill.getCostToUse() > 0;
	}

	/**
	 * Turns on a given skill.
	 */
	public void activateSkill() {
		mSkillActive = true;
	}

	/**
	 * Turns off a  given skill.
	 */
	public void deActivateSkill() {
		mSkillActive = false;
	}

	/**
	 * Checks to see if the desired <code>Skill</code> is an active skill.
	 * @return True if active, false if passive.
	 */
	public boolean skillIsActive() {
		return mSkillActive;
	}

	/**
	 * Uses a <code>Potion</code> if the <code>User</code> has one.
	 */
	public void usedItem(final Item item) {
		Potion p = (Potion) item;
		if (p.isUsable()) {
			mInventory.remove(item);
			if (p.getType() == PotionType.MP_Potion) {
				if (mCurrentMP + p.getPower() < mMaxMP) {
					mCurrentMP += p.getPower();
				} else
				{
					mCurrentMP = mMaxMP;
				}
			} else {
				if (mCurrentHP + p.getPower() < mMaxHP) {
					mCurrentHP += p.getPower();
				} else
				{
					mCurrentHP = mMaxHP;
				}
			}

		}
	}
	
	/**
	 * Generates a unique integer prime number/primary key for each <code>Armor</code> object.
	 * @return The uniquely generated number.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((mAllItems == null) ? 0 : mAllItems.hashCode());
		result = prime * result + ((mArmor == null) ? 0 : mArmor.hashCode());
		long temp;
		temp = Double.doubleToLongBits(mAttackPower);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mBaseDefense);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((mClassId == null) ? 0 : mClassId.hashCode());
		temp = Double.doubleToLongBits(mCurrentHP);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mCurrentMP);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((mCurrentSkill == null) ? 0 : mCurrentSkill.hashCode());
		temp = Double.doubleToLongBits(mDefense);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + mDefensePoint;
		temp = Double.doubleToLongBits(mEquipmentCap);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + mExperience;
		result = prime * result + ((mInventory == null) ? 0 : mInventory.hashCode());
		result = prime * result + mLevel;
		temp = Double.doubleToLongBits(mMaxHP);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mMaxMP);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mMovespeed);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mNextLevelExp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((mPlayerId == null) ? 0 : mPlayerId.hashCode());
		result = prime * result + mPowerPoint;
		result = prime * result + mScore;
		result = prime * result + ((mSkill == null) ? 0 : mSkill.hashCode());
		result = prime * result + (mSkillActive ? 1231 : 1237);
		result = prime * result + mSpeedPoint;
		result = prime * result + ((mWeapon == null) ? 0 : mWeapon.hashCode());
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
		Player other = (Player) obj;
		if (mAllItems == null) {
			if (other.mAllItems != null)
				return false;
		} else if (!mAllItems.equals(other.mAllItems))
			return false;
		if (mArmor == null) {
			if (other.mArmor != null)
				return false;
		} else if (!mArmor.equals(other.mArmor))
			return false;
		if (Double.doubleToLongBits(mAttackPower) != Double.doubleToLongBits(other.mAttackPower))
			return false;
		if (Double.doubleToLongBits(mBaseDefense) != Double.doubleToLongBits(other.mBaseDefense))
			return false;
		if (mClassId == null) {
			if (other.mClassId != null)
				return false;
		} else if (!mClassId.equals(other.mClassId))
			return false;
		if (Double.doubleToLongBits(mCurrentHP) != Double.doubleToLongBits(other.mCurrentHP))
			return false;
		if (Double.doubleToLongBits(mCurrentMP) != Double.doubleToLongBits(other.mCurrentMP))
			return false;
		if (mCurrentSkill == null) {
			if (other.mCurrentSkill != null)
				return false;
		} else if (!mCurrentSkill.equals(other.mCurrentSkill))
			return false;
		if (Double.doubleToLongBits(mDefense) != Double.doubleToLongBits(other.mDefense))
			return false;
		if (mDefensePoint != other.mDefensePoint)
			return false;
		if (Double.doubleToLongBits(mEquipmentCap) != Double.doubleToLongBits(other.mEquipmentCap))
			return false;
		if (mExperience != other.mExperience)
			return false;
		if (mInventory == null) {
			if (other.mInventory != null)
				return false;
		} else if (!mInventory.equals(other.mInventory))
			return false;
		if (mLevel != other.mLevel)
			return false;
		if (Double.doubleToLongBits(mMaxHP) != Double.doubleToLongBits(other.mMaxHP))
			return false;
		if (Double.doubleToLongBits(mMaxMP) != Double.doubleToLongBits(other.mMaxMP))
			return false;
		if (Double.doubleToLongBits(mMovespeed) != Double.doubleToLongBits(other.mMovespeed))
			return false;
		if (Double.doubleToLongBits(mNextLevelExp) != Double.doubleToLongBits(other.mNextLevelExp))
			return false;
		if (mPlayerId == null) {
			if (other.mPlayerId != null)
				return false;
		} else if (!mPlayerId.equals(other.mPlayerId))
			return false;
		if (mPowerPoint != other.mPowerPoint)
			return false;
		if (mScore != other.mScore)
			return false;
		if (mSkill == null) {
			if (other.mSkill != null)
				return false;
		} else if (!mSkill.equals(other.mSkill))
			return false;
		if (mSkillActive != other.mSkillActive)
			return false;
		if (mSpeedPoint != other.mSpeedPoint)
			return false;
		if (mWeapon == null) {
			if (other.mWeapon != null)
				return false;
		} else if (!mWeapon.equals(other.mWeapon))
			return false;
		return true;
	}

	/**
	 * Returns a String representation of an <code>Player</code> object.
	 * @return The String representation of an <code>Player</code> object.
	 */
	@Override
	public String toString() {
		return "Player [ID=" + mID + ", Name=" + mName + ", Max HP=" + mMaxHP + ", Attack=" + mAttackPower
				+ ", Defense=" + mDefense + ", Level=" + mLevel + "]";
	}
}
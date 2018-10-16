/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.model;

import edu.orangecoastcollege.cs272.view.GameScene;
import javafx.scene.image.Image;

/**
 * The <code>Projectile</code> class stores information in regards to a shootable projectile that
 * a <code>Character</code> and all affiliated classes can shoot.
 * 
 * @author Vichet Meng
 * @version 1.0
 */
public class Projectile extends Object {
	private double mDamage;
	private double mSpeed;
	private double mSpeedX, mSpeedY;
	private boolean mVisible;
	private Character mShooter;
	
	/**
	 * Parameterized <code>Armor</code> constructor that initializes all fields to the given parameters.
	 * @param shooter The shooter to set.
	 * @param destX The x-destination to set.
	 * @param destY The y-destination to set.
	 * @param damage The damage to set.
	 * @param name The name to set.
	 * @param image The image to set.
	 */
	public Projectile(Character shooter, double destX, double destY, double damage, String name, Image image) {
		super(shooter.getX(), shooter.getY(), name, image);
		mDamage = damage;
		mImage = image;
		mVisible = true;
		mSpeed = 10;
		double dY = (double) (mY - destY);
		double dX = (double) (mX - destX);
		double angle = Math.atan(dY / dX);
		if ((dX > 0 && dY > 0) || (dX > 0 && dY < 0))
			angle += Math.PI;
		mSpeedY = Math.sin(angle) * mSpeed;
		mSpeedX = Math.cos(angle) * mSpeed;
		mShooter = shooter;
	}

	/**
	 * Returns the speed of the <code>Projectile</code>
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
	 * Sets the current damage to the parameter value
	 * @param damage The damage to set
	 */
	public final void setDamage(double damage) {
		this.mDamage = damage;
	}

	/**
	 * Sets the current visible to the parameter value
	 * @param visible The visible to set
	 */
	public final void setVisible(boolean visible) {
		this.mVisible = visible;
	}

	/**
	 * Sets the current shooter to the parameter value
	 * @param shooter The shooter to set
	 */
	public final void setShooter(Character shooter) {
		this.mShooter = shooter;
	}
	
	/**
	 * Accessor method that return the speedX
	 * @return the speedX
	 */
	public double getSpeedX() {
		return mSpeedX;
	}

	/**
	 * Mutator method that sets the speedX of the class
	 * @param speedX the speedX to set
	 */
	public void setSpeedX(double speedX) {
		mSpeedX = speedX;
	}

	/**
	 * Accessor method that return the speedY
	 * @return the speedY
	 */
	public double getSpeedY() {
		return mSpeedY;
	}

	/**
	 * Mutator method that sets the speedY of the class
	 * @param speedY the speedY to set
	 */
	public void setSpeedY(double speedY) {
		mSpeedY = speedY;
	}
	
	/**
	 * Returns the damage of the <code>Projectile</code>
	 * @return damage The damage
	 */
	public final double getDamage() {
		return this.mDamage;
	}

	/**
	 * Returns the shooter of the <code>Projectile</code>
	 * @return shooter The shooter
	 */
	public final Character getShooter() {
		return this.mShooter;
	}

	/**
	 * Updates the current x/y speeds accordingly, as well as sets
	 * the visibility to invisible if necessary.
	 */
	public void update() {
		mX += mSpeedX;
		mY += mSpeedY;
		if (mX < 0 || mX > GameScene.WINDOW_WIDTH || mY < 0 || mY > GameScene.WINDOW_HEIGHT) {
			mVisible = false;
		}
	}
	
	/**
	 * Returns whether or not a given <code>Projectile</code> is visible or not.
	 * @return True if visible, false if not.
	 */
	public boolean isVisible() {
		return mVisible;
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
		temp = Double.doubleToLongBits(mDamage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mSpeed);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mSpeedX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mSpeedY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (mVisible ? 1231 : 1237);
		return result;
	}

	/**
	 * Compares one <code>Projectile</code> object to another, checking for equality between the two.
	 * @param obj The other <code>Projectile</code> object to compare to.
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
		Projectile other = (Projectile) obj;
		if (Double.doubleToLongBits(mDamage) != Double.doubleToLongBits(other.mDamage))
			return false;
		if (Double.doubleToLongBits(mSpeed) != Double.doubleToLongBits(other.mSpeed))
			return false;
		if (Double.doubleToLongBits(mSpeedX) != Double.doubleToLongBits(other.mSpeedX))
			return false;
		if (Double.doubleToLongBits(mSpeedY) != Double.doubleToLongBits(other.mSpeedY))
			return false;
		if (mVisible != other.mVisible)
			return false;
		return true;
	}

	/**
	 * Returns a String representation of an <code>Projectile</code> object.
	 * @return The String representation of an <code>Projectile</code> object.
	 */
	@Override
	public String toString() {
		return "Projectile [Damage=" + mDamage + ", Speed=" + mSpeed + ", Visibility=" + mVisible + ", Shooter="
				+ mShooter + "]";
	}
}
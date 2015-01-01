
package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

/**
 * This class represents an asteroid.
 * @author Samuel Rodriguez Martinez
 *
 */
public class Asteroid extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 4;
	
	private int asteroidWidth = 32;
	private int asteroidHeight = 32;
	private int speed = DEFAULT_SPEED;
	private int horizontalSpeed = DEFAULT_SPEED;
	private int horizontalLocation;
	private long lastAsteroidTime = 0;
	private boolean isNew = false;
	private int randomMultiplier = -1;

	private Random rand = new Random();
	
	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public Asteroid(GameScreen screen){
		this.setLocation(
        		rand.nextInt(screen.getWidth() - asteroidWidth),
        		0);
		this.setSize(asteroidWidth, asteroidHeight);
		this.horizontalLocation = rand.nextInt(screen.getWidth() - asteroidWidth);
	}
	
	/**
	 * This class sets the location of the asteroid based on given coordinates
	 * @param x - the x coordinate of the asteroid
	 * @param y - the y coordinate of the asteroid
	 */
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		this.horizontalLocation = x;
	}
	
	/**
	 * Returns the asteroid's image width
	 * @return the asteroid's image width
	 */
	public int getAsteroidWidth() {
		return asteroidWidth;
	}
	
	/**
	 * Returns the asteroid's image height
	 * @return the asteroid's image height
	 */
	public int getAsteroidHeight() {
		return asteroidHeight;
	}

	/**
	 * Returns the current asteroid speed
	 * @return the current asteroid speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Set the current asteroid speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the default asteroid speed.
	 * @return the default asteroid speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
	
	/**
	 * Returns the asteroid's horizontal location, the x coordinate
	 * @return the asteroid's horizontal location, the x coordinate
	 */
	public int getHorizontalLocation() {
		return horizontalLocation;
	}
	
	/**
	 * Sets the asteroids horizontal speed.
	 * @param speed - the asteroids horizontal speed
	 */
	public void setHorizontalSpeed(int speed) {
		this.horizontalSpeed = speed;
	}
	
	/**
	 * Returns the asteroid's horizontal speed
	 * @return the asteroid's horizontal speed
	 */
	public int getHorizontalSpeed() {
		return this.horizontalSpeed;
	}
	
	/**
	 * Sets the asteroid's last time
	 * @param time - the asteroid's last time
	 */
	public void setLastAsteroidTime(long time) {
		this.lastAsteroidTime = time;
	}
	
	/**
	 * Returns the asteroid's last time.
	 * @return the asteroid's last time
	 */
	public long getLastAsteroidTime() {
		return this.lastAsteroidTime;
	}
	
	/**
	 * Returns the new status of the asteroid.
	 * @return true if the asteroid is new, false otherwise.
	 */
	public boolean isNew() {
		return isNew;
	}
	
	/**
	 * Sets the status of the asteroid.
	 * @param status - should be true if the asteroid is new, false otherwise.
	 */
	public void isNew(boolean status) {
		isNew = status;
	}
	
	/**
	 * Generates and returns a random movement speed multiplier.
	 * @return a random movement speed multiplier.
	 */
	public int getRandomMovementMultiplier() {
		if(randomMultiplier == -1)
			randomMultiplier = rand.nextInt();
		
		return randomMultiplier;
	}
}

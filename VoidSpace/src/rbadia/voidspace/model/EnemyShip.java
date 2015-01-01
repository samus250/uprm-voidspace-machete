package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

/**
 * This class represents an enemy ship.
 * 
 * @author Samuel Rodriguez Martinez
 *
 */
public class EnemyShip extends Rectangle {
  private static final long serialVersionUID = 1L;

  public static final int DEFAULT_SPEED = 3;

  private int enemyShipWidth = 25;
  private int enemyShipHeightHeight = 25;
  private int speed = DEFAULT_SPEED;
  private int horizontalSpeed = DEFAULT_SPEED;
  private int horizontalLocation;
  private long lastEnemyShipTime = 0;
  private boolean isNew = false;
  private int randomMultiplier = -1;

  // added shit
  private long enemyBulletTime = 0;
  private int cooldown;
  private int minCooldown = 250;
  private int maxCooldown = 750;

  private Random rand = new Random();

  /**
   * Crates a new enemy ship at a random x location at the top of the screen
   * 
   * @param screen
   *          the game screen
   */
  public EnemyShip(GameScreen screen) {
    this.setLocation(rand.nextInt(screen.getWidth() - enemyShipWidth), 0);
    this.setSize(enemyShipWidth, enemyShipHeightHeight);
    this.horizontalLocation = rand.nextInt(screen.getWidth() - enemyShipWidth);
  }

  /**
   * Sets the location of the enemy ship
   * 
   * @param x
   *          - the x coordinate of the enemy ship
   * @param y
   *          - the y coordinate of the enemy ship
   */
  public void setLocation(int x, int y) {
    super.setLocation(x, y);
    this.horizontalLocation = x;
  }

  /**
   * Returns the enemy ship's image width.
   * 
   * @return the enemy ship's image width
   */
  public int getEnemyShipWidth() {
    return enemyShipWidth;
  }

  /**
   * Returns the enemy ship's image height.
   * 
   * @return the enemy ship's image height
   */
  public int getEnemyShipHeight() {
    return enemyShipHeightHeight;
  }

  /**
   * Returns the current asteroid speed
   * 
   * @return the current asteroid speed
   */
  public int getSpeed() {
    return speed;
  }

  /**
   * Set the current asteroid speed
   * 
   * @param speed
   *          the speed to set
   */
  public void setSpeed(int speed) {
    this.speed = speed;
  }

  /**
   * Returns the default asteroid speed.
   * 
   * @return the default asteroid speed
   */
  public int getDefaultSpeed() {
    return DEFAULT_SPEED;
  }

  /**
   * Returns the horizontal x coordinate of the enemy ship.
   * 
   * @return the horizontal x coordinate of the enemy ship
   */
  public int getHorizontalLocation() {
    return horizontalLocation;
  }

  /**
   * Sets the horizontal speed of the enemy ship.
   * 
   * @param speed
   *          - the speed of the enemy ship
   */
  public void setHorizontalSpeed(int speed) {
    this.horizontalSpeed = speed;
  }

  /**
   * Returns the horizontal speed of the enemy ship.
   * 
   * @return the horizontal speed of the enemy ship
   */
  public int getHorizontalSpeed() {
    return this.horizontalSpeed;
  }

  /**
   * Sets the enemy ship's bullet time.
   * 
   * @param enemyBulletTime
   *          - the enemy ship's bullet time
   */
  public void setEnemyBulletTime(long enemyBulletTime) {
    this.enemyBulletTime = enemyBulletTime;
  }

  /**
   * Returns the enemy ship's bullet time.
   * 
   * @return the enemy ship's bullet time
   */
  public long getEnemyBulletTime() {
    return enemyBulletTime;
  }

  /**
   * Changes the random cooldown time of the bullets fired by the enemy ship.
   */
  public void changeCooldown() {
    this.cooldown = rand.nextInt(maxCooldown - minCooldown) + minCooldown;
  }

  /**
   * Gets the cooldown time of the bullets that the enemy ship fires.
   * 
   * @return the cooldown time of the bullets that the enemy ship fires
   */
  public int getCooldown() {
    return cooldown;
  }

  /**
   * Sets the enemy ship's last time.
   * 
   * @param time
   *          - the enemy ship's last time.
   */
  public void setLastEnemyShipTime(long time) {
    this.lastEnemyShipTime = time;
  }

  /**
   * Returns the enemy ship's last time.
   * 
   * @return the enemy ship's last time
   */
  public long getLastEnemyShipTime() {
    return this.lastEnemyShipTime;
  }

  /**
   * Returns the new status of the enemy ship.
   * 
   * @return true if the enemy ship is new, false otherwise
   */
  public boolean isNew() {
    return isNew;
  }

  /**
   * Sets the new status of the enemy ship.
   * 
   * @param status
   *          - should be true if the enemy ship is new, false otherwise
   */
  public void isNew(boolean status) {
    isNew = status;
  }

  /**
   * Returns a random movement multiplier for the enemy ship's speed.
   * 
   * @return a random movement multiplier for the enemy ship's speed.
   */
  public int getRandomMovementMultiplier() {
    if (randomMultiplier == -1) {
      this.randomMultiplier = this.rand.nextInt();
    }
    return this.randomMultiplier;
  }
}

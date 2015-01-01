package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.ChuckNorris;
import rbadia.voidspace.model.EnemyBullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Ship;

/**
 * Manages and draws game graphics and images.
 */
public class GraphicsManager {
  private BufferedImage shipImg;
  private BufferedImage bulletImg;
  private BufferedImage asteroidImg;
  private BufferedImage asteroidExplosionImg;
  private BufferedImage shipExplosionImg;
  private BufferedImage enemyShipImg;
  private BufferedImage enemyBulletImg;
  private BufferedImage enemyShipExplosionImg;
  private BufferedImage chuckNorrisImg;

  /**
   * Creates a new graphics manager and loads the game images.
   */
  public GraphicsManager() {
    // load images
    try {
      this.shipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/ship.png"));
      this.asteroidImg = ImageIO.read(getClass().getResource(
          "/rbadia/voidspace/graphics/asteroid.png"));
      this.asteroidExplosionImg = ImageIO.read(getClass().getResource(
          "/rbadia/voidspace/graphics/asteroidExplosion.png"));
      this.shipExplosionImg = ImageIO.read(getClass().getResource(
          "/rbadia/voidspace/graphics/shipExplosion.png"));
      this.bulletImg = ImageIO
          .read(getClass().getResource("/rbadia/voidspace/graphics/bullet.png"));

      this.enemyShipImg = ImageIO.read(getClass().getResource(
          "/rbadia/voidspace/graphics/enemyShip.png"));
      this.enemyBulletImg = ImageIO.read(getClass().getResource(
          "/rbadia/voidspace/graphics/enemyBullet.png"));
      this.enemyShipExplosionImg = ImageIO.read(getClass().getResource(
          "/rbadia/voidspace/graphics/enemyShipExplosion.png"));
      this.chuckNorrisImg = ImageIO.read(getClass().getResource(
          "/rbadia/voidspace/graphics/CHUCK_NORRIS_INDESTRUCTABLE.png"));
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
          "VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
      e.printStackTrace();
      System.exit(-1);
    }
  }

  /**
   * Draws a ship image to the specified graphics canvas.
   * 
   * @param ship
   *          the ship to draw
   * @param g2d
   *          the graphics canvas
   * @param observer
   *          object to be notified
   */
  public void drawShip(Ship ship, Graphics2D g2d, ImageObserver observer) {
    g2d.drawImage(shipImg, ship.x, ship.y, observer);
  }

  /**
   * Draws a bullet image to the specified graphics canvas.
   * 
   * @param bullet
   *          the bullet to draw
   * @param g2d
   *          the graphics canvas
   * @param observer
   *          object to be notified
   */
  public void drawBullet(Bullet bullet, Graphics2D g2d, ImageObserver observer) {
    g2d.drawImage(bulletImg, bullet.x, bullet.y, observer);
  }

  /**
   * Draws an asteroid image to the specified graphics canvas.
   * 
   * @param asteroid
   *          the asteroid to draw
   * @param g2d
   *          the graphics canvas
   * @param observer
   *          object to be notified
   */
  public void drawAsteroid(Asteroid asteroid, Graphics2D g2d, ImageObserver observer) {
    g2d.drawImage(asteroidImg, asteroid.x, asteroid.y, observer);
  }

  /**
   * Draws a chuck norris image to the specified graphics canvas.
   * 
   * @param chuckNorris
   *          the chucknorris to draw
   * @param g2d
   *          the graphics canvas
   * @param observer
   *          object to be notified
   */
  public void drawChuckNorris(ChuckNorris chuckNorris, Graphics2D g2d, ImageObserver observer) {
    g2d.drawImage(chuckNorrisImg, chuckNorris.x, chuckNorris.y, observer);
  }

  /**
   * Draws a ship explosion image to the specified graphics canvas.
   * 
   * @param shipExplosion
   *          the bounding rectangle of the explosion
   * @param g2d
   *          the graphics canvas
   * @param observer
   *          object to be notified
   */
  public void drawShipExplosion(Rectangle shipExplosion, Graphics2D g2d, ImageObserver observer) {
    g2d.drawImage(shipExplosionImg, shipExplosion.x, shipExplosion.y, observer);
  }

  /**
   * Draws an asteroid explosion image to the specified graphics canvas.
   * 
   * @param asteroidExplosion
   *          the bounding rectangle of the explosion
   * @param g2d
   *          the graphics canvas
   * @param observer
   *          object to be notified
   */
  public void drawAsteroidExplosion(Rectangle asteroidExplosion, Graphics2D g2d,
      ImageObserver observer) {
    g2d.drawImage(asteroidExplosionImg, asteroidExplosion.x, asteroidExplosion.y, observer);
  }

  /**
   * Draws an enemy ship explosion image to the specified graphics canvas.
   * 
   * @param enemyShipExplosion
   *          - the bounding rectangle of the explosion
   * @param g2d
   *          - the graphics canvas
   * @param observer
   *          - object to be notified
   */
  public void drawEnemyShipExplosion(Rectangle enemyShipExplosion, Graphics2D g2d,
      ImageObserver observer) {
    g2d.drawImage(enemyShipExplosionImg, enemyShipExplosion.x, enemyShipExplosion.y, observer);
  }

  /**
   * Draws an enemy ship image to the specified graphics canvas
   * 
   * @param ship
   *          - the enemy ship to draw
   * @param g2d
   *          - the graphics canvas
   * @param observer
   *          - the object to be notified
   */
  public void drawEnemyShip(EnemyShip ship, Graphics2D g2d, ImageObserver observer) {
    g2d.drawImage(enemyShipImg, ship.x, ship.y, observer);
  }

  /**
   * Draws an enemy bullet image to the specified canvas
   * 
   * @param bullet
   *          - the enemy bullet to draw
   * @param g2d
   *          - the graphics canvas
   * @param observer
   *          - the object to be notified
   */
  public void drawEnemyBullet(EnemyBullet bullet, Graphics2D g2d, ImageObserver observer) {
    g2d.drawImage(enemyBulletImg, bullet.x, bullet.y, observer);
  }

}

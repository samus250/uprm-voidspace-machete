package rbadia.voidspace.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.ChuckNorris;
import rbadia.voidspace.model.EnemyBullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Main game screen. Handles all game graphics updates and some of the game
 * logic.
 */
public class GameScreen extends JPanel {
  private static final long serialVersionUID = 1L;

  private BufferedImage backBuffer;
  private Graphics2D g2d;

  private long lastShipTime;

  private Rectangle asteroidExplosion;
  private Rectangle shipExplosion;
  private Rectangle enemyShipExplosion;

  private JLabel shipsValueLabel;
  private JLabel destroyedValueLabel;
  private JLabel destroyedEnemyShipsValueLabel;
  private JLabel levelValueLabel;
  private JLabel pointsValueLabel;
  private JLabel bulletsFiredValueLabel;
  private JLabel bulletsRemainingValueLabel;
  private JLabel hitMissRatioValueLabel;

  private Random rand;

  private Font originalFont;
  private Font bigFont;
  private Font biggestFont;

  private GameStatus status;
  private SoundManager soundMan;
  private GraphicsManager graphicsMan;
  private GameLogic gameLogic;

  /**
   * This method initializes
   * 
   */
  public GameScreen() {
    super();
    // initialize random number generator
    rand = new Random();

    initialize();

    // init graphics manager
    graphicsMan = new GraphicsManager();

    // init back buffer image
    backBuffer = new BufferedImage(GameSettings.GAME_FRAME_X_SIZE, GameSettings.GAME_FRAME_Y_SIZE,
        BufferedImage.TYPE_INT_RGB);
    g2d = backBuffer.createGraphics();
  }

  /**
   * Initialization method (for VE compatibility).
   */
  private void initialize() {
    // set panel properties
    this.setSize(new Dimension(GameSettings.GAME_FRAME_X_SIZE, GameSettings.GAME_FRAME_Y_SIZE));
    this.setPreferredSize(new Dimension(GameSettings.GAME_FRAME_X_SIZE,
        GameSettings.GAME_FRAME_Y_SIZE));
    this.setBackground(Color.BLACK);
  }

  /**
   * Update the game screen.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    // draw current backbuffer to the actual game screen
    g.drawImage(backBuffer, 0, 0, this);
  }

  /**
   * Update the game screen's backbuffer image.
   */
  public void updateScreen() {
    Ship ship = gameLogic.getShip();
    ArrayList<Asteroid> asteroids = gameLogic.getAsteroidList();
    ArrayList<EnemyShip> enemyShips = gameLogic.getEnemyShipList();
    ArrayList<ChuckNorris> chuckNorriss = gameLogic.getChuckNorrisList();
    List<Bullet> bullets = gameLogic.getBullets();
    List<EnemyBullet> enemyBullets = gameLogic.getEnemyBullets();

    // always set gameLogic in GameStatus for gameLogic's asteroid list, etc.
    status.setGameLogic(gameLogic);

    // set orignal font - for later use
    if (this.originalFont == null) {
      this.originalFont = g2d.getFont();
      this.bigFont = originalFont;
    }

    // erase screen
    g2d.setPaint(Color.BLACK);
    g2d.fillRect(0, 0, getSize().width, getSize().height);

    // draw 50 random stars
    drawStars(50);

    // if the game is starting, draw "Get Ready" message
    if (status.isGameStarting()) {
      drawGetReady();
      return;
    }

    // if the game is over, draw the "Game Over" message
    if (status.isGameOver()) {
      // draw the message
      drawGameOver();

      long currentTime = System.currentTimeMillis();

      // draw the explosions until their time passes... for each asteroid
      for (int i = 0; i < asteroids.size(); i++) {
        Asteroid asteroid = asteroids.get(i);
        if ((currentTime - asteroid.getLastAsteroidTime()) < GameSettings.NEW_ASTEROID_DELAY) {
          graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
        }
      }

      // draw the explosions until their time passes... for each enemy ship
      for (int i = 0; i < enemyShips.size(); i++) {
        EnemyShip enemyShip = enemyShips.get(i);
        if ((currentTime - enemyShip.getLastEnemyShipTime()) < GameSettings.NEW_ENEMY_SHIP_DELAY) {
          graphicsMan.drawEnemyShipExplosion(enemyShipExplosion, g2d, this);
        }
      }

      if ((currentTime - lastShipTime) < GameSettings.NEW_SHIP_DELAY) {
        graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
      }
      return;
    }

    // the game has not started yet
    if (!status.isGameStarted()) {
      // draw game title screen
      initialMessage();
      return;
    }

    // draw Asteroids
    drawAsteroids(asteroids);

    // draw Chuck Norris
    drawChuckNorriss(chuckNorriss);

    // drawEnemyShip
    drawEnemyShip(enemyShips);

    // draw bullets
    drawBullets(bullets);

    // draw enemyBullets
    drawEnemyBullets(enemyBullets);

    // draw ship
    drawShip(ship);

    // check bullet-asteroid collision
    checkBulletAsteroidCollision(asteroids, bullets);

    checkBulletChuckNorrisCollision(chuckNorriss, bullets, enemyBullets);

    // check bullet-enemyShip collision
    checkBulletEnemyShipCollision(enemyShips, bullets);

    // check enemyBullet-ship collision
    checkShipEnemyBulletCollision(ship, enemyBullets);

    // check asteroid-ship collision
    checkAsteroidsShipCollision(asteroids, ship);

    // check chuck norris - ship collision
    checkChuckNorrisShipCollision(chuckNorriss, ship);

    // check ship-enemyShip collision
    checkShipEnemyShipCollision(ship, enemyShips);

    // check bullet collisions
    checkBulletEnemyBulletCollision(bullets, enemyBullets);

    // update asteroids destroyed label
    destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));

    // update enemyShip destroyed label
    destroyedEnemyShipsValueLabel.setText(Long.toString(status.getEnemyShipsDestroyed()));

    // update ships left label
    shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));

    // update level label
    levelValueLabel.setText(Integer.toString(status.getLevel()));

    // update points label
    pointsValueLabel.setText(Long.toString(status.getPoints()));

    // update bullets fired label
    bulletsFiredValueLabel.setText(Long.toString(status.getBulletsFired()));

    // update bullets remaining label only if bullets are limited
    if (GameSettings.BULLETS_ARE_LIMITED)
      bulletsRemainingValueLabel.setText(Long.toString(status.getBulletsRemaining()));

    // update hit-miss ratio
    if (status.getBulletsFired() > 0) {
      hitMissRatioValueLabel.setText(String.format("%.2f", status.getHitMissRatio()));
    } else {
      hitMissRatioValueLabel.setText(" --");
    }

  }

  /**
   * Draws the asteroids to the screen
   * 
   * @param asteroids
   *          - the array list of asteroids to be drawn
   */
  private void drawAsteroids(ArrayList<Asteroid> asteroids) {
    // draw asteroid
    for (int i = 0; i < asteroids.size(); i++) {
      Asteroid asteroid = asteroids.get(i);
      if (!asteroid.isNew()) {
        // draw the asteroid until it reaches the bottom of the screen

        int horizontalTranslation;
        int verticalTranslation;
        if (asteroid.getY() + asteroid.getSpeed() < this.getHeight()) {

          // change horizontal displacement speed
          if (status.getLevel() < GameSettings.LEVEL_TO_MOVE_ASTEROIDS) {
            horizontalTranslation = 0;
          } else if (GameSettings.ASTEROID_SINUSOIDAL_MOVEMENT) {
            long currentTime = System.currentTimeMillis();
            double cs = Math.cos(currentTime / 1000.0 + asteroid.getRandomMovementMultiplier());
            horizontalTranslation = (int) (3 * cs + 2 * cs * cs * cs);
          }
          // else if(GameSettings.ASTEROID_NELIANS_TECATEX_MOVEMENT) {
          // horizontalTranslation = asteroid.getHorizontalSpeed() *
          // rand.nextInt();
          // }
          else if (asteroid.getHorizontalLocation() < (this.getWidth() / 2)) {
            if (GameSettings.ASTEROID_NELIANS_TECATEX_MOVEMENT)
              horizontalTranslation = asteroid.getHorizontalSpeed()
                  * (rand.nextInt(asteroid.getDefaultSpeed()) - 1) + 1;
            else
              horizontalTranslation = asteroid.getHorizontalSpeed();
          } else {
            if (GameSettings.ASTEROID_NELIANS_TECATEX_MOVEMENT)
              horizontalTranslation = -asteroid.getHorizontalSpeed()
                  * (rand.nextInt(asteroid.getDefaultSpeed()) - 1) + 1;
            else
              horizontalTranslation = -asteroid.getHorizontalSpeed();
          }

          // change vertical displacement speed
          if (status.getLevel() < GameSettings.LEVEL_TO_MOVE_ASTEROIDS_2X) {
            verticalTranslation = asteroid.getSpeed();
          } else if (status.getLevel() < GameSettings.LEVEL_TO_MOVE_ASTEROIDS_3X) {
            verticalTranslation = (int) (asteroid.getSpeed() * GameSettings.SPEED_TO_MOVE_ASTEROIDS_2X);
          } else {
            verticalTranslation = (int) (asteroid.getSpeed() * GameSettings.SPEED_TO_MOVE_ASTEROIDS_3X);
          }

          // for sinosoidal path
          // double cs = Math.cos(currentTime / 1000.0);
          /* (int) (3*cs + 2*cs*cs*cs) */
          asteroid.translate(horizontalTranslation, verticalTranslation);
          graphicsMan.drawAsteroid(asteroid, g2d, this);
        } else {
          asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
          asteroid.setHorizontalSpeed(rand.nextInt(asteroid.getSpeed()));

        }
      } else {
        long currentTime = System.currentTimeMillis();
        // check the individual last asteroid's time
        if ((currentTime - asteroid.getLastAsteroidTime()) > GameSettings.NEW_ASTEROID_DELAY) {
          // draw a new asteroid
          asteroid.setLastAsteroidTime(currentTime);
          asteroid.isNew(false);
          asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
        } else {
          // draw explosion (keep drawing this explosion...)
          graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
        }
      }
    }

  }

  /**
   * This class draws the chuck norris's on the screen
   * 
   * @param chuckNorriss
   *          - the list of chuck norriss to draw
   */
  private void drawChuckNorriss(ArrayList<ChuckNorris> chuckNorriss) {
    // draw chuck norris
    for (int i = 0; i < chuckNorriss.size(); i++) {
      ChuckNorris chuckNorris = chuckNorriss.get(i);
      if (!chuckNorris.isNew()) {
        // draw the chuck norris until it reaches the bottom of the screen

        int horizontalTranslation;
        int verticalTranslation;
        if (chuckNorris.getY() + chuckNorris.getSpeed() < this.getHeight()) {

          // change horizontal displacement speed
          if (status.getLevel() < GameSettings.LEVEL_TO_MOVE_ASTEROIDS) {
            horizontalTranslation = 0;
          } else if (GameSettings.ASTEROID_SINUSOIDAL_MOVEMENT) {
            long currentTime = System.currentTimeMillis();
            double cs = Math.cos(currentTime / 1000.0 + chuckNorris.getRandomMovementMultiplier());
            horizontalTranslation = (int) (3 * cs + 2 * cs * cs * cs);
          }
          // else if(GameSettings.ASTEROID_NELIANS_TECATEX_MOVEMENT) {
          // horizontalTranslation = asteroid.getHorizontalSpeed() *
          // rand.nextInt();
          // }
          else if (chuckNorris.getHorizontalLocation() < (this.getWidth() / 2)) {
            if (GameSettings.ASTEROID_NELIANS_TECATEX_MOVEMENT)
              horizontalTranslation = chuckNorris.getHorizontalSpeed()
                  * (rand.nextInt(chuckNorris.getDefaultSpeed()) - 1) + 1;
            else
              horizontalTranslation = chuckNorris.getHorizontalSpeed();
          } else {
            if (GameSettings.ASTEROID_NELIANS_TECATEX_MOVEMENT)
              horizontalTranslation = -chuckNorris.getHorizontalSpeed()
                  * (rand.nextInt(chuckNorris.getDefaultSpeed()) - 1) + 1;
            else
              horizontalTranslation = -chuckNorris.getHorizontalSpeed();
          }

          // change vertical displacement speed
          if (status.getLevel() < GameSettings.LEVEL_TO_MOVE_ASTEROIDS_2X) {
            verticalTranslation = chuckNorris.getSpeed();
          } else if (status.getLevel() < GameSettings.LEVEL_TO_MOVE_ASTEROIDS_3X) {
            verticalTranslation = (int) (chuckNorris.getSpeed() * GameSettings.SPEED_TO_MOVE_ASTEROIDS_2X);
          } else {
            verticalTranslation = (int) (chuckNorris.getSpeed() * GameSettings.SPEED_TO_MOVE_ASTEROIDS_3X);
          }

          // for sinosoidal path
          // double cs = Math.cos(currentTime / 1000.0);
          /* (int) (3*cs + 2*cs*cs*cs) */
          chuckNorris.translate(horizontalTranslation, verticalTranslation);
          graphicsMan.drawChuckNorris(chuckNorris, g2d, this);
        } else {
          chuckNorris.setLocation(rand.nextInt(getWidth() - chuckNorris.width), 0);
          chuckNorris.setHorizontalSpeed(rand.nextInt(chuckNorris.getSpeed()));

        }
      } else {
        long currentTime = System.currentTimeMillis();
        // check the individual last asteroid's time
        if ((currentTime - chuckNorris.getLastAsteroidTime()) > GameSettings.NEW_ASTEROID_DELAY) {
          // draw a new asteroid
          chuckNorris.setLastAsteroidTime(currentTime);
          chuckNorris.isNew(false);
          chuckNorris.setLocation(rand.nextInt(getWidth() - chuckNorris.width), 0);
        } else {
          // draw explosion (keep drawing this explosion...)
          graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
        }
      }
    }
  }

  /**
   * Draws the enemy ships to the screen
   * 
   * @param enemyShips
   *          - the array list of enemy ships to be drawn
   */
  private void drawEnemyShip(ArrayList<EnemyShip> enemyShips) {
    // draw enemyShip
    for (int i = 0; i < enemyShips.size(); i++) {
      EnemyShip enemyShip = enemyShips.get(i);
      if (!enemyShip.isNew()) {
        // draw the enemyShip until it reaches the bottom of the screen

        int horizontalTranslation;
        int verticalTranslation;
        if (enemyShip.getY() + enemyShip.getSpeed() < this.getHeight()) {

          long currentTime = System.currentTimeMillis();
          double cs = Math.cos(currentTime / 1000.0 + enemyShip.getRandomMovementMultiplier());

          if (status.getLevel() < GameSettings.LEVEL_TO_MOVE_ENEMY_SHIP_2X) {
            verticalTranslation = enemyShip.getSpeed();
            horizontalTranslation = (int) (3 * cs + 2 * cs * cs * cs);
          } else if (status.getLevel() < GameSettings.LEVEL_TO_MOVE_ENEMY_SHIP_3X) {
            verticalTranslation = (int) (enemyShip.getSpeed() * GameSettings.SPEED_TO_MOVE_ENEMY_SHIP_2X);
            horizontalTranslation = (int) (3 * cs + 2 * cs * cs * cs);
          } else {
            verticalTranslation = (int) (enemyShip.getSpeed() * GameSettings.SPEED_TO_MOVE_ENEMY_SHIP_3X);
            horizontalTranslation = (int) (5 * cs + 2 * cs * cs * cs);
          }

          enemyShip.translate(horizontalTranslation, verticalTranslation);
          graphicsMan.drawEnemyShip(enemyShip, g2d, this);

          // fire enemy bullets
          if ((currentTime - enemyShip.getEnemyBulletTime()) > enemyShip.getCooldown()) {
            enemyShip.setEnemyBulletTime(currentTime);
            gameLogic.fireEnemyBullet(i);
            enemyShip.changeCooldown();
          }

        } else {
          enemyShip.setLocation(rand.nextInt(getWidth() - enemyShip.width), 0);
          enemyShip.setHorizontalSpeed(rand.nextInt(enemyShip.getSpeed()));

        }
      } else {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - enemyShip.getLastEnemyShipTime()) > GameSettings.NEW_ENEMY_SHIP_DELAY) {
          // draw a new enemy ship (plus fired bullet)
          enemyShip.setLastEnemyShipTime(currentTime);
          enemyShip.isNew(false);
          enemyShip.setLocation(rand.nextInt(getWidth() - enemyShip.width), 0);
          gameLogic.fireEnemyBullet(i);
        } else {
          // draw explosion
          graphicsMan.drawEnemyShipExplosion(enemyShipExplosion, g2d, this);
        }
      }
    }
  }

  /**
   * Draws the palyer's bullets to the screen
   * 
   * @param bullets
   *          - the list of bullets to be drawn
   */
  private void drawBullets(List<Bullet> bullets) {
    for (int i = 0; i < bullets.size(); i++) {
      Bullet bullet = bullets.get(i);
      graphicsMan.drawBullet(bullet, g2d, this);

      boolean remove = gameLogic.moveBullet(bullet);
      if (remove) {
        bullets.remove(i);
        i--;
      }
    }
  }

  /**
   * Draws the enemy ship's bullets to the screen
   * 
   * @param enemyBullets
   *          - the list of enemy bullets to be drawn
   */
  private void drawEnemyBullets(List<EnemyBullet> enemyBullets) {
    for (int i = 0; i < enemyBullets.size(); i++) {
      EnemyBullet enemyBullet = enemyBullets.get(i);
      graphicsMan.drawEnemyBullet(enemyBullet, g2d, this);

      boolean remove = gameLogic.moveEnemyBullet(enemyBullet);
      if (remove) {
        enemyBullets.remove(i);
        i--;
      }
    }
  }

  /**
   * Draws the player's ship to the screen
   * 
   * @param ship
   *          - the ship to be drawn
   */
  private void drawShip(Ship ship) {
    // draw ship
    if (!status.isNewShip()) {
      // draw it in its current location
      graphicsMan.drawShip(ship, g2d, this);
    } else {
      // draw a new one
      long currentTime = System.currentTimeMillis();
      if ((currentTime - lastShipTime) > GameSettings.NEW_SHIP_DELAY) {
        lastShipTime = currentTime;
        status.setNewShip(false);
        ship = gameLogic.newShip(this);
      } else {
        // draw explosion
        graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
      }
    }
  }

  /**
   * Checks the collisions between the enemy ships and the player's bullets
   * 
   * @param enemyShips
   *          - the list of enemy ships on the screen
   * @param bullets
   *          - the list of the player's bullets on the screen
   */
  private void checkBulletEnemyShipCollision(ArrayList<EnemyShip> enemyShips, List<Bullet> bullets) {
    for (int j = 0; j < enemyShips.size(); j++) {
      EnemyShip enemyShip = enemyShips.get(j);
      for (int i = 0; i < bullets.size(); i++) {
        Bullet bullet = bullets.get(i);
        if (enemyShip.intersects(bullet)) {
          // increase asteroids destroyed count
          status.setEnemyShipsDestroyed(status.getEnemyShipsDestroyed() + 1);

          // "remove" enemyShip
          enemyShipExplosion = new Rectangle(enemyShip.x, enemyShip.y, enemyShip.width,
              enemyShip.height);
          enemyShip.setLocation(-enemyShip.width, -enemyShip.height);
          enemyShip.isNew(true);
          enemyShip.setLastEnemyShipTime(System.currentTimeMillis());

          // play asteroid explosion sound
          soundMan.playEnemyShipExplosionSound();

          // remove bullet
          bullets.remove(i);
          break;
        }
      }
    }
  }

  /**
   * Check collisions between chuck norris and bullets
   * 
   * @param chuckNorriss
   *          - list of all chuck norris in screen
   * @param bullets
   *          - list of player's bullets
   */
  private void checkBulletChuckNorrisCollision(ArrayList<ChuckNorris> chuckNorriss,
      List<Bullet> bullets, List<EnemyBullet> enemyBullets) {
    for (int j = 0; j < chuckNorriss.size(); j++) {
      ChuckNorris chuckNorris = chuckNorriss.get(j);
      for (int i = 0; i < bullets.size(); i++) {
        Bullet bullet = bullets.get(i);
        if (chuckNorris.intersects(bullet)) {
          // remove bullet
          bullets.remove(i);
          enemyBullets.add(new EnemyBullet(chuckNorris));

          // sound
          soundMan.playChuckNorrisSound();
          break;
        }
      }
    }
  }

  /**
   * Check collisions between the player's bullets and the enemy's bullets.
   * 
   * @param bullets
   *          - list of the player's bullets
   * @param enemyBullets
   *          - list of the enemy's bullets
   */
  private void checkBulletEnemyBulletCollision(List<Bullet> bullets, List<EnemyBullet> enemyBullets) {
    for (int j = 0; j < enemyBullets.size(); j++) {
      EnemyBullet enemyBullet = enemyBullets.get(j);
      for (int i = 0; i < bullets.size(); i++) {
        Bullet bullet = bullets.get(i);
        if (enemyBullet.intersects(bullet)) {
          // remove bullet
          bullets.remove(i);
          enemyBullets.remove(j);
          break;
        }
      }
    }
  }

  /**
   * Checks the collisions between the player's ship and the enemy's bullets
   * 
   * @param ship
   *          - the player's ship on the screen
   * @param enemyBullets
   *          - the list of enemy bullets on the screen
   */
  private void checkShipEnemyBulletCollision(Ship ship, List<EnemyBullet> enemyBullets) {
    // check enemyBullet-ship collision
    for (int i = 0; i < enemyBullets.size(); i++) {
      EnemyBullet enemyBullet = enemyBullets.get(i);
      if (ship.intersects(enemyBullet)) {
        // decrease number of ships left
        status.setShipsLeft(status.getShipsLeft() - 1);

        // "remove" ship
        shipExplosion = new Rectangle(ship.x, ship.y, ship.width, ship.height);
        ship.setLocation(this.getWidth() + ship.width, -ship.height);
        status.setNewShip(true);
        lastShipTime = System.currentTimeMillis();

        // play ship explosion sound
        soundMan.playShipExplosionSound();

        // remove bullet
        enemyBullets.remove(i);
        break;
      }
    }
  }

  /**
   * Checks collisions between the player's ship and the enemy's ships
   * 
   * @param ship
   *          - the player's ship on the screen
   * @param enemyShips
   *          - the list of enemy ships on the screen
   */
  private void checkShipEnemyShipCollision(Ship ship, ArrayList<EnemyShip> enemyShips) {
    // check ship-enemyShip collision
    for (int i = 0; i < enemyShips.size(); i++) {
      EnemyShip enemyShip = enemyShips.get(i);
      if (enemyShip.intersects(ship)) {
        // decrease number of ships left
        status.setShipsLeft(status.getShipsLeft() - 1);

        status.setEnemyShipsDestroyed(status.getEnemyShipsDestroyed() + 1);

        // "remove" enemyShip
        enemyShipExplosion = new Rectangle(enemyShip.x, enemyShip.y, enemyShip.width,
            enemyShip.height);
        enemyShip.setLocation(-enemyShip.width, -enemyShip.height);
        enemyShip.isNew(true);
        enemyShip.setLastEnemyShipTime(System.currentTimeMillis());

        // "remove" ship
        shipExplosion = new Rectangle(ship.x, ship.y, ship.width, ship.height);
        ship.setLocation(this.getWidth() + ship.width, -ship.height);
        status.setNewShip(true);
        lastShipTime = System.currentTimeMillis();

        // play ship explosion sound
        soundMan.playShipExplosionSound();
        // play asteroid explosion sound
        soundMan.playEnemyShipExplosionSound();
      }
    }
  }

  /**
   * Checks collisions between the player's bullets and the asteroids
   * 
   * @param asteroids
   *          - the list of asteroids on the screen
   * @param bullets
   *          - the list of the player's bullets on the screen
   */
  private void checkBulletAsteroidCollision(ArrayList<Asteroid> asteroids, List<Bullet> bullets) {
    // check bullet-asteroid collisions for each asteroid
    for (int j = 0; j < asteroids.size(); j++) {
      Asteroid asteroid = asteroids.get(j);
      for (int i = 0; i < bullets.size(); i++) {
        Bullet bullet = bullets.get(i);
        if (asteroid.intersects(bullet)) {
          // increase asteroids destroyed count
          status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

          // "remove" asteroid
          asteroidExplosion = new Rectangle(asteroid.x, asteroid.y, asteroid.width, asteroid.height);
          asteroid.setLocation(-asteroid.width, -asteroid.height);
          asteroid.isNew(true);
          asteroid.setLastAsteroidTime(System.currentTimeMillis());

          // play asteroid explosion sound
          soundMan.playAsteroidExplosionSound();

          // remove bullet
          bullets.remove(i);
          break;
        }
      }
    }
  }

  /**
   * Checks the collisions between the asteroids and the player's ship
   * 
   * @param asteroids
   *          - the list of asteroids on the screen
   * @param ship
   *          - the player's ship on the screen
   */
  private void checkAsteroidsShipCollision(ArrayList<Asteroid> asteroids, Ship ship) {
    // check ship-asteroid collisions for each asteroid
    for (int i = 0; i < asteroids.size(); i++) {
      Asteroid asteroid = asteroids.get(i);
      if (asteroid.intersects(ship)) {
        // decrease number of ships left
        status.setShipsLeft(status.getShipsLeft() - 1);

        status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

        // "remove" asteroid
        asteroidExplosion = new Rectangle(asteroid.x, asteroid.y, asteroid.width, asteroid.height);
        asteroid.setLocation(-asteroid.width, -asteroid.height);
        asteroid.isNew(true);
        asteroid.setLastAsteroidTime(System.currentTimeMillis());

        // "remove" ship
        shipExplosion = new Rectangle(ship.x, ship.y, ship.width, ship.height);
        ship.setLocation(this.getWidth() + ship.width, -ship.height);
        status.setNewShip(true);
        lastShipTime = System.currentTimeMillis();

        // play ship explosion sound
        soundMan.playShipExplosionSound();
        // play asteroid explosion sound
        soundMan.playAsteroidExplosionSound();
      }
    }
  }

  /**
   * Check collisions between chuck norris and the player's ship
   * 
   * @param chuckNorriss
   *          - the chuck norris list
   * @param ship
   *          - the player's ship
   */
  private void checkChuckNorrisShipCollision(ArrayList<ChuckNorris> chuckNorriss, Ship ship) {
    // check ship-asteroid collisions for each asteroid
    for (int i = 0; i < chuckNorriss.size(); i++) {
      ChuckNorris chuckNorris = chuckNorriss.get(i);
      if (chuckNorris.intersects(ship)) {
        // decrease number of ships left
        status.setShipsLeft(status.getShipsLeft() - 1);

        // "remove" ship
        shipExplosion = new Rectangle(ship.x, ship.y, ship.width, ship.height);
        ship.setLocation(this.getWidth() + ship.width, -ship.height);
        status.setNewShip(true);
        lastShipTime = System.currentTimeMillis();

        // play chuck norris explosion sound
        soundMan.playChuckNorrisExplosionSound();
      }
    }
  }

  /**
   * Draws the "Game Over" message.
   */
  private void drawGameOver() {
    String gameOverStr = "GAME OVER";
    Font currentFont = biggestFont == null ? bigFont : biggestFont;
    float fontSize = currentFont.getSize2D();
    bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
    FontMetrics fm = g2d.getFontMetrics(bigFont);
    int strWidth = fm.stringWidth(gameOverStr);
    if (strWidth > this.getWidth() - 10) {
      biggestFont = currentFont;
      bigFont = biggestFont;
      fm = g2d.getFontMetrics(bigFont);
      strWidth = fm.stringWidth(gameOverStr);
    }
    int ascent = fm.getAscent();
    int strX = (this.getWidth() - strWidth) / 2;
    int strY = (this.getHeight() + ascent) / 2;
    g2d.setFont(bigFont);
    g2d.setPaint(Color.WHITE);
    g2d.drawString(gameOverStr, strX, strY);
  }

  /**
   * Draws the initial "Get Ready!" message.
   */
  private void drawGetReady() {
    String readyStr = "Get Ready!";
    g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
    FontMetrics fm = g2d.getFontMetrics();
    int ascent = fm.getAscent();
    int strWidth = fm.stringWidth(readyStr);
    int strX = (this.getWidth() - strWidth) / 2;
    int strY = (this.getHeight() + ascent) / 2;
    g2d.setPaint(Color.WHITE);
    g2d.drawString(readyStr, strX, strY);
  }

  /**
   * Draws the specified number of stars randomly on the game screen.
   * 
   * @param numberOfStars
   *          the number of stars to draw
   */
  private void drawStars(int numberOfStars) {
    g2d.setColor(Color.WHITE);
    for (int i = 0; i < numberOfStars; i++) {
      int x = (int) (Math.random() * this.getWidth());
      int y = (int) (Math.random() * this.getHeight());
      g2d.drawLine(x, y, x, y);
    }
  }

  /**
   * Display initial game title screen.
   */
  private void initialMessage() {
    String gameTitleStr = "Void Space";

    Font currentFont = biggestFont == null ? bigFont : biggestFont;
    float fontSize = currentFont.getSize2D();
    bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
    FontMetrics fm = g2d.getFontMetrics(bigFont);
    int strWidth = fm.stringWidth(gameTitleStr);
    if (strWidth > this.getWidth() - 10) {
      bigFont = currentFont;
      biggestFont = currentFont;
      fm = g2d.getFontMetrics(currentFont);
      strWidth = fm.stringWidth(gameTitleStr);
    }
    g2d.setFont(bigFont);
    int ascent = fm.getAscent();
    int strX = (this.getWidth() - strWidth) / 2;
    int strY = (this.getHeight() + ascent) / 2 - ascent;
    g2d.setPaint(Color.YELLOW);
    g2d.drawString(gameTitleStr, strX, strY);

    g2d.setFont(originalFont);
    fm = g2d.getFontMetrics();
    String newGameStr = "Press <Space> to Start a New Game.";
    strWidth = fm.stringWidth(newGameStr);
    strX = (this.getWidth() - strWidth) / 2;
    strY = (this.getHeight() + fm.getAscent()) / 2 + ascent + 16;
    g2d.setPaint(Color.WHITE);
    g2d.drawString(newGameStr, strX, strY);

    fm = g2d.getFontMetrics();
    String exitGameStr = "Press <Esc> to Exit the Game.";
    strWidth = fm.stringWidth(exitGameStr);
    strX = (this.getWidth() - strWidth) / 2;
    strY = strY + 16;
    g2d.drawString(exitGameStr, strX, strY);

    // by team machete
    fm = g2d.getFontMetrics();
    String teamMachete = "By: Team Machete";
    strWidth = fm.stringWidth(teamMachete);
    strX = (this.getWidth() - strWidth) / 2;
    strY = strY + 16;
    g2d.drawString(teamMachete, strX, strY);

    fm = g2d.getFontMetrics();
    String names = "Samuel Rodriguez & Nelian Colon";
    strWidth = fm.stringWidth(names);
    strX = (this.getWidth() - strWidth) / 2;
    strY = strY + 16;
    g2d.drawString(names, strX, strY);
  }

  /**
   * Prepare screen for game over.
   */
  public void doGameOver() {
    shipsValueLabel.setForeground(new Color(128, 0, 0));
  }

  /**
   * Prepare screen for a new game.
   */
  public void doNewGame() {
    // init all asteroid times is not necessary
    lastShipTime = -GameSettings.NEW_SHIP_DELAY;

    bigFont = originalFont;
    biggestFont = null;

    // set labels' text
    shipsValueLabel.setForeground(Color.WHITE);
    shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));
    destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));
    pointsValueLabel.setText(Long.toString(status.getPoints()));
    levelValueLabel.setText(Integer.toString(status.getLevel()));
    destroyedEnemyShipsValueLabel.setText(Long.toString(status.getEnemyShipsDestroyed()));
    bulletsFiredValueLabel.setText(Long.toString(status.getBulletsFired()));
    if (GameSettings.BULLETS_ARE_LIMITED)
      bulletsRemainingValueLabel.setText(Long.toString(status.getBulletsRemaining()));

    // update hit-miss ratio
    if (status.getBulletsFired() > 0) {
      hitMissRatioValueLabel.setText(String.format("%.2f", status.getHitMissRatio()));
    } else {
      hitMissRatioValueLabel.setText(" --");
    }

  }

  /**
   * Sets the game graphics manager.
   * 
   * @param graphicsMan
   *          the graphics manager
   */
  public void setGraphicsMan(GraphicsManager graphicsMan) {
    this.graphicsMan = graphicsMan;
  }

  /**
   * Sets the game logic handler
   * 
   * @param gameLogic
   *          the game logic handler
   */
  public void setGameLogic(GameLogic gameLogic) {
    this.gameLogic = gameLogic;
    this.status = gameLogic.getStatus();
    this.soundMan = gameLogic.getSoundMan();
  }

  /**
   * Sets the label that displays the value for asteroids destroyed.
   * 
   * @param destroyedValueLabel
   *          the label to set
   */
  public void setDestroyedValueLabel(JLabel destroyedValueLabel) {
    this.destroyedValueLabel = destroyedValueLabel;
  }

  /**
   * Sets the label that displays the value of the enemy ships destroyed
   * 
   * @param destroyedEnemyShipsValueLabel
   *          - the label to set
   */
  public void setDestroyedEnemyShipsValueLabel(JLabel destroyedEnemyShipsValueLabel) {
    this.destroyedEnemyShipsValueLabel = destroyedEnemyShipsValueLabel;
  }

  /**
   * Sets the label that displays the value of the points the player has
   * 
   * @param pointsValueLabel
   *          - the label to be set
   */
  public void setPointsValueLabel(JLabel pointsValueLabel) {
    this.pointsValueLabel = pointsValueLabel;
  }

  /**
   * sets the label that displays the value of the level the player is in
   * 
   * @param levelValueLabel
   *          - the label to be set
   */
  public void setLevelValueLabel(JLabel levelValueLabel) {
    this.levelValueLabel = levelValueLabel;
  }

  /**
   * sets the label that displays the value of the bullets that the player has
   * fired
   * 
   * @param bulletsFiredValueLabel
   *          - the label to be set
   */
  public void setBulletsFiredValueLabel(JLabel bulletsFiredValueLabel) {
    this.bulletsFiredValueLabel = bulletsFiredValueLabel;
  }

  /**
   * sets the label that displays the value of the bullets remaining
   * 
   * @param bulletsRemainingValueLabel
   *          - the label to set
   */
  public void setBulletsRemainingValueLabel(JLabel bulletsRemainingValueLabel) {
    this.bulletsRemainingValueLabel = bulletsRemainingValueLabel;
  }

  /**
   * sets the label that displyas the value of the hit/miss ratio
   * 
   * @param hitMissRatioValueLabel
   *          - the label to set
   */
  public void setHitMissRatioValueLabel(JLabel hitMissRatioValueLabel) {
    this.hitMissRatioValueLabel = hitMissRatioValueLabel;
  }

  /**
   * Sets the label that displays the value for ship (lives) left
   * 
   * @param shipsValueLabel
   *          the label to set
   */
  public void setShipsValueLabel(JLabel shipsValueLabel) {
    this.shipsValueLabel = shipsValueLabel;
  }
}

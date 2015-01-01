package rbadia.voidspace.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.ChuckNorris;
import rbadia.voidspace.model.EnemyBullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Handles general game logic and status.
 */
public class GameLogic {
  private GameScreen gameScreen;
  private GameStatus status;
  private SoundManager soundMan;

  private Ship ship;
  private ArrayList<Asteroid> asteroids;
  private ArrayList<EnemyShip> enemyShips;
  private ArrayList<ChuckNorris> chuckNorriss;
  private List<Bullet> bullets;
  private List<EnemyBullet> enemyBullets;

  /**
   * Create a new game logic handler
   * 
   * @param gameScreen
   *          the game screen
   */
  public GameLogic(GameScreen gameScreen) {
    this.gameScreen = gameScreen;

    // initialize game status information
    status = new GameStatus();
    // initialize the sound manager
    soundMan = new SoundManager();

    // init some variables
    bullets = new ArrayList<Bullet>();
    enemyBullets = new ArrayList<EnemyBullet>();
    asteroids = new ArrayList<Asteroid>();
    chuckNorriss = new ArrayList<ChuckNorris>();
  }

  /**
   * Returns the game status
   * 
   * @return the game status
   */
  public GameStatus getStatus() {
    return status;
  }

  /**
   * Returns the sound manager
   * 
   * @return the sound manager
   */
  public SoundManager getSoundMan() {
    return soundMan;
  }

  /**
   * Returns the game screen
   * 
   * @return the game screen
   */
  public GameScreen getGameScreen() {
    return gameScreen;
  }

  /**
   * Prepare for a new game.
   */
  public void newGame() {
    status.setGameStarting(true);

    // init game variables
    bullets = new ArrayList<Bullet>();
    enemyBullets = new ArrayList<EnemyBullet>();
    asteroids = new ArrayList<Asteroid>();
    enemyShips = new ArrayList<EnemyShip>();
    chuckNorriss = new ArrayList<ChuckNorris>();

    // init game status
    status.setShipsLeft(GameSettings.INITIAL_NUM_OF_SHIPS);
    status.setGameOver(false);
    status.setAsteroidsDestroyed(0);
    status.setEnemyShipsDestroyed(0);
    status.setPoints(0);
    status.setLevel(1);
    status.setBulletsFired(0);
    status.setBulletsRemaining(GameSettings.INITIAL_BULLETS_REMAINING);

    // init the ship and the asteroid
    newShip(gameScreen);

    // make new asteroids for first level
    for (int i = 0; i < GameSettings.NUM_ASTEROIDS_L1; i++)
      asteroids.add(newAsteroid(gameScreen));

    // make new enemyShips for first level
    for (int i = 0; i < GameSettings.NUM_ENEMY_SHIPS_L1; i++)
      enemyShips.add(newEnemyShip(gameScreen));

    // make new chuckNorriss for first level
    for (int i = 0; i < GameSettings.NUM_CHUCK_NORRIS_L1; i++)
      chuckNorriss.add(newChuckNorris(gameScreen));

    // prepare game screen
    gameScreen.doNewGame();

    // delay to display "Get Ready" message for GET_READY_DELAY_TIME
    // milliseconds
    Timer timer = new Timer(GameSettings.GET_READY_DELAY_TIME, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        status.setGameStarting(false);
        status.setGameStarted(true);
      }
    });
    timer.setRepeats(false);
    timer.start();
  }

  /**
   * Check game or level ending conditions.
   */
  public void checkConditions() {
    // check game over conditions
    if (!status.isGameOver() && status.isGameStarted()) {
      if (status.getShipsLeft() == 0) {
        gameOver();
      }
    }
  }

  /**
   * Actions to take when the game is over.
   */
  public void gameOver() {
    status.setGameStarted(false);
    status.setGameOver(true);
    gameScreen.doGameOver();

    // delay to display "Game Over" message for GAME_OVER_DELAY_TIME
    // milliseconds
    Timer timer = new Timer(GameSettings.GAME_OVER_DELAY_TIME, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        status.setGameOver(false);
      }
    });
    timer.setRepeats(false);
    timer.start();
  }

  /**
   * Fire a bullet from ship.
   */
  public void fireBullet() {
    if (status.getBulletsRemaining() > 0) {
      Bullet bullet = new Bullet(ship);
      bullets.add(bullet);
      soundMan.playBulletSound();
      status.setBulletsFired(status.getBulletsFired() + 1);
      if (GameSettings.BULLETS_ARE_LIMITED)
        status.setBulletsRemaining(status.getBulletsRemaining() - 1);
    }
  }

  // must receive as parameter the index ship that will fire
  /**
   * Fire an enemy bullet from the enemy ship.
   */
  public void fireEnemyBullet(int index) {
    EnemyBullet enemyBullet = new EnemyBullet(enemyShips.get(index));
    enemyBullets.add(enemyBullet);
    soundMan.playEnemyBulletSound();
  }

  /**
   * Move a bullet once fired.
   * 
   * @param bullet
   *          the bullet to move
   * @return if the bullet should be removed from screen
   */
  public boolean moveBullet(Bullet bullet) {
    if (bullet.getY() - bullet.getSpeed() >= 0) {
      bullet.translate(0, -bullet.getSpeed());
      return false;
    } else {
      return true;
    }
  }

  /**
   * Move an enemy bullet once fired
   * 
   * @param enemyBullet
   *          - the bullet to move
   * @return bool - if the bullet should be removed from the screen
   */
  public boolean moveEnemyBullet(EnemyBullet enemyBullet) {
    if (enemyBullet.getY() - enemyBullet.getSpeed() >= 0) {
      enemyBullet.translate(0, -enemyBullet.getSpeed());
      return false;
    } else {
      return true;
    }
  }

  /**
   * Create a new ship (and replace current one).
   */
  public Ship newShip(GameScreen screen) {
    this.ship = new Ship(screen);
    return ship;
  }

  /**
   * Create a new asteroid
   * 
   * @param screen
   *          - the game screen where to create it
   * @return the new asteroid
   */
  public Asteroid newAsteroid(GameScreen screen) {
    return new Asteroid(screen);
  }

  /**
   * Create a new Chuck Norris
   * 
   * @param screen
   *          - the game screen where to create it
   * @return the new chuck norris
   */
  public ChuckNorris newChuckNorris(GameScreen screen) {
    return new ChuckNorris(screen);
  }

  /**
   * Create a new enemy ship
   * 
   * @param screen
   *          - the game screen where to create it
   * @return the new enemy ship
   */
  public EnemyShip newEnemyShip(GameScreen screen) {
    return new EnemyShip(screen);
  }

  /**
   * Returns the ship.
   * 
   * @return the ship
   */
  public Ship getShip() {
    return ship;
  }

  /**
   * Returns the asteroid list.
   * 
   * @return the asteroid list
   */
  public ArrayList<Asteroid> getAsteroidList() {
    return this.asteroids;
  }

  /**
   * Returns the chuck norris list.
   * 
   * @return the chuck norris list
   */
  public ArrayList<ChuckNorris> getChuckNorrisList() {
    return this.chuckNorriss;
  }

  /**
   * Returns the enemy ship array list
   * 
   * @return the enemy ship array list
   */
  public ArrayList<EnemyShip> getEnemyShipList() {
    return this.enemyShips;
  }

  /**
   * Returns the list of bullets.
   * 
   * @return the list of bullets
   */
  public List<Bullet> getBullets() {
    return bullets;
  }

  /**
   * Returns the list of enemy bullets
   * 
   * @return
   */
  public List<EnemyBullet> getEnemyBullets() {
    return enemyBullets;
  }
}

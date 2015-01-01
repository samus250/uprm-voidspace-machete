package rbadia.voidspace.main;

/**
 * This static utility class contains all static final variables for game
 * settings, the game can be configured fully with this class. DEDICATED TO
 * NELIAN
 * 
 * @author Samuel Rodriguez Martinez & Nelian E. Colon Collazo
 *
 */
public class GameSettings {

  // DISPLAY SIZES
  public static final int MAIN_FRAME_X_SIZE = 720;
  public static final int MAIN_FRAME_Y_SIZE = 605;
  public static final int GAME_FRAME_X_SIZE = 700;
  public static final int GAME_FRAME_Y_SIZE = 500;
  // MESSAGES DELAY TIME
  public static final int GET_READY_DELAY_TIME = 1500;
  public static final int GAME_OVER_DELAY_TIME = 3000;

  // INITIAL PLAYER'S LIVES
  public static final int INITIAL_NUM_OF_SHIPS = 5;

  // PLAYER'S BULLETS
  public static final boolean BULLETS_ARE_LIMITED = true;
  public static final int INITIAL_BULLETS_REMAINING = 100;
  public static final int LEVELS_FOR_GAINING_BULLETS = 1;
  public static final int BULLET_GAIN_PER_LEVELS = 75;
  public static final int BULLETS_PER_LIVES = 50;

  // POINT SYSTEM SETTINGS
  public static final int ENEMY_SHIP_POINTS = 1000;
  public static final int ASTEROID_POINTS = 500;

  // level upgrade settings
  public static final boolean CHANGE_LEVEL_WITH_POINTS = false;
  public static final int POINTS_TO_NEXT_LEVEL = 1000;
  public static final int TARGETS_TO_NEXT_LEVEL = 10;

  // EXPLOSION DELAY SETTINGS
  public static final int NEW_SHIP_DELAY = 500;
  public static final int NEW_ASTEROID_DELAY = 500;
  public static final int NEW_ENEMY_SHIP_DELAY = 500;

  // CHANGES THROUGH LEVELS
  // asteroid changes
  public static final boolean ASTEROID_SINUSOIDAL_MOVEMENT = false;
  public static final boolean ASTEROID_NELIANS_TECATEX_MOVEMENT = false;
  public static final int LEVEL_TO_MOVE_ASTEROIDS = 2;
  public static final int LEVEL_TO_MOVE_ASTEROIDS_2X = 5;
  public static final int LEVEL_TO_MOVE_ASTEROIDS_3X = 10;
  public static final double SPEED_TO_MOVE_ASTEROIDS_2X = 1.2;
  public static final double SPEED_TO_MOVE_ASTEROIDS_3X = 1.5;

  // levels to change asteroids
  public static final int LEVEL_FOR_ASTEROIDS_L2 = 2;
  public static final int LEVEL_FOR_ASTEROIDS_L3 = 10;

  // number of asteroids per level change
  public static final int NUM_ASTEROIDS_L1 = 1;
  public static final int NUM_ASTEROIDS_L2 = 3;
  public static final int NUM_ASTEROIDS_L3 = 5;

  // changes of number of enemyShips
  public static final int LEVEL_FOR_ENEMY_SHIPS_L2 = 5;
  public static final int LEVEL_FOR_ENEMY_SHIPS_L3 = 10;

  // number of enemyShips per level change
  public static final int NUM_ENEMY_SHIPS_L1 = 1;
  public static final int NUM_ENEMY_SHIPS_L2 = 3;
  public static final int NUM_ENEMY_SHIPS_L3 = 5;

  // number of chuckNorriss per level change
  public static final int NUM_CHUCK_NORRIS_L1 = 1;

  // enemy ship changes
  public static final int LEVEL_TO_MOVE_ENEMY_SHIP_2X = 5;
  public static final int LEVEL_TO_MOVE_ENEMY_SHIP_3X = 10;
  public static final double SPEED_TO_MOVE_ENEMY_SHIP_2X = 1.2;
  public static final double SPEED_TO_MOVE_ENEMY_SHIP_3X = 1.5;

  // player's bullets changes
  public static final int LEVEL_TO_FASTER_BULLETS = 5;
  public static final int BULLETS_PER_SECOND = 5;
  public static final int BULLETS_PER_SECOND_FAST = 7;

  // player's extra ships changes
  public static final int LEVELS_FOR_EXTRA_SHIPS = 1;
  public static final int EXTRA_SHIPS_PER_LEVELS = 1;

}

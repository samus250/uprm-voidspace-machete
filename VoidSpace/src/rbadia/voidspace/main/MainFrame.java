package rbadia.voidspace.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The game's main frame. Contains all the game's labels, file menu, and game
 * screen.
 */
public class MainFrame extends JFrame {
  private static final long serialVersionUID = 1L;

  private JPanel jContentPane = null;

  private GameScreen gameScreen = null;

  private JLabel destroyedLabel;
  private JLabel destroyedValueLabel;
  private JLabel shipsLabel;
  private JLabel shipsValueLabel;
  private JLabel pointsLabel;
  private JLabel pointsValueLabel;
  private JLabel levelLabel;
  private JLabel levelValueLabel;
  private JLabel destroyedEnemyShipsLabel;
  private JLabel destroyedEnemyShipsValueLabel;
  private JLabel bulletsFiredLabel;
  private JLabel bulletsFiredValueLabel;
  private JLabel bulletsRemainingLabel;
  private JLabel bulletsRemainingValueLabel;
  private JLabel hitMissRatioLabel;
  private JLabel hitMissRatioValueLabel;

  /**
   * This is the default constructor
   */
  public MainFrame() {
    super();
    initialize();
  }

  /**
   * This method initializes this
   * 
   * @return void
   */
  private void initialize() {
    this.setSize(GameSettings.MAIN_FRAME_X_SIZE, GameSettings.MAIN_FRAME_Y_SIZE);
    this.setContentPane(getJContentPane());
    jContentPane.setBackground(Color.BLACK);
    this.setTitle("Void Space");
    // this.setResizable(false);

    Dimension dim = this.getToolkit().getScreenSize();
    Rectangle bounds = this.getBounds();
    this.setLocation((dim.width - bounds.width) / 2, (dim.height - bounds.height) / 2);
  }

  /**
   * This method initializes jContentPane
   * 
   * @return javax.swing.JPanel
   */
  private JPanel getJContentPane() {
    if (jContentPane == null) {

      // for hitMissRatioValueLabel
      GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
      gridBagConstraints16.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints16.gridy = 4;
      gridBagConstraints16.anchor = GridBagConstraints.WEST;
      gridBagConstraints16.weightx = 1.0D;
      gridBagConstraints16.gridx = 3;

      // for hitMissRatioLabel
      GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
      gridBagConstraints15.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints15.gridy = 4;
      gridBagConstraints15.anchor = GridBagConstraints.EAST;
      gridBagConstraints15.weightx = 1.0D;
      gridBagConstraints15.gridx = 2;

      // for bulletsRemainingValueLabel
      GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
      gridBagConstraints14.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints14.gridy = 3;
      gridBagConstraints14.anchor = GridBagConstraints.WEST;
      gridBagConstraints14.weightx = 1.0D;
      gridBagConstraints14.gridx = 1;

      // for bulletsRemainingLabel
      GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
      gridBagConstraints13.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints13.gridy = 3;
      gridBagConstraints13.anchor = GridBagConstraints.EAST;
      gridBagConstraints13.weightx = 1.0D;
      gridBagConstraints13.gridx = 0;

      // for bulletsFiredValueLabel
      GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
      gridBagConstraints12.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints12.gridy = 3;
      gridBagConstraints12.anchor = GridBagConstraints.WEST;
      gridBagConstraints12.weightx = 1.0D;
      gridBagConstraints12.gridx = 3;

      // for bulletsFiredLabel
      GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
      gridBagConstraints11.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints11.gridy = 3;
      gridBagConstraints11.anchor = GridBagConstraints.EAST;
      gridBagConstraints11.weightx = 1.0D;
      gridBagConstraints11.gridx = 2;

      // for pointsValueLabel
      GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
      gridBagConstraints10.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints10.gridy = 4;
      gridBagConstraints10.anchor = GridBagConstraints.WEST;
      gridBagConstraints10.weightx = 1.0D;
      gridBagConstraints10.gridx = 1;

      // for pointsLabel
      GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
      gridBagConstraints9.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints9.gridy = 4;
      gridBagConstraints9.anchor = GridBagConstraints.EAST;
      gridBagConstraints9.weightx = 1.0D;
      gridBagConstraints9.gridx = 0;

      // for ENEMY SHIPS VALUE
      GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
      gridBagConstraints8.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints8.gridy = 2;
      gridBagConstraints8.anchor = GridBagConstraints.WEST;
      gridBagConstraints8.weightx = 1.0D;
      gridBagConstraints8.gridx = 3;

      // for ENEMY SHIPS
      GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
      gridBagConstraints7.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints7.gridy = 2;
      gridBagConstraints7.anchor = GridBagConstraints.EAST;
      gridBagConstraints7.weightx = 1.0D;
      gridBagConstraints7.gridx = 2;

      // for levelValueLabel
      GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
      gridBagConstraints6.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints6.gridy = 2;
      gridBagConstraints6.anchor = GridBagConstraints.WEST;
      gridBagConstraints6.weightx = 1.0D;
      gridBagConstraints6.gridx = 1;

      // for levelLabel
      GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
      gridBagConstraints5.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints5.gridy = 2;
      gridBagConstraints5.anchor = GridBagConstraints.EAST;
      gridBagConstraints5.weightx = 1.0D;
      gridBagConstraints5.gridx = 0;

      // for asteroid value label
      GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
      gridBagConstraints4.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints4.gridy = 1;
      gridBagConstraints4.anchor = GridBagConstraints.WEST;
      gridBagConstraints4.weightx = 1.0D;
      gridBagConstraints4.gridx = 3;

      // for asteroid label
      GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
      gridBagConstraints3.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints3.gridy = 1;
      gridBagConstraints3.anchor = GridBagConstraints.EAST;
      gridBagConstraints3.weightx = 1.0D;
      gridBagConstraints3.gridx = 2;

      // for ship value label
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints2.gridy = 1;
      gridBagConstraints2.anchor = GridBagConstraints.WEST;
      gridBagConstraints2.weightx = 1.0D;
      gridBagConstraints2.gridx = 1;

      // for Ship label
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints1.gridy = 1;
      gridBagConstraints1.anchor = GridBagConstraints.EAST;
      gridBagConstraints1.weightx = 1.0D;
      gridBagConstraints1.gridx = 0;

      // i do not know for what
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 0;
      gridBagConstraints.fill = GridBagConstraints.NONE;
      gridBagConstraints.gridwidth = 4;

      shipsLabel = new JLabel("Ships Left: ");
      shipsValueLabel = new JLabel(Integer.toString(GameSettings.INITIAL_NUM_OF_SHIPS));
      destroyedLabel = new JLabel("Asteroids Destroyed: ");
      destroyedValueLabel = new JLabel("0");

      // stuff
      levelLabel = new JLabel("Level: ");
      levelValueLabel = new JLabel("1");
      pointsLabel = new JLabel("Points: ");
      pointsValueLabel = new JLabel("0");
      destroyedEnemyShipsLabel = new JLabel("Enemy Ships Destroyed: ");
      destroyedEnemyShipsValueLabel = new JLabel("0");
      hitMissRatioLabel = new JLabel("Hit / Miss %:");
      hitMissRatioValueLabel = new JLabel(" --");

      // bullets stats
      bulletsFiredLabel = new JLabel("Bullets Fired: ");
      bulletsFiredValueLabel = new JLabel("0");
      if (GameSettings.BULLETS_ARE_LIMITED) {
        bulletsRemainingLabel = new JLabel("Bullets Remaining: ");
        bulletsRemainingValueLabel = new JLabel(
            Long.toString(GameSettings.INITIAL_BULLETS_REMAINING));
      }

      jContentPane = new JPanel();
      jContentPane.setLayout(new GridBagLayout());
      jContentPane.add(getGameScreen(), gridBagConstraints);
      jContentPane.add(shipsLabel, gridBagConstraints1);
      jContentPane.add(shipsValueLabel, gridBagConstraints2);
      jContentPane.add(destroyedLabel, gridBagConstraints3);
      jContentPane.add(destroyedValueLabel, gridBagConstraints4);

      // add points and level labels
      jContentPane.add(levelLabel, gridBagConstraints5);
      jContentPane.add(levelValueLabel, gridBagConstraints6);
      jContentPane.add(destroyedEnemyShipsLabel, gridBagConstraints7);
      jContentPane.add(destroyedEnemyShipsValueLabel, gridBagConstraints8);
      jContentPane.add(pointsLabel, gridBagConstraints9);
      jContentPane.add(pointsValueLabel, gridBagConstraints10);

      // bullets fired
      jContentPane.add(bulletsFiredLabel, gridBagConstraints11);
      jContentPane.add(bulletsFiredValueLabel, gridBagConstraints12);
      // bullets remaining
      if (GameSettings.BULLETS_ARE_LIMITED) {
        jContentPane.add(bulletsRemainingLabel, gridBagConstraints13);
        jContentPane.add(bulletsRemainingValueLabel, gridBagConstraints14);
      }

      // hitMiss ratio
      jContentPane.add(hitMissRatioLabel, gridBagConstraints15);
      jContentPane.add(hitMissRatioValueLabel, gridBagConstraints16);

      destroyedLabel.setForeground(Color.WHITE);
      destroyedValueLabel.setForeground(Color.WHITE);
      shipsLabel.setForeground(Color.WHITE);
      shipsValueLabel.setForeground(Color.WHITE);
      pointsLabel.setForeground(Color.WHITE);
      pointsValueLabel.setForeground(Color.WHITE);
      levelLabel.setForeground(Color.WHITE);
      levelValueLabel.setForeground(Color.WHITE);
      destroyedEnemyShipsLabel.setForeground(Color.WHITE);
      destroyedEnemyShipsValueLabel.setForeground(Color.WHITE);
      bulletsFiredLabel.setForeground(Color.WHITE);
      bulletsFiredValueLabel.setForeground(Color.WHITE);
      bulletsRemainingLabel.setForeground(Color.WHITE);
      bulletsRemainingValueLabel.setForeground(Color.WHITE);
      hitMissRatioLabel.setForeground(Color.WHITE);
      hitMissRatioValueLabel.setForeground(Color.WHITE);

    }
    return jContentPane;
  }

  /**
   * This method initializes gameScreen
   * 
   * @return GameScreen
   */
  public GameScreen getGameScreen() {
    if (gameScreen == null) {
      gameScreen = new GameScreen();
      gameScreen.setShipsValueLabel(shipsValueLabel);
      gameScreen.setDestroyedValueLabel(destroyedValueLabel);
      gameScreen.setPointsValueLabel(pointsValueLabel);
      gameScreen.setLevelValueLabel(levelValueLabel);
      gameScreen.setDestroyedEnemyShipsValueLabel(destroyedEnemyShipsValueLabel);
      gameScreen.setBulletsFiredValueLabel(bulletsFiredValueLabel);
      if (GameSettings.BULLETS_ARE_LIMITED)
        gameScreen.setBulletsRemainingValueLabel(bulletsRemainingValueLabel);
      gameScreen.setHitMissRatioValueLabel(hitMissRatioValueLabel);
    }
    return gameScreen;
  }

} // @jve:decl-index=0:visual-constraint="10,10"

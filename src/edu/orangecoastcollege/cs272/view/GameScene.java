/**	
 * CS A272
 * Dat One Adventure Game
 */
package edu.orangecoastcollege.cs272.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.orangecoastcollege.cs272.controller.Controller;
import edu.orangecoastcollege.cs272.model.Enemy;
import edu.orangecoastcollege.cs272.model.Projectile;
import edu.orangecoastcollege.cs272.model.SkillType;
import edu.orangecoastcollege.cs272.model.SpriteAnimation;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * The <code>GameScene</code> class is where the main core of the game takes place.
 * Everything from the gameplay, to the drops, etc.
 * 
 * @author Vichet Meng
 * @version 1.0
 */
public final class GameScene {
	public static final double WINDOW_WIDTH = 1000;
	public static final double WINDOW_HEIGHT = WINDOW_WIDTH * 9 / 16;
	public static final double MAX_BAR_WIDTH = 300;
	public static final double BAR_HEIGHT = 10;
	public static final String FIRE_BALL_PATH = "resources/images/skill/fireball.png";
	public static final String BOOT_PATH = "resources/images/skill/boots.png";
	public static final String FIRE_BALL_SHOT_PATH = "resources/images/player/attack/fireball.png";
	public static final String NORMAL_SHOT_PATH = "resources/images/player/attack/projectile.png";
	public static final double SKILL_ICON_SIDE = 64;
	public static final double PADDING = 20;
	
	//private static final String TROLL_PATH = "resources/images/enemy/troll_move.png";
	private static final String TROLL_WOUNDED_PATH = "resources/images/enemy/troll_move_wounded.png";
	private static final String TROLL_DEATH_PATH = "resources/images/enemy/troll_death.png";

	// Controller
	private static final Controller controller = Controller.getInstance();

	// Enemies and projectiles Lists
	private List<Enemy> allEnemyList = controller.getAllEnemyList();
	private List<Projectile> allProjectileList = controller.getAllProjectiles();

	// Scenes and Stages
	// private Stage mainStage;
	private Scene mainScene;

	// Views
	private Label currentLevelLabel;
	private ImageView playerIV;
	private ArrayList<ImageView> enemyIVs = new ArrayList<>();
	private ArrayList<ImageView> projectilesIV = new ArrayList<>();
	private Rectangle HPBar;
	private Rectangle MPBar;
	private Label hpLabel;
	private Label mpLabel;
	private Label itemPickedLabel;
	private ImageView skillSlot1;
	private ImageView skillSlot2;
	private ImageView activeSkill;
	private Pane container;

	// Sound and animation
	private Animation playerAnimation;
	private ArrayList<Animation> enemysAnimation = new ArrayList<>();

	// Main loop
	private AnimationTimer mainGameLoop;

	// Movements
	boolean up = false, down = false, left = false, right = false, running = false;
	double dx, dy;

	// Miscellaneous
	boolean pause = false;
	int enemyAttackTimer = 0;

	/**
	 * Initializes and loads up the level.
	 * @param level The level to load.
	 */
	public GameScene(final int level) {
		controller.loadNewLevel(level);
	}

	/**
	 * Retrieves the current main scene and prepares all the necessary animations, images,
	 * and key inputs for functionality.
	 * @return The current main scene.
	 */
	public Scene getMainScene() {
		if (mainScene == null) {

			preparePlayerIV();
			container = mainContainerSetup();
			mainScene = new Scene(container, WINDOW_WIDTH, WINDOW_HEIGHT, Color.AQUA);

			prepareEnemiesIVs();
			prepareAnimations();
			handleKeyInputs();

			mainGameLoop = new AnimationTimer() {
				/**
				 * Handles the player movements.
				 */
				@Override
				public void handle(long now) {
					if (!pause) {
						final double cx = playerIV.getBoundsInLocal().getWidth() / 2;
						final double cy = playerIV.getBoundsInLocal().getHeight() / 2;
						if (isEnemyPlayerColliding()) {
							if (dx != 0)
								controller.movePlayer(-dx, 0, cx, cy);
							if (dy != 0)
								controller.movePlayer(0, -dy, cx, cy);
						}
						double moveSpeed = controller.getPlayer().getMovespeed();
						dx = 0;
						dy = 0;
						if (up)
							dy -= moveSpeed;
						if (down)
							dy += moveSpeed;
						if (left)
							dx -= moveSpeed;
						if (right)
							dx += moveSpeed;
						if (running) {
							dx *= 1.2;
							dy *= 1.2;
						}
						controller.movePlayer(dx, dy, cx, cy);
						redraw();
					}
				}
			};
			mainGameLoop.start();
		}
		return mainScene;
	}

	/**
	 * Set up the main container, background image etc.
	 * 
	 * @return the set up Pane.
	 */
	private Pane mainContainerSetup() {
		Pane newContainer = new Pane(playerIV);
		BackgroundImage BI = new BackgroundImage(new Image(new File(MainView.GAME_BACKGROUND_PATH).toURI().toString()),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		newContainer.setBackground(new Background(BI));
		// HPBar setup
		HPBar = new Rectangle(MAX_BAR_WIDTH, BAR_HEIGHT, Color.RED);
		HPBar.relocate(20, 30);

		String maxHPStr = String.valueOf(controller.getPlayer().getMaxHP());
		// HPLabel setup
		hpLabel = new Label(maxHPStr + "/" + maxHPStr);
		hpLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
		hpLabel.setTextFill(Color.RED);
		hpLabel.relocate(15, 10);
		// hpLabel.setBorder(new Border(new BorderStroke(, arg1, arg2, arg3)));

		String maxMPStr = String.valueOf(controller.getPlayer().getMaxMP());
		// MPLabel setup
		mpLabel = new Label(maxMPStr + "/" + maxMPStr);
		mpLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
		mpLabel.setTextFill(Color.BLUE);
		mpLabel.relocate(15, 40);

		// MPBar setup
		MPBar = new Rectangle(MAX_BAR_WIDTH, BAR_HEIGHT, Color.BLUE);
		MPBar.relocate(20, 60);
		
		// Initialize item label
		itemPickedLabel = new Label();
		
		itemPickedLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));
		itemPickedLabel.setTextFill(Color.BLUE);
		itemPickedLabel.relocate(WINDOW_WIDTH / 2, 40);
		itemPickedLabel.setVisible(false);

		// Skill slot set upW
		skillSlot1 = new ImageView(new Image(new File(FIRE_BALL_PATH).toURI().toString()));
		skillSlot2 = new ImageView(new Image(new File(BOOT_PATH).toURI().toString()));
		skillSlot1.setFitHeight(SKILL_ICON_SIDE);
		skillSlot1.setFitWidth(SKILL_ICON_SIDE);
		skillSlot1.relocate(40 + SKILL_ICON_SIDE, WINDOW_HEIGHT - (SKILL_ICON_SIDE + PADDING));
		skillSlot2.setFitHeight(SKILL_ICON_SIDE);
		skillSlot2.setFitWidth(SKILL_ICON_SIDE);
		skillSlot2.relocate(20, WINDOW_HEIGHT - (SKILL_ICON_SIDE + PADDING));
		activeSkill = new ImageView(new Image(new File(BOOT_PATH).toURI().toString()));
		activeSkill.setFitHeight(SKILL_ICON_SIDE);
		activeSkill.setFitWidth(SKILL_ICON_SIDE);
		activeSkill.relocate(WINDOW_WIDTH - (SKILL_ICON_SIDE + PADDING), PADDING);
		
		
		// Current level label setup
		
		currentLevelLabel = new Label("CURRENT LEVEL : " + controller.getCurrentLevel());
		currentLevelLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));
		currentLevelLabel.setTextFill(Color.DARKRED);
		currentLevelLabel.relocate(WINDOW_WIDTH / 2 - 50, WINDOW_HEIGHT - 50);

		newContainer.getChildren().addAll(HPBar, MPBar, hpLabel, mpLabel, skillSlot1, skillSlot2, activeSkill, itemPickedLabel, currentLevelLabel);
		return newContainer;
	}

	private void preparePlayerIV() {
		//controller.getPlayer().setMaxHP(100000);
		//controller.getPlayer().setCurrentHP(100000);
		playerIV = new ImageView(controller.getPlayer().getImage());
		// playerIV.relocate(x, y);
		playerIV.setViewport(new Rectangle2D(0, 0, SKILL_ICON_SIDE, SKILL_ICON_SIDE));

		// container.getChildren().add(playerIV);
	}

	private void prepareEnemiesIVs() {
		// Create Enemy IVs
		for (int i = 0; i < allEnemyList.size(); i++) {
			ImageView enemyToAdd = new ImageView(allEnemyList.get(i).getImage());

			enemyIVs.add(enemyToAdd);
			container.getChildren().add(enemyToAdd);
		}
	}

	/**
	 * Pauses the game.
	 */
	public void pauseGame() {
		pause = true;
	}

	/**
	 * Unpauses the game.
	 */
	public void unpauseGame() {
		pause = false;
	}

	// private void prepareSounds() {
	// // Sound setup
	// Media sound = new Media(new
	// File("gameplaymusic.mp3").toURI().toString());
	// mainGameSound = new MediaPlayer(sound);
	// mainGameSound.setAutoPlay(true);
	// mainGameSound.setVolume(0.5);
	// mainGameSound.setOnEndOfMedia(new Runnable() {
	// public void run() {
	// mainGameSound.seek(Duration.ZERO);
	// }
	// });
	//
	// }

	private void prepareAnimations() {
		// Animation for enemies
		for (int i = 0; i < enemyIVs.size(); i++) {
			Animation animation = new SpriteAnimation(enemyIVs.get(i), Duration.millis(1000), 8, 8, 0, 0, 64, 64);
			enemysAnimation.add(animation);
			animation.setCycleCount(Animation.INDEFINITE);
			animation.play();
		}

		// Animation for player
		playerAnimation = new SpriteAnimation(playerIV, Duration.millis(1000), 8, 8, 0, 0, 64, 64);
		playerAnimation.setCycleCount(Animation.INDEFINITE);
	}

	private void handleKeyInputs() {
		// Mouse events
		// Shot
		mainScene.setOnMouseClicked(e -> {
			controller.playerShot(e.getX(), e.getY());
			ImageView projectileToAdd = new ImageView(allProjectileList.get(allProjectileList.size() - 1).getImage());
			projectilesIV.add(projectileToAdd);
			container.getChildren().add(projectileToAdd);
		});

		// Keyboard inputs
		mainScene.setOnKeyPressed(e -> {

			switch (e.getCode()) {
			case W:
				up = true;
				break;
			case S:
				down = true;
				break;
			case A:
				left = true;
				break;
			case D:
				right = true;
				break;
			case SHIFT:
				running = true;
				break;
			case SPACE:
				controller.playerUsedSkill();
				break;
			case ESCAPE:
				pauseGame();
				ViewNavigator.loadScene(MainView.PROGRAM_NAME + ": Paused", ViewNavigator.PAUSE_SCENE);
				//toggleInventory();
				break;
			case DIGIT1:
				// Player selected movement speed.
				controller.playerSwitchedSkill(SkillType.Passive);
				activeSkill.setImage(new Image(new File(BOOT_PATH).toURI().toString()));
				break;
			case DIGIT2:
				// Player selected fireball.
				controller.playerSwitchedSkill(SkillType.Active);
				activeSkill.setImage(new Image(new File(FIRE_BALL_PATH).toURI().toString()));
				break;
			case I:
				toggleInventory();
				break;
			default:
				break;
			}

			playerAnimation.play();
			if (running)
				playerAnimation.setRate(2);
			if (!running)
				playerAnimation.setRate(1);
		});
		mainScene.setOnKeyReleased(e -> {

			switch (e.getCode()) {
			case W:
				up = false;
				break;
			case S:
				down = false;
				break;
			case A:
				left = false;
				break;
			case D:
				right = false;
				break;
			case SHIFT:
				running = false;
				break;
			case SPACE:
				controller.playerUnusedSkill();
				break;
			default:
				break;

			}

			if (!up && !down && !right && !left)
				playerAnimation.pause();
		});

	}

	/**
	 * Opens up the <code>InventoryScene</code>.
	 */
	public void toggleInventory() {
		pauseGame();
		ViewNavigator.loadScene(MainView.PROGRAM_NAME + "Inventory", ViewNavigator.INVENTORY_SCENE);
	}

	/**
	 * Creates the Game Over scene
	 * 
	 * @return the Game Over scene.
	 */
	// TODO: Redesign needed
	@SuppressWarnings({ "deprecation", "unused" })
	private Scene createGameOverScreen() {
		Group group = new Group();
		Scene gameOverScene = new Scene(group, WINDOW_WIDTH, WINDOW_HEIGHT, Color.RED);
		Label gameOverLb = new Label("GAME OVER!");
		gameOverLb.setTextFill(Color.BLACK);
		gameOverLb.setStyle("-fx-font: 40 arial;");
		group.getChildren().add(gameOverLb);
		gameOverLb.impl_processCSS(true);
		gameOverLb.relocate(WINDOW_WIDTH / 2 - gameOverLb.prefWidth(-1) / 2,
				WINDOW_HEIGHT / 2 - gameOverLb.prefHeight(-1) / 2);
		return gameOverScene;
	}

	/**
	 * Creates the win scene
	 * 
	 * @return the win scene.
	 */
	// TODO: Redesign needed
	private void loadVictoryScreen() {
		mainGameLoop.stop();
		ViewNavigator.loadScene("Victory!", ViewNavigator.VICTORY_SCENE);
	}

	private void loadDefeatScreen() {
		mainGameLoop.stop();
		ViewNavigator.loadScene("Defeat (There's always next time!)", ViewNavigator.DEFEATED_SCENE);
	}

	// Function to synchronize the view with the model
	private void redraw() {
		updateEnemies();
		updatePlayer();
		updateBullets();
		checkingBulletCollision();
	}

	/**
	 * Loop and updates the player locations as well as the player ImageView.
	 * The game over screen is displayed if the player is dead.
	 */
	private void updatePlayer() {
		if (!controller.getPlayer().isAlive()) {
			loadDefeatScreen();
			return;
		}
		// Update the HPBar and Label
		String hpText = String.valueOf(controller.getPlayer().getCurrentHP()) + "/"
				+ String.valueOf(controller.getPlayer().getMaxHP());
		HPBar.setWidth(MAX_BAR_WIDTH * (controller.getPlayer().getCurrentHP() / controller.getPlayer().getMaxHP()));
		hpLabel.setText(hpText);

		// Update the MPBar and the Label
		String mpText = String.valueOf(controller.getPlayer().getCurrentMP()) + "/"
				+ String.valueOf(controller.getPlayer().getMaxMP());
		MPBar.setWidth(MAX_BAR_WIDTH * (controller.getPlayer().getCurrentMP() / controller.getPlayer().getMaxMP()));
		mpLabel.setText(mpText);

		// Update player location
		playerIV.relocate(controller.getPlayer().getX() - playerIV.getBoundsInLocal().getWidth() / 2,
				controller.getPlayer().getY() - playerIV.getBoundsInLocal().getHeight() / 2);

		// Check the directional input of the player and rotate the image view
		// accordingly.
		if (right) {
			if (right && up)
				playerIV.setRotate(45);
			else if (right && down)
				playerIV.setRotate(135);
			else
				playerIV.setRotate(90);
		} else if (left) {
			if (left && up)
				playerIV.setRotate(315);
			else if (left && down)
				playerIV.setRotate(225);
			else
				playerIV.setRotate(270);
		} else if (up)
			playerIV.setRotate(0);
		else if (down)
			playerIV.setRotate(180);
	}

	/**
	 * This function loops through all the enemies in the enemy list and update
	 * the location. It also update the animation of the enemies.
	 */
	private void updateEnemies() {
		// Enemy shoot
		// Update all enemies location
		enemyAttackTimer++;
		for (int i = 0; i < enemyIVs.size(); i++) {
			ImageView currEnemyIV = enemyIVs.get(i);
			Enemy currEnemy = allEnemyList.get(i);
			// Update enemy location
			currEnemy.update(controller.getPlayer());
			enemyAttackTimer = enemyAttackTimer % (200 / currEnemy.getAttackSpeed());
			if (enemyAttackTimer == 0 && currEnemy.isAlive()) {
				controller.enemyShot(currEnemy);
				ImageView projectileToAdd = new ImageView(
						allProjectileList.get(allProjectileList.size() - 1).getImage());
				projectilesIV.add(projectileToAdd);
				container.getChildren().add(projectileToAdd);
			}

			if (((currEnemy.getCurrHP() / currEnemy.getMaxHP()) < 0.5) && !currEnemy.isWounded()) {
				currEnemy.setWounded(true);
				enemysAnimation.get(i).stop();
				enemysAnimation.remove(i);
				currEnemyIV.setImage(new Image(new File(TROLL_WOUNDED_PATH).toURI().toString()));
				Animation woundedAnimation = new SpriteAnimation(currEnemyIV, Duration.millis(1000), 8, 8, 0, 0, 64,
						64);
				woundedAnimation.setCycleCount(Animation.INDEFINITE);
				woundedAnimation.play();
				enemysAnimation.add(i, woundedAnimation);
			}
			// if (!isEnemyPlayerColliding())
			currEnemyIV.relocate(currEnemy.getX() - currEnemyIV.getBoundsInLocal().getWidth() / 2,
					currEnemy.getY() - currEnemyIV.getBoundsInLocal().getHeight() / 2);
			if (currEnemy.isAlive())
				currEnemyIV.setRotate(Math.toDegrees(Math.atan2(controller.getPlayer().getY() - currEnemy.getY(),
						controller.getPlayer().getX() - currEnemy.getX())) + 90);
		}
	}

	/**
	 * This function loops through all the projectile on screen and update the
	 * location.
	 */
	private void updateBullets() {
		// Loop through and update the projectiles location
		// Remove the projectile if it is not visible anymore
		for (int i = 0; i < allProjectileList.size(); i++) {
			Projectile currProjectile = allProjectileList.get(i);
			currProjectile.update();

			ImageView currProjectileIV = projectilesIV.get(i);

			if (!allProjectileList.get(i).isVisible()) {
				// Projectile not visible anymore, so remove it.
				container.getChildren().remove(projectilesIV.get(i));
				allProjectileList.remove(currProjectile);
				projectilesIV.remove(currProjectileIV);

			} else {
				currProjectileIV.relocate(currProjectile.getX() - currProjectileIV.getBoundsInLocal().getWidth() / 2,
						currProjectile.getY() - currProjectileIV.getBoundsInLocal().getHeight() / 2);
			}
		}
	}

	/**
	 * This checks if the player is colliding with the enemies on screen
	 * 
	 * @return true if colliding, false otherwise
	 */
	private boolean isEnemyPlayerColliding() {
		boolean collsion = false;
		for (ImageView eIV : enemyIVs) {
			if (collisionDetected(playerIV, eIV)) {
				collsion = true;
				break;
			}
		}

		return collsion;
	}

	/**
	 * This check the collision of the bullet and the enemies
	 */
	private void checkingBulletCollision() {
		boolean found = false;
		if (allEnemyList.isEmpty()) {
			controller.saveGame();
			controller.addScore(controller.getPlayer().getName(),
					controller.getPlayer().getScore(), 0.0);
			loadVictoryScreen();
			return;
		}
		// Check collision between bullet and enemy
		for (int bulletIndex = 0; bulletIndex < projectilesIV.size(); bulletIndex++) {
			ImageView currBulletIV = projectilesIV.get(bulletIndex);
			Projectile currBullet = allProjectileList.get(bulletIndex);
			for (int enemyIndex = 0; enemyIndex < enemyIVs.size(); enemyIndex++) {
				ImageView currEnemyIV = enemyIVs.get(enemyIndex);
				Enemy currEnemy = allEnemyList.get(enemyIndex);
				if (collisionDetected(currBulletIV, currEnemyIV) && currEnemy.isAlive()
						&& currBullet.getName().equals(Controller.PLAYER_SHOT_NAME)) {
					// Remove the bullet after it hits the enemy from the
					// view
					// list, projectile list, and the screen
					container.getChildren().remove(currBulletIV);
					projectilesIV.remove(currBulletIV);
					allProjectileList.remove(currBullet);

					// Take away enemy HP
					currEnemy.gotAttacked(currBullet.getDamage());
					if (!currEnemy.isAlive()) {
						int random = (int) (Math.random() * 3);
						int itemRandom = (int) (Math.random() * 2 + 1);
						if (random == 1) {
							controller.playerPickedUpItem(String.valueOf(itemRandom));
							// If 1 then HP Potion, 2 Mana Potion
							if (itemRandom == 1) {
								itemPickedLabel.setTextFill(Color.RED);
								itemPickedLabel.setText("Picked up health potion!");
							}
							else {
								itemPickedLabel.setTextFill(Color.BLUE);
								itemPickedLabel.setText("Picked up mana potion!");
							}
							itemPickedLabel.setVisible(true);
						}
						
						// Play death animation
						Animation deathAnimation = new SpriteAnimation(currEnemyIV, Duration.millis(1000), 32, 4, 0, 0,
								64, 64);
						enemysAnimation.get(enemyIndex).stop();
						currEnemyIV.setImage(new Image(new File(TROLL_DEATH_PATH).toURI().toString()));

						deathAnimation.play();
						enemysAnimation.set(enemyIndex, deathAnimation);

						// Delay enemy deletion until the animation
						// completes

						Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
							container.getChildren().remove(currEnemyIV);
							enemysAnimation.remove(deathAnimation);
							allEnemyList.remove(currEnemy);
							enemyIVs.remove(currEnemyIV);
							itemPickedLabel.setVisible(false);
						}));
						timeline.play();
					}
					found = true;
					break;
				}
				if (found)
					break;
			}

		}
		for (int bulletIndex = 0; bulletIndex < projectilesIV.size(); bulletIndex++) {
			ImageView currBulletIV = projectilesIV.get(bulletIndex);
			Projectile currBullet = allProjectileList.get(bulletIndex);
			if (collisionDetected(currBulletIV, playerIV) && currBullet.getName().equals(Controller.ENEMY_SHOT_NAME)) {
				// Deal damage to player.
				controller.playerGotHit((Enemy) currBullet.getShooter());
				// Remove from imageView list, projectile lists and from the
				// main container.

				projectilesIV.remove(currBulletIV);
				allProjectileList.remove(currBullet);
				container.getChildren().remove(currBulletIV);
				break;
			}
		}
	}

	/**
	 * Helper function to check collision between 2 image views
	 * 
	 * @param obj1 The first image.
	 * @param obj2 The second image.
	 * @return true if they are colliding, false otherwise.
	 */
	private boolean collisionDetected(ImageView obj1, ImageView obj2) {
		return obj1.getBoundsInParent().intersects(obj2.getBoundsInParent());
	}
}
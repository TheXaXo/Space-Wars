package objectClasses;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.media.AudioClip;
import sample.GameController;

public class Player extends Sprite {
    private GameController gameController;
    private Double speed;
    private Integer lives;
    private long points;
    private Scene theScene;
    private boolean fired;
    private Timer timer;

    private boolean goLeft;
    private boolean goRight;
    private boolean goUp;
    private boolean goDown;
    private boolean held = false;


    public Rectangle r = new Rectangle();
    public Rectangle rv = new Rectangle();
    public Rectangle rv2 = new Rectangle();

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public Scene getTheScene() {
        return theScene;
    }

    public void setTheScene(Scene theScene) {
        this.theScene = theScene;
    }

    public Player(GameController gameController, Double speed, Integer lives, Scene theScene) throws IOException {
        this.gameController = gameController;
        this.speed = speed;
        this.fired = false;
        this.lives = lives;
        this.points = 0L;
        this.theScene = theScene;
        this.timer = new Timer();

    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }

    public Integer getLives() {
        return lives;
    }

    public void setLives(Integer lives) {
        this.lives = lives;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public void updatePlayerLocation(Canvas canvas) {
        //Offset Formula
        double heightOffset = canvas.getHeight() - 72;
        double widthOffset = canvas.getWidth() - 82;

        double speedMultiplier = 1;

        //Speed up if held var is true(see GameController key events)
        //if (KeyListener.held) {//TODO
            //speedMultiplier = 1.5;
        //}

        if (goUp) {
            this.positionY = Math.max(0, this.positionY - this.speed * speedMultiplier);
        }
        if (goDown) {
            this.positionY = Math.min(heightOffset, this.positionY + this.speed * speedMultiplier);
        }
        if (goLeft) {
            this.positionX = Math.max(0, this.positionX - this.speed * speedMultiplier);
        }
        if (goRight) {
            this.positionX = Math.min(widthOffset, this.positionX + this.speed * speedMultiplier);
        }

        //Updates the hitbox with every player update
        this.r.setY(this.positionY + 38);
        this.r.setX(this.positionX + 13);

        this.rv.setY(this.positionY + 11);
        this.rv.setX(this.positionX + 20);

        this.rv2.setY(this.positionY + 27);
        this.rv2.setX(this.positionX + 38);
    }

    public void getFirePermition(){
        Player that = this;
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                that.setFired(false);
            }
        }, 0, 1000);
    }


    public Missile fire() {
        if (fired) {
            return null;
        }
        AudioClip shoot = new AudioClip(Paths.get("src/resources/sound/lasergun.mp3").toUri().toString());
        shoot.play(0.7);

        //Make missile
        Missile missile = new Missile();
        missile.setPosition(this.positionX + this.width / 1.2, this.positionY + this.height / 2, 2);

        //Load missile sprites
        BufferedImage missileSpriteSheet = null;

        try {
            missileSpriteSheet = ImageIO.read(new File(Controller.PROJECT_PATH + "/src/resources/missiles/largeMissiles.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        missile.setSpriteParameters(31, 7, 1, 23);
        missile.loadSpriteSheet(missileSpriteSheet);
        missile.splitSprites();
        //gameController.setMissiles(missile);//TODO update current level missiles
        this.setFired(true);
        return missile;
    }

    public void initializePlayerControls(Scene theScene, Level level) {
        //Add event listener
        theScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    this.goUp = true;
                    break;
                case DOWN:
                    this.goDown = true;
                    break;
                case LEFT:
                    this.goLeft = true;
                    break;
                case RIGHT:
                    this.goRight = true;
                    break;
                case SPACE:
                    //System.out.println(player.isFired());
                    if(!this.isFired()){
                        level.getMissiles().add(this.fire());
                        this.setFired(true);
                    }
                    break;
                case SHIFT:
                    this.held = true;
            }
        });

        theScene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                    this.goUp = false;
                    break;
                case DOWN:
                    this.goDown = false;
                    break;
                case LEFT:
                    this.goLeft = false;
                    break;
                case RIGHT:
                    this.goRight = false;
                    break;
                case SHIFT:
                    this.held = false;
            }
        });
    }


    public void initializeHitboxes() {
        this.r.setWidth(57);
        this.r.setHeight(7);
        this.r.setStroke(Color.TRANSPARENT);
        this.r.setFill(Color.TRANSPARENT);

        this.rv.setWidth(4);
        this.rv.setHeight(58);
        this.rv.setStroke(Color.TRANSPARENT);
        this.rv.setFill(Color.TRANSPARENT);

        this.rv2.setWidth(3);
        this.rv2.setHeight(28);
        this.rv2.setStroke(Color.TRANSPARENT);
        this.rv2.setFill(Color.TRANSPARENT);
    }

    //Method that checks if the player collides with a given object
    public boolean checkCollision(double x, double y, int offset) {
        int hitX = (int) x;
        int hitY = (int) y;
        int mainX = (int) this.r.getX();
        int mainY = (int) this.r.getY();
        int mainX1 = (int) this.rv.getX();
        int mainY1 = (int) this.rv.getY();
        int mainX2 = (int) this.rv2.getX();
        int mainY2 = (int) this.rv2.getY();

        if ((hitX <= mainX + (int) this.r.getWidth() && hitX + offset >= mainX && hitY <= mainY + (int) this.r.getHeight() && hitY + offset >= mainY) ||
                (hitX <= mainX1 + (int) this.rv.getWidth() && hitX + offset >= mainX1 && hitY <= mainY1 + (int) this.rv.getHeight() && hitY + offset >= mainY1) ||
                (hitX <= mainX2 + (int) this.rv2.getWidth() && hitX + offset >= mainX2 && hitY <= mainY2 + (int) this.rv2.getHeight() && hitY + offset >= mainY2)
                ) {
            return true;
        }
        return false;
    }

    public void resetPlayerPosition(Canvas canvas) {
        this.positionX = 50;
        this.positionY = canvas.getHeight() / 2;
    }

}
package objectClasses;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.Random;

public class Asteroid extends Sprite {
    public boolean isHit = false;
    private double speed;

    public Asteroid(double speed){
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void updateAsteroidLocation(Canvas canvas) {
        //Offset so that asteroids don't spawn outside of boundaries
        double heightOffset = 37;

        //Offset Formula
        double offset = canvas.getHeight() - heightOffset;

        Random rnd = new Random();

        this.positionX -= this.speed;

        if (this.positionX < -20) {
            this.positionX = canvas.getWidth();
            this.positionY = rnd.nextInt((int) offset);
            this.isHit = false;
        }
    }

    /*public static void initializeAsteroids(Asteroid[] asteroids, Canvas canvas) {
        for (int i = 0; i < asteroids.length; i++) {
            Asteroid currentAsteroid = new Asteroid(2.5);//HardCoded
            String path = "resources/asteroid/asteroid" + String.valueOf(SpawnCoordinates.getRandom(4)) + ".png";
            Image image = new Image(path);

            currentAsteroid.setImage(image);
            currentAsteroid.setPosition(SpawnCoordinates.getSpawnX(canvas), SpawnCoordinates.getSpawnY(canvas), 2.5);//HardCoded
            asteroids[i] = currentAsteroid;
        }
    }*/
}
package managers;


import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import entities.Asteroid;
import entities.Level;
import entities.SpawnCoordinates;

import java.util.ArrayList;
import java.util.Random;

public class AsteroidManager {
    //TODO Asteroid class must only have fields, getters and setters. All other methods must be managed by the AsteroidManager class!

    //Initializing Asteroids
    //Checking Asteroid Collisions with player or missile and add explosion to List<Explosion>
    //Must implement all methods from Asteroid class
    private PlayerManager playerManager;

    public AsteroidManager(PlayerManager playerManager) {
        this.setPlayerManager(playerManager);
    }

    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public ArrayList<Asteroid> initializeAsteroids(Canvas canvas) {
        ArrayList<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Asteroid currentAsteroid = new Asteroid(2.5);
            String path = "resources/asteroid/asteroid" +
                    String.valueOf(SpawnCoordinates.getRandom(4)) + ".png";
            Image image = new Image(path);

            currentAsteroid.setImage(image);
            currentAsteroid.setPosition(SpawnCoordinates.getSpawnX(canvas),
                    SpawnCoordinates.getSpawnY(canvas), 2.5);

            asteroids.add(currentAsteroid);
        }
        return asteroids;
    }

    private void updateAsteroidLocation(Canvas canvas, Asteroid asteroid) {
        //Offset so that asteroids don't spawn outside of boundaries
        double heightOffset = 37;

        //Offset Formula
        double offset = canvas.getHeight() - heightOffset;

        Random rnd = new Random();

        asteroid.setPositionX(asteroid.getPositionX() - asteroid.getSpeed());

        if (asteroid.getPositionX() < -20) {
            asteroid.setPositionX(canvas.getWidth());
            asteroid.setPositionY(rnd.nextInt((int) offset));
            asteroid.setHitStatus(false);
        }
    }

    public void manageAsteroids(Level level, AnimationTimer timer) {
        //TODO Iterate through all asteroids and remove the asteroid that was hit and/or remove the missile that was hit

        //Add explosion to list
        //Iterate through all asteroids
        for (Asteroid asteroidToRenderAndUpdate : level.getAsteroids()) {
            if (!asteroidToRenderAndUpdate.getHitStatus()) {
                asteroidToRenderAndUpdate.render(level.getGc());
                manageAsteroidCollision(level, asteroidToRenderAndUpdate, timer);
            }
            //Asteroid speed updating every rotation making them faster:
            asteroidToRenderAndUpdate.setSpeed(asteroidToRenderAndUpdate.getSpeed() + 0.00002);
            this.updateAsteroidLocation(level.getCanvas(), asteroidToRenderAndUpdate);
        }
    }

    private void manageAsteroidCollision(Level level, Asteroid asteroid, AnimationTimer timer) {
        if (this.playerManager.checkCollision(asteroid.getPositionX(),
                asteroid.getPositionY(), 32)) {

            asteroid.setPositionX(-1300);
            this.playerManager.resetPlayerPosition(level.getCanvas());

            level.getPlayer().setLives(level.getPlayer().getLives() - 1);

            try {
                level.checkIfPlayerIsDead(level.getScene(), timer);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //TODO create class to work with all text fields in the game scene
            //TODO Change color of ship when hit, or some kind of visual effect
            //TODO Implement ship damage tracker
        }
    }
}
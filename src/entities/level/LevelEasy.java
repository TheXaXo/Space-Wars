package entities.level;

import entities.Constants;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class LevelEasy extends Level {
    @Override
    public void setDifficultyParameters() {
        super.initializeAsteroids(Constants.ASTEROID_HEALTH_EASY, Constants.ASTEROIDS_COUNT_EASY);
        super.initializeUfos(Constants.UFO_COUNT_EASY, Constants.UFO_SPEED_EASY);
        try {
            super.initializeBosses();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        super.setBackgroundImage(new Image(Constants.EASY_LEVEL_BACKGROUND));
    }
}
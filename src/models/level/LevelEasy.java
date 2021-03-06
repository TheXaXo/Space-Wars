package models.level;

import utils.Constants;
import javafx.scene.image.Image;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class LevelEasy extends BaseLevel {

    private static final String BOSS_TYPE = "Pedobear";

    @Override
    public void setDifficultyParameters() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        super.initializeEnemies(
                Constants.ASTEROID_HEALTH_EASY,
                Constants.ASTEROID_SPEED_EASY,
                Constants.ASTEROID_COUNT_EASY,
                Constants.UFO_HEALTH_EASY,
                Constants.UFO_SPEED_EASY,
                Constants.UFO_COUNT_EASY);

        try {
            super.initializeBoss(BOSS_TYPE);
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException | IOException e) {
            e.printStackTrace();
        }

        super.setBackgroundImage(new Image(Constants.EASY_LEVEL_BACKGROUND));
    }
}
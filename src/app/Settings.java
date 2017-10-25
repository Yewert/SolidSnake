package app;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import app.drawing.TextureType;
import javafx.scene.paint.Color;
import model.creatures.Creature;
import model.creatures.CreatureType;
import model.game.GameSettings;

//TODO: It's a placeholder for settings configuration, something should be implemented in "model" in order to get everything working
// Note: shitty implementation
public class Settings {

    private int size;
    private VisualSettings skins; //= new SkinSettings(1, 1, 1);
    private GameSettings gameplaySettings;

    public Settings(int size, VisualSettings skinSettings, GameSettings gameplaySettings){
        this.size = size;
        this.skins = skinSettings;
        this.gameplaySettings = gameplaySettings;
    }

    private static final Map<TextureType, Function<Integer, Color>> colorDict = Map.of(
        TextureType.SimpleSnakeBodyPart, (snake) -> Color.LIGHTBLUE,
        TextureType.SnakeHead, (snake) -> {
            switch (snake){
                case 0:
                    return Color.BLUE;
                case 1:
                    return Color.RED;
                case 2:
                    return Color.WHITE;
                default:
                    throw new IllegalArgumentException("");
            }
        },
        TextureType.TailDiscardSnakeBodyPart, (snake) -> Color.AQUAMARINE,
        TextureType.Apple, (snake) -> Color.FORESTGREEN,
        TextureType.Mushroom, (snake) -> Color.YELLOWGREEN,
        TextureType.Wall, (snake) -> Color.BLACK
    );

    // Note: we can get this info from GameFrame class --> are these getters unnecessary?
    public int getSize() {
        return size;
    }
    public int getCols() {
        return gameplaySettings.getWidth();
    }
    public int getRows() {
        return gameplaySettings.getHeight();
    }

    public int getHeight() {
        return getCols() * size;
    }
    public int getWidth() {
        return getRows() * size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static Map<TextureType, Function<Integer, Color>> getColorDict(){
        return colorDict;
    }

    public GameSettings getGameplaySettings() {
        return gameplaySettings;
    }

    public void setGameplaySettings(GameSettings gameplaySettings) {
        this.gameplaySettings = gameplaySettings;
    }

    public VisualSettings getSkins() {
        return skins;
    }

    public void setSkins(VisualSettings skins) {
        this.skins = skins;
    }
}

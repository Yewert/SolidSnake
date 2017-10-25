package app;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import app.drawing.TextureType;
import javafx.scene.paint.Color;
import model.creatures.Creature;
import model.creatures.CreatureType;

//TODO: It's a placeholder for settings configuration, something should be implemented in "model" in order to get everything working
// Note: shitty implementation
public final class Settings {

    private static int size = 20;
    private static int cols = 30;
    private static int rows = 30;

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
    public static int getSize() {
        return size;
    }
    public static int getCols() {
        return cols;
    }
    public static int getRows() {
        return rows;
    }

    public static int getHeight() {
        return cols * size;
    }
    public static int getWidth() {
        return rows * size;
    }

    public static void setSize(int size) {
        Settings.size = size;
    }
    public static void setCols(int cols) {
        Settings.cols = cols;
    }
    public static void setRows(int rows) {
        Settings.rows = rows;
    }

    public static Map<TextureType, Function<Integer, Color>> getColorDict(){
        return colorDict;
    }
}

package app;

import java.util.HashMap;
import java.util.Map;

import app.drawing.TextureType;
import javafx.scene.paint.Color;

//TODO: It's a placeholder for settings configuration, something should be implemented in "model" in order to get everything working
// Note: shitty implementation
public final class Settings {

    private static int size = 20;
    private static int cols = 30;
    private static int rows = 30;

    private static final Map<TextureType, Color> colorDict = new HashMap<TextureType, Color>(){{
        put(TextureType.SimpleSnakeBodyPart, Color.LIGHTBLUE);
        put(TextureType.SnakeHead, Color.BLUE);
        put(TextureType.TailDiscardSnakeBodyPart, Color.AQUAMARINE);
        put(TextureType.Apple, Color.FORESTGREEN);
        put(TextureType.Mushroom, Color.ORANGERED);
        put(TextureType.Wall, Color.BLACK);
    } };

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

    public static Map<TextureType, Color> getColorDict(){
        return colorDict;
    }
}

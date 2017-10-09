package app;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;

//TODO: It's a placeholder for settings configuration, something should be implemented in "models" in order to get everything working
// Note: shitty implementation
public final class Settings {

    private static int _size;
    private static int _cols;
    private static int _rows;
    private static int _height;
    private static int _width;

    private static Map<TextureType, Color> _colorDict = new HashMap<TextureType, Color>();

    private Settings(){
        _size = 10;
        _cols = 30;
        _rows = 30;
        updateResolution();

        //TODO: colors (textures) for dead Snake (at least head).
        //Can't be done with current implementation
        _colorDict.put(TextureType.SimpleSnakeBodyPart, Color.LIGHTBLUE);
        _colorDict.put(TextureType.SimpleSnakeHead, Color.BLUE);
    }

    // Note: we can get this info from GameFrame class --> are these getters unnecessary?
    public static int getSize() {
        return _size;
    }
    public static int getCols() {
        return _cols;
    }
    public static int getRows() {
        return _rows;
    }

    public static int getHeight() {
        return _height;
    }
    public static int getWidth() {
        return _width;
    }

    public static void setSize(int size) {
        _size = size;
        updateResolution();
    }
    public static void setCols(int cols) {
        _cols = cols;
        updateResolution();
    }
    public static void setRows(int rows) {
        _rows = rows;
        updateResolution();
    }
    private static void updateResolution() {
        _height = _cols * _size;
        _width = _rows * _size;
    }

    public static Map<TextureType, Color> getColorDict(){
        return _colorDict;
    }
}

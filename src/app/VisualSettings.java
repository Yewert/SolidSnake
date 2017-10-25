package app;

import app.drawing.TextureType;
import javafx.scene.image.Image;
import java.util.Map;

public interface VisualSettings {
    Map<Integer, Map<TextureType, Image>>  getSpritesForPlayers();
    Map<TextureType, Image> getSpritesForSubjects();
}

package app;

import app.drawing.TextureType;
import java.util.Map;
import javafx.scene.image.Image;

public interface VisualSettings {

  Map<Integer, Map<TextureType, Image>> getSpritesForPlayers();

  Map<TextureType, Image> getSpritesForSubjects();
}

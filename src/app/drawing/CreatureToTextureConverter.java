package app.drawing;

import model.creatures.CreatureType;
import java.util.HashMap;
import java.util.Map;

public final class CreatureToTextureConverter {
    public static final Map<CreatureType, TextureType> converters = Map.of(
            CreatureType.SimpleSnakeBodyPart, TextureType.SimpleSnakeBodyPart,
            CreatureType.SnakeHead, TextureType.SnakeHead,
            CreatureType.Wall, TextureType.Wall,
            CreatureType.Apple, TextureType.Apple,
            CreatureType.Mushroom, TextureType.Mushroom,
            CreatureType.TailDiscardBodyPart, TextureType.TailDiscardSnakeBodyPart
    );
}
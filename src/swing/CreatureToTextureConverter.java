package swing;

import java.util.HashMap;
import java.util.Map;
import model.creatures.CreatureType;

public final class CreatureToTextureConverter {

  public static final Map<CreatureType, TextureType> converters = new HashMap<>() {
    {
      put(CreatureType.SimpleSnakeBodyPart, TextureType.SimpleSnakeBodyPart);
      put(CreatureType.SnakeHead, TextureType.SnakeHead);
      put(CreatureType.Wall, TextureType.Wall);
      put(CreatureType.Apple, TextureType.Apple);
      put(CreatureType.Mushroom, TextureType.Mushroom);
      put(CreatureType.TailDiscardBodyPart, TextureType.TailDiscardSnakeBodyPart);
    }
  };
}
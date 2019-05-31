package model.creatures;

import static model.creatures.CreatureType.SimpleSnakeBodyPart;
import static model.creatures.CreatureType.SnakeHead;
import static model.creatures.CreatureType.TailDiscardBodyPart;

public final class CreatureTypeValidator {

  public static boolean isSnake(CreatureType type) {
    return type == SnakeHead || type == SimpleSnakeBodyPart || type == TailDiscardBodyPart;
  }
}

package model.creatures;

import model.creatures.CreatureType;

import static model.creatures.CreatureType.*;

public final class CreatureTypeValidator {
    public static boolean isSnake(CreatureType type){
        return type == SnakeHead || type == SimpleSnakeBodyPart || type == TailDiscardBodyPart;
    }
}

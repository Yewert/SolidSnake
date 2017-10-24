package model.creatures.snakes;

import model.creatures.Creature;
import model.utils.Direction;
import model.utils.Point;

public interface SnakeBodyPart extends Creature {
    boolean isDead();
    void setIsDead();
    boolean isHead();
    Point getLocation();
    Direction getCurrentDirection();
    void setCurrentDirection(Direction newDirection);
    Snake getSnake();
    SnakeBodyPart getNextBodyPart();
    SnakeBodyPart getPrecedingBodyPart();
    void attachNewBodyPart(SnakeBodyPart bodyPart);
    void attachToPrecedingBodyPart(SnakeBodyPart bodyPart);
    void deattachNextBodyPart();
    SnakeBodyPartSkeleton getSkeleton();
    Direction getPreviousDirection();
}

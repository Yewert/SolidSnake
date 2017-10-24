package model.creatures.snakes;


import model.creatures.CreatureType;
import model.creatures.Creature;
import model.utils.Direction;
import model.utils.Point;

import static model.creatures.CreatureType.*;

public class SimpleSnakeBodyPart implements SnakeBodyPart {

    private final SnakeBodyPartSkeleton skeleton;
    private static final CreatureType CREATURE_TYPE = SimpleSnakeBodyPart;

    public SimpleSnakeBodyPart(Direction direction, Point location, Snake snake) {
        skeleton = new SnakeBodyPartSkeleton(false, direction, location, snake);
    }
    
    @Override
    public void makeMove(Creature[][] field, int currentTurn) {
        skeleton.makeMove(field, currentTurn);
    }

    @Override
    public boolean isDead() {
        return skeleton.isDead();
    }

    @Override
    public void setIsDead() {
        skeleton.setIsDead();
    }

    @Override
    public boolean isHead() {
        return skeleton.isHead();
    }


    @Override
    public void interactWith(Creature otherCreature) {
        CreatureType type = otherCreature.getCreatureType();
        if (type == SnakeHead || type == SimpleSnakeBodyPart || type == TailDiscardBodyPart) {
            skeleton.setIsDead();
        }
        else {
            throw new UnsupportedOperationException(String.format("DA FAK MADAFAKA?!" +
                        " I DON'T KNOW HOW TO SPEAK TO WHAMEN!" +
                        "(This (%s) doesn't know hot to interact with (%s))",
                    this.toString(),
                    otherCreature.toString()));
        }
    }

    @Override
    public void cleanUp() {
        skeleton.cleanUp();
    }

    @Override
    public Point getLocation() {
        return skeleton.getLocation();
    }

    @Override
    public CreatureType getCreatureType() {
        return CREATURE_TYPE;
    }

    @Override
    public Direction getCurrentDirection() {
        return skeleton.getCurrentDirection();
    }

    @Override
    public void setCurrentDirection(Direction newDirection) {
        skeleton.setCurrentDirection(newDirection);
    }


    @Override
    public Snake getSnake() {
        return skeleton.getSnake();
    }

    @Override
    public SnakeBodyPart getNextBodyPart() {
        return skeleton.getNextBodyPart();
    }

    @Override
    public SnakeBodyPart getPrecedingBodyPart() {
        return skeleton.getPrecedingBodyPart();
    }

    @Override
    public void attachNewBodyPart(SnakeBodyPart bodyPart) {
        skeleton.attachNewBodyPart(bodyPart);
    }

    @Override
    public void attachToPrecedingBodyPart(SnakeBodyPart bodyPart) {
        skeleton.attachToPrecedingBodyPart(bodyPart);
    }

    @Override
    public void deattachNextBodyPart() {
        skeleton.deattachNextBodyPart();
    }

    @Override
    public SnakeBodyPartSkeleton getSkeleton() {
        return skeleton;
    }

    @Override
    public Direction getPreviousDirection() {
        return skeleton.getPreviousDirection();
    }

    @Override
    public String toString() {
        return String.format("%s at (%d, %d) with %s",
                CREATURE_TYPE.toString(),
                skeleton.getLocation().getX(),
                skeleton.getLocation().getY(),
                skeleton.getCurrentDirection().toString());
    }
}

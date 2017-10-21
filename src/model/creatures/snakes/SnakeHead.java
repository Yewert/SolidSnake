package model.creatures.snakes;

import model.creatures.*;
import model.utils.Direction;
import model.utils.Point;

public class SnakeHead implements SnakeBodyPart {
    private static final CreatureType CREATURE_TYPE = CreatureType.SnakeHead;
    private final SnakeBodyPartSkeleton skeleton;

    public SnakeHead(Direction direction, Point location, Snake snake){
        skeleton = new SnakeBodyPartSkeleton(true, direction, location, snake);
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
    public void interactWith(Creature otherCreature) {
        if (otherCreature instanceof Apple){
            skeleton.getSnake().incrementScore(10);
            //skeleton.getSnake().setLastBoost(CreatureType.Apple);
            SnakeBodyPart tail = getSnake().getTail();
            skeleton.attachNewBodyPart(new SimpleSnakeBodyPart(
                    false,
                    Direction.None,
                    tail.getLocation(),
                    skeleton.getSnake())
            );
        }
        else if (otherCreature instanceof Mushroom){
            skeleton.getSnake().incrementScore(20);
            SnakeBodyPart tail = getSnake().getTail();
            skeleton.attachNewBodyPart(new TailDiscardBodyPart(
                    Direction.None,
                    tail.getLocation(),
                    skeleton.getSnake())
            );
        }
        else if (otherCreature instanceof SimpleSnakeBodyPart) {
            skeleton.setIsDead();
        }
        else if (otherCreature instanceof  TailDiscardBodyPart){
        }
        else if (otherCreature instanceof SnakeHead) {
            skeleton.setIsDead();
        }
        else if (otherCreature instanceof Wall){
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
    public boolean isHead() {
        return skeleton.isHead();
    }

    @Override
    public Point getLocation() {
        return skeleton.getLocation();
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
    public Direction getPreviousDirection() {
        return skeleton.getPreviousDirection();
    }

    @Override
    public CreatureType getCreatureType() {
        return CREATURE_TYPE;
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
}

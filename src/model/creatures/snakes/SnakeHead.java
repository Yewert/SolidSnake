package model.creatures.snakes;

import model.creatures.Creature;
import model.utils.Direction;
import model.utils.Point;
import model.creatures.CreatureType;
import java.util.LinkedList;
import java.util.Queue;

import static model.creatures.CreatureType.*;

public class SnakeHead implements SnakeBodyPart {
    private static final CreatureType CREATURE_TYPE = SnakeHead;
    private final SnakeBodyPartSkeleton skeleton;
    private final Queue<CreatureType> newBodyParts;
    private int tailDiscardsToAppend = 3;

    public SnakeHead(Direction direction, Point location, Snake snake){
        skeleton = new SnakeBodyPartSkeleton(true, direction, location, snake);
        newBodyParts = new LinkedList<>();
    }

    public SnakeHead(Direction direction, Point location, Snake snake, int tailDiscardsPerMushroom){
        skeleton = new SnakeBodyPartSkeleton(true, direction, location, snake);
        newBodyParts = new LinkedList<>();
        if(tailDiscardsPerMushroom < 1){
            //TODO: Insert some memes into exception message
            throw new IllegalArgumentException("");
        }
        tailDiscardsToAppend = tailDiscardsPerMushroom;
    }

    @Override
    public void makeMove(Creature[][] field, int currentTurn) {
        if (newBodyParts.size() > 0){
            CreatureType toAdd = newBodyParts.remove();
            SnakeBodyPart tail = getSnake().getTail();
            if (toAdd == SimpleSnakeBodyPart){
                skeleton.attachNewBodyPart(new SimpleSnakeBodyPart(
                        Direction.None,
                        tail.getLocation(),
                        skeleton.getSnake())
                );
            }
            else if (toAdd == TailDiscardBodyPart){
                skeleton.attachNewBodyPart(new TailDiscardBodyPart(
                        Direction.None,
                        tail.getLocation(),
                        skeleton.getSnake())
                );
            }
            else {
                throw new UnsupportedOperationException(String.format("What a fokin Frankenstine" +
                        " creature (you can't append %s to the snake", toAdd));
            }
        }
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
    public void interactWith(Creature otherCreature) {
        CreatureType type = otherCreature.getCreatureType();
        if (type == Apple){
            skeleton.getSnake().incrementScore(10);
            newBodyParts.add(SimpleSnakeBodyPart);
        }
        else if (type == Mushroom){
            skeleton.getSnake().incrementScore(20);
            for (int i = 0; i < tailDiscardsToAppend; i++) {
                newBodyParts.add(TailDiscardBodyPart);
            }
        }
        else if (type == SimpleSnakeBodyPart) {
            skeleton.setIsDead();
        }
        else if (type ==  TailDiscardBodyPart){
            newBodyParts.clear();
        }
        else if (type == SnakeHead) {
            skeleton.setIsDead();
        }
        else if (type == Wall){
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

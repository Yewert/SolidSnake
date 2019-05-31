package model.creatures.snakes;

import static model.creatures.CreatureType.Apple;
import static model.creatures.CreatureType.Mushroom;
import static model.creatures.CreatureType.SimpleSnakeBodyPart;
import static model.creatures.CreatureType.SnakeHead;
import static model.creatures.CreatureType.TailDiscardBodyPart;
import static model.creatures.CreatureType.Wall;

import java.util.LinkedList;
import java.util.Queue;
import model.creatures.Creature;
import model.creatures.CreatureType;
import model.utils.Direction;
import model.utils.Point;

public class SnakeHead implements SnakeBodyPart {

  private static final CreatureType CREATURE_TYPE = SnakeHead;
  private final SnakeBodyPartSkeleton skeleton;
  private final Queue<CreatureType> newBodyParts;
  private int tailDiscardsToAppend = 3;

  public SnakeHead(Direction direction, Point location, Snake snake) {
    skeleton = new SnakeBodyPartSkeleton(true, direction, location, snake);
    newBodyParts = new LinkedList<>();
  }

  public SnakeHead(Direction direction, Point location, Snake snake, int tailDiscardsPerMushroom)
      throws IllegalArgumentException {
    skeleton = new SnakeBodyPartSkeleton(true, direction, location, snake);
    newBodyParts = new LinkedList<>();
    if (tailDiscardsPerMushroom < 1) {
      //TODO: Insert some memes into exception message
      throw new IllegalArgumentException("");
    }
    tailDiscardsToAppend = tailDiscardsPerMushroom;
  }

  @Override
  public void makeMove(Creature[][] field, int currentTurn) {
    if (newBodyParts.size() > 0) {
      CreatureType toAdd = newBodyParts.remove();
      SnakeBodyPart tail = getSnake().getTail();
      switch (toAdd) {
        case SimpleSnakeBodyPart:
          skeleton.attachNewBodyPart(new SimpleSnakeBodyPart(
              Direction.None,
              tail.getLocation(),
              skeleton.getSnake())
          );
          break;
        case TailDiscardBodyPart:
          skeleton.attachNewBodyPart(new TailDiscardBodyPart(
              Direction.None,
              tail.getLocation(),
              skeleton.getSnake())
          );
          break;
        default:
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
  public void interactWith(Creature otherCreature) throws UnsupportedOperationException {
    CreatureType type = otherCreature.getCreatureType();
    if (type == Apple) {
      skeleton.getSnake().incrementScore(10);
      newBodyParts.add(SimpleSnakeBodyPart);
      return;
    }
    if (type == Mushroom) {
      skeleton.getSnake().incrementScore(20);
      for (int i = 0; i < tailDiscardsToAppend; i++) {
        newBodyParts.add(TailDiscardBodyPart);
      }
      return;
    }
    if (type == SimpleSnakeBodyPart) {
      skeleton.setIsDead();
      return;
    }
    if (type == TailDiscardBodyPart) {
      newBodyParts.clear();
      SnakeBodyPart asBodyPart = (SnakeBodyPart) otherCreature;
      while (true) {
        if (asBodyPart.getCreatureType() == SimpleSnakeBodyPart) {
          skeleton.getSnake().incrementScore(40);
        } else if (asBodyPart.getCreatureType() == TailDiscardBodyPart) {
          skeleton.getSnake().incrementScore(30);
        }
        asBodyPart = asBodyPart.getNextBodyPart();
        if (asBodyPart == null) {
          break;
        }
      }
      return;
    }
    if (type == SnakeHead) {
      skeleton.setIsDead();
      return;
    }
    if (type == Wall) {
      skeleton.setIsDead();
      return;
    }
    throw new UnsupportedOperationException(String.format("DA FAK MADAFAKA?!" +
            " I DON'T KNOW HOW TO SPEAK TO WHAMEN!" +
            "(This (%s) doesn't know hot to interact with (%s))",
        this.toString(),
        otherCreature.toString()));
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

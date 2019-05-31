package model.creatures;

import model.utils.Direction;
import model.utils.Point;

public class Wall implements Creature {

  private final Point location;

  public Wall(Point location) {
    this.location = location;
  }

  @Override
  public void makeMove(Creature[][] field, int currentTurn) {
  }

  @Override
  public boolean isDead() {
    return false;
  }

  @Override
  public void interactWith(Creature otherCreature) {
  }

  @Override
  public void cleanUp() {
  }

  @Override
  public Point getLocation() {
    return location;
  }

  @Override
  public Direction getCurrentDirection() {
    return Direction.None;
  }

  @Override
  public CreatureType getCreatureType() {
    return CreatureType.Wall;
  }

  @Override
  public Direction getPreviousDirection() {
    return Direction.None;
  }
}

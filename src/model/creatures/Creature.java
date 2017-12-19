package model.creatures;

import model.utils.Direction;
import model.utils.Point;

public interface Creature {

  void makeMove(Creature[][] field, int currentTurn); //TODO: any other args?

  boolean isDead();

  void interactWith(Creature otherCreature);

  void cleanUp();

  Point getLocation();

  Direction getCurrentDirection();

  CreatureType getCreatureType();

  Direction getPreviousDirection();
}

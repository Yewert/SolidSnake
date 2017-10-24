package model.creatures;

import model.utils.Direction;
import model.utils.Point;

import static model.creatures.CreatureType.*;

public class Mushroom implements Creature {

    private final int turnCreated;
    private final int turnsAlive;
    private boolean isDead = false;
    private Point location;

    public Mushroom(Point location, int turnCreated, int turnsAlive){
        this.location = location;
        this.turnCreated = turnCreated;
        this.turnsAlive = turnsAlive;
    }

    @Override
    public void makeMove(Creature[][] field, int currentTurn) {
        if (turnCreated + turnsAlive == currentTurn){
            isDead = true;
        }
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public void interactWith(Creature otherCreature) throws IllegalArgumentException {
        CreatureType type = otherCreature.getCreatureType();
        if (CreatureTypeValidator.isSnake(type)) {
            isDead = true;
        }
        else{
            throw new IllegalArgumentException("This mushroom doesn't know how to behave in these " +
                    "circumstances WutFace");
        }
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
        return CreatureType.Mushroom;
    }

    @Override
    public Direction getPreviousDirection() {
        return Direction.None;
    }
}

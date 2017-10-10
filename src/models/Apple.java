package models;

class Apple implements Creature {

    Apple(Point location){
        _location = location;
    }

    @Override
    public void makeMove(Creature[][] field) {
    }

    private boolean _isDead = false;
    @Override
    public boolean isDead() {
        return _isDead;
    }

    @Override
    public void interactWith(Creature otherCreature) {
        if (otherCreature instanceof SnakeBodyPart)
            _isDead = true;
        else{
            throw new UnsupportedOperationException("This apple doesn't know how to behave in these " +
                    "circumstances WutFace");
        }
    }

    @Override
    public void cleanUp() {
    }

    private Point _location;
    @Override
    public Point getLocation() {
        return _location;
    }

    @Override
    public Direction getDirection() {
        return Direction.None;
    }

    @Override
    public CreatureType getCreatureType() {
        return CreatureType.Apple;
    }
}
package model.creatures.snakes;

import model.utils.Direction;
import model.utils.Point;

public class Snake {

    private SnakeBodyPart head;
    private int score;

    public Snake(Point location, Direction startingDirection) {
        this.head = new SnakeHead(startingDirection, location, this);
        score = 0;
    }
    public SnakeBodyPart getHead() {
        return head;
    }

    public SnakeBodyPart getTail(){
        SnakeBodyPart bodyPart = head;
        while (true){
            SnakeBodyPart temporaryBodyPart = bodyPart.getNextBodyPart();
            if(temporaryBodyPart == null)
                break;
            bodyPart = temporaryBodyPart;
        }
        return bodyPart;
    }
    public boolean isDead(){
        return head.isDead();
    }

    public Direction getCurrentDirection() {
        return head.getCurrentDirection();
    }

    public void setCurrentDirection(Direction newDirection) {
        head.setCurrentDirection(newDirection);
    }

    public int getScore() {
        return score;
    }

    protected void incrementScore(int value) {
        if (value < 0) {
            throw new UnsupportedOperationException("How are you gonna win if you" +
                    " are adding negative amount points to score?!");
        }
        score += value;
    }
}

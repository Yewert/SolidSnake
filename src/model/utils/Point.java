package model.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }


    public int getX(){
        return x;
    }


    public int getY(){
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        Point otherPoint = obj instanceof Point ? ((Point) obj) : null;
        return otherPoint != null && x == otherPoint.x && y == otherPoint.y;
    }

    @Override
    public int hashCode() {
        return x * 6291469 + y;
    }

    public boolean isInBounds(int width, int height){
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public static Point generateRandomInBounds(int x1, int x2,
                                               int y1, int y2,
                                               int borderX,
                                               int borderY){
        //TODO: args checking
        int randomX = ThreadLocalRandom.current().
                nextInt(x1 + borderX, x2 - borderX + 1);
        int randomY = ThreadLocalRandom.current().
                nextInt(y1 + borderY, y2 - borderY + 1);
        return new Point(randomX, randomY);
    }

    @Override
    public String toString() {
        return String.format("X:%d Y:%d", x, y);
    }
}

package app;

import model.creatures.CreatureType;
import model.game.GameSettings;
import model.utils.Direction;
import model.utils.Point;

import java.util.HashSet;
import java.util.Set;

import static model.creatures.CreatureType.*;

public class GameplaySettings implements GameSettings {

    private final CreatureType[][] field;
    private final boolean spawnEnabled;
    private final int appleSpawnRate;
    private final int appleDeathRate;
    private final int mushroomSpawnRate;
    private final int mushroomDeathRate;
    private final int snakesAmount;


    public GameplaySettings(CreatureType[][] field,
                            boolean spawnEnabled,
                            int appleSpawnRate, int appleDeathRate,
                            int mushroomSpawnRate, int mushroomDeathRate, int snakesAmount) {
        this.field = field;
        this.spawnEnabled = spawnEnabled;
        this.appleSpawnRate = appleSpawnRate;
        this.appleDeathRate = appleDeathRate;
        this.mushroomSpawnRate = mushroomSpawnRate;
        this.mushroomDeathRate = mushroomDeathRate;
        this.snakesAmount = snakesAmount;
    }

    @Override
    public int getAppleSpawnRate() {
        return appleSpawnRate;
    }

    @Override
    public int getAppleDeathRate() {
        return appleDeathRate;
    }

    @Override
    public int getMushroomSpawnRate() {
        return mushroomSpawnRate;
    }

    @Override
    public int getMushroomDeathRate() {
        return mushroomDeathRate;
    }

    @Override
    public boolean isFoodSpawnEnabled() {
        return spawnEnabled;
    }

    @Override
    public CreatureType[][] getInitialField() {
        return field;
    }

    @Override
    public int getSnakesAmount() {
        return snakesAmount;
    }

    @Override
    public int getWidth() {
        return field.length;
    }

    @Override
    public int getHeight() {
        return field[0].length;
    }

    public static CreatureType[][] getRandomField (int width, int height, int snakeCount) throws IllegalArgumentException{
        CreatureType[][] field = new CreatureType[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(i == 0 || i == width - 1 || j == 0 || j == height - 1) {
                    field[i][j] = Wall;
                }
            }
        }
        Point[] snakesLocations = GameplaySettings.generateSafeRandomPoints(field,
                snakeCount,0, field.length - 1,
                0, field[0].length - 1, 2, 2);
        Point[] applesLocations = GameplaySettings.generateSafeRandomPoints(field,
                snakeCount, 0, field.length - 1,
                0, field[0].length - 1, 1, 1);
        for (int i = 0; i < snakeCount; i++) {
            if (snakesLocations[i] == null) {
                throw new IllegalArgumentException("The world is too small for both of us!" +
                        "(Field too small - could not spawn a snake)");
            }
            field[snakesLocations[i].getX()][snakesLocations[i].getY()] = SnakeHead;
            if(applesLocations[i] != null){
                field[applesLocations[i].getX()][applesLocations[i].getY()] = Apple;
            }
        }
        return field;
    }

    private static Set<Point> generatePointForEveryCell(int x1, int x2, int y1, int y2,
                                                        int borderX, int borderY){
        Set<Point> points = new HashSet<>();
        for (int x = x1 + borderX; x < x2 - borderX + 1; x++) {
            for (int y = y1 + borderY; y < y2 - borderY + 1; y++) {
                points.add(new Point(x, y));
            }
        }
        return points;
    }

    private static Point[] generateSafeRandomPoints(CreatureType[][] field, int amount,
                                                    int x1, int x2, int y1, int y2,
                                                    int borderX, int borderY){
        Set<Point> points = generatePointForEveryCell(x1, x2, y1, y2, borderX, borderY);
        Point[] randomPoints = new Point[amount];
        int pointNumber = 0;
        while (true) {
            if (points.size() == 0){
                break;
            }
            Point randomPoint = Point.generateRandomInBounds(x1, x2,
                    y1, y2,
                    borderX, borderY);
            if (field[randomPoint.getX()][randomPoint.getY()] == null) {
                randomPoints[pointNumber] = randomPoint;
                pointNumber++;
            }
            if (pointNumber == amount){
                break;
            }
            points.remove(randomPoint);
        }
        return randomPoints;
    }
}
package model;

import model.creatures.CreatureType;
import model.game.Game;
import model.game.GameFrame;
import model.game.GameSettings;
import model.utils.Direction;
import model.utils.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import static model.creatures.CreatureType.*;

public class TestOneSnake {

    private GameSettings generateSimpleMock(){
        CreatureType[][] field = {
                {Wall,     Wall,      Wall,     Wall,     Wall,     Wall},
                {Wall,     Apple,     null,     null,     null,     Wall},
                {Wall,     Apple,     SnakeHead,null,     null,     Wall},
                {Wall,     Apple,     null,     null,     null,     Wall},
                {Wall,     Apple,     Apple,    null,     null,     Wall},
                {Wall,     Wall,      Wall,     Wall,     Wall,     Wall}
        };
        return new GameSettingsMock(field,
                false,
                0,
                100000,
                0,
                100000,
                1);
    }
    private GameSettings generateMockForMushroomTest(){
        CreatureType[][] field = {
                {Wall,     Wall,      Wall,     Wall,     Wall,     Wall},
                {Wall,     Mushroom,  null,     null,     null,     Wall},
                {Wall,     Mushroom,  SnakeHead,null,     null,     Wall},
                {Wall,     Mushroom,  null,     null,     null,     Wall},
                {Wall,     Mushroom,  Apple,    null,     null,     Wall},
                {Wall,     Wall,      Wall,     Wall,     Wall,     Wall}
        };
        return new GameSettingsMock(field,
                false,
                0,
                100000,
                0,
                100000,
                1);
    }

    private GameSettings settingsMock;
    private GameSettings settingsMockForMushroomTest;

    @Before
    public void setUp() throws Exception{
        settingsMock = generateSimpleMock();
        settingsMockForMushroomTest = generateMockForMushroomTest();
    }

    @Test
    public void testIsMoving(){
        Game game = new Game(settingsMock);
        Direction[] directions = {Direction.Right};
        GameFrame frame = game.makeTurn(directions);
        Assert.assertTrue(frame.getTypes().get(new Point(3,2))
                == CreatureType.SnakeHead);
    }

    @Test
    public void testDoesNot180(){
        Game game = new Game(settingsMock);
        Direction[] directions = {Direction.Right};
        Direction[] directions2 = {Direction.Left};
        GameFrame frame = game.makeTurn(directions);
        frame = game.makeTurn(directions2);
        Assert.assertTrue(frame.getTypes().get(new Point(4,2))
                == CreatureType.SnakeHead);
    }

    @Test
    public void testEatsAppleGrowsAndGetsPoints(){
        Game game = new Game(settingsMock);
        Direction[] directions = {Direction.Down};
        Direction[] directions2 = {Direction.Down};
        Direction[] directions3 = {Direction.Right};
        GameFrame frame = game.makeTurn(directions);
        frame = game.makeTurn(directions2);
        frame = game.makeTurn(directions3);
        Assert.assertTrue(frame
                    .getTypes()
                    .get(new Point(3,4))== CreatureType.SnakeHead);
        Assert.assertTrue(frame
                .getTypes()
                .get(new Point(2, 4)) == CreatureType.SimpleSnakeBodyPart);
        Assert.assertEquals(10, frame.getScores()[0]);
    }

    @Test
    public void testDiesFromWall(){
        Game game = new Game(settingsMock);
        Direction[] directions = {Direction.Up};
        GameFrame frame = game.makeTurn(directions);
        frame = game.makeTurn(directions);
        Assert.assertEquals(null, frame);
    }

    @Test
    public void testCollisionSelf(){
        Game game = new Game(settingsMock);
        Direction[] firstDir = {Direction.Up};
        GameFrame frame = game.makeTurn(firstDir);
        Direction[][] directions = {
                {Direction.Left},
                {Direction.Down},
                {Direction.Down},
                {Direction.Down},
                {Direction.Right},
                {Direction.Up},
                {Direction.Left}
        };
        for (Direction[] direction : directions) {
            frame = game.makeTurn(direction);
        }
        Assert.assertEquals(null, frame);
    }

    @Test
    public void testCollisionWithTailDiscardBlock(){
        Game game = new Game(settingsMockForMushroomTest);
        Direction[] firstDir = {Direction.Up};
        GameFrame frame = game.makeTurn(firstDir);
        Direction[][] directions = {
                {Direction.Left},
                {Direction.Down},
                {Direction.Down},
                {Direction.Down},
                {Direction.Right},
                {Direction.Up},
                {Direction.Left}
        };
        for (Direction[] direction : directions) {
            frame = game.makeTurn(direction);
        }
        Assert.assertNotEquals(null, frame);
        Assert.assertTrue(!frame.getTypes().containsKey(new Point(1, 2)));
    }
}

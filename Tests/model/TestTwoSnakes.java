package model;

import model.creatures.CreatureType;
import model.game.Game;
import model.game.GameFrame;
import model.game.GameSettings;
import model.utils.Direction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static model.creatures.CreatureType.*;
import static model.creatures.CreatureType.Wall;

public class TestTwoSnakes {

    private GameSettings generateMock(){
        CreatureType[][] field = {
                {Wall,     Wall,      Wall,     Wall,     Wall,     Wall},
                {Wall,     Apple,   SnakeHead,  Apple,    Apple,    Wall},
                {Wall,     Apple,     null,     null,   SnakeHead,  Wall},
                {Wall,     Mushroom,  null,     null,     null,     Wall},
                {Wall,     null,      null,     null,     null,     Wall},
                {Wall,     Wall,      Wall,     Wall,     Wall,     Wall}
        };
        return new GameSettingsMock(field,
                false,
                0,
                100000,
                0,
                100000,
                2);
    }

    private GameSettings settingsMock;

    @Before
    public void setUp(){
        settingsMock = generateMock();
    }

    @Test
    public void testCollideHeads(){
        Game game = new Game(settingsMock);
        Direction[] directions1 = {Direction.Right, Direction.Up};
        Direction[] directions2 = {Direction.Down, Direction.Right};
        Direction[] directions3 = {Direction.Down, Direction.Down};
        Direction[] directions4 = {Direction.Right, Direction.Down};
        Direction[] directions5 = {Direction.Right, Direction.Left};
        GameFrame frame = game.makeTurn(directions1);
        frame = game.makeTurn(directions2);
        frame = game.makeTurn(directions3);
        frame = game.makeTurn(directions4);
        frame = game.makeTurn(directions5);
        Assert.assertEquals(null, frame);
    }

    @Test
    public void testCollideSimpleBodyPart(){
        Game game = new Game(settingsMock);
        Direction[] directions1 = {Direction.Right, Direction.Up};
        Direction[] directions2 = {Direction.Down, Direction.Right};
        Direction[] directions3 = {Direction.Right, Direction.Left};
        Direction[] directions4 = {Direction.Right, Direction.Down};
        GameFrame frame = game.makeTurn(directions1);
        frame = game.makeTurn(directions2);
        frame = game.makeTurn(directions3);
        frame = game.makeTurn(directions4);
        Assert.assertEquals(null, frame);
    }

    @Test
    public void testCollideTailDiscardBodyPart(){
        Game game = new Game(settingsMock);
        Direction[] directions1 = {Direction.Down, Direction.None};
        Direction[] directions2 = {Direction.Left, Direction.None};
        Direction[] directions3 = {Direction.Down, Direction.None};
        Direction[] directions4 = {Direction.Right, Direction.Left};
        Direction[] directions5 = {Direction.Right, Direction.Left};
        Direction[] directions6 = {Direction.Right, Direction.Down};
        GameFrame frame = game.makeTurn(directions1);
        frame = game.makeTurn(directions2);
        frame = game.makeTurn(directions3);
        frame = game.makeTurn(directions4);
        frame = game.makeTurn(directions5);
        frame = game.makeTurn(directions6);
        Assert.assertNotEquals(null, frame);
        int[] expScores = new int[] {30, 90};
        int[] scores = frame.getScores();
        for (int i = 0; i < scores.length; i++) {
            Assert.assertEquals(expScores[i], scores[i]);
        }
    }
}

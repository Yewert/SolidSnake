package model.game;

import model.creatures.Creature;
import model.creatures.CreatureType;
import model.utils.Direction;
import model.utils.Point;

import java.util.HashMap;
import java.util.Map;

public class GameFrame {

    private final int height;
    private final int width;
    private final Map<Point, CreatureInfo> typeInfos;
    private final Map<Point, CreatureType> types;
    private final int[] scores;

    public GameFrame(int width,
                     int height,
                     Map<Point, Creature> creatures,
                     int[] scores){
        this.width = width;
        this.height = height;
        types = extractTypes(creatures);
        typeInfos = convertICreatureToTextureInfo(creatures);
        this.scores = scores;
    }

    private Map<Point, CreatureType> extractTypes(Map<Point, Creature> creatures){
        Map<Point, CreatureType> typeMap = new HashMap<>();
        for (Point location : creatures.keySet()) {
            Creature creature = creatures.get(location);
            typeMap.put(location, creature.getCreatureType());
        }
        return typeMap;
    }

    private Map<Point, CreatureInfo> convertICreatureToTextureInfo(Map<Point, Creature> creatures){
        Map<Point, CreatureInfo> textures = new HashMap<>();
        for (Point location : creatures.keySet()) {
            Creature creature = creatures.get(location);
            CreatureInfo texture = new CreatureInfo(creature.getCreatureType(),
                    creature.getCurrentDirection());
            textures.put(location, texture);
        }
        return textures;
    }



    public class CreatureInfo {
        private CreatureType type;
        private Direction direction;

        public CreatureInfo(CreatureType type,
                            Direction direction){
            this.type = type;
            this.direction = direction;
        }

        public CreatureType getType() {
            return type;
        }
        public Direction getDirection() {
            return direction;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Map<Point, CreatureInfo> getCreaturesInfo(){
        return typeInfos;
    }

    public int[] getScores() {
        return scores;
    }

    public Map<Point, CreatureType> getTypes() {
        return types;
    }
}

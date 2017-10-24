package model;

import model.creatures.CreatureType;
import model.game.GameSettings;

public class GameSettingsMock implements GameSettings {

    private final CreatureType[][] field;
    private final boolean spawnEnabled;
    private final int appleSpawnRate;
    private final int appleDeathRate;
    private final int mushroomSpawnRate;
    private final int mushroomDeathRate;
    private final int snakesAmount;


    public GameSettingsMock(CreatureType[][] field,
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
}

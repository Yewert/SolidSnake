package app;

import app.drawing.TextureType;
import javafx.scene.image.Image;
import java.util.Map;

import static app.drawing.TextureType.*;

public class SkinSettings implements VisualSettings {
    private final Map<Integer, Map<TextureType, Image>> sprites;
    private final Map<TextureType, Image> spritesForSubjects;
    public SkinSettings(int player1Skin, int player2Skin, int player3Skin){
        sprites = Map.of(
                0, Map.of(
                        SnakeHead, new Image(String.format("images/Skin%d/Head1.png", player1Skin)),
                        TailDiscardSnakeBodyPart, new Image(String.format("images/Skin%d/TailDiscard.png", player1Skin)),
                        SimpleSnakeBodyPart, new Image(String.format("images/Skin%d/Simple.png", player1Skin))
                ),
                1, Map.of(
                        SnakeHead, new Image(String.format("images/Skin%d/Head2.png", player2Skin)),
                        TailDiscardSnakeBodyPart, new Image(String.format("images/Skin%d/TailDiscard.png", player2Skin)),
                        SimpleSnakeBodyPart, new Image(String.format("images/Skin%d/Simple.png", player2Skin))
                ),
                2, Map.of(
                        SnakeHead, new Image(String.format("images/Skin%d/Head3.png", player3Skin)),
                        TailDiscardSnakeBodyPart, new Image(String.format("images/Skin%d/TailDiscard.png", player3Skin)),
                        SimpleSnakeBodyPart, new Image(String.format("images/Skin%d/Simple.png", player3Skin))
                )
        );
        spritesForSubjects = Map.of(
                Apple, new Image("images/Apple.png"),
                Mushroom, new Image("images/Mushroom.png"),
                Wall, new Image("images/Wall.png")
        );
    }


    @Override
    public Map<Integer, Map<TextureType, Image>> getSpritesForPlayers() {
        return sprites;
    }

    public Map<TextureType, Image> getSpritesForSubjects() {
        return spritesForSubjects;
    }
}

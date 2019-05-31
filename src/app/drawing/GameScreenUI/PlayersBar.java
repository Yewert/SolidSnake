package app.drawing.GameScreenUI;

import app.Settings;
import app.SkinSettings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PlayersBar extends StackPane {

  private HBox playerBox;

  public PlayersBar(Settings settings) {
    SkinSettings skinSettings = (SkinSettings) settings.getSkins();
    int playersCount = settings.getGameplaySettings().getSnakesAmount();

    VBox root = new VBox(5);
    root.setAlignment(Pos.CENTER);
    playerBox = new HBox(10);
    playerBox.setAlignment(Pos.CENTER);

    //TODO: change to array in skinSettings
    if (playersCount > 0) {
      PlayerBox box1 = new PlayerBox(skinSettings.getPlayer1Skin(), 1);
      box1.setOnMouseClicked(event -> {
        int player1Skin = skinSettings.getPlayer1Skin();
        player1Skin = Math.floorMod((player1Skin + 1), skinSettings.getSkinVariations());
        skinSettings.setSkins(
            player1Skin,
            skinSettings.getPlayer2Skin(),
            skinSettings.getPlayer3Skin()
        );
        box1.setImage(skinSettings.getPlayer1Skin(), 1);
      });
      playerBox.getChildren().add(box1);
    }
    if (playersCount > 1) {
      PlayerBox box2 = new PlayerBox(skinSettings.getPlayer2Skin(), 2);
      box2.setOnMouseClicked(event -> {
        int player2Skin = skinSettings.getPlayer2Skin();
        player2Skin = Math.floorMod((player2Skin + 1), skinSettings.getSkinVariations());
        skinSettings.setSkins(
            skinSettings.getPlayer1Skin(),
            player2Skin,
            skinSettings.getPlayer3Skin()
        );
        box2.setImage(skinSettings.getPlayer2Skin(), 2);
      });
      playerBox.getChildren().add(box2);
    }
    if (playersCount > 2) {
      PlayerBox box3 = new PlayerBox(skinSettings.getPlayer3Skin(), 3);
      box3.setOnMouseClicked(event -> {
        int player3Skin = skinSettings.getPlayer3Skin();
        player3Skin = Math.floorMod((player3Skin + 1), skinSettings.getSkinVariations());
        skinSettings.setSkins(
            skinSettings.getPlayer1Skin(),
            skinSettings.getPlayer2Skin(),
            player3Skin
        );
        box3.setImage(skinSettings.getPlayer3Skin(), 3);
      });
      playerBox.getChildren().add(box3);
    }

    root.getChildren().addAll(playerBox);
    getChildren().add(root);
  }

  public void update(int[] scores) {
    for (Node node : playerBox.getChildren()) {
      if (node instanceof PlayerBox) {
        ((PlayerBox) node).update(scores);
      }
    }
  }
}

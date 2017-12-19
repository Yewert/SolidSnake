package app.drawing.GameScreenUI;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PlayerBox extends StackPane {

  private ImageView playerImage;
  private Text score;
  private int player;

  public PlayerBox(int skinID, int playerNumber) {
    HBox root = new HBox(5);
    StackPane info = new StackPane();
    root.setAlignment(Pos.CENTER);
    info.setAlignment(Pos.CENTER_LEFT);

    player = playerNumber - 1;
    playerImage = new ImageView(getImage(skinID, playerNumber));
    score = new Text();
    score.setFont(new Font(40));
    score.setFill(Color.WHITE);

    Rectangle bg = new Rectangle(180, 80, Color.TRANSPARENT);
    Rectangle scoreTransparentBg = new Rectangle(100, 80, Color.TRANSPARENT);

    info.getChildren().addAll(scoreTransparentBg, score);
    root.getChildren().addAll(playerImage, info);
    getChildren().addAll(bg, root);
    setAlignment(Pos.CENTER);
  }

  public void update(int[] scores) {
    score.setText(String.valueOf(scores[player]));
  }

  public void setImage(int skinID, int player) {
    playerImage.setImage(getImage(skinID, player));
  }

  private Image getImage(int skinID, int player) {
    return new Image(
        String.format("images/Skin%d/Head%d.png", skinID, player),
        75,
        0,
        true,
        false
    );
  }
}

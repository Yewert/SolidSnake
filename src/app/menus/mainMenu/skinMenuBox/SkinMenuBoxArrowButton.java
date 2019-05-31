package app.menus.mainMenu.skinMenuBox;

import app.menus.menu.MenuObject;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.utils.Direction;

public class SkinMenuBoxArrowButton extends MenuObject {

  public SkinMenuBoxArrowButton(Direction direction) {
    ImageView arrowIV = rotateImage(
        new Image("images/ArrowRightRed.png",
            50,
            0,
            true,
            true),
        direction
    );

    setAlignment(Pos.CENTER);
    getChildren().add(arrowIV);
  }

  private ImageView rotateImage(Image image, Direction direction) {
    ImageView iv = new ImageView(image);
    iv.setRotate(getAngle(direction));
    return iv;
  }

  private double getAngle(Direction direction) {
    switch (direction) {
      case Right:
        return 0;
      case Down:
        return 90;
      case Left:
        return 180;
      case None:
      case Up:
        return 270;
    }
    return 0;
  }
}

package app.menus.mainMenu.skinMenuBox;

import app.menus.menu.MenuObject;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.utils.Direction;

public class SkinMenuBoxPreviewBox extends StackPane {

  private ImageView imageView;
  private Map<String, MenuObject> buttons;

  public SkinMenuBoxPreviewBox(Image image) {
    setAlignment(Pos.CENTER);
    this.imageView = new ImageView(image);

    VBox box = new VBox();
    MenuObject buttonPrev = new SkinMenuBoxArrowButton(Direction.Up);
    MenuObject buttonNext = new SkinMenuBoxArrowButton(Direction.Down);
    buttons = Map.of(
        "buttonPrev", buttonPrev,
        "buttonNext", buttonNext
    );

    box.getChildren().addAll(buttonPrev, this.imageView, buttonNext);
    getChildren().add(box);
  }

  public void setImage(Image image) {
    this.imageView.setImage(image);
  }

  public Map<String, MenuObject> getButtonsMap() {
    return buttons;
  }
}

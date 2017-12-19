package app.menus.mainMenu;

import app.menus.menu.MenuBox;
import app.menus.menu.MenuObject;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;

public class MainMenuBox extends MenuBox {

  public MainMenuBox(MenuObject... items) {
    VBox root = new VBox();
    root.setAlignment(Pos.CENTER);
    root.getChildren().add(createSeparator());
    for (MenuObject item : items) {
      root.getChildren().addAll(item, createSeparator());
    }
    getChildren().add(root);
  }

  private Line createSeparator() {
    LinearGradient gradient = new LinearGradient(
        0, 0,
        1, 0,
        true,
        CycleMethod.NO_CYCLE,
        new Stop(0, Color.TRANSPARENT),
        new Stop(0.4, Color.GREY),
        new Stop(0.6, Color.GREY),
        new Stop(1, Color.TRANSPARENT)
    );

    Line sep = new Line();
    sep.setEndX(200);
    sep.setStroke(gradient);
    return sep;
  }

  @Override
  public Map<String, MenuObject> getButtonsMap() {
    return null;
  }
}

package app.menus.mainMenu;

import app.menus.menu.MenuObject;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MainMenuSlider extends MenuObject {

  private Slider slider;

  public MainMenuSlider(double min, double max, double value, String name) {
    slider = new Slider(min, max, value);

    VBox root = new VBox();

    Text text = new Text(name);
    text.setFill(Color.DARKGREY);
    text.setFont(Font.font("Calibri", FontWeight.SEMI_BOLD, 22));
    text.setTextAlignment(TextAlignment.CENTER);

    root.setAlignment(Pos.CENTER);
    root.getChildren().addAll(text, slider);
    setAlignment(Pos.CENTER);
    getChildren().addAll(root);

    setOnMouseEntered(event -> text.setFill(Color.WHITE));
    setOnMouseExited(event -> text.setFill(Color.DARKGREY));
  }

  public Slider getSlider() {
    return slider;
  }
}

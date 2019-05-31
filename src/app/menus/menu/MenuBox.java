package app.menus.menu;

import java.util.Map;
import javafx.scene.layout.StackPane;

public abstract class MenuBox extends StackPane {

  public abstract Map<String, MenuObject> getButtonsMap();
}

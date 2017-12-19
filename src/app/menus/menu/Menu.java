package app.menus.menu;

import java.util.Map;
import javafx.scene.layout.StackPane;

public abstract class Menu extends StackPane {

  public abstract void reload();

  public abstract Map<String, MenuObject> getButtonsMap();
}

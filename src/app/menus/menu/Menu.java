package app.menus.menu;

import javafx.scene.layout.StackPane;
import java.util.Map;

public abstract class Menu extends StackPane {
    public abstract void reload();
    public abstract Map<String, MenuButton> getButtonsMap();
}

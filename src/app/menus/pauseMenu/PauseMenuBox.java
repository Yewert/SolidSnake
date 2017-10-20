package app.menus.pauseMenu;

import app.menus.menu.MenuBox;
import app.menus.menu.MenuButton;

public class PauseMenuBox extends MenuBox {

    public PauseMenuBox(MenuButton... items) {
        for (MenuButton item : items) {
            getChildren().add(item);
        }
    }
}

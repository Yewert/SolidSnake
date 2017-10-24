package app.menus.mainMenu;

import app.menus.menu.MenuBox;
import app.menus.menu.MenuButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;

public class MainMenuBox extends MenuBox {
    public MainMenuBox(MenuButton... items) {
        getChildren().add(createSeparator());

        for (MenuButton item : items) {
            getChildren().addAll(item, createSeparator());
        }
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
}

package app.menus.pauseMenu;

import app.menus.menu.MenuButton;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PauseMenuButton extends MenuButton {

    public PauseMenuButton(String name, double size){
        LinearGradient gradient = new LinearGradient(
                0, 0,
                1, 0,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.TRANSPARENT),
                new Stop(0.4, Color.BLACK),
                new Stop(0.6, Color.BLACK),
                new Stop(1, Color.TRANSPARENT)
        );

        Rectangle bg = new Rectangle(200, 30);
        bg.setFill(Color.TRANSPARENT);
        bg.setOpacity(0.4);

        Text text = new Text(name);
        text.setFill(Color.BLACK);
        text.setFont(Font.font("Calibri", FontWeight.SEMI_BOLD, size));

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);

        setOnMouseEntered(event -> {
            text.setFill(Color.LIGHTGREY);
            bg.setFill(gradient);
        });
        setOnMouseExited(event -> {
            bg.setFill(Color.TRANSPARENT);
            text.setFill(Color.BLACK);
        });
        setOnMousePressed(event -> {
            text.setFill(Color.WHITE);
        });
        setOnMouseReleased(event -> {
            text.setFill(Color.LIGHTGREY);
        });
    }
}

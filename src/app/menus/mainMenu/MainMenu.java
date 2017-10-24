package app.menus.mainMenu;

import app.menus.menu.Menu;
import app.menus.menu.MenuBox;
import app.menus.menu.MenuButton;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.util.Map;

public class MainMenu extends Menu {

    private Map<String, MenuButton> buttons;
    private VBox menuWithInfo;
    private StackPane startPane;
    private MainMenuInfoText infoText;

    public MainMenu(){
        menuWithInfo = new VBox();
        menuWithInfo.setAlignment(Pos.BOTTOM_CENTER);

        startPane = new StackPane();
        infoText = new MainMenuInfoText("");

        MenuButton mainPlay = new MainMenuButton("PLAY");
        MenuButton mainOptions = new MainMenuButton("OPTIONS");
        MenuButton mainExit = new MainMenuButton("EXIT");
        MenuBox menuMain = new MainMenuBox(
                mainPlay,
                mainOptions,
                mainExit
        );

        MenuButton optionsControls = new MainMenuButton("CONTROLS");
        MenuButton optionsSkins = new MainMenuButton("SKINS");
        MenuButton optionsBack = new MainMenuButton("BACK");
        MenuBox menuOptions = new MainMenuBox(
                optionsControls,
                optionsSkins,
                optionsBack
        );

        MenuButton skinsBlue = new MainMenuButton("BLUE SNAKE");
        MenuButton skinsRed = new MainMenuButton("RED SNAKE");
        MenuButton skinsBack = new MainMenuButton("BACK");
        MenuBox menuSkins = new MainMenuBox(
                skinsBlue,
                skinsRed,
                skinsBack
        );

        MenuButton playSolo = new MainMenuButton("SOLO");
        MenuButton playDuo = new MainMenuButton("DUO");
        MenuButton playTrio = new MainMenuButton("TRIO");
        MenuButton playBack = new MainMenuButton("BACK");
        MenuBox menuPlay = new MainMenuBox(
                playSolo,
                playDuo,
                playTrio,
                playBack
        );


        mainPlay.setOnMouseClicked(event -> {
            fadeFromMenuToMenu(menuMain, menuPlay);
            infoText.setText("");
        });
        mainOptions.setOnMouseClicked(event -> {
            fadeFromMenuToMenu(menuMain, menuOptions);
            infoText.setText("");
        });
        mainExit.setOnMouseClicked(event -> System.exit(0));


        optionsControls.setOnMouseClicked(event -> {
            infoText.setText("Not featured yet");
        });
        optionsSkins.setOnMouseClicked(event -> {
            fadeFromMenuToMenu(menuOptions, menuSkins);
            infoText.setText("");
        });
        optionsBack.setOnMouseClicked(event -> {
            fadeFromMenuToMenu(menuOptions, menuMain);
            infoText.setText("");
        });


        skinsBlue.setOnMouseClicked(event -> {
            infoText.setText("Not featured yet");
        });
        skinsRed.setOnMouseClicked(event -> {
            infoText.setText("Not featured yet");
        });
        skinsBack.setOnMouseClicked(event -> {
            fadeFromMenuToMenu(menuSkins, menuOptions);
            infoText.setText("");
        });


        playSolo.setOnMouseClicked(event -> {});
        playDuo.setOnMouseClicked(event -> {
            infoText.setText("Not featured yet");
        });
        playBack.setOnMouseClicked(event -> {
            fadeFromMenuToMenu(menuPlay, menuMain);
            infoText.setText("");
        });


        initMenu(menuMain);
        initMenu(menuOptions);
        initMenu(menuSkins);
        initMenu(menuPlay);

        startPane.getChildren().add(menuMain);
        menuWithInfo.getChildren().addAll(startPane, infoText);
        getChildren().add(menuWithInfo);

        //TODO: Java9 ONLY!!!!!
        buttons = Map.of("playSolo", playSolo, "playDuo", playDuo, "playTrio", playTrio);
    }

    @Override
    public void reload() {
        getChildren().clear();
        getChildren().add(menuWithInfo);
    }

    @Override
    public Map<String, MenuButton> getButtonsMap(){
        return buttons;
    }

    private void fadeFromMenuToMenu(MenuBox from, MenuBox to){
        FadeTransition frFrom = new FadeTransition(Duration.millis(200), from);
        frFrom.setFromValue(1);
        frFrom.setToValue(0);

        FadeTransition ftTo = new FadeTransition(Duration.millis(200), to);
        ftTo.setFromValue(0);
        ftTo.setToValue(1);

        frFrom.play();
        frFrom.setOnFinished(event -> {
            startPane.getChildren().remove(from);
            to.setOpacity(0);
            startPane.getChildren().add(to);
            ftTo.play();
        });
    }

    private void initMenu(MenuBox menu){
        menu.setAlignment(Pos.BOTTOM_CENTER);
        menu.setMaxWidth(300);
        menu.setTranslateY(-20);
    }
}

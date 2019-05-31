package app.menus.mainMenu;

import app.Settings;
import app.SkinSettings;
import app.menus.mainMenu.skinMenuBox.SkinMenuBox;
import app.menus.menu.Menu;
import app.menus.menu.MenuBox;
import app.menus.menu.MenuObject;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainMenu extends Menu {

  private final Map<String, MenuObject> buttons;
  private final VBox menuWithInfo;
  private StackPane startPane;
  public final BiConsumer<Integer, Integer> tableUpdater;
  public final Consumer<List<Entry<String, Integer>>> scoreLoader;
  public final Supplier<Boolean> isTournamentGameAvailable;
  public final Supplier<String> getWinner;
  public final Supplier<Void> deleteTournamentSave;
  public final Supplier<Void> tournamentSaver;

  public MainMenu(Settings settings) {
    menuWithInfo = new VBox();
    menuWithInfo.setAlignment(Pos.BOTTOM_CENTER);

    startPane = new StackPane();

    MenuObject mainPlay = new MainMenuButton("PLAY");
    MenuObject mainTournament = new MainMenuButton("TOURNAMENT");
    MenuObject mainScores = new MainMenuButton("SCORES");
    MenuObject mainOptions = new MainMenuButton("OPTIONS");
    MenuObject mainExit = new MainMenuButton("EXIT");
    MenuBox menuMain = new MainMenuBox(
        mainPlay,
        mainTournament,
        mainScores,
        mainOptions,
        mainExit
    );

    MainMenuSlider optionsSpeed = new MainMenuSlider(1, 20, 21 - settings.getSpeed() / 50,
        "GAME SPEED");
    MainMenuSlider optionsSize = new MainMenuSlider(10, 60, settings.getSize(),
        "SIZE OF GAME OBJECTS");
    MenuObject optionsSkins = new MainMenuButton("SKINS");
    MenuObject optionsBack = new MainMenuButton("BACK");
    MenuBox menuOptions = new MainMenuBox(
        optionsSpeed,
        optionsSize,
        optionsSkins,
        optionsBack
    );

    MenuBox menuSkins = new SkinMenuBox((SkinSettings) settings.getSkins());

    MenuObject playSolo = new MainMenuButton("SOLO");
    MenuObject playDuo = new MainMenuButton("DUO");
    MenuObject playTrio = new MainMenuButton("TRIO");
    MenuObject playBack = new MainMenuButton("BACK");
    MenuBox menuPlay = new MainMenuBox(
        playSolo,
        playDuo,
        playTrio,
        playBack
    );

    TournamentMenu menuTournament = new TournamentMenu();
    tournamentSaver = menuTournament.tournamentSaver;
    tableUpdater = menuTournament.tableUpdater;
    isTournamentGameAvailable = menuTournament.isTournamentGameAvailable;
    deleteTournamentSave = menuTournament.tournamentDeleter;

    menuTournament.getButtonsMap().get("tournamentBack").setOnMouseClicked(event -> {
      if (event.getClickCount() < 2) {
        tournamentSaver.get();
        fadeFromMenuToMenu(menuTournament, menuMain);
      }
    });

    ScoresMenu menuScores = new ScoresMenu();
    scoreLoader = menuScores.scoreLoader;

    menuScores.getButtonsMap().get("scoresBack").setOnMouseClicked(event -> {
      if (event.getClickCount() < 2) {
        fadeFromMenuToMenu(menuScores, menuMain);
      }
    });

    mainPlay.setOnMouseClicked(event -> {
      if (event.getClickCount() < 2) {
        fadeFromMenuToMenu(menuMain, menuPlay);
      }
    });
    mainTournament.setOnMouseClicked(event -> {
      if (event.getClickCount() < 2) {
        fadeFromMenuToMenu(menuMain, menuTournament);
      }
    });
    mainScores.setOnMouseClicked(event -> {
      if (event.getClickCount() > 1) {
        return;
      }
      fadeFromMenuToMenu(menuMain, menuScores);
    });
    mainOptions.setOnMouseClicked(event -> {
      if (event.getClickCount() < 2) {
        fadeFromMenuToMenu(menuMain, menuOptions);
      }
    });
    mainExit.setOnMouseClicked(event -> System.exit(0));

    optionsSpeed.getSlider().setBlockIncrement(1);
    optionsSpeed.getSlider().setMajorTickUnit(1);
    optionsSpeed.getSlider().setMinorTickCount(0);
    optionsSpeed.getSlider().setShowTickLabels(true);
    optionsSpeed.getSlider().setSnapToTicks(true);
    optionsSpeed.getSlider().valueProperty().addListener((observable, oldValue, newValue) -> {
      settings.setSpeed(newValue.intValue() * 50);
      settings.setSpeed((21 - newValue.intValue()) * 50);
    });
    optionsSize.getSlider().setBlockIncrement(5);
    optionsSize.getSlider().setMajorTickUnit(10);
    optionsSize.getSlider().setMinorTickCount(4);
    optionsSize.getSlider().setShowTickLabels(true);
    optionsSize.getSlider().setShowTickMarks(true);
    optionsSize.getSlider().setSnapToTicks(true);
    optionsSize.getSlider().valueProperty().addListener((observable, oldValue, newValue) ->
        settings.setSize(newValue.intValue()));
    optionsSkins.setOnMouseClicked(event -> {
      if (event.getClickCount() < 2) {
        fadeFromMenuToMenu(menuOptions, menuSkins);
      }
    });
    optionsBack.setOnMouseClicked(event -> {
      if (event.getClickCount() < 2) {
        fadeFromMenuToMenu(menuOptions, menuMain);
      }
    });

    Map<String, MenuObject> skinButtons = menuSkins.getButtonsMap();
    skinButtons.get("skinAccept").setOnMouseClicked(event -> {
      if (event.getClickCount() < 2) {
        fadeFromMenuToMenu(menuSkins, menuOptions);
      }
    });
    skinButtons.get("skinDecline").setOnMouseClicked(event -> {
      if (event.getClickCount() < 2) {
        fadeFromMenuToMenu(menuSkins, menuOptions);
      }
    });

    playBack.setOnMouseClicked(event -> {
      if (event.getClickCount() < 2) {
        fadeFromMenuToMenu(menuPlay, menuMain);
      }
    });

    initMenu(menuMain);
    initMenu(menuOptions);
    initMenu(menuSkins);
    initMenu(menuPlay);

    startPane.getChildren().add(menuMain);
    menuWithInfo.getChildren().add(startPane);
    getChildren().add(menuWithInfo);

    buttons = Map.of("playSolo", playSolo,
        "playDuo", playDuo,
        "playTrio", playTrio,
        "mainTournament", mainTournament,
        "tournamentPlay", menuTournament.getButtonsMap().get("tournamentPlay"),
        "scoresLoad", menuScores.getButtonsMap().get("scoresLoad"));
    getWinner = menuTournament.getWinner;
  }

  @Override
  public void reload() {
    getChildren().clear();
    getChildren().add(menuWithInfo);
  }

  @Override
  public Map<String, MenuObject> getButtonsMap() {
    return buttons;
  }

  private void fadeFromMenuToMenu(MenuBox from, MenuBox to) {
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

  private void initMenu(MenuBox menu) {
    menu.setAlignment(Pos.BOTTOM_CENTER);
    menu.setMaxWidth(300);
    menu.setTranslateY(-20);
  }
}

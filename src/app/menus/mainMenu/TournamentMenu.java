package app.menus.mainMenu;

import app.menus.menu.MenuBox;
import app.menus.menu.MenuObject;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TournamentMenu extends MenuBox {

  private Map<String, MenuObject> buttons;
  private Stack<Label> labels = new Stack<>();
  private MenuObject playButton;
  private MenuObject backButton;
  public final BiConsumer<Integer, Integer> tableUpdater;
  private MatchScheduler scheduler;
  private MatchPair currentPair;
  private GridPane tournamentGrid;
  public final Supplier<Boolean> isTournamentGameAvailable;

  public TournamentMenu() {
    tableUpdater = this::updateScores;
    playButton = new MainMenuButton("PLAY");
    backButton = new MainMenuButton("BACK");
    buttons = Map.of("tournamentBack", backButton, "tournamentPlay", playButton);
    initTournamentCreation();
    isTournamentGameAvailable = this::isGameAvailable;
  }

  private void initTournamentCreation() {

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.BOTTOM_LEFT);
    TextField text = new TextField();
    addTextLimiter(text, 10);
    MenuObject addPersonButton = new MainMenuButton("ADD PLAYER");
    addPersonButton.setOnMouseClicked(event -> {
      if (labels.size() < 5) {
        String input = text.getText();
        if (input.isEmpty()) {
          Alert alert = new Alert(AlertType.WARNING);
          alert.setContentText("Empty input");
          alert.showAndWait();
          return;
        }
        Label newPlayer = new Label(input);
        newPlayer.setTextFill(Color.WHITE);
        text.clear();
        labels.push(newPlayer);
        grid.add(newPlayer, 1, 5 - labels.size(), 2, 1);
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("5 players max");
        alert.showAndWait();
      }
    });
    MenuObject removePlayerButton = new MainMenuButton("REMOVE PLAYER");
    removePlayerButton.setOnMouseClicked(event -> {
      if (labels.size() > 0) {
        Label removedPlayer = labels.pop();
        grid.getChildren().remove(removedPlayer);
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("No one to remove");
        alert.showAndWait();
      }
    });
    MenuObject saveTournamentButton = new MainMenuButton("START");
    saveTournamentButton.setOnMouseClicked(event -> {
      if (labels.size() > 1) {
        switchToTournament();
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Not enough players");
        alert.showAndWait();
      }
    });
    grid.add(backButton, 0, 6);
    grid.add(addPersonButton, 1, 6);
    grid.add(removePlayerButton, 2, 6);
    grid.add(saveTournamentButton, 3, 6);
    grid.add(text, 1, 5, 2, 1);
    getChildren().add(grid);
  }

  public void switchToTournament() {
    getChildren().clear();
    initTournament();
  }

  public void switchToCreation() {
    getChildren().clear();
    initTournamentCreation();
  }

  private void initTournament() {
    VBox root = new VBox();
    tournamentGrid = new GridPane();
    ColumnConstraints colConstr0 = new ColumnConstraints(100, 100, 100);
    tournamentGrid.getColumnConstraints().add(colConstr0);
    for (Integer j = 0; j < labels.size() + 1; j++) {
      for (int i = 0; i < labels.size() + 1; i++) {
        if (i == 0 && j != 0) {
          ColumnConstraints colConstr = new ColumnConstraints(50, 100, 100);
          tournamentGrid.getColumnConstraints().add(colConstr);
          Label l = new Label(j.toString());
          l.setTextFill(Color.WHITE);
          tournamentGrid.add((l), j, 0);
        } else if (j == 0 && i != 0) {
          Label playerName = new Label(labels.get(i - 1).getText());
          playerName.setMaxSize(100, 30);
          tournamentGrid.add(labels.get(i - 1), 0, i);
        } else if (i == j) {
          Rectangle rectangle = new Rectangle();
          rectangle.setWidth(100);
          rectangle.setHeight(30);
          rectangle.setFill(Color.WHITE);
          Label stubLabel = new Label("", rectangle);
          tournamentGrid.add(stubLabel, j, i);
        }
      }
    }
    GridPane buttonRow = new GridPane();
    buttonRow.add(playButton, 1, 0);
    buttonRow.add(backButton, 0, 0);
    MenuObject abortButton = new MainMenuButton("ABORT");
    abortButton.setOnMouseClicked(e -> switchToCreation());
    buttonRow.add(abortButton, 2, 0);
    root.getChildren().addAll(tournamentGrid, buttonRow);
    getChildren().add(root);
    scheduler = new MatchScheduler(labels.size());
    currentPair = scheduler.getNextPair();
    labels.clear();
  }

  public static void addTextLimiter(final TextField tf, final int maxLength) {
    tf.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(final ObservableValue<? extends String> ov, final String oldValue,
          final String newValue) {
        if (tf.getText().length() > maxLength) {
          String s = tf.getText().substring(0, maxLength);
          tf.setText(s);
        }
      }
    });
  }

  public void updateScores(Integer score1, Integer score2) {
    currentPair.registerScores(score1, score2);
    Label points1 = new Label(currentPair.getFirstPlayerPoints().toString());
    points1.setTextFill(Color.WHITE);
    tournamentGrid.add(points1, currentPair.getPlayerTwo(),
        currentPair.getPlayerOne());
    Label points2 = new Label(currentPair.getSecondPlayerPoints().toString());
    points2.setTextFill(Color.WHITE);
    tournamentGrid.add(points2, currentPair.getPlayerOne(),
        currentPair.getPlayerTwo());
    currentPair = scheduler.getNextPair();
  }

  @Override
  public Map<String, MenuObject> getButtonsMap() {
    return buttons;
  }

  public boolean isGameAvailable() {
    return currentPair != null;
  }
}

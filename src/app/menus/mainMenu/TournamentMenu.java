package app.menus.mainMenu;

import app.menus.menu.MenuBox;
import app.menus.menu.MenuObject;
import com.alibaba.fastjson.JSON;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TournamentMenu extends MenuBox {

  private Map<String, MenuObject> buttons;
  private Stack<Label> labels = new Stack<>();
  private MenuObject playButton;
  private MenuObject backButton;
  private Tournament tournament;
  private GridPane tournamentGrid;
  public final BiConsumer<Integer, Integer> tableUpdater;
  public final Supplier<Boolean> isTournamentGameAvailable;
  public final Supplier<String> getWinner;
  public final Supplier<Void> tournamentSaver;
  public final Supplier<Void> tournamentDeleter;

  public TournamentMenu() {
    tableUpdater = this::updateScores;
    playButton = new MainMenuButton("PLAY");
    backButton = new MainMenuButton("BACK");
    buttons = Map.of("tournamentBack", backButton, "tournamentPlay", playButton);
    tournament = tryLoadTournament();
    if (tournament == null) {
      initTournamentCreation();
    } else {
      initTournament();
    }
    isTournamentGameAvailable = this::isGameAvailable;
    getWinner = this::determineWinner;
    tournamentSaver = () -> {
      trySaveTournament();
      return null;
    };
    tournamentDeleter = () -> {
      deleteTournament();
      return null;
    };
  }

  private String determineWinner() {
    return tournament.determineWinner();
  }

  private Boolean isGameAvailable() {
    return tournament.isGameAvailable();
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
    tournament = new Tournament(labels.stream()
        .map(Labeled::getText).toArray(String[]::new));
    initTournament();
  }

  private void switchToCreation() {
    labels.clear();
    tournament = null;
    deleteTournament();
    getChildren().clear();
    initTournamentCreation();
  }

  private void initTournament() {
    VBox root = new VBox();
    root.setAlignment(Pos.TOP_CENTER);
    tournamentGrid = new GridPane();
    tournamentGrid.setAlignment(Pos.TOP_CENTER);
    for (int i = 0; i < tournament.getPlayerNames().length + 1; i++) {
      tournamentGrid
          .getRowConstraints()
          .add(new RowConstraints(50, 50, 50));
    }
    tournamentGrid
        .getColumnConstraints()
        .add(new ColumnConstraints(150, 150, 150));
    for (int i = 1; i < tournament.getPlayerNames().length + 2; i++) {
      tournamentGrid
          .getColumnConstraints()
          .add(new ColumnConstraints(100, 100, 100));
    }
    redrawGrid();
    HBox buttonRow = new HBox();
    buttonRow.setAlignment(Pos.CENTER );
    MenuObject abortButton = new MainMenuButton("ABORT");
    abortButton.setOnMouseClicked(e -> switchToCreation());
    buttonRow.getChildren().addAll(backButton, abortButton, playButton);
    root.getChildren().addAll(tournamentGrid, buttonRow);
    getChildren().add(root);
  }

  private static void addTextLimiter(final TextField tf, final int maxLength) {
    tf.textProperty().addListener((ov, oldValue, newValue) -> {
      if (tf.getText().length() > maxLength) {
        String s = tf.getText().substring(0, maxLength);
        tf.setText(s);
      }
    });
  }

  private void updateScores(Integer score1, Integer score2) {
    tournament.registerCurrentPairScores(score1, score2);
    tournament.setUpNextPair();
    redrawGrid();
  }

  private void redrawGrid() {
    tournamentGrid.getChildren().clear();
    String[][] repr = tournament.getRepresentation();
    for (int i = 0; i < repr.length; i++) {
      for (int j = 0; j < repr[i].length; j++) {
        Label lbl;
        if (i == j && repr[i][j] == null) {
          Rectangle rectangle = new Rectangle();
          rectangle.setWidth(100);
          rectangle.setHeight(50);
          rectangle.setFill(Color.WHITE);
          lbl = new Label("", rectangle);
        } else if (Objects.equals(repr[i][j], "NXT")) {
          lbl = new Label("NXT");
          lbl.setTextFill(Color.RED);
        } else {
          lbl = new Label(repr[i][j]);
          lbl.setTextFill(Color.WHITE);
        }
        GridPane.setValignment(lbl, VPos.CENTER);
        GridPane.setHalignment(lbl, HPos.CENTER);
        tournamentGrid.add(lbl, i, j);
      }
    }
  }

  @Override
  public Map<String, MenuObject> getButtonsMap() {
    return buttons;
  }

  private Tournament tryLoadTournament() {
    try {
      return JSON.parseObject(
          String.join(
              "",
              Files.readAllLines(
                  Paths.get("sers/tournament.json"))),
          Tournament.class);
    } catch (IOException e) {
      return null;
    }
  }

  private void deleteTournament() {
    File file = new File("sers/tournament.json");
    if (file.exists()) {
      file.delete();
    }
  }

  private void trySaveTournament() {
    try {
      if (tournament != null) {
        File file = new File("sers/tournament.json");
        if (file.exists()) {
          file.delete();
        } else {
          file.getParentFile().mkdirs();
          file.createNewFile();
        }
        Files.write(file.toPath(), JSON.toJSONBytes(tournament));
      }
    } catch (IOException e) {
      Alert al = new Alert(AlertType.ERROR);
      al.setContentText(e.getMessage());
      al.showAndWait();
    }
  }
}

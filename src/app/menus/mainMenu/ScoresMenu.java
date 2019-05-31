package app.menus.mainMenu;

import app.menus.menu.MenuBox;
import app.menus.menu.MenuObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoresMenu extends MenuBox {

  private Map<String, MenuObject> buttons;
  private List<Label> labels = new ArrayList<>();
  private GridPane scoresGrid;
  public final Consumer<List<Entry<String, Integer>>> scoreLoader;

  public ScoresMenu() {
    VBox vb = new VBox();
    ColumnConstraints playersColumn = new ColumnConstraints(200, 200, 200);
    scoresGrid = new GridPane();
    scoresGrid.getColumnConstraints().add(playersColumn);
    scoresGrid.setGridLinesVisible(true);
    scoresGrid.setAlignment(Pos.TOP_CENTER);
    HBox buttonRow = new HBox();
    buttonRow.setAlignment(Pos.BOTTOM_CENTER);
    MainMenuButton backButton = new MainMenuButton("BACK");
    MainMenuButton loadStatsButton = new MainMenuButton("RELOAD");
    buttons = Map.of("scoresBack", backButton, "scoresLoad", loadStatsButton);
    buttonRow.getChildren().addAll(backButton, loadStatsButton);
    scoreLoader = this::LoadScores;
    vb.setAlignment(Pos.BOTTOM_CENTER);
    vb.getChildren().addAll(scoresGrid, buttonRow);
    getChildren().add(vb);
  }

  @Override
  public Map<String, MenuObject> getButtonsMap() {
    return buttons;
  }

  private void LoadScores(List<Entry<String, Integer>> scores) {
    scoresGrid.getChildren().removeAll(labels);
    labels.clear();
    for (int i = 0; i < scores.size(); i++) {
      if (scores.get(i) == null) {
        continue;
      }
      Label newPlayer = new Label(scores.get(i).getKey());
      newPlayer.setTextFill(Color.WHITE);
      newPlayer.setFont(new Font(20));
      Label score = new Label(scores.get(i).getValue().toString());
      score.setTextFill(Color.WHITE);
      score.setFont(new Font(20));
      labels.add(newPlayer);
      labels.add(score);
      scoresGrid.add(newPlayer, 0, i);
      scoresGrid.add(score, 1, i);
    }
  }
}

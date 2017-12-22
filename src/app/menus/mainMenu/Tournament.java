package app.menus.mainMenu;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.stream.DoubleStream;

public class Tournament {

  public MatchScheduler getScheduler() {
    return scheduler;
  }

  public String[] getPlayerNames() {
    return playerNames;
  }

  @JSONField(serialize = false)
  public MatchPair getCurrentPair() {
    return scheduler.getCurrentPair();
  }

  public Double[] getScores() {
    return scores;
  }

  public void setTable(Double[][] table) {
    this.table = table;
  }

  public void setScheduler(MatchScheduler scheduler) {
    this.scheduler = scheduler;
  }

  public void setPlayerNames(String[] playerNames) {
    this.playerNames = playerNames;
  }

  public void setScores(Double[] scores) {
    this.scores = scores;
  }

  public Double[][] getTable() {

    return table;
  }

  @JSONField
  private Double[][] table;
  @JSONField
  private MatchScheduler scheduler;
  @JSONField
  private String[] playerNames;
  @JSONField
  private Double[] scores;

  public Tournament(String[] playerNames) {
    scheduler = new MatchScheduler(playerNames.length);
    this.playerNames = playerNames;
    scores = DoubleStream
        .generate(() -> 0.0)
        .limit(playerNames.length)
        .boxed()
        .toArray(Double[]::new);
    table = new Double[playerNames.length][playerNames.length];
    setUpNextPair();
  }

  public Tournament(Double[][] table, MatchScheduler scheduler, String[] playerNames,
      Double[] scores) {
    this.table = table;
    this.scheduler = scheduler;
    this.playerNames = playerNames;
    this.scores = scores;
  }

  @JSONField(serialize = false)
  public boolean isGameAvailable() {
    return scheduler.getCurrentPair() != null;
  }

  @JSONField(serialize = false)
  public void setUpNextPair() {
    scheduler.setUpNextPair();
  }

  public void registerCurrentPairScores(Integer playerOneScore, Integer playerTwoScore) {
    scheduler.getCurrentPair().registerScores(playerOneScore, playerTwoScore);
    table[scheduler.getCurrentPair().getPlayerOne() - 1][scheduler.getCurrentPair().getPlayerTwo() - 1] =
        scheduler.getCurrentPair().getSecondPlayerPoints();
    table[scheduler.getCurrentPair().getPlayerTwo() - 1][scheduler.getCurrentPair().getPlayerOne() - 1] =
        scheduler.getCurrentPair().getFirstPlayerPoints();
    scores[scheduler.getCurrentPair().getPlayerOne() - 1] += scheduler.getCurrentPair().getFirstPlayerPoints();
    scores[scheduler.getCurrentPair().getPlayerTwo() - 1] += scheduler.getCurrentPair().getSecondPlayerPoints();
  }

  @JSONField(serialize = false)
  public String determineWinner() {
    String winner = null;
    Double currentBest = -1.0;
    for (int i = 0; i < scores.length; i++) {
      if (scores[i] > currentBest) {
        currentBest = scores[i];
        winner = playerNames[i];
      } else if (scores[i].equals(currentBest)) {
        winner = null;
      }
    }
    return winner;
  }

  @JSONField(serialize = false)
  public String[][] getRepresentation() {
    String[][] result = new String[1 + playerNames.length + 1][1 + playerNames.length];
    int columnIndexOfCurrentPair = scheduler.getCurrentPair() == null ? -1 : scheduler.getCurrentPair().getPlayerTwo();
    int rowIndexOfCurrentPair = scheduler.getCurrentPair() == null ? -1 : scheduler.getCurrentPair().getPlayerOne();
    result[0][0] = "";
    result[result.length - 1][0] = "PTS";
    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[i].length; j++) {
        if (i == j && i != 0) {
          result[i][j] = null;
        } else if (i == 0 && j != 0) {
          result[i][j] = playerNames[j - 1];
        } else if (j == 0 && i != 0 && i != result.length - 1) {
          result[i][j] = String.format("%d", i);
        } else if (columnIndexOfCurrentPair != -1 && columnIndexOfCurrentPair == i
            && rowIndexOfCurrentPair == j) {
          result[i][j] = "NXT";
        } else if (i == result.length - 1 && j != 0) {
          result[i][j] = scores[j - 1].toString();
        } else if (i != 0 && j != 0 && i != result.length - 1) {
          result[i][j] = table[i - 1][j - 1] == null ? "" : table[i - 1][j - 1].toString();
        }
      }
    }
    return result;
  }
}

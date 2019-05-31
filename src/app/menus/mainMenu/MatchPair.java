package app.menus.mainMenu;

public class MatchPair {

  private final int playerOne;
  private final int playerTwo;
  private Double pts1 = null;
  private Double pts2 = null;

  public MatchPair(int playerOne, int playerTwo) {
    this.playerOne = playerOne;
    this.playerTwo = playerTwo;
  }

  public MatchPair(int playerOne, int playerTwo, Double pts1, Double pts2) {
    this.playerOne = playerOne;
    this.playerTwo = playerTwo;
    this.pts1 = pts1;
    this.pts2 = pts2;
  }

  public void registerScores(int score1, int score2) {
    if (score1 == score2) {
      pts1 = 0.5;
      pts2 = 0.5;
    } else if (score1 > score2) {
      pts1 = 1.0;
      pts2 = 0.0;
    } else {
      pts1 = 0.0;
      pts2 = 1.0;
    }
  }

  public Double getFirstPlayerPoints() {
    return pts1;
  }

  public Double getSecondPlayerPoints() {
    return pts2;
  }

  public int getPlayerOne() {
    return playerOne;
  }

  public int getPlayerTwo() {
    return playerTwo;
  }
}

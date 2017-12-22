package app.menus.mainMenu;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Map;

public class MatchScheduler {

  //NICE FUCKING IENUMERABLE JAVA
  @JSONField
  private int counter = -1;
  @JSONField
  private MatchPair[] pairs;
  private static Map<Integer, Integer> numberOfMatches = Map.of(2, 1, 3, 3, 4, 6, 5, 10);
  private final int numberOfPlayers;

  public MatchScheduler(int numberOfPlayers, MatchPair[] pairs, int counter) {
    this.numberOfPlayers = numberOfPlayers;
    this.pairs = pairs;
    this.counter = counter;
  }

  public MatchScheduler(int numberOfPlayers) {
    this.numberOfPlayers = numberOfPlayers;
    pairs = new MatchPair[numberOfMatches.get(numberOfPlayers)];
    int counter1 = 0;
    for (int i = 1; i < numberOfPlayers + 1; i++) {
      for (int j = i + 1; j < numberOfPlayers + 1; j++) {
        pairs[counter1] = new MatchPair(i, j);
        counter1++;
      }
    }
  }

  public int getNumberOfPlayers() {
    return numberOfPlayers;
  }

  public MatchPair[] getPairs() {
    return pairs;
  }

  public int getCounter() {
    return counter;
  }

  @JSONField(serialize = false)
  public void setUpNextPair() {
    counter++;
  }

  @JSONField(serialize = false)
  public MatchPair getCurrentPair() {
    if (counter >= pairs.length) {
      return null;
    }
    return pairs[counter];
  }
}

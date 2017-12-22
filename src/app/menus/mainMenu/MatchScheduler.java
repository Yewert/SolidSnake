package app.menus.mainMenu;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Map;

public class MatchScheduler {

  private static Map<Integer, Integer> numberOfMatches = Map.of(2, 1, 3, 3, 4, 6, 5, 10);
  private final int numberOfPlayers;

  public MatchScheduler(int numberOfPlayers, MatchPair[] pairs, int counter) {
    this.numberOfPlayers = numberOfPlayers;
    this.pairs = pairs;
    this.counter = counter;
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

  public void setPairs(MatchPair[] pairs) {
    this.pairs = pairs;
  }

  @JSONField
  private MatchPair[] pairs;

  public void setCounter(int counter) {
    this.counter = counter;
  }

  //NICE FUCKING IENUMERABLE JAVA
  @JSONField
  private int counter = -1;

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

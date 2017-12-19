package app.menus.mainMenu;

import java.util.Map;

public class MatchScheduler {

  private static Map<Integer, Integer> numberOfMatches = Map.of(2, 1, 3, 3, 4, 6, 5, 10);
  private final int numberOfPlayers;
  private final MatchPair[] pairs;
  //NICE FUCKING IENUMERABLE JAVA
  private int counter = 0;

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

  public MatchPair getNextPair() {
    if (counter >= pairs.length) {
      return null;
    }
    MatchPair pair = pairs[counter];
    counter++;
    return pair;
  }

}

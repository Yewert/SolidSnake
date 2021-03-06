package app;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class StatsHolder {

  public HashMap<String, Integer> getWinners() {
    return winners;
  }

  @JSONField
  private HashMap<String, Integer> winners;

  public StatsHolder() {
    winners = new HashMap<>();
  }

  public void updateWinner(String nickname) {
    winners.compute(nickname, (k, v) -> v == null ? 1 : v + 1);
  }

  public List<Entry<String, Integer>> getTop(Integer number) {
    List<Entry<String, Integer>> toSort = new ArrayList<>(winners
        .entrySet());
    toSort.sort(Entry.comparingByValue(Comparator.reverseOrder()));
    List<Entry<String, Integer>> list = new ArrayList<>();
    long limit = number.longValue();
    for (Entry<String, Integer> stringIntegerEntry : toSort) {
      if (limit-- == 0) {
        break;
      }
      list.add(stringIntegerEntry);
    }
    return list;
  }
}

package app;

import com.alibaba.fastjson.JSON;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map.Entry;

public class StatsManager {

  private final String url;

  public StatsManager(String url) {

    this.url = url;
  }

  public void updateStats(String winner) throws IOException {
    StatsHolder stats;
    File file = new File(url);
    if (file.exists()) {
      List<String> lines = Files.readAllLines(file.toPath());
      stats = getDeserializedStats(String.join("", lines));
      stats = stats == null ? new StatsHolder() : stats;
      stats.updateWinner(winner);
      byte[] jsoned = JSON.toJSONBytes(stats);
      Files.write(file.toPath(), jsoned);
    } else {
      stats = new StatsHolder();
      stats.updateWinner(winner);
      file.getParentFile().mkdirs();
      file.createNewFile();
      byte[] jsoned = JSON.toJSONBytes(stats);
      Files.write(file.toPath(), jsoned);
    }
  }

  public List<Entry<String, Integer>> getTop(Integer number) throws IOException {
    StatsHolder stats;
    File file = new File(url);
    if (file.exists()) {
      List<String> lines = Files.readAllLines(file.toPath());
      stats = getDeserializedStats(String.join("", lines));
      stats = stats == null ? new StatsHolder() : stats;
    } else {
      stats = new StatsHolder();
    }
    return stats.getTop(number);
  }

  private StatsHolder getDeserializedStats(String content) {
    return JSON.parseObject(content, StatsHolder.class);
  }

}

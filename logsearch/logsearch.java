import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.*;

public class logsearch {
  private static Map<String, Integer> keywords = new HashMap<String, Integer>();
  private static String[] defaultKeywords = { "Google", "Mozilla", "Safari" };

  public static void main(String[] args) {
    if (args.length == 0) {
      getDefaultKeywords();
    } else {
      getKeywordFromArgs(args);
    }
    readLogs();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String jsonOutput = gson.toJson(keywords);
    System.out.println(jsonOutput);
  }

  private static void getDefaultKeywords() {
    for (var i = 0; i < defaultKeywords.length; i++) {
      keywords.put(defaultKeywords[i], 0);
    }
  }

  private static void getKeywordFromArgs(String[] keyword) {
    for (var i = 0; i < keyword.length; i++) {
      keywords.put(keyword[i], 0);
    }
  }

  private static void readLogs() {
    try {
      FileInputStream fstream = new FileInputStream("./logs/log.log");
      BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
      String strLine;
      while ((strLine = br.readLine()) != null) {
        for (Map.Entry<String, Integer> entry : keywords.entrySet()) {
          if (strLine.contains(entry.getKey())) {
            keywords.put(entry.getKey(), entry.getValue() + 1);
          }
        }
      }
      fstream.close();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }
}
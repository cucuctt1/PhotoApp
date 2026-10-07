package thanhdnh.ueh.edu.article_app;

import android.app.Activity;
import android.content.Context;
import android.widget.GridView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserData {
  public static UserList data;
  private final Context context; private final GridView gridview;
  private final ExecutorService executor = Executors.newSingleThreadExecutor();
  public UserData(Context context, GridView gridview) { this.context = context; this.gridview = gridview; }
  public static User getUserFromId(int id) {
    if (data == null || data.getUsers() == null) return null;
    for (User user : data.getUsers()) if (user.getId() == id) return user;
    return null;
  }
  public void loadData(String url, Activity activity) {
    executor.execute(() -> {
      String jsonString = null;
      File file = Downloader.downloadFile(url, context.getCacheDir());
      if (file != null) {
        jsonString = readText(file);
      }

      // If download failed or returned 404/empty, fallback to local assets
      if (jsonString == null || jsonString.isEmpty() || jsonString.contains("404")) {
        try (InputStream is = context.getAssets().open("users.json")) {
          int size = is.available();
          byte[] buffer = new byte[size];
          is.read(buffer);
          jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      final String finalJson = jsonString;
      if (finalJson != null && !finalJson.isEmpty()) {
        activity.runOnUiThread(() -> {
          try {
            data = new Gson().fromJson(finalJson, UserList.class);
            if (data != null && data.getUsers() != null) {
              gridview.setAdapter(new UserAdapter(data.getUsers(), context));
            } else {
              Toast.makeText(context, "Invalid data format received", Toast.LENGTH_SHORT).show();
            }
          } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error parsing user data", Toast.LENGTH_SHORT).show();
          }
        });
      } else {
        activity.runOnUiThread(() -> {
          Toast.makeText(context, "Failed to load user data.", Toast.LENGTH_LONG).show();
        });
      }
    });
  }
  public String readText(File file) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
      StringBuilder buffer = new StringBuilder(); String line;
      while ((line = reader.readLine()) != null) buffer.append(line).append('\n');
      return buffer.toString();
    } catch (IOException e) { e.printStackTrace(); return ""; }
  }
}

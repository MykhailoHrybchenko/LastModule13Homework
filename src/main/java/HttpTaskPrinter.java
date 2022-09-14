import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskPrinter {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    public static List<TaskUser> getTasks(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        final List<TaskUser> tasks = GSON.fromJson(response.body(), new TypeToken<List<TaskUser>>(){}.getType());
        List<TaskUser> activeTasks = new ArrayList<>();
        for (TaskUser task : tasks) {
            if (!task.completed) {
                activeTasks.add(task);
            }
        }
        return activeTasks;
    }
}

class TaskUser {
    public int userId;
    public int id;
    public String title;
    public boolean completed;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isStatus() {
        return completed;
    }

    public void setStatus(boolean status) {
        this.completed = status;
    }

    @Override
    public String toString() {
        return "TaskUser{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", status=" + completed +
                '}';
    }
}

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CommentPrinter {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    static String jsonPath = "/src/main/resources/user-X-post-Y-comments.json";
    private static final String GET_COMMENTS = "https://jsonplaceholder.typicode.com/posts/10/comments";

    public static int sendGetHighestPostId(URI uri, int taskTwoUserId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/" + taskTwoUserId + "/posts"))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<Posts> posts = GSON.fromJson(response.body(), new TypeToken<List<Posts>>() {
        }.getType());
        return posts.stream()
                .map(Posts::getId)
                .max(Integer::compare)
                .orElse(1);
    }

    public static List<Comments> sendGetUserComments(URI uri, int highestPostId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/" + highestPostId + "/comments"))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), new TypeToken<List<Comments>>() {
        }.getType());
    }

    public static void writeCommentsToJson(List<Comments> comments, int taskTwoUserId, int highestPostId) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("target/user-" + taskTwoUserId + "-post-" + highestPostId + "-comments.json"))) {
            writer.write(GSON.toJson(comments));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

class Comments {
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;

    public Comments(int postId, int id, String name, String email, String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public Comments() {
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "postId=" + postId +
                ", id=" + id +
                ", name=" + name +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}

class Posts {
    private int userId;
    private int id;
    private String title;
    private String body;

    public Posts(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public Posts() {
    }

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}

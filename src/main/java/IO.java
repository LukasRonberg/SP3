import java.util.ArrayList;
import java.util.HashSet;

public interface IO {
    ArrayList<Show> readShowsFromFile(String path);
    ArrayList<Movie> readMoviesFromFile(String path);
    HashSet<User> readUserData(String path);

    void saveUserData(HashSet<User> users);

    void saveUserData(HashSet<User> users, User currentUser);
}

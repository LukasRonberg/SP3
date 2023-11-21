import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    String Username;
    String Password;

    private ArrayList<Media> seenMedia = new ArrayList<>();

    private ArrayList<Media> savedMedia = new ArrayList<>();


    public void AddMediaToSeen(Media media) {
        seenMedia.add(media);
    }

    public ArrayList<Media> getSeenMedia() {
        return(seenMedia);
    }

    public void addMediaToSaved(Media media) {
        savedMedia.add(media);
    }

    public ArrayList<Media> getSavedMedia() {
        return(savedMedia);
    }

    public void removeFromSaved(Media media) {
        if (savedMedia.contains(media)) {
            savedMedia.remove(media);
        } else {
            System.out.println("Media item not found in saved list: " + media);
        }
    }

    public User(String username, String password, ArrayList<Media> seenMedia, ArrayList<Media> savedMedia) {
        Username = username;
        Password = password;
        this.seenMedia = seenMedia;
        this.savedMedia = savedMedia;
    }
}

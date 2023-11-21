import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    String Username;
    String Password;

    private ArrayList<Media> SeenMedia = new ArrayList<~>();

    private ArrayList<Media> SavedMedia = new ArrayList<~>();


    public void AddMediaToSeen(Media media) {
        Set<Media> SeenMedia = getSeenMedia(SeenMedia);
        SeenMedia.add(media);
        getSeenMedia(SeenMedia);
    }

    public Set<Media> getSeenMedia(Set<Media> seenMedia) {
        Set<Media> SeenMedia = new HashSet<Media>();
        return getSeenMedia(SeenMedia);
    }

    public void addMediaToSaved(Media media) {
        List<Media> SavedMedia = getSavedMedia();
        SavedMedia.add(media);
        saveSeenMedia((Set<Media>) SavedMedia);
    }

    public List<Media> getSavedMedia() {
        return new ArrayList<>();
    }

    public ArrayList<Object> removeFromSaved(Media media) {
        List<Media> SavedMedia = getSavedMedia();
        if (SavedMedia.contains(media)) {
            SavedMedia.remove(media);
        } else {
            System.out.println("Media item not found in saved list: " + media);
        }
        return null;
    }
        private List<Media> getSavedMedia(List<Media> SavedMedia) {
            return SavedMedia;
        }
}

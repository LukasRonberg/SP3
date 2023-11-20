import java.util.ArrayList;

public abstract class Media {

    String Title;

    public Media(String title, String yearReleased, ArrayList<String> categories, Double rating) {
        Title = title;
        this.yearReleased = yearReleased;
        Categories = categories;
        Rating = rating;
    }

    String yearReleased;

    Double Rating;

    protected ArrayList<String> Categories = new ArrayList<String>();


    public void Play() {

    }

    public void Stop() {
}








}


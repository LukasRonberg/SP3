import java.util.ArrayList;

public abstract class Media {
    protected String Title;
    protected String yearReleased;
    protected Double Rating;
    protected ArrayList<String> Categories;


    public String getTitle() {
        return Title;
    }

    public Media(String title, String yearReleased, ArrayList<String> categories, Double rating) {
        Title = title;
        this.yearReleased = yearReleased;
        Categories = categories;
        Rating = rating;
    }

    public String getYearReleased() {
        return yearReleased;
    }

    public Double getRating() {
        return Rating;
    }
    public ArrayList<String> getCategories() {
        return Categories;
    }


    public void Play() {

    }

    public void Stop() {
}








}


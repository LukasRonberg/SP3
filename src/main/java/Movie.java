import java.util.ArrayList;

public class Movie extends Media{
    public Movie(String title, String yearReleased,ArrayList<String> categories,  Double rating) {
        super(title, yearReleased, categories, rating);
    }

    @Override
    public void Play() {
        System.out.println("You are now playing the movie: " + this.Title + ".");
    }

    // TODO: 20-11-2023 Skal lige gøres finere
    @Override
    public String toString() {
        return this.Title +". "+ this.yearReleased +". "+ this.Categories.toString() +". "+ this.Rating +":";
    }
}

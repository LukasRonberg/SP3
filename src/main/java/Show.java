import java.util.ArrayList;

public class Show extends Media{

    //int[][] SeasonAndEpisodes;
    ArrayList<String> SeasonAndEpisodes;

    public Show(String title, String yearReleased, ArrayList<String> categories, Double rating, /*int[][]*/ ArrayList<String>seasonAndEpisodes) {
        super(title, yearReleased, categories, rating);
        SeasonAndEpisodes = seasonAndEpisodes;
    }


    public void PlayEpisode(int[][]E) {

    }

    // TODO: 20-11-2023 Skal lige gøres finere 
    @Override
    public String toString() {
        return this.Title +". "+ this.yearReleased +". "+ this.Categories.toString() +". "+ this.Rating + ". "+ this.SeasonAndEpisodes.toString() + ":";
    }

}

import java.util.ArrayList;

public class Show extends Media{

    //int[][] SeasonAndEpisodes;
    ArrayList<String> SeasonAndEpisodes;

    public Show(String title, String yearReleased, ArrayList<String> categories, Double rating, /*int[][]*/ ArrayList<String>seasonAndEpisodes) {
        super(title, yearReleased, categories, rating);
        SeasonAndEpisodes = seasonAndEpisodes;
    }

    public ArrayList<String> getSeasonAndEpisodes(){
        return SeasonAndEpisodes;
    }

    @Override
    public void Play() {
        System.out.println("You are now playing the show: " + this.Title + ".");
    }

    public void PlayEpisode(int season, int episode) {
        System.out.println("You are now playing the show: " + this.Title + ", Season: " + season + ", Episode: " + episode);
    }

    // TODO: 20-11-2023 Skal lige gøres finere 
    @Override
    public String toString() {
        return this.Title +". "+ this.yearReleased +". "+ this.Categories.toString() +". "+ this.Rating + ". "+ this.SeasonAndEpisodes.toString() + ":";
    }

}

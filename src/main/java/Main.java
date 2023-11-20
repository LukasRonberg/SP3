import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        FileIO fileHandler = new FileIO();

        ArrayList<Movie> movies = fileHandler.readMoviesFromFile("src/main/java/100bedstefilm.txt");
        ArrayList<Show> shows = fileHandler.readShowsFromFile("src/main/java/100bedsteserier.txt");

        //Display all Movies
        System.out.println("Movies:");
        for (Movie movie : movies){
            System.out.println(movie);
        }

        //Display all Movies
        System.out.println("\n Shows:");
        for (Show show : shows){
            System.out.println(show);
        }
    }
}

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

//Tænker vi skal have en showMenu metode måske, som giver en
//de forskellige muligheder man kan vælge imellem

public class MainMenu {
    TextUI ui = new TextUI();
    FileIO io = new FileIO();
    private ArrayList<Media> allMedia = new ArrayList<>();

    public void searchByName(String title) {
        ArrayList<Show> shows = io.readShowsFromFile("100bedsteserier.txt");
        ArrayList<Movie> movies = io.readMoviesFromFile("100bedstefilm.txt");
        allMedia.addAll(shows);
        allMedia.addAll(movies);

        boolean found = false;
        for (Media m : allMedia) {
            if (m.getTitle().equalsIgnoreCase(title)) {
                found = true;
                break;
            }
        }
        if (found) {
            if (ui.getInput("We have " + title + ". Would you like to watch? (Y/N)").equalsIgnoreCase("Y")) {
                //Play metode skal indsættes her
            } else {
                String response = ui.getInput("We do not have " + title + ". Do you want to keep searching?(Y/N)");
                if (response.equalsIgnoreCase("Y")) {
                    String newSearch = ui.getInput("Enter the title to search again: ");
                    searchByName(newSearch);
                } else {
                    //return til menu metode og besked??
                }
            }
        }
    }

    public void searchByCategories(ArrayList<String> Categories) {
        ArrayList<Show> shows = io.readShowsFromFile("100bedsteserier.txt");
        ArrayList<Movie> movies = io.readMoviesFromFile("100bedstefilm.txt");
        allMedia.addAll(shows);
        allMedia.addAll(movies);

        ArrayList<String> titles = new ArrayList<>();

        boolean found = false;
        for (Media m : allMedia) {
            if (m.getCategories().containsAnyIgnoreCase()) {
                titles.add(m.getTitle());
            }
        }
        if(!titles.isEmpty()){
            ui.displayMessage("List of shows that have the categories: ");
            for(int i = 0;i < titles.size() ;i++){
                ui.displayMessage((i + 1)+") "+titles.get(i));
            }
            int choice = 0;
            do{
            try{
               choice = Integer.parseInt(ui.getInput("Enter the number to select the movie/show."));
                if(choice < 0 || choice > titles.size()){
                    ui.displayMessage("Invalid. Please pick a number between 1 and "+titles.size());
                }
            }catch (NumberFormatException e){
                ui.displayMessage("Invalid. Please enter a valid number");
            }
        } while (choice < 0 || choice > titles.size());
            if(choice !=0){
                String selected = titles.get(choice - 1);
                ui.displayMessage("You selected: " + selected);
                //Play metode
            } else {
                ui.displayMessage("Returning to Menu");
                //Return til menu
            }
        }
    }

    //Vi skal nok have en currentUser på en eller anden måde
    //Mangler getter på seenMedia, som er private i User klassen
    User user = new User();

    public void viewSeenMedia() {
        ArrayList<Media> seenMedia = user.getSeenMedia();
        if (seenMedia.isEmpty()) {
            ui.displayMessage("You have not watched anything yet.");
        } else {
            ui.displayMessage("Your seen media: ");
            for (Media media : seenMedia) {
                ui.displayMessage(media.getTitle() + "\n");
            }
        }
    }

    public void viewSavedMedia() {
        ArrayList<Media> savedMedia = user.getSavedMedia();
        if (savedMedia.isEmpty()) {
            ui.displayMessage("You have not watched anything yet.");
        } else {
            ui.displayMessage("Your seen media: ");
            for (Media media : savedMedia) {
                ui.displayMessage(media.getTitle() + "\n");
            }
        }
    }
}

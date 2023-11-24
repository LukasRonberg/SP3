import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

//Tænker vi skal have en showMenu metode måske, som giver en
//de forskellige muligheder man kan vælge imellem

public class MainMenu {
    TextUI ui = new TextUI();
    FileIO io = new FileIO();
    private ArrayList<Media> allMedia = new ArrayList<>();

    public void searchByName(String title, User currentUser) {
        ArrayList<Media> media = new ArrayList<Media>();

        boolean found = false;
        for (Media m : allMedia) {
            if (m.getTitle().toLowerCase().contains(title.toLowerCase())) {
                found = true;
                media.add(m);
            }
        }
        checkIfFound(found, media, currentUser, "title: \"" + title, "searchByName");
    }

    public void searchByCategories(String categories, User currentUser) {
        ArrayList<Media> titles = new ArrayList<>();
// TODO: 21/11/2023 skal kunne søge på flere kategorier på én gang
        // TODO: 21/11/2023 tilføj parameter currentUser og gør at media bliver tilføjet til seen efter play metode
        boolean found = false;
        for (Media m : allMedia) {
            if (m.getCategories().contains(categories)) {
                titles.add(m);
                found = true;
            }
        }
        checkIfFound(found, titles, currentUser, "categories: \""+categories, "searchByCategories");
    }

    private void checkIfFound(Boolean found, ArrayList<Media> titles, User currentUser, String SearchType, String currentFunction){
        if (found) {
            ui.displayMessage("List of media that have the "+SearchType+"\": ");
            for (int i = 0; i < titles.size(); i++) {
                ui.displayMessage((i + 1) + ") " + titles.get(i));
            }

            int choice = userChoice(titles.size());

            if (choice != 0) {
                Media selected = titles.get(choice - 1);
                mediaChoices(currentUser, selected);
            } else {
                ui.displayMessage("Returning to Menu");
                //Return til menu
            }
        } else {
            String response = ui.getInput("We do not have anything with the " + SearchType + "\". Do you want to keep searching?(Y/N)");
            if (response.equalsIgnoreCase("Y")) {
                switch (currentFunction) {
                    case "searchByCategories" ->
                            searchByCategories(ui.getInput("Enter the " + SearchType.split(":")[0] + " to search again: "), currentUser);
                    case "searchByName" ->
                            searchByName(ui.getInput("Enter the " + SearchType.split(":")[0] + " to search again: "), currentUser);
                    case "searchByYear" ->
                            searchByYear(ui.getInput("Enter the " + SearchType.split(":")[0] + " to search again: "), currentUser);
                    case "searchByRating" ->
                            searchByRating(Double.parseDouble(ui.getInput("Enter the " + SearchType.split(":")[0] + " to search again: ")), currentUser);
                }
            } else {
                ui.displayMessage("Returning to menu");
            }
        }
    }


    private void mediaChoices(User currentUser, Media selected){
        boolean exists = false;
        String selectResponse;
        for (Media media: currentUser.getSavedMedia()) {
            if (media.Title.equals(selected.Title)) {
                exists = true;
                break;
            }
        }
        if(exists) {
            selectResponse = ui.getInput("You selected: " + selected + ". \n1) Watch now \n2) Remove from saved \n3) Return to main menu");
        }
        else {
            selectResponse = ui.getInput("You selected: " + selected + ". \n1) Watch now \n2) Add to saved \n3) Return to main menu");
        }
// TODO: 24-11-2023 brug klasser til season og episode således at vi kan gemme hvilke episoder der er blevet set. 
        if(selectResponse.equals("1")) {
            if(selected.getClass() == Show.class){
                var showSeasonsAndEpisodes = ((Show)selected).getSeasonAndEpisodes();
                int seasonInput = Integer.parseInt(ui.getInput("Select a season from 1 to " + showSeasonsAndEpisodes.get(showSeasonsAndEpisodes.size()-1).split("-")[0]));
                if(seasonInput > 0 && seasonInput <= showSeasonsAndEpisodes.size()-1){
                    int episodeInput = Integer.parseInt(ui.getInput("Select a episode from 1 to " + showSeasonsAndEpisodes.get(seasonInput).split("-")[1]));
                    if(episodeInput > 0 && episodeInput <= Integer.parseInt(showSeasonsAndEpisodes.get(seasonInput).split("-")[1])){
                        ((Show)selected).PlayEpisode(seasonInput,episodeInput);
                        currentUser.AddMediaToSeen(selected);
                    }
                }
            } else {
                selected.Play();
                currentUser.AddMediaToSeen(selected);
            }
            //Play metode
        } else if (selectResponse.equals("2")){
            if(!exists){
                ui.displayMessage("Added media to saved");
                currentUser.addMediaToSaved(selected);
            } else {
                ui.displayMessage("Removed media from saved");
                currentUser.removeFromSaved(selected);
            }
        }
    }
    public void searchByYear(String yearReleased, User currentUser) {
        ArrayList<Media> years = new ArrayList<>();
        Media media = null;
        boolean found = false;
        for (Media m : allMedia) {
            if (m.getYearReleased().contains(yearReleased) && !yearReleased.isEmpty()) {
                years.add(m);
                found = true;
                media = m;
            }
        }

        checkIfFound(found, years,currentUser, "year(s): \""+yearReleased,"searchByYear");
    }


    public void searchByRating(Double Rating, User currentUser) {
        ArrayList<Media> rating = new ArrayList<>();
        boolean found = false;
        for (Media media : allMedia) {
            if (media.getRating() == Rating) {
                rating.add(media);
                found = true;
            }
        }
            checkIfFound(found, rating, currentUser, "rating: \"" +Rating, "searchByRating");

        //return rating;
    }
    public void viewSeenMedia(User currentUser) {
        ArrayList<Media> seenMedia = currentUser.getSeenMedia();
        if (seenMedia.isEmpty()) {
            ui.displayMessage("You have not watched anything yet.");
        } else {
            ui.displayMessage("Your seen media: ");
            for (Media media : seenMedia) {
                ui.displayMessage(media.getTitle());
            }
        }
    }

    // TODO: 21/11/2023 Den skal give en liste over savedMedia og så skal man kunne vælge om man vil se en af sine gemte film/serier
    // TODO: 21/11/2023 eller gå tilbage til menuen.
    public void viewSavedMedia(User currentUser) {
        ArrayList<Media> savedMedia = currentUser.getSavedMedia();
        Media media = null;
        if (savedMedia.isEmpty()) {
            ui.displayMessage("You have not saved anything yet.");
        } else {
            ui.displayMessage("Your saved media: ");
            for (int i = 0; i < savedMedia.size(); i++) {
                ui.displayMessage((i + 1) + ") " + savedMedia.get(i));
            }

            int choice = userChoice(savedMedia.size());
            Media selected = savedMedia.get(choice - 1);
            mediaChoices(currentUser, selected);
        }
    }
    private int userChoice(int maxChoice){
        int choice = 0;
        do {
            try {
                choice = Integer.parseInt(ui.getInput("Enter the number to select the movie/show."));
                if (choice < 0 || choice > maxChoice) {
                    ui.displayMessage("Invalid. Please pick a number between 1 and " + maxChoice);
                }
            } catch (NumberFormatException e) {
                ui.displayMessage("Invalid. Please enter a valid number");
            }
        } while (choice < 0 || choice > maxChoice);
        return choice;
    }

    public void startUp(){
        ArrayList<Show> shows = io.readShowsFromFile("src/main/java/100bedsteserier.txt");
        ArrayList<Movie> movies = io.readMoviesFromFile("src/main/java/100bedstefilm.txt");
        allMedia.addAll(shows);
        allMedia.addAll(movies);
    }
}

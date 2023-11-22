import java.util.ArrayList;

//Tænker vi skal have en showMenu metode måske, som giver en
//de forskellige muligheder man kan vælge imellem

public class MainMenu {
    TextUI ui = new TextUI();
    FileIO io = new FileIO();
    private ArrayList<Media> allMedia = new ArrayList<>();

    public void searchByName(String title, User currentUser) {
        Media media = null;
        boolean found = false;
        // TODO: 21/11/2023 brug contains og gem objektet. funktionen skal kunne finde filmen, fx the godfather hvis bare man skriver god
        // TODO: 21/11/2023 den skal spørge om man vil tilføje mediet til savedMedia eller om man vil se den med det samme
        for (Media m : allMedia) {
            if (m.getTitle().equalsIgnoreCase(title)) {
                found = true;
                media = m;
                break;
            }
        }
        if (found) {
            if (ui.getInput("We have " + title + ". Would you like to watch? (Y/N)").equalsIgnoreCase("Y")) {
                currentUser.AddMediaToSeen(media);
                //Play metode skal indsættes her
            }  else {
                    //return til menu metode og besked??
                }
            } else {
            String response = ui.getInput("We do not have " + title + ". Do you want to keep searching?(Y/N)");
            if (response.equalsIgnoreCase("Y")) {
                String newSearch = ui.getInput("Enter the title to search again: ");
                searchByName(newSearch, currentUser);
            }
        }
    }

    public void searchByCategories(String categories, User currentUser) {
        ArrayList<Media> titles = new ArrayList<>();
        Media media = null;
// TODO: 21/11/2023 skal kunne søge på flere kategorier på én gang
        // TODO: 21/11/2023 tilføj parameter currentUser og gør at media bliver tilføjet til seen efter play metode
        boolean found = false;
        for (Media m : allMedia) {
            if (m.getCategories().contains(categories)) {
                titles.add(m);
                found = true;
                media = m;
            }
        }

        if(found){
            ui.displayMessage("List of shows that have the categories: ");
            for(int i = 0;i < titles.size() ;i++){
                ui.displayMessage((i + 1)+") "+titles.get(i));
            }

            int choice = userChoice(titles.size());

            if(choice !=0){
                Media selected = titles.get(choice - 1);
                ui.displayMessage("You selected: " + selected);
                currentUser.AddMediaToSeen(selected);
                //Play metode
            } else {
                ui.displayMessage("Returning to Menu");
                //Return til menu
            }
        } else {
            String response = ui.getInput("We do not have " + categories + ". Do you want to keep searching?(Y/N)");
            if (response.equalsIgnoreCase("Y")) {
                searchByCategories(ui.getInput("Enter the categories to search again: "), currentUser);
            } else {
                ui.displayMessage("Returning to menu");
            }
        }
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
            ui.displayMessage("You have not watched anything yet.");
        } else {
            ui.displayMessage("Your saved media: ");
            for (int i = 0; i < savedMedia.size(); i++) {
                ui.displayMessage((i + 1) + ") " + savedMedia.get(i));
            }

            int choice = userChoice(savedMedia.size());
            if(choice !=0){
                Media selected = savedMedia.get(choice - 1);
                ui.displayMessage("You selected: " + selected);
                currentUser.AddMediaToSeen(selected);
                //Play metode
            } else {
                ui.displayMessage("Returning to Menu");
                //Return til menu
            }
            /*for (Media media : savedMedia) {
                ui.displayMessage(media.getTitle());
            }*/
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

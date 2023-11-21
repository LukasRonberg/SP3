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
        for (Media m : allMedia) {
            if (m.getTitle().equalsIgnoreCase(title)) {
                found = true;
                media = m;
                break;
            }
        }
        if (found) {
            if (ui.getInput("We have " + title + ". Would you like to watch? (Y/N)").equalsIgnoreCase("Y")) {
                currentUser.addMediaToSaved(media);
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

    public void searchByCategories(String categories) {
        ArrayList<Media> titles = new ArrayList<>();
// TODO: 21/11/2023 skal kunne søge på flere kategorier på en gang
        boolean found = false;
        for (Media m : allMedia) {
            if (m.getCategories().contains(categories)) {
                titles.add(m);
                found = true;
            }
        }


        if(found){
            ui.displayMessage("List of shows that have the categories: ");
            for(int i = 0;i < titles.size() ;i++){
                ui.displayMessage((i + 1)+") "+titles.get(i));
            }
            int choice = 0;
            do{
            try{
                // TODO: 21/11/2023 ui.getInput skal ændres på et tidspunkt til ui.getInt, så vi fjerner Integer.praseInt
               choice = Integer.parseInt(ui.getInput("Enter the number to select the movie/show."));
                if(choice < 0 || choice > titles.size()){
                    ui.displayMessage("Invalid. Please pick a number between 1 and "+titles.size());
                }
            }catch (NumberFormatException e){
                ui.displayMessage("Invalid. Please enter a valid number");
            }
        } while (choice < 0 || choice > titles.size());
            if(choice !=0){
                Media selected = titles.get(choice - 1);
                ui.displayMessage("You selected: " + selected);
                //Play metode
            } else {
                ui.displayMessage("Returning to Menu");
                //Return til menu
            }
        } else {
            String response = ui.getInput("We do not have " + categories + ". Do you want to keep searching?(Y/N)");
            if (response.equalsIgnoreCase("Y")) {

                searchByCategories(ui.getInput("Enter the categories to search again: "));

            } else {
                ui.displayMessage("Returning to menu");
            }
        }
    }

    //Vi skal nok have en currentUser på en eller anden måde
    //Mangler getter på seenMedia, som er private i User klassen
    //User user = new User();

    public void viewSeenMedia() {
        ArrayList<Media> seenMedia = user.getSeenMedia();
        if (seenMedia.isEmpty()) {
            ui.displayMessage("You have not watched anything yet.");
        } else {
            ui.displayMessage("Your seen media: ");
            for (Media media : seenMedia) {
                ui.displayMessage(media.getTitle());
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
                ui.displayMessage(media.getTitle());
            }
        }
    }
    public void startUp(){
        ArrayList<Show> shows = io.readShowsFromFile("src/main/java/100bedsteserier.txt");
        ArrayList<Movie> movies = io.readMoviesFromFile("src/main/java/100bedstefilm.txt");
        allMedia.addAll(shows);
        allMedia.addAll(movies);
    }
}

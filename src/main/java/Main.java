import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        FileIO fileHandler = new FileIO();
        TextUI textUI = new TextUI();
        StartMenu startMenu = new StartMenu();
        MainMenu mainMenu = new MainMenu();
        User currentUser = null;

        startMenu.start();
        boolean loggedIn = false;
        while(!loggedIn) {
            String choice = textUI.getInput("Please enter 1 to login or 2 to register: ");
            if (choice.equals("1")) {
                currentUser = startMenu.login();
                if(currentUser != null) loggedIn = true;
            } else if (choice.equals("2")) {
                startMenu.createUser();
            }
        }
        if(currentUser != null && loggedIn){
            mainMenu.startUp();
            while(true) {
                String choice = textUI.getInput("Press any of the keys to access content\n" +
                        "1. Search By Name\n" +
                        "2. Search By Categorie\n" +
                        "3. View Seen\n" +
                        "4. View Saved");
                switch (choice) {
                    case "1":
                        mainMenu.searchByName(textUI.getInput("Please enter the name of your desired movie"), currentUser);
                        break;
                    case "2":
                        mainMenu.searchByCategories(textUI.getInput("Please enter the categories of your desired movie"), currentUser);
                        break;
                    case "3":
                        mainMenu.viewSeenMedia(currentUser);
                        break;
                    case "4":
                        mainMenu.viewSavedMedia(currentUser);
                        break;
                }
            }
        }
        /*ArrayList<Movie> movies = fileHandler.readMoviesFromFile("src/main/java/100bedstefilm.txt");
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
        }*/
    }
}

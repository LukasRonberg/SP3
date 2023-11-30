import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
    FileIO fileHandler = new FileIO();
        TextUI textUI = new TextUI();
        StartMenu startMenu = new StartMenu();
        MainMenu mainMenu = new MainMenu();
        User currentUser = null;
        GUI gui = new GUI();


        startMenu.createUser(gui.username, gui.password);

        //startMenu.start();
        gui.setUpGUI();

        //startMenu.login(gui.username, gui.password);
        boolean loggedIn = false;
        while(!loggedIn) {
            String choice = textUI.getInput("Please enter 1 to login or 2 to register: ");
            if (choice.equals("1")) {
                //currentUser = startMenu.login();
                if(currentUser != null) loggedIn = true;
            } else if (choice.equals("2")) {
                //startMenu.createUser();
            }
        }
        if(currentUser != null && loggedIn){
            mainMenu.startUp(currentUser);
            while(true) {
                String choice = textUI.getInput("Press any of the keys to access content\n" +
                        "1. Search By Name\n" +
                        "2. Search By Categories\n" +
                        "3. Search By Years\n" +
                        "4. Search By Rating\n" +
                        "5. View Seen\n" +
                        "6. View Saved\n" +
                        "7. Quit");
                switch (choice) {
                    case "1":
                        mainMenu.searchByName(textUI.getInput("Please enter the name of your desired movie"));
                        break;
                    case "2":
                        mainMenu.searchByCategories(textUI.getInput("Please enter the categories of your desired movie"));
                        break;
                    case "3":
                        mainMenu.searchByYear(textUI.getInput("Please enter the year(s) of your desired movie"));
                        break;
                    case "4":
                        //TODO Eksistere som en Double, skal have den til, at fungere iblandt de andre Strings
                        //Double ratingChoice = textUI.getInput("Press any of the keys to access content\n" +
                        boolean accepted = false;
                        while(!accepted){
                            try{
                                mainMenu.searchByRating((Double.parseDouble(textUI.getInput("Please enter the Rating of your desired movie to find similar rated movie(s)"))));
                                accepted = true;
                            } catch (NumberFormatException e){
                                textUI.displayMessage("Not a valid input please try again: ");
                            }
                        }
                        break;
                    case "5":
                        mainMenu.viewSeenMedia();
                        break;
                    case "6":
                        mainMenu.viewSavedMedia();
                        break;
                    case "7":
                        System.out.println("Thank you for using our streaming website, the application will now close.");
                        fileHandler.saveUserData(fileHandler.readUserData("src/main/java/userdata.txt"), currentUser);
                        System.exit(0);
                        break;
                }
            }
        }
        /*ArrayList<Movie> movies = fileHandler.readMoviesFromFile("src/main/java/100bedstefilm.txt");
        ArrayList<Show> shows = fileHandler.readShowsFromFile("src/main/java/100bedsteserier.txt");

        //Display all Movies
        System.out.println("\n Movies:");
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

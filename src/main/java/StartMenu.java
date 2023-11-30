import java.util.ArrayList;
import java.util.HashSet;

public class StartMenu {

    private final DBConnector fileHandler = new DBConnector();
    private final TextUI textUI = new TextUI();
    private HashSet<User> userList = new HashSet<User>();

    public void start() {
        userList = fileHandler.readUserData("src/main/java/userdata.txt");
    }

    // TODO: 21-11-2023 Gør således at bruger har minimum antal tegn 
    public void createUser() {
        String username = textUI.getInput("Please enter your username below:");
        String password = textUI.getInput("Please enter your password below:");
        start();
        boolean exists = false;
        if(userList  == null) {
            fileHandler.saveUserData(null,new User(username, password, new ArrayList<Media>(), new ArrayList<Media>()));
            textUI.displayMessage("Congratulations your user has been created!");
        }
        else if(userList.isEmpty()){
            userList.add(new User(username, password, new ArrayList<Media>(), new ArrayList<Media>()));
            fileHandler.saveUserData(userList);
            textUI.displayMessage("Congratulations your user has been created!");
            return;
        } else {

            for (User user : userList) {
                //if (exists) break;
                if (user.Username.equals(username)) {
                    textUI.displayMessage("Username already exists!");
                    exists = true;
                    return;
                }

            }
            userList.add(new User(username, password, new ArrayList<Media>(), new ArrayList<Media>()));
            fileHandler.saveUserData(userList);
            textUI.displayMessage("Congratulations your user has been created!");

        }

    }

    public User login() {
        String username = textUI.getInput("Please enter your username below:");
        String password = textUI.getInput("Please enter your password below:");

        for (User user : userList) {
            if (username.equals(user.Username) && password.equals(user.Password)) {
                return user;
            }
        }

        textUI.displayMessage("The information you've entered isn't correct");
        return null;
    }
}





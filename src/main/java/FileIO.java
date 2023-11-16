import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class FileIO {

    public ArrayList<Show> readShowsFromFile (String path){
        return null;
    }
    public ArrayList<Movie> readMoviesFromFile (String path){
        return null;
    }



    public HashSet<User> readUserData(String path) {
        ArrayList<String> data = new ArrayList<>();
        //instantier File
        File file = new File(path);

        try {
            Scanner scan = new Scanner(file);
            scan.nextLine(); //Skip header
            while (scan.hasNextLine()) {
                String s = scan.nextLine();// Hele linjen vil stå i én string   ==>  "Egon, 200"
                data.add(s);
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

        return data;
    }

    public void saveUserData(HashSet<User> users) {
        try {
            FileWriter writer = new FileWriter("src/data.txt");
            writer.write("Name,Balance" + "\n");
            for (User c : users) {
                String textTosave = c.getName() + "," + c.getPassword();
                writer.write(textTosave + "\n");//Egon,5200
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("noget gik galt ved skrivning til fil");
        }

    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.*;

public class FileIO {

    public ArrayList<Show> readShowsFromFile (String path)
    {
        ArrayList<Show> data = new ArrayList<>();
        //instantier File
        File file = new File(path);

        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String s = scan.nextLine();// Hele linjen vil stå i én string   ==>  "Egon, 200
                var stringSplit = s.split(";");
                data.add(new Show(stringSplit[0],
                        stringSplit[1].trim(),
                        getStringList(stringSplit[2]),
                        Double.parseDouble(stringSplit[3].trim().replace(',','.')),
                        getStringList(stringSplit[4])
                        ));
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

        return data;

    }
    public ArrayList<Movie> readMoviesFromFile (String path)
    {
        ArrayList<Movie> data = new ArrayList<>();
        //instantier File
        File file = new File(path);

        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String s = scan.nextLine();// Hele linjen vil stå i én string   ==>  "Egon, 200
                var stringSplit = s.split(";");
                data.add(new Movie(stringSplit[0],
                        stringSplit[1].trim(),
                        getStringList(stringSplit[2]),
                        Double.parseDouble(stringSplit[3].trim().replace(',','.'))
                ));
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

        return data;

    }

    private ArrayList<String> getStringList(String input){
        /*ArrayList<String> returnList = new ArrayList<>();
        var newString = input.replace('[', ' ');
        newString = newString.replace(']', ' ');
        newString = newString.trim();
        var newStringArray = newString.split(",");
        returnList.addAll(Arrays.asList(newStringArray));

        return returnList;*/

        String cleanedString = input.replaceAll("[\\[\\]]", "").trim();
        List<String> stringList = Arrays.asList(cleanedString.split("\\s*,\\s*"));
        return new ArrayList<>(stringList);

    }

    private ArrayList<Media> getList(String input){

        return null;
    }

/*
    public HashSet<User> readUserData(String path) {
        HashSet<User> data = new HashSet<>();
        //instantier File
        File file = new File(path);

        try {
            Scanner scan = new Scanner(file);
            //scan.nextLine(); //Skip header
            while (scan.hasNextLine()) {
                String s = scan.nextLine();// Hele linjen vil stå i én string   ==>  "Egon, 200"
                var splitString = s.split(";");
                // TODO: 20-11-2023 Opsæt user ved hjælp af Mikkels arbejde 
                data.add(new User(splitString[0],
                        splitString[1],
                        new ArrayList<Media>(Arrays.asList(splitString[2])),
                        new ArrayList<Media>(Arrays.asList(splitString[3]))
                ));
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

        return data;
        //return null;
    }

    public void saveUserData(HashSet<User> users) {
        try {
            FileWriter writer = new FileWriter("src/userdata.txt");
            for (User user : users) {
                writer.write(user.Username + ";"+ user.Password + ";" + user.getSavedMedia() + ";" + user.getSeenMedia() + ";");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("noget gik galt ved skrivning til fil");
        }
    }

    public void appendSingleUser(User user){
        try {
            FileWriter writer = new FileWriter("src/userdata.txt");

            writer.write(user.Username + ";"+ user.Password + ";" + user.getSavedMedia() + ";" + user.getSeenMedia() + ";");

            writer.close();
        } catch (IOException e) {
            System.out.println("noget gik galt ved skrivning til fil");
        }
    }*/
}

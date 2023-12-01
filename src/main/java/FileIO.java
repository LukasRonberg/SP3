import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class FileIO implements IO {

    @Override
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
    @Override
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


    private ArrayList<Media> splitCreateAndAdd(String[] toSplit) {
        //System.out.println(Arrays.toString(toSplit));
        ArrayList<Media> media = new ArrayList<>();
        if (toSplit.length > 1) {
            for (var split : toSplit) {
                //var seenSplit2 = split.substring(1,split.length()-1).split(". ");
                String[] seenSplit2 = split.substring(1).split("\\. ");
                if(seenSplit2.length > 1){

                    //System.out.println(seenSplit2.length);
                    if (seenSplit2.length < 6) {
                        //System.out.println(seenSplit2.length);
                        //System.out.println(Arrays.toString(seenSplit2));
                        //System.out.println(split);
                        //if(seenSplit2.length == 3) {
                        media.add(new Movie(seenSplit2[0],
                                seenSplit2[1].trim(),
                                getStringList(seenSplit2[2]),
                                Double.parseDouble(seenSplit2[3].trim().replace(',', '.'))));
                        //}
                    } else {
                        //System.out.println(seenSplit2[1]);
                        //var seenSplit2 = split.split(", ");
                        media.add(new Show(seenSplit2[0],
                                seenSplit2[1].trim(),
                                getStringList(seenSplit2[2]+", "+ seenSplit2[3]+ ", " +seenSplit2[4]),
                                Double.parseDouble(seenSplit2[5].trim().replace(',', '.')),
                                getStringList(seenSplit2[6])
                        ));
                    }
                }
            }
        }
        return media;
    }
@Override
    public HashSet<User> readUserData(String path) {
        HashSet<User> data = new HashSet<>();
        //instantier File
        File file = new File(path);

        try {
            Scanner scan = new Scanner(file);
            //scan.nextLine(); //Skip header
            while (scan.hasNextLine()) {
                String s = scan.nextLine();// Hele linjen vil stå i én string   ==>  "Egon, 200"
                if(s.isEmpty()) break;
                var splitString = s.split(";");
                var seenSplit = splitString[2].split(":");

                var savedSplit = splitString[3].split(":");
                // TODO: 20-11-2023 Opsæt user ved hjælp af Mikkels arbejde
                data.add(new User(splitString[0],
                        splitString[1],
                        //new ,
                        splitCreateAndAdd(savedSplit),
                        splitCreateAndAdd(seenSplit)
                        //new ArrayList<Media>(/*Arrays.asList(splitString[2])*/),
                        //new ArrayList<Media>(/*Arrays.asList(splitString[3])*/)
                ));
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

        return data;
        //return null;
    }
@Override
    public void saveUserData(HashSet<User> users) {
        try {
            FileWriter writer = new FileWriter("src/main/java/userdata.txt");
            for (User user : users) {
                writer.write(user.Username + ";"+ user.Password + ";" + user.getSavedMedia() + ";" + user.getSeenMedia() + ";\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("noget gik galt ved skrivning til fil");
        }
    }
    @Override
    public boolean saveUserData(HashSet<User> users, User currentUser) {
        try {
            FileWriter writer = new FileWriter("src/main/java/userdata.txt");
            for (User user : users) {
                if(user.Username.equals(currentUser.Username)) writer.write(currentUser.Username + ";"+ currentUser.Password + ";" + currentUser.getSavedMedia() + ";" + currentUser.getSeenMedia() + ";\n");
                else writer.write(user.Username + ";"+ user.Password + ";" + user.getSavedMedia() + ";" + user.getSeenMedia() + ";\n");
            }
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println("noget gik galt ved skrivning til fil");
            return false;
        }
    }


    // TODO: 21-11-2023 FIKS DETTE SÅ DET IKKE BLIVER OVERSKREVET
    public void appendSingleUser(User user){
        try {
            //FileWriter writer = new FileWriter("src/main/java/userdata.txt");

            //writer.append(user.Username + ";"+ user.Password + ";" + user.getSavedMedia() + ";" + user.getSeenMedia() + ";\n");
            Files.write(Paths.get("src/main/java/userdata.txt"), (user.Username + ";"+ user.Password + ";" + user.getSavedMedia() + ";" + user.getSeenMedia() + ";\n").getBytes(), StandardOpenOption.APPEND);
            //writer.close();
        } catch (IOException e) {
            System.out.println("noget gik galt ved skrivning til fil");
        }
    }
}

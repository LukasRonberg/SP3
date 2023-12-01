import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class DBConnector implements IO {

    // database URL
    static final String DB_URL = "jdbc:mysql://localhost:3306/streaming";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "OlPR!?Qgh768KGmn!?Qw4poQgcvx7890!?";
    Connection conn = null;

    public void StartDBConnection() {

        //Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);


        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Show> readShowsFromFile(String path) {
        if(conn == null) StartDBConnection();
        ArrayList<Show> mediaList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name,year,genre, rating, seasonAndEpisode FROM streaming.shows");

            while (rs.next()) {
                String name = rs.getString("name");
                String year = rs.getString("year");
                String genre = rs.getString("genre");
                Double rating = rs.getDouble("rating");
                String seasonAndEpisode = rs.getString("seasonAndEpisode");
                ArrayList<String> aList = new ArrayList<String>(Arrays.asList(genre.split(". ")));
                ArrayList<String> bList = new ArrayList<>(Arrays.asList(seasonAndEpisode.split(". ")));//rs.getString("seasonAndEpisode");
                // TODO: 30/11/2023 Skal også læse gemte og sete medier
                Show newUser = new Show(name,year+"", aList,rating,bList);
                mediaList.add(newUser);
                //System.out.println(lastName + "\n");

            }
            //conn.close(); skulle være ude for while loopet, for ellers lukker den connection hver gang loopet kører
            //conn.close();
            stmt.close();
            rs.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }

        return mediaList;
        //return null;
    }

    @Override
    public ArrayList<Movie> readMoviesFromFile(String path) {
        if(conn == null) StartDBConnection();
        ArrayList<Movie> mediaList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name,year,genre, rating FROM streaming.movies");

            while (rs.next()) {
                String name = rs.getString("name");
                int year = rs.getInt("year");
                String genre = rs.getString("genre");
                Double rating = rs.getDouble("rating");
                ArrayList<String> aList = new ArrayList<String>(Arrays.asList(genre.split(". ")));
                // TODO: 30/11/2023 Skal også læse gemte og sete medier
                Movie newUser = new Movie(name,year+"", aList,rating);
                mediaList.add(newUser);
                //System.out.println(lastName + "\n");

            }
            //conn.close(); skulle være ude for while loopet, for ellers lukker den connection hver gang loopet kører
            //conn.close();
            stmt.close();
            rs.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }

        return mediaList;
    }

    @Override
    public HashSet<User> readUserData(String path) {
        if(conn == null) StartDBConnection();
        HashSet<User> userList = new HashSet<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT userID,name,password FROM streaming.users");

            while (rs.next()) {
                int userID = rs.getInt("userID");
                String password = rs.getString("password");
                String username = rs.getString("name");
                // TODO: 30/11/2023 Skal også læse gemte og sete medier


                ArrayList<Media> seenMedia = new ArrayList<Media>();
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT users.userID, \n" +
                        "       watched_media.movieID AS watchedMovieID, \n" +
                        "       movies.name AS movieName,\n" +
                        "       movies.year AS movieYear,\n" +
                        "       movies.genre AS movieGenre,\n" +
                        "       movies.rating AS movieRating\n" +
                        "FROM users\n" +
                        "LEFT JOIN watched_media ON users.userID = watched_media.userID\n" +
                        "LEFT JOIN movies ON watched_media.movieID = movies.movieID\n" +
                        "WHERE users.userID = "+userID+";");

                while(resultSet.next()){
                    String movieName = resultSet.getString("movieName");
                    String movieYear = resultSet.getString("movieYear");
                    String movieGenre = resultSet.getString("movieGenre");
                    Double movieRating = resultSet.getDouble("movieRating");
                    if(movieName == null || movieName.isEmpty()) break;
                    ArrayList<String> aList = new ArrayList<String>(Arrays.asList(movieGenre.split(". ")));
                    Movie media = new Movie(movieName,movieYear+"", aList, movieRating);
                    seenMedia.add(media);
                }


                User newUser = new User(username,password, seenMedia,new ArrayList<Media>());
                userList.add(newUser);
                //System.out.println(lastName + "\n");

            }
            //conn.close();
            stmt.close();
            rs.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }

        return userList;
    }

    @Override
    public void saveUserData(HashSet<User> users) {

    }

    @Override
    public boolean saveUserData(HashSet<User> users, User currentUser) {
        Statement stmt = null;
        try{
            String sql = "INSERT INTO streaming.users (name,password) VALUES('" + currentUser.Username + "','" + currentUser.Password + "')";

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            stmt.close();
            //conn.close();

        }catch(SQLIntegrityConstraintViolationException e){
            //System.out.println("Username already taken");
            return false;
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            /*try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }*///end finally try
        }
        return true;
    }
}

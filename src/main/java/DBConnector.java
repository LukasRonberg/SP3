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
        return null;
    }

    @Override
    public ArrayList<Movie> readMoviesFromFile(String path) {
        if(conn == null) StartDBConnection();
        ArrayList<Movie> mediaList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name,year,genre, rating FROM streaming.movie");

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
            conn.close();
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
            ResultSet rs = stmt.executeQuery("SELECT name,password FROM streaming.user");

            while (rs.next()) {
                String password = rs.getString("password");
                String username = rs.getString("name");
                // TODO: 30/11/2023 Skal også læse gemte og sete medier
                User newUser = new User(username,password,new ArrayList<Media>(),new ArrayList<Media>());
                userList.add(newUser);
                //System.out.println(lastName + "\n");

            }
            conn.close();
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
            String sql = "INSERT INTO streaming.user (name,password) VALUES('" + currentUser.Username + "','" + currentUser.Password + "')";

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();

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
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }
        return true;
    }

    public void readData() {

        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT name, population FROM world.city WHERE id BETWEEN 1 and 10";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while(rs.next()){
                //Retrieve by column name


                String name = rs.getString("Name");
                int population = rs.getInt("Population");
                System.out.println(name + ": " + population);

            }
            //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
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
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try



    }

    public int readPopulation(String city) {

        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT population FROM world.city WHERE name = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, city);

            ResultSet rs = stmt.executeQuery();

            //STEP 4: Extract data from result set
            while(rs.next()){
                //Retrieve by column name

                return rs.getInt("Population");

            }
            //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
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
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        return 0;


    }
}

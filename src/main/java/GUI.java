import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GUI {
    StartMenu startMenu;
    MainMenu mainMenu;
    public User currentUser;
    int x;
    int y;

        public void setUpGUI(){
            startMenu = new StartMenu();
            mainMenu = new MainMenu();
            startMenu.start();
            JFrame f=new JFrame();//creating instance of JFrame
            //f.setBounds(0,0,1920, 1080);//x axis, y axis, width, height
            f.setExtendedState(JFrame.MAXIMIZED_BOTH);
            //f.setUndecorated(true);
            //f.setVisible(true);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            x = (int) ((dimension.getWidth() - f.getWidth()) / 2);
            y = (int) ((dimension.getHeight() - f.getHeight()) / 2);

            JButton loginBtn=new JButton("Login");//creating instance of JButton
            JButton registerBtn=new JButton("Register");//creating instance of JButton

            loginBtn.setBounds(x-250,y-50,500, 80);//x axis, y axis, width, height
            registerBtn.setBounds(x-250,y+50,500, 80);//x axis, y axis, width, height

            loginBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    f.remove(loginBtn);
                    f.remove(registerBtn);
                    //f.revalidate();
                    f.repaint();
                    //startMenu.login();
                    JTextField tf=new JTextField();
                    tf.setBounds(x-250,y-50,500, 80);
                    JTextField tf1=new JTextField();
                    tf1.setBounds(x-250,y+50,500, 80);
                    JButton loginBtn=new JButton("Login");//creating instance of JButton
                    loginBtn.setBounds(x-250,y+150,500, 80);
                    loginBtn.addActionListener(e1 -> {
                        currentUser = startMenu.login(tf.getText(),tf1.getText());
                        if(currentUser != null){
                            f.remove(tf);
                            f.remove(tf1);
                            f.remove(loginBtn);

                            //f.add(tf);
                            //f.add(tf1);
                            //f.add(loginBtn);
                            f.repaint();
                            mainMenu.startUp(currentUser);
                            GUIHomeScreen(f);
                        } else{
                            JOptionPane.showMessageDialog(f, "My Goodness, The information provided is wrong!");
                        }

            });
                    f.add(tf);
                    f.add(tf1);
                    f.add(loginBtn);
                }

            });

            registerBtn.addActionListener(e -> {
                f.remove(loginBtn);
                f.remove(registerBtn);
                //f.revalidate();
                f.repaint();
                //startMenu.login();
                JTextField tf=new JTextField();
                tf.setBounds(x-250,y-50,500, 80);
                JTextField tf1=new JTextField();
                tf1.setBounds(x-250,y+50,500, 80);
                JButton loginBtn1 =new JButton("Register");//creating instance of JButton
                loginBtn1.setBounds(x-250,y+150,500, 80);

                loginBtn1.addActionListener(e12 -> {
                    if(startMenu.createUser(tf.getText(),tf1.getText())){
                        f.remove(tf);
                        f.remove(tf1);
                        f.remove(loginBtn1);
                        f.add(loginBtn1);
                        f.add(registerBtn);
                        f.repaint();
                    }
                    else {
                        JOptionPane.showMessageDialog(f, "Oh my lord, The username provided was already taken!");
                    }

                });

                f.add(tf);
                f.add(tf1);
                f.add(loginBtn1);
            });

            f.add(loginBtn);//adding button in JFrame
            f.add(registerBtn);//adding button in JFrame

            f.setLayout(null);//using no layout managers
            f.setVisible(true);//making the frame visible

        }


        private void GUIHomeScreen(JFrame frame){
            JButton searchByNameBtn=new JButton("searchByNameBtn");//creating instance of JButton
            JButton searchByCategoriesBtn=new JButton("searchByCategoriesBtn");//creating instance of JButton
            JButton searchByYearsBtn=new JButton("searchByYearsBtn");//creating instance of JButton
            JButton searchByRatingBtn=new JButton("searchByRatingBtn");//creating instance of JButton
            JButton viewSeen=new JButton("viewSeen");//creating instance of JButton
            JButton viewSaved=new JButton("viewSaved");//creating instance of JButton

            searchByNameBtn.setBounds(x-900,y-50,250, 80);//x axis, y axis, width, height
            searchByCategoriesBtn.setBounds(x-600,y-50,250, 80);//x axis, y axis, width, height
            searchByYearsBtn.setBounds(x-300,y-50,250, 80);//x axis, y axis, width, height
            searchByRatingBtn.setBounds(x,y-50,250, 80);//x axis, y axis, width, height
            viewSeen.setBounds(x+300,y-50,250, 80);//x axis, y axis, width, height
            viewSaved.setBounds(x+600,y-50,250, 80);//x axis, y axis, width, height

            searchByNameBtn.addActionListener(e1 -> {
                frame.remove(searchByNameBtn);
                frame.remove(searchByCategoriesBtn);
                frame.remove(searchByYearsBtn);
                frame.remove(searchByRatingBtn);
                frame.remove(viewSeen);
                frame.remove(viewSaved);
                frame.repaint();
                JTextField tf1=new JTextField();
                tf1.setBounds(x-250,y+50,500, 80);
                JButton searchBtn=new JButton("Search");//creating instance of JButton
                searchBtn.setBounds(x-250,y+150,500, 80);
                searchBtn.addActionListener(e2 -> {
                    ArrayList<Media> media = mainMenu.searchByName(tf1.getText());

                    for (Media m: media) {
                        //int random = Math.random();
                        double a = Math.random()*(400-200+1)+200;
                        var mediaBtn = new JButton(m.Title);//creating instance of JButton
                        mediaBtn.setBounds(x-(int)a,y-150,500, 80);
                        frame.add(mediaBtn);
                    }
                    frame.repaint();
                });
                frame.add(tf1);
                frame.add(searchBtn);
            });

            searchByCategoriesBtn.addActionListener(e1 -> {

            });

            searchByYearsBtn.addActionListener(e1 -> {

            });

            searchByRatingBtn.addActionListener(e1 -> {

            });

            viewSeen.addActionListener(e1 -> {
                mainMenu.viewSeenMedia();
            });

            viewSaved.addActionListener(e1 -> {

            });

            frame.add(searchByNameBtn);
            frame.add(searchByCategoriesBtn);
            frame.add(searchByYearsBtn);
            frame.add(searchByRatingBtn);
            frame.add(viewSeen);
            frame.add(viewSaved);

            frame.repaint();
        }
}

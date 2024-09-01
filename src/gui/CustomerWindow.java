package gui;

import api.AccommodationsInitialization;
import api.Customer;
import api.ReviewsInitialization;
import api.UsersInitialization;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CustomerWindow extends JFrame {
    JButton searchButton,viewButton, LogOut;
    JTextArea welcome;
    JLabel image;
    int xcounter = 20, ycounter = 10;

    public CustomerWindow(Customer user, UsersInitialization users, AccommodationsInitialization accommodations, ReviewsInitialization reviews) {
        welcome=new JTextArea("Welcome "+user.getName());
        BuildUI();

        //LogOut
        LogOut.addActionListener(e -> {
            new LoginForm(users,accommodations,reviews);
            dispose();
        });
        //View all Accommodations
        viewButton.addActionListener(e -> {
            new ReviewsDashboard(user,users,accommodations,reviews);
            dispose();
        });
        //Search Accommodations
        searchButton.addActionListener(e -> {
            new SearchWindow2(user,users,accommodations,reviews);
            dispose();
        });

    }

    private void BuildUI(){
        //-----logo--------
        ImageIcon icon;
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/Logo.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        double dim = (0.25 * GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight());
        Image img2 = null;
        if (img!=null) {
            img2 = img.getScaledInstance((int) dim, (int) dim, Image.SCALE_SMOOTH);
        }
        icon = new ImageIcon(img2);
        //----------------
        //-----user profile----
        ImageIcon icon2;
        BufferedImage img3 = null;
        try {
            img3 = ImageIO.read(new File("src/blank-profile-picture.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        double dim2 = (0.03 * GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight());
        Image img4 = null;
        if (img3!=null) {
            img4 = img3.getScaledInstance((int) dim2, (int) dim2, Image.SCALE_SMOOTH);
        }
        icon2 = new ImageIcon(img4);
        image = new JLabel();
        image.setIcon(icon2);
        image.setBounds(xcounter+720, ycounter, 50, 50);
        add(image);
        //---------------------

        //Buttons
        viewButton = new JButton("View all reviews");
        viewButton.setFont(new Font("Cambria", Font.BOLD, 15));
        viewButton.setBackground(new Color(158, 213, 197));
        viewButton.setSize(220, 30);
        viewButton.setLocation(xcounter+270, ycounter+250); //290, 260
        add(viewButton);

        searchButton = new JButton("Search Accommodation");
        searchButton.setFont(new Font("Cambria", Font.BOLD, 15));
        searchButton.setBackground(new Color(158, 213, 197));
        searchButton.setSize(220, 30);
        searchButton.setLocation(xcounter+270, ycounter+300); //290, 310
        add(searchButton);

        LogOut = new JButton("Sign Out");
        LogOut.setFont(new Font("Cambria", Font.BOLD, 15));
        LogOut.setBackground(new Color(158, 213, 197));
        LogOut.setSize(100, 30);
        LogOut.setLocation(xcounter+330, ycounter+650); //350, 660
        add(LogOut);

        setTitle("Customer Window");
        setIconImage(icon.getImage());

        setBounds(300, 90, 800, 800);
        setLayout(null);

        setResizable(false);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(222, 245, 229));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

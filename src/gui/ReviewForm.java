package gui;

import api.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class ReviewForm extends JFrame {
    JButton submit,cancel;
    JLabel title,comment_label,stars_label,image;
    JTextArea textfield;
    JSlider bar;
    int xcounter = 20, ycounter = 10;

    public ReviewForm(Customer customer,Accommodation accommodation,UsersInitialization users,AccommodationsInitialization accommodations,ReviewsInitialization reviews){
        BuildUI();

        submit.addActionListener(e -> {
            submitButton(accommodation,customer,users,accommodations,reviews);
        });

        cancel.addActionListener(e -> {
            new ShowAccommodationForCustomer(accommodation, customer,users,accommodations,reviews);
            dispose();
        });

    }

    private void submitButton(Accommodation accommodation,Customer customer,UsersInitialization users,AccommodationsInitialization accommodations,ReviewsInitialization reviews){

        if(!textfield.getText().equals("")){
            String text = textfield.getText();
            int score = Integer.parseInt(String.valueOf(bar.getValue()));
            Date date= new Date();
            long time=date.getTime();
            Timestamp ts = new Timestamp(time);

            Review r = new Review(customer,accommodation,text,score,ts);
            reviews.addNewReview(r);
            accommodations.updateRating(accommodation,score);

            new ShowAccommodationForCustomer(accommodation,customer,users,accommodations,reviews);
            dispose();
        }
    }

    private void BuildUI(){
        ImageIcon icon;
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/Logo.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        icon = new ImageIcon(img);

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

        title = new JLabel("Review Form");
        title.setFont(new Font("Cambria", Font.BOLD, 30));
        title.setSize(190, 35);
        title.setLocation(xcounter+280, ycounter+10);
        add(title);

        comment_label=new JLabel("What do you think about our accommodation?");
        comment_label.setFont(new Font("Cambria", Font.BOLD, 15));
        comment_label.setSize(320, 20);
        comment_label.setLocation(xcounter+220, ycounter+100);
        add(comment_label);

        textfield = new JTextArea();
        textfield.setToolTipText("Max characters: 500");
        textfield.setDocument(new JTextFieldLimit(500));
        textfield.setLineWrap(true);
        textfield.setWrapStyleWord(true);
        textfield.setFont(new Font("Cambria", Font.PLAIN, 15));
        textfield.setSize(450, 180);
        textfield.setLocation(xcounter+155, ycounter+140);
        add(textfield);

        stars_label=new JLabel("Stars:");
        stars_label.setFont(new Font("Cambria", Font.BOLD, 15));
        stars_label.setSize(50, 20);
        stars_label.setLocation(xcounter+355, ycounter+400);
        add(stars_label);

        bar = new JSlider();
        bar.setFont(new Font("Cambria", Font.BOLD, 15));
        bar.setBackground(new Color(222, 245, 229));
        bar.setSize(200, 50);
        bar.setLocation(xcounter+280, ycounter+420);
        bar.setMaximum(5);
        bar.setMinimum(1);
        bar.setMinorTickSpacing(1);
        bar.setMajorTickSpacing(1);
        bar.setSnapToTicks(true);
        bar.setPaintTrack(true);
        bar.setPaintLabels(true);
        bar.setPaintTicks(true);
        add(bar);

        //Buttons:
        submit=new JButton("Submit");
        submit.setFont(new Font("Cambria", Font.BOLD, 15));
        submit.setSize(100, 30);
        submit.setBackground(new Color(158, 213, 197));
        submit.setLocation(xcounter+600, ycounter+650);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Cambria", Font.BOLD, 15));
        cancel.setBackground(new Color(158, 213, 197));
        cancel.setSize(100, 30);
        cancel.setLocation(xcounter+50, ycounter+650);
        add(cancel);

        setTitle("Review Form");
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

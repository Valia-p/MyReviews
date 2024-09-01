package gui;
import api.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ShowAccommodationForCustomer extends JFrame {
    JButton add,back,delete,process;
    JLabel title, name,type, location,description,luxuries,reviewsn,mo;
    JLabel squareSize,floor,constructionYear,rooms,suites;
    JLabel breakfast,pool,cinemaRoom,gym,privatePool;
    JList luxList;
    String[] lux;
    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter sorter;
    private JScrollPane scroll;
    JLabel image;
    int xcounter = 20, ycounter = 10;

    public ShowAccommodationForCustomer(Accommodation accommodation, Customer customer, UsersInitialization users, AccommodationsInitialization accommodations, ReviewsInitialization reviews ){
        String[] columnNames = {"Username","Comment","Stars","Date"};
        model = new DefaultTableModel(reviews.getAccommodationReviews(accommodation), columnNames);
        lux =accommodation.getLuxuries().AvailableLuxuries(accommodation);
        BuildUI(accommodation);

        //Back Button
        back.addActionListener(e -> {
            new SearchWindow2(customer, users, accommodations,reviews);
            dispose();
        });

        //Add Review Button
        add.addActionListener(e -> {
            if(reviews.ApproveReview(customer,accommodation)) {
                new ReviewForm(customer, accommodation, users, accommodations, reviews);
                dispose();
            }
            else{
                //message that they have already made a review to this accommodation
            }
        });

        //Delete
        delete.addActionListener(e -> {
            int row= table.getSelectedRow();
            if(row>=0) {
                if (customer.getUsername().equals(table.getValueAt(row, 0))) {
                    Review r = reviews.findReview(customer.getUsername(), accommodation.getName());
                    if (r != null) {
                        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this review? ");
                        if (result == 0) {
                            reviews.deleteReview(r);
                            Accommodation a = accommodations.findAccommodation(accommodation.getName());
                            accommodations.UpdateAfterReviewDelete(a, table.getValueAt(row, 2).toString());
                            model.setDataVector(reviews.getAccommodationReviews(accommodation), columnNames);
                        }
                    }
                }
            }
        });

        process.addActionListener(e -> {
            int row= table.getSelectedRow();
            if(row>=0) {
                Review r = reviews.findReview(customer.getUsername(), accommodation.getName());
                if (r != null) {
                    new ProcessReview(customer, r, accommodations, users, reviews);
                    dispose();
                }
            }
        });
    }


    private void BuildUI(Accommodation accommodation){
        //------------window pic----------
        ImageIcon icon;
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/Logo.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        icon = new ImageIcon(img);
        //---------------------
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

        title = new JLabel("Information of Accommodation");
        title.setFont(new Font("Cambria", Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        title.setSize(445, 35);
        title.setLocation(xcounter+157, ycounter+10); //250, 20
        add(title);

        //Information
        name=new JLabel("Name: " + accommodation.getName());
        name.setFont(new Font("Cambria", Font.BOLD, 15));
        name.setSize(370, 20);
        name.setLocation(xcounter+40, ycounter+100);
        add(name);

        type = new JLabel("Type: " + accommodation.getType());
        type.setFont(new Font("Cambria", Font.BOLD, 15));
        type.setSize(300, 20);
        type.setLocation(xcounter+380, ycounter+100);
        add(type);

        location = new JLabel("Location: " + accommodation.getLocation().getAddress() +", " + accommodation.getLocation().getCity() +
                ", " + accommodation.getLocation().getPostcode());
        location.setFont(new Font("Cambria", Font.BOLD, 15));
        location.setToolTipText("Address, City, Postcode");
        location.setSize(700, 20);
        location.setLocation(xcounter+40, ycounter+130);
        add(location);

        description = new JLabel("Description : " + accommodation.getDescription());
        description.setFont(new Font("Cambria", Font.BOLD, 15));
        description.setSize(680, 20);
        description.setLocation(xcounter+40, ycounter+160);
        add(description);

        //Luxuries List
        luxuries=new JLabel("Luxuries: ");
        luxuries.setFont(new Font("Cambria", Font.BOLD, 15));
        luxuries.setSize(70, 20);
        luxuries.setLocation(xcounter+40, ycounter+190);
        add(luxuries);

        JScrollPane scrollPane = new JScrollPane();
        luxList = new JList(lux);
        luxList.setEnabled(false);
        scrollPane.setViewportView(luxList);
        scrollPane.setFont(new Font("Cambria", Font.BOLD, 15));
        scrollPane.setSize(270, 40);
        scrollPane.setLocation(xcounter+110, ycounter+190);
        add(scrollPane);

        if(accommodation instanceof Apartment){
            squareSize = new JLabel("Square Size: " + ((Apartment) accommodation).getSquareSize());
            squareSize.setFont(new Font("Cambria", Font.BOLD, 15));
            squareSize.setSize(150, 20);
            squareSize.setLocation(xcounter+40, ycounter+240);
            add(squareSize);

            floor = new JLabel("Floors: " + ((Apartment) accommodation).getFloor());
            floor.setFont(new Font("Cambria", Font.BOLD, 15));
            floor.setSize(150, 20);
            floor.setLocation(xcounter+380, ycounter+240);
            add(floor);

            constructionYear = new JLabel("Construction Year: "+ ((Apartment) accommodation).getConstructionYear());
            constructionYear.setFont(new Font("Cambria", Font.BOLD, 15));
            constructionYear.setSize(200, 20);
            constructionYear.setLocation(xcounter+40, ycounter+270);
            add(constructionYear);
        }
        else if(accommodation instanceof Hotel){
            rooms = new JLabel("Rooms: "+ ((Hotel) accommodation).getRooms());
            rooms.setFont(new Font("Cambria", Font.BOLD, 15));
            rooms.setSize(150, 20);
            rooms.setLocation(xcounter+40, ycounter+240);
            add(rooms);

            suites = new JLabel("Suites: "+ ((Hotel) accommodation).getSuites());
            suites.setFont(new Font("Cambria", Font.BOLD, 15));
            suites.setSize(150, 20);
            suites.setLocation(xcounter+380, ycounter+240);
            add(suites);

            if (((Hotel) accommodation).getBreakfast()) {
                breakfast = new JLabel("Breakfast: Provided");
            }
            else {
                breakfast = new JLabel("Breakfast: Not Provided");
            }
            breakfast.setFont(new Font("Cambria", Font.BOLD, 15));
            breakfast.setSize(150, 20);
            breakfast.setLocation(xcounter+40, ycounter+270);
            add(breakfast);

            if (((Hotel) accommodation).getPool()) {
                pool = new JLabel("Pool: Provided");
            }
            else {
                pool = new JLabel("Pool: Not Provided");
            }
            pool.setFont(new Font("Cambria", Font.BOLD, 15));
            pool.setSize(150, 20);
            pool.setLocation(xcounter+380, ycounter+270);
            add(pool);
        }
        else if(accommodation instanceof Villa){
            if (((Villa) accommodation).getCinemaRoom()) {
                cinemaRoom = new JLabel("Cinema Room: Provided");
            }
            else {
                cinemaRoom = new JLabel("Cinema Room: Not Provided");
            }
            cinemaRoom.setFont(new Font("Cambria", Font.BOLD, 15));
            cinemaRoom.setSize(200, 20);
            cinemaRoom.setLocation(xcounter+40, ycounter+240);
            add(cinemaRoom);

            if (((Villa) accommodation).getGym()) {
                gym = new JLabel("Gym: Provided");
            }
            else {
                gym = new JLabel("Gym: Not Provided");
            }
            gym.setFont(new Font("Cambria", Font.BOLD, 15));
            gym.setSize(150, 20);
            gym.setLocation(xcounter+380, ycounter+240);
            add(gym);

            if (((Villa) accommodation).getPrivatePool()) {
                privatePool = new JLabel("Private Pool: Provided");
            }
            else {
                privatePool = new JLabel("Private Pool: Not Provided");
            }
            privatePool.setFont(new Font("Cambria", Font.BOLD, 15));
            privatePool.setSize(200, 20);
            privatePool.setLocation(xcounter+40, ycounter+270);
            add(privatePool);
        }

        reviewsn = new JLabel("Total Reviews: " + accommodation.getReviewsNum());
        reviewsn.setFont(new Font("Cambria", Font.BOLD, 15));
        reviewsn.setSize(200, 20);
        reviewsn.setLocation(xcounter+40, ycounter+320);
        add(reviewsn);

        if(accommodation.getReviewsNum()!=0){
            mo = new JLabel("Stars: " + accommodation.getStars()/accommodation.getReviewsNum());
        }
        else{
            mo = new JLabel("Stars: 0");
        }
        mo.setFont(new Font("Cambria", Font.BOLD, 15));
        mo.setSize(70, 20);
        mo.setLocation(xcounter+380, ycounter+320);
        add(mo);

        //Table
        sorter = new TableRowSorter<>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setDefaultEditor(Object.class, null);

        scroll = new JScrollPane(table);
        scroll.setFont(new Font("Cambria", Font.BOLD, 15));
        scroll.setSize(700, 200);
        scroll.setLocation(xcounter+30, ycounter+350);
        add(scroll);

        //Buttons
        delete = new JButton("Delete your review");
        delete.setFont(new Font("Cambria", Font.BOLD, 15));
        delete.setBackground(new Color(158, 213, 197));
        delete.setSize(170, 30);
        delete.setLocation(xcounter+30, ycounter+580);
        add(delete);

        process = new JButton("Modify your review");
        process.setFont(new Font("Cambria", Font.BOLD, 15));
        process.setBackground(new Color(158, 213, 197));
        process.setSize(170, 30);
        process.setLocation(xcounter+295, ycounter+580);
        add(process);

        add=new JButton("Add Review");
        add.setFont(new Font("Cambria", Font.BOLD, 15));
        add.setBackground(new Color(158, 213, 197));
        add.setSize(170, 30);
        add.setLocation(xcounter+560, ycounter+580);
        add(add);

        back=new JButton("Back");
        back.setFont(new Font("Cambria", Font.BOLD, 15));
        back.setBackground(new Color(158, 213, 197));
        back.setSize(100, 30);
        back.setLocation(xcounter+330, ycounter+650);
        add(back);

        setTitle("Accommodation For Customer");
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

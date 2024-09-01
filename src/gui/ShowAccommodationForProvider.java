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

public class ShowAccommodationForProvider extends JFrame {
    JButton cancel,delete,process;
    JLabel image,title,name,type,location,description,luxuries,reviewsn,mo;
    JList luxList;
    JLabel SqS,Fl,Cy,roomsL,suitesL,breakfast,pool,privatePool,gym,cinemaRoom;

    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter sorter;
    private JScrollPane scroll;
    String[] lux;
    int xcounter = 20, ycounter = 10;

    public ShowAccommodationForProvider(Accommodation accommodation,Provider provider,AccommodationsInitialization accommodations,UsersInitialization users,ReviewsInitialization reviews){
        String[] columnNames = {"Username","Comment","Stars","Date"};
        model = new DefaultTableModel(reviews.getAccommodationReviews(accommodation), columnNames);
        lux =accommodation.getLuxuries().AvailableLuxuries(accommodation);
        BuildUI(accommodation);

        cancel.addActionListener(e -> {
            new ProviderWindow(provider,users,accommodations,reviews);
            dispose();
        });

        delete.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + accommodation.getName() + "?");
            if (result == 0) {
                try {
                    reviews.DeleteAllReviews(accommodation);
                    accommodations.deleteAccommodation(accommodation);
                    new ProviderWindow(provider,users,accommodations,reviews);
                    dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        process.addActionListener(e -> {
            new ProcessAccommodation(provider, accommodation, accommodations, users, reviews);
            dispose();
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

        type = new JLabel("Type: "+ accommodation.getType());
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

        description = new JLabel("Description: " + accommodation.getDescription());
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
            SqS = new JLabel("Square Size: " + String.valueOf(((Apartment) accommodation).getSquareSize()));
            SqS.setFont(new Font("Cambria", Font.BOLD, 15));
            SqS.setSize(150, 20);
            SqS.setLocation(xcounter+40, ycounter+240);
            add(SqS);

            Fl = new JLabel("Floors: " + String.valueOf(((Apartment) accommodation).getFloor()));
            Fl.setFont(new Font("Cambria", Font.BOLD, 15));
            Fl.setSize(150, 20);
            Fl.setLocation(xcounter+380, ycounter+240);
            add(Fl);

            Cy = new JLabel("Construction Year: " + String.valueOf(((Apartment) accommodation).getConstructionYear()));
            Cy.setFont(new Font("Cambria", Font.BOLD, 15));
            Cy.setSize(200, 20);
            Cy.setLocation(xcounter+40, ycounter+270);
            add(Cy);
        }
        else if(accommodation instanceof Hotel){
            roomsL = new JLabel("Rooms: " + String.valueOf(((Hotel) accommodation).getRooms()));
            roomsL.setFont(new Font("Cambria", Font.BOLD, 15));
            roomsL.setSize(150, 20);
            roomsL.setLocation(xcounter+40, ycounter+240);
            add(roomsL);

            suitesL = new JLabel("Suites: " + String.valueOf(((Hotel) accommodation).getSuites()));
            suitesL.setFont(new Font("Cambria", Font.BOLD, 15));
            suitesL.setSize(150, 20);
            suitesL.setLocation(xcounter+380, ycounter+240);
            add(suitesL);

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
        delete = new JButton("Delete Accommodation");
        delete.setFont(new Font("Cambria", Font.BOLD, 15));
        delete.setBackground(new Color(158, 213, 197));
        delete.setSize(220, 30);
        delete.setLocation(xcounter+30, ycounter+580);
        add(delete);

        process = new JButton("Process Accommodation");
        process.setFont(new Font("Cambria", Font.BOLD, 15));
        process.setBackground(new Color(158, 213, 197));
        process.setSize(220, 30);
        process.setLocation(xcounter+510, ycounter+580);
        add(process);

        cancel = new JButton("Back");
        cancel.setFont(new Font("Cambria", Font.BOLD, 15));
        cancel.setBackground(new Color(158, 213, 197));
        cancel.setSize(100, 30);
        cancel.setLocation(xcounter+330, ycounter+650);
        add(cancel);

        setTitle("Accommodation For Provider");
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

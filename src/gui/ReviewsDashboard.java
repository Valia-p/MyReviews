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

public class ReviewsDashboard extends JFrame {
    JButton BackButton, LogOut,delete , process ,view;
    JLabel mo;
    JLabel error = new JLabel("");
    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter sorter;
    private JScrollPane scroll;
    JLabel image;
    int xcounter=20, ycounter=10;

    public ReviewsDashboard(Customer customer, UsersInitialization users, AccommodationsInitialization accommodations, ReviewsInitialization reviews ){
        String[] columnNames = {"Name","Type","City","Your Rating","Date"};
        model= new DefaultTableModel(reviews.getCustomerReviews(customer),columnNames);
        BuildUI(reviews,customer);

        //Back
        BackButton.addActionListener(e -> {
            remove(this);
            new CustomerWindow(customer,users,accommodations,reviews);
            this.setVisible(false);
        });

        //LogOut
        LogOut.addActionListener(e -> {
            remove(this);
            new LoginForm(users,accommodations,reviews);
            this.setVisible(false);
        });

        //Delete
        delete.addActionListener(e -> {
            int row= table.getSelectedRow();
            if(row>=0) {
                Review r = reviews.findReview(customer.getUsername(), table.getValueAt(row, 0).toString());
                if(r!=null) {
                    int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this review? ");
                    if (result == 0) {
                        reviews.deleteReview(r);
                        Accommodation a = accommodations.findAccommodation(table.getValueAt(row, 0).toString());
                        accommodations.UpdateAfterReviewDelete(a, table.getValueAt(row, 3).toString());
                        model.setDataVector(reviews.getCustomerReviews(customer), columnNames);
                    }
                }
            }

        });

        process.addActionListener(e -> {
            int row= table.getSelectedRow();
            if(row>=0) {
                Review r = reviews.findReview(customer.getUsername(), table.getValueAt(row, 0).toString());
                if (r != null) {
                    new ProcessReview(customer, r, accommodations, users, reviews);
                    dispose();
                }
            }
        });

        view.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row>=0){
                Accommodation a = accommodations.findAccommodation(table.getValueAt(row,0).toString());
                new ShowAccommodationForCustomer(a,customer,users,accommodations,reviews);
                dispose();
            }
        });
    }


    private void BuildUI(ReviewsInitialization reviews,Customer customer){
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

        //table
        sorter = new TableRowSorter<>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setDefaultEditor(Object.class, null);
        scroll = new JScrollPane(table);
        scroll.setFont(new Font("Cambria", Font.BOLD, 15));
        scroll.setSize(700, 100);
        scroll.setLocation(xcounter+30, ycounter+90);
        if(table.getModel().getRowCount()==0){
            error.setText("You have made zero reviews");
            error.setFont(new Font("Cambria", Font.BOLD, 20));
            error.setForeground(Color.red);
            error.setSize(280, 20);
            error.setLocation(xcounter+260, ycounter+50);
        }
        add(error);
        add(scroll);

        //MO of reviews
        mo = new JLabel("Average reviews score: " + reviews.calculateMO(customer));
        mo.setFont(new Font("Cambria", Font.BOLD, 15));
        mo.setSize(190, 20);
        mo.setLocation(xcounter+290, ycounter+210);
        add(mo);

        //Buttons
        delete = new JButton("Delete Review");
        delete.setFont(new Font("Cambria", Font.BOLD, 15));
        delete.setBackground(new Color(158, 213, 197));
        delete.setSize(200, 30);
        delete.setLocation(xcounter+30, ycounter+250); //410, 260
        add(delete);

        process = new JButton("Process Review");
        process.setFont(new Font("Cambria", Font.BOLD, 15));
        process.setBackground(new Color(158, 213, 197));
        process.setSize(200, 30);
        process.setLocation(xcounter+280, ycounter+250); //410, 260
        add(process);

        view = new JButton("View Accommodation");
        view.setFont(new Font("Cambria", Font.BOLD, 15));
        view.setBackground(new Color(158, 213, 197));
        view.setSize(200, 30);
        view.setLocation(xcounter+530, ycounter+250); //410, 260
        add(view);

        BackButton = new JButton("Back");
        BackButton.setFont(new Font("Cambria", Font.BOLD, 15));
        BackButton.setBackground(new Color(158, 213, 197));
        BackButton.setSize(100, 30);
        BackButton.setLocation(xcounter+30, ycounter+650);
        add(BackButton);

        LogOut = new JButton("Sign Out");
        LogOut.setFont(new Font("Cambria", Font.BOLD, 15));
        LogOut.setBackground(new Color(158, 213, 197));
        LogOut.setSize(100, 30);
        LogOut.setLocation(xcounter+610, ycounter+650);
        add(LogOut);

        setTitle("Reviews Dashboard");
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

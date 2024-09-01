package gui;

import api.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProviderWindow extends JFrame {
    JButton addButton, process, delete,view, logOut;
    JTextField searchField;
    JLabel searchLabel,message,TotalReviews,Mo;
    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter sorter;
    private JScrollPane scroll;
    String[][] rows;
    String tr,mo;
    JLabel image;
    int xcounter = 20, ycounter = 10;

    public ProviderWindow(Provider provider, UsersInitialization users, AccommodationsInitialization accommodations, ReviewsInitialization reviews) {
        String[] columnNames = {"Name","Type","Address","City","Postcode","Average Score","Reviews Number"};
        rows = accommodations.AccommodationList(provider);
        tr = rows[rows.length-1][7];
        mo = rows[rows.length-1][8];
        String[][] copy = new String[rows.length-1][9];
        for(int i=0;i<rows.length-1;i++){
            for(int j=0;j<9;j++) {
                copy[i][j]=rows[i][j];
            }
        }
        model = new DefaultTableModel(copy, columnNames);

        BuildUI();

        addButton.addActionListener(e -> {
            new AddAccommodation(provider, users, accommodations, reviews);
            dispose();
        });

        logOut.addActionListener(e -> {
            new LoginForm(users, accommodations, reviews);
            dispose();
        });

        view.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row >= 0) {
                message.setVisible(false);
                String accommodationName = table.getValueAt(row, 0).toString();
                Accommodation a = accommodations.findAccommodation(accommodationName);
                if (a != null) {
                    new ShowAccommodationForProvider(a,provider,accommodations ,users,reviews);
                    dispose();
                }
            }
            else{
                message.setText("Select the accommodation you want to view");
                message.setVisible(true);
            }
        });

        delete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row >= 0) {
                message.setVisible(false);
                String accommodationName = table.getValueAt(row, 0).toString();
                Accommodation a = accommodations.findAccommodation(accommodationName);
                if (a != null) {
                    int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + a.getName() + "?");
                    if (result == 0) {
                        try {
                            reviews.DeleteAllReviews(a);
                            accommodations.deleteAccommodation(a);
                            rows=accommodations.AccommodationList(provider);
                            String[][] copy1 = new String[rows.length-1][9];
                            for(int i=0;i<rows.length-1;i++){
                                for(int j=0;j<9;j++) {
                                    copy1[i][j]=rows[i][j];
                                }
                            }
                            model.setDataVector(copy1, columnNames);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            else{
                message.setText("Select the accommodation you want to delete");
                message.setVisible(true);
            }
        });

        process.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row >= 0) {
                message.setVisible(false);
                String accommodationName = table.getValueAt(row, 0).toString();
                Accommodation a = accommodations.findAccommodation(accommodationName);
                if (a != null) {
                    message.setVisible(false);
                    new ProcessAccommodation(provider, a, accommodations, users, reviews);
                    dispose();
                }
            }
            else{
                message.setText("Select the accommodation you want to process");
                message.setVisible(true);
            }
        });


        //Search
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(searchField.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                search(searchField.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                search(searchField.getText());
            }
            public void search(String str) {
                if (str.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(str));
                }
            }
        });
    }


    private void BuildUI(){
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

        searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Cambria", Font.BOLD, 15));
        searchLabel.setSize(60, 20);
        searchLabel.setLocation(xcounter+275, ycounter+50); //295, 60
        add(searchLabel);

        searchField = new JTextField();
        searchField.setFont(new Font("Cambria", Font.PLAIN, 15));
        searchField.setSize(150, 20);
        searchField.setLocation(xcounter+335, ycounter+50); //355, 60
        add(searchField);

        //Table
        sorter = new TableRowSorter<>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setDefaultEditor(Object.class, null);
        table.setBackground(new Color(249, 249, 249));
        setLayout(new FlowLayout(FlowLayout.CENTER));

        scroll = new JScrollPane(table);
        scroll.setFont(new Font("Cambria", Font.BOLD, 15));
        scroll.setSize(700, 100);
        scroll.setLocation(xcounter+30, ycounter+80); //50, 90
        add(scroll);

        TotalReviews = new JLabel("Total Reviews : " + tr);
        TotalReviews.setFont(new Font("Cambria", Font.BOLD, 15));
        TotalReviews.setSize(130, 20);
        TotalReviews.setLocation(xcounter+130, ycounter+200); //150, 210
        add(TotalReviews);

        Mo = new JLabel("Average Score : " + mo);
        Mo.setFont(new Font("Cambria", Font.BOLD, 15));
        Mo.setSize(130, 20);
        Mo.setLocation(xcounter+510, ycounter+200); //530, 210
        add(Mo);

        //Buttons
        delete = new JButton("Delete Accommodation");
        delete.setFont(new Font("Cambria", Font.BOLD, 15));
        delete.setBackground(new Color(158, 213, 197));
        delete.setSize(220, 30);
        delete.setLocation(xcounter+140, ycounter+250); //160, 260
        add(delete);

        process = new JButton("Process Accommodation");
        process.setFont(new Font("Cambria", Font.BOLD, 15));
        process.setBackground(new Color(158, 213, 197));
        process.setSize(220, 30);
        process.setLocation(xcounter+390, ycounter+250); //410, 260
        add(process);

        addButton = new JButton("Add Accommodation");
        addButton.setFont(new Font("Cambria", Font.BOLD, 15));
        addButton.setBackground(new Color(158, 213, 197));
        addButton.setSize(220, 30);
        addButton.setLocation(xcounter+140, ycounter+300);
        add(addButton);

        view = new JButton("View Accommodation");
        view.setFont(new Font("Cambria", Font.BOLD, 15));
        view.setBackground(new Color(158, 213, 197));
        view.setSize(220, 30);
        view.setLocation(xcounter+390, ycounter+300);
        add(view);

        logOut = new JButton("Log Out");
        logOut.setFont(new Font("Cambria", Font.BOLD, 15));
        logOut.setBackground(new Color(158, 213, 197));
        logOut.setSize(100, 30);
        logOut.setLocation(xcounter+330, ycounter+650);
        add(logOut);

        //Message
        message = new JLabel("");
        message.setForeground(Color.red);
        message.setFont(new Font("Cambria", Font.BOLD, 20));
        message.setSize(430, 20);
        message.setLocation(xcounter+185, ycounter+475); //165
        add(message);

        setTitle("Provider Window");
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

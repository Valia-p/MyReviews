package gui;
import api.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SearchWindow2  extends JFrame {
    private JTextField searchField;
    private JLabel searchLabel, image;
    private TableModel model;
    private JTable table;
    private TableRowSorter sorter;
    private JScrollPane scroll;
    private JButton returnButton,filters;
    private int xcounter = 20, ycounter = 10;

    public SearchWindow2(Customer customer, UsersInitialization users, AccommodationsInitialization accommodations, ReviewsInitialization reviews) {
        String[] columnNames = {"Name","Type","Address","City","Postcode","Average Score","Reviews Number"};
        model = new DefaultTableModel(accommodations.view(), columnNames);
        BuildUI();

        //Search without filters only text
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

        //Go Back Button
        returnButton.addActionListener(e -> {
            new CustomerWindow(customer, users, accommodations,reviews);
            dispose();
        });

        //To select a row
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int rowIndex = table.getSelectedRow();
                String accommodationName = table.getValueAt(rowIndex,0).toString();
                Accommodation accommodation = accommodations.findAccommodation(accommodationName);
                new ShowAccommodationForCustomer(accommodation,customer,users,accommodations,reviews);
                dispose();
            }

        });

        filters.addActionListener(e -> {
            new FiltersWindow(customer,users,accommodations,reviews);
            dispose();
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
        searchLabel.setLocation(xcounter+250, ycounter+50);
        add(searchLabel);

        searchField = new JTextField();
        searchField.setFont(new Font("Cambria", Font.PLAIN, 15));
        searchField.setSize(200, 20);
        searchField.setLocation(xcounter+310, ycounter+50);
        add(searchField);

        sorter = new TableRowSorter<>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setBackground(new Color(249, 249, 249));
        setLayout(new FlowLayout(FlowLayout.CENTER));

        scroll = new JScrollPane(table);
        scroll.setFont(new Font("Cambria", Font.BOLD, 15));
        scroll.setSize(700, 300);
        scroll.setLocation(xcounter+30, ycounter+100);
        add(scroll);

        //Buttons
        returnButton = new JButton("Back");
        returnButton.setFont(new Font("Cambria", Font.BOLD, 15));
        returnButton.setBackground(new Color(158, 213, 197));
        returnButton.setSize(120, 30);
        returnButton.setLocation(xcounter+320, ycounter+650);
        add(returnButton);

        filters= new JButton("Add filters");
        filters.setFont(new Font("Cambria", Font.BOLD, 15));
        filters.setBackground(new Color(158, 213, 197));
        filters.setSize(120, 30);
        filters.setLocation(xcounter+320, ycounter+450);
        add(filters);

        setTitle("Search Window");
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

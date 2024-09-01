package gui;

import api.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FiltersWindow extends JFrame {
    private JTextField name_txt,address_txt,city_txt;
    private JLabel Name,Type,Address,City,Lux, image;
    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter sorter;
    private JScrollPane scroll;
    private JRadioButton apartment,hotel,villa;
    private ButtonGroup lgroup;
    private JButton back , clear , set;
    private int xcounter = 20, ycounter = 10;
    List<Checkbox> checkboxes = new ArrayList<>();

    public FiltersWindow(Customer customer, UsersInitialization users, AccommodationsInitialization accommodations, ReviewsInitialization reviews){
        String[] columnNames = {"Name","Type","Address","City","Postcode","Average Score","Reviews Number"};
        model = new DefaultTableModel(accommodations.view(), columnNames);

        BuildUI();

        //Set selected filters
        set.addActionListener(e -> {
            String name = name_txt.getText();
            String address = address_txt.getText();
            String city = city_txt.getText();
            String type="";

            if(apartment.isSelected()){
                type="Apartment";
            }
            else if(hotel.isSelected()){
                type="Hotel";
            }
            else if(villa.isSelected()){
                type="Villa";
            }

            int count =0 ;
            boolean luxsSelected = false;
            Boolean[] state = new Boolean[9];
            for(Checkbox ch : checkboxes){
                state[count] = ch.getState();
                if(ch.getState())
                    luxsSelected=true;
                count++;
            }
            if(luxsSelected){
                model.setDataVector(accommodations.searchForLuxuries(state),columnNames);
            }
            else
                model.setDataVector(accommodations.view(), columnNames);
            updateTable(name,address,city,type);

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

        //Clear Button
        clear.addActionListener(e -> {
            name_txt.setText("");
            address_txt.setText("");
            city_txt.setText("");
            lgroup.clearSelection();
            for(Checkbox ch : checkboxes){
                ch.setState(false);
            }
        });

        //Go Back Button
        back.addActionListener(e -> {
            new SearchWindow2(customer,users,accommodations,reviews);
            dispose();
        });

    }

    private void updateTable(String name,String address,String city,String type){
        ArrayList<RowFilter<Object, Object>> andFilters = new ArrayList<>();
        if(!name.equals("")){
            RowFilter<Object,Object> nameFilter = RowFilter.regexFilter(name,table.getColumnModel().getColumnIndex("Name"));
            andFilters.add(nameFilter);
        }
        if(!address.equals("")){
            RowFilter<Object,Object> addressFilter = RowFilter.regexFilter(address,table.getColumnModel().getColumnIndex("Address"));
            andFilters.add(addressFilter);
        }
        if(!city.equals("")){
            RowFilter<Object,Object> cityFilter = RowFilter.regexFilter(city,table.getColumnModel().getColumnIndex("City"));
            andFilters.add(cityFilter);
        }
        if(!type.equals("")){
            RowFilter<Object,Object> typeFilter = RowFilter.regexFilter(type,table.getColumnModel().getColumnIndex("Type"));
            andFilters.add(typeFilter);
        }

        sorter.setRowFilter(RowFilter.andFilter(andFilters));
        table.setRowSorter(sorter);

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

        Name=new JLabel("Name:");
        Name.setFont(new Font("Cambria", Font.BOLD, 15));
        Name.setSize(50, 20);
        Name.setLocation(xcounter+280, ycounter+50);
        add(Name);

        name_txt=new JTextField(15);
        name_txt.setFont(new Font("Cambria", Font.PLAIN, 15));
        name_txt.setSize(150, 20);
        name_txt.setLocation(xcounter+330, ycounter+50);
        add(name_txt);

        Type= new JLabel("Type:");
        Type.setFont(new Font("Cambria", Font.BOLD, 15));
        Type.setSize(50, 20);
        Type.setLocation(xcounter+95, ycounter+80);
        add(Type);

        apartment = new JRadioButton("Apartment",false);
        apartment.setFont(new Font("Cambria", Font.PLAIN, 15));
        apartment.setBackground(new Color(222, 245, 229));
        apartment.setSize(100, 20);
        apartment.setLocation(xcounter+155, ycounter+80);
        add(apartment);

        hotel = new JRadioButton("Hotel",false);
        hotel.setFont(new Font("Cambria", Font.PLAIN, 15));
        hotel.setBackground(new Color(222, 245, 229));
        hotel.setSize(100, 20);
        hotel.setLocation(xcounter+345, ycounter+80);
        add(hotel);

        villa = new JRadioButton("Villa",false);
        villa.setFont(new Font("Cambria", Font.PLAIN, 15));
        villa.setBackground(new Color(222, 245, 229));
        villa.setSize(100, 20);
        villa.setLocation(xcounter+515, ycounter+80);
        add(villa);

        lgroup=new ButtonGroup();
        lgroup.add(apartment);
        lgroup.add(hotel);
        lgroup.add(villa);

        Address = new JLabel("Address:");
        Address.setFont(new Font("Cambria", Font.PLAIN, 15));
        Address.setSize(60, 20);
        Address.setLocation(xcounter+135, ycounter+110);
        add(Address);

        address_txt = new JTextField();
        address_txt.setFont(new Font("Cambria", Font.PLAIN, 15));
        address_txt.setSize(150, 20);
        address_txt.setLocation(xcounter+195, ycounter+110);
        add(address_txt);

        City = new JLabel("City:");
        City.setFont(new Font("Cambria", Font.PLAIN, 15));
        City.setSize(50, 20);
        City.setLocation(xcounter+375, ycounter+110);
        add(City);

        city_txt = new JTextField(15);
        city_txt.setFont(new Font("Cambria", Font.PLAIN, 15));
        city_txt.setSize(150, 20);
        city_txt.setLocation(xcounter+410, ycounter+110);
        add(city_txt);

        Lux = new JLabel("Luxuries:");
        Lux.setFont(new Font("Cambria", Font.BOLD, 15));
        Lux.setSize(70, 20);
        Lux.setLocation(xcounter+95, ycounter+140);
        add(Lux);

        int len=0;
        String labels[] = {"View", "Bathroom", "Laundering", "Entertainment", "Heating", "Internet","Kitchen","Outdoor","Parking"};
        for (int i = 0; i < labels.length; i++) {
            Checkbox checkbox = new Checkbox(labels[i]);
            checkboxes.add(checkbox);
            checkbox.setSize(150, 20);
            if (i<3) {
                checkbox.setLocation(xcounter+180+len, ycounter+140);
            }
            else if (i<6) {
                if (i==3) len = 0;
                checkbox.setLocation(xcounter+180+len, ycounter+160);
            }
            else {
                if (i==6) len=0;
                checkbox.setLocation(xcounter+180+len, ycounter+180);
            }
            add(checkbox);
            len+=150;
        }

        clear = new JButton("Clear Filters");
        clear.setFont(new Font("Cambria", Font.BOLD, 15));
        clear.setBackground(new Color(158, 213, 197));
        clear.setSize(120, 30);
        clear.setLocation(xcounter+95, ycounter+230);
        add(clear);

        set = new JButton("Set Filters");
        set.setFont(new Font("Cambria", Font.BOLD, 15));
        set.setBackground(new Color(158, 213, 197));
        set.setSize(120, 30);
        set.setLocation(xcounter+520, ycounter+230);
        add(set);

        sorter = new TableRowSorter<>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setModel(model);
        table.setBackground(new Color(249, 249, 249));
        setLayout(new FlowLayout(FlowLayout.CENTER));

        scroll = new JScrollPane(table);
        scroll.setFont(new Font("Cambria", Font.BOLD, 15));
        scroll.setSize(700, 150);
        scroll.setLocation(xcounter+30, ycounter+280);
        add(scroll);

        back = new JButton("Back");
        back.setFont(new Font("Cambria", Font.BOLD, 15));
        back.setBackground(new Color(158, 213, 197));
        back.setSize(120, 30);
        back.setLocation(xcounter+315, ycounter+650);
        add(back);

        setTitle("Add Filters");
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

package gui;

import api.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AddAccommodation extends JFrame {
    JLabel image, title;
    JButton addAcc, cancel , clear;
    JLabel roomLabel, suiteLabel, poolLabel, breakfastLabel, squareSizeLabel, floorLabel, constructionYearLabel,
            cinemaRoomLabel, gymLabel, privatePoolLabel;
    JLabel nameLabel, descriptionLabel, type, location, luxuries;
    JLabel viewLabel, bathroomLabel, launderingLabel, entertainmentLabel, heatingAirConditioningLabel, internetLabel, kitchenDiningRoomLabel,
            outdoorLabel, parkingLabel;
    JCheckBox hotel, villa, apartment , breakfast, pool,cinemaRoom, gym, privatePool;
    JTextField view, bathroom, laundering, entertainment, heatingAirConditioning, internet, kitchenDiningRoom, outdoor, parking;
    JTextArea description;
    JTextField name, address, city, postcode, room, suite, squareSize, floor, constructionYear;

    ButtonGroup group;
    int xcounter = 20, ycounter = 10;
    private final JLabel message = new JLabel("");

    public AddAccommodation(Provider provider, UsersInitialization users, AccommodationsInitialization accommodations, ReviewsInitialization reviews ) {
        BuildUI();

        hotel.addActionListener(e -> {
            if (hotel.isSelected()) {
                roomLabel.setEnabled(true);
                room.setEnabled(true);

                suiteLabel.setEnabled(true);
                suite.setEnabled(true);

                breakfastLabel.setEnabled(true);
                breakfast.setEnabled(true);

                poolLabel.setEnabled(true);
                pool.setEnabled(true);
                //
                squareSizeLabel.setEnabled(false);
                squareSize.setEnabled(false);

                floorLabel.setEnabled(false);
                floor.setEnabled(false);

                constructionYearLabel.setEnabled(false);
                constructionYear.setEnabled(false);
                //
                cinemaRoomLabel.setEnabled(false);
                cinemaRoom.setEnabled(false);

                gymLabel.setEnabled(false);
                gym.setEnabled(false);

                privatePoolLabel.setEnabled(false);
                privatePool.setEnabled(false);
            }
        });

        apartment.addActionListener(e -> {
            if (apartment.isSelected()) {
                squareSizeLabel.setEnabled(true);
                squareSize.setEnabled(true);

                floorLabel.setEnabled(true);
                floor.setEnabled(true);

                constructionYearLabel.setEnabled(true);
                constructionYear.setEnabled(true);
                //
                roomLabel.setEnabled(false);
                room.setEnabled(false);

                suiteLabel.setEnabled(false);
                suite.setEnabled(false);

                breakfastLabel.setEnabled(false);
                breakfast.setEnabled(false);

                poolLabel.setEnabled(false);
                pool.setEnabled(false);
                //
                cinemaRoomLabel.setEnabled(false);
                cinemaRoom.setEnabled(false);

                gymLabel.setEnabled(false);
                gym.setEnabled(false);

                privatePoolLabel.setEnabled(false);
                privatePool.setEnabled(false);
            }
        });

        villa.addActionListener(e -> {
            if (villa.isSelected()) {
                cinemaRoomLabel.setEnabled(true);
                cinemaRoom.setEnabled(true);

                gymLabel.setEnabled(true);
                gym.setEnabled(true);

                privatePoolLabel.setEnabled(true);
                privatePool.setEnabled(true);
                //
                roomLabel.setEnabled(false);
                room.setEnabled(false);

                suiteLabel.setEnabled(false);
                suite.setEnabled(false);

                breakfastLabel.setEnabled(false);
                breakfast.setEnabled(false);

                poolLabel.setEnabled(false);
                pool.setEnabled(false);
                //
                squareSizeLabel.setEnabled(false);
                squareSize.setEnabled(false);

                floorLabel.setEnabled(false);
                floor.setEnabled(false);

                constructionYearLabel.setEnabled(false);
                constructionYear.setEnabled(false);
            }
        });

        addAcc.addActionListener(e -> {
            AddBtn(provider,accommodations);
        });

        cancel.addActionListener(e -> {
            new ProviderWindow(provider, users, accommodations, reviews);
            dispose();
        });

        clear.addActionListener(e -> {
            name.setText("");
            address.setText("");
            city.setText("");
            postcode.setText("");
            description.setText("");
            group.clearSelection();
            view.setText("");
            laundering.setText("");
            kitchenDiningRoom.setText("");
            internet.setText("");
            parking.setText("");
            outdoor.setText("");
            bathroom.setText("");
            heatingAirConditioning.setText("");
            entertainment.setText("");
            roomLabel.setEnabled(false);
            room.setEnabled(false);
            suiteLabel.setEnabled(false);
            suite.setEnabled(false);
            breakfastLabel.setEnabled(false);
            breakfast.setEnabled(false);
            poolLabel.setEnabled(false);
            pool.setEnabled(false);
            squareSizeLabel.setEnabled(false);
            squareSize.setEnabled(false);
            floorLabel.setEnabled(false);
            floor.setEnabled(false);
            constructionYearLabel.setEnabled(false);
            constructionYear.setEnabled(false);
            cinemaRoomLabel.setEnabled(false);
            cinemaRoom.setEnabled(false);
            gymLabel.setEnabled(false);
            gym.setEnabled(false);
            privatePoolLabel.setEnabled(false);
            privatePool.setEnabled(false);
        });
    }

    private void AddBtn(Provider provider,AccommodationsInitialization accommodations){
        if(!name.getText().equals("") && !address.getText().equals("") && !city.getText().equals("") && !postcode.getText().equals("") && group.getSelection()!=null){
            message.setVisible(false);
            Location location = new Location(address.getText(), city.getText(), postcode.getText());
            Luxuries luxuries = new Luxuries();

            if (!view.getText().equals("")) {
                luxuries.setSpecificLux(0,view.getText());
            }
            if (!bathroom.getText().equals("")){
                luxuries.setSpecificLux(1,bathroom.getText());
            }
            if (!laundering.getText().equals("")) {
                luxuries.setSpecificLux(2,laundering.getText());
            }
            if (!entertainment.getText().equals("")) {
                luxuries.setSpecificLux(3,entertainment.getText());
            }
            if (!heatingAirConditioning.getText().equals("")) {
                luxuries.setSpecificLux(4,heatingAirConditioning.getText());
            }
            if (!internet.getText().equals("")) {
                luxuries.setSpecificLux(5,internet.getText());
            }
            if (!kitchenDiningRoom.getText().equals("")) {
                luxuries.setSpecificLux(6,kitchenDiningRoom.getText());
            }
            if (!outdoor.getText().equals("")) {
                luxuries.setSpecificLux(7,outdoor.getText());
            }
            if (!parking.getText().equals("")) {
                luxuries.setSpecificLux(8,parking.getText());
            }

            Accommodation acc = new Accommodation();
            if(hotel.isSelected()){
                acc = new Hotel(name.getText(), description.getText(), location, luxuries, provider, 0, 0,Integer.parseInt(room.getText()),Integer.parseInt(suite.getText()),breakfast.isSelected(),pool.isSelected());
            }
            else if(apartment.isSelected()){
                acc = new Apartment(name.getText(), description.getText(), location, luxuries, provider, 0, 0,Integer.parseInt(squareSize.getText()),Integer.parseInt(floor.getText()),Integer.parseInt(constructionYear.getText()));
            }
            else if(villa.isSelected()){
                acc = new Villa(name.getText(), description.getText(), location, luxuries, provider, 0, 0,cinemaRoom.isSelected(),gym.isSelected(),privatePool.isSelected());
            }
            try {
                if (accommodations.addNewAccommodation(acc)) {
                    message.setForeground(Color.green);
                    message.setText("Addition Completed");
                } else {
                    message.setForeground(Color.RED);
                    message.setText("Accommodation already exists");
                }
                message.setVisible(true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else {
            message.setText("All fields are required except from luxuries");
            message.setVisible(true);
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

        title = new JLabel("Add Accommodation");
        title.setFont(new Font("Cambria", Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        title.setSize(300, 35);
        title.setLocation(xcounter+230, ycounter+10); //250, 20
        add(title);

        nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Cambria", Font.BOLD, 15));
        nameLabel.setSize(100, 20);
        nameLabel.setLocation(xcounter+70, ycounter+70);
        add(nameLabel);

        name = new JTextField();
        name.setFont(new Font("Cambria", Font.PLAIN, 15));
        name.setSize(150, 20);
        name.setLocation(xcounter+170, ycounter+70);
        add(name);

        //description
        descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(new Font("Cambria", Font.BOLD, 15));
        descriptionLabel.setSize(100, 20);
        descriptionLabel.setLocation(xcounter+70, ycounter+100);
        add(descriptionLabel);

        description = new JTextArea();
        description.setLineWrap(true);
        description.setFont(new Font("Cambria", Font.PLAIN, 15));
        description.setSize(470, 40);
        description.setLocation(xcounter+170, ycounter+100);
        add(description);

        //location
        location = new JLabel("Location:");
        location.setFont(new Font("Cambria", Font.BOLD, 15));
        location.setSize(100, 20);
        location.setLocation(xcounter+70, ycounter+160);
        add(location);

        address = new JTextField("Address");
        address.setFont(new Font("Cambria", Font.PLAIN, 15));
        address.setSize(150, 20);
        address.setLocation(xcounter+170, ycounter+160);
        add(address);

        city = new JTextField("City");
        city.setFont(new Font("Cambria", Font.PLAIN, 15));
        city.setSize(150, 20);
        city.setLocation(xcounter+330, ycounter+160);
        add(city);

        postcode = new JTextField("Postcode");
        postcode.setFont(new Font("Cambria", Font.PLAIN, 15));
        postcode.setSize(150, 20);
        postcode.setLocation(xcounter+490, ycounter+160);
        add(postcode);

        //luxuries
        luxuries = new JLabel("Luxuries:");
        luxuries.setFont(new Font("Cambria", Font.BOLD, 15));
        luxuries.setSize(100, 20);
        luxuries.setLocation(xcounter+70, ycounter+190);
        add(luxuries);

        viewLabel = new JLabel("View:");
        viewLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        viewLabel.setSize(70, 20);
        viewLabel.setLocation(xcounter+70, ycounter+220);
        add(viewLabel);

        view = new JTextField("none");
        view.setFont(new Font("Cambria", Font.PLAIN, 15));
        view.setSize(50, 20);
        view.setLocation(xcounter+140, ycounter+220);
        add(view);

        outdoorLabel = new JLabel("Outdoor:");
        outdoorLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        outdoorLabel.setSize(70, 20);
        outdoorLabel.setLocation(xcounter+70, ycounter+250);
        add(outdoorLabel);

        outdoor = new JTextField("none");
        outdoor.setFont(new Font("Cambria", Font.PLAIN, 15));
        outdoor.setSize(50, 20);
        outdoor.setLocation(xcounter+140, ycounter+250);
        add(outdoor);

        parkingLabel = new JLabel("Parking:");
        parkingLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        parkingLabel.setSize(70, 20);
        parkingLabel.setLocation(xcounter+70, ycounter+280);
        add(parkingLabel);

        parking = new JTextField("none");
        parking.setFont(new Font("Cambria", Font.PLAIN, 15));
        parking.setSize(50, 20);
        parking.setLocation(xcounter+140, ycounter+280);
        add(parking);

        launderingLabel = new JLabel("Laundering:");
        launderingLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        launderingLabel.setSize(90, 20);
        launderingLabel.setLocation(xcounter+230, ycounter+220);
        add(launderingLabel);

        laundering = new JTextField("none");
        laundering.setFont(new Font("Cambria", Font.PLAIN, 15));
        laundering.setSize(50, 20);
        laundering.setLocation(xcounter+320, ycounter+220);
        add(laundering);

        bathroomLabel = new JLabel("Bathroom:");
        bathroomLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        bathroomLabel.setSize(90, 20);
        bathroomLabel.setLocation(xcounter+230, ycounter+250);
        add(bathroomLabel);

        bathroom = new JTextField("none");
        bathroom.setFont(new Font("Cambria", Font.PLAIN, 15));
        bathroom.setSize(50, 20);
        bathroom.setLocation(xcounter+320, ycounter+250);
        add(bathroom);

        internetLabel = new JLabel("Internet:");
        internetLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        internetLabel.setSize(90, 20);
        internetLabel.setLocation(xcounter+230, ycounter+280);
        add(internetLabel);

        internet = new JTextField("none");
        internet.setFont(new Font("Cambria", Font.PLAIN, 15));
        internet.setSize(50, 20);
        internet.setLocation(xcounter+320, ycounter+280);
        add(internet);

        heatingAirConditioningLabel = new JLabel("Heating - Air Conditioning:");
        heatingAirConditioningLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        heatingAirConditioningLabel.setSize(180, 20);
        heatingAirConditioningLabel.setLocation(xcounter+410, ycounter+220);
        add(heatingAirConditioningLabel);

        heatingAirConditioning = new JTextField("none");
        heatingAirConditioning.setFont(new Font("Cambria", Font.PLAIN, 15));
        heatingAirConditioning.setSize(50, 20);
        heatingAirConditioning.setLocation(xcounter+590, ycounter+220);
        add(heatingAirConditioning);

        kitchenDiningRoomLabel = new JLabel("Kitchen - Dining Room:");
        kitchenDiningRoomLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        kitchenDiningRoomLabel.setSize(180, 20);
        kitchenDiningRoomLabel.setLocation(xcounter+410, ycounter+250);
        add(kitchenDiningRoomLabel);

        kitchenDiningRoom = new JTextField("none");
        kitchenDiningRoom.setFont(new Font("Cambria", Font.PLAIN, 15));
        kitchenDiningRoom.setSize(50, 20);
        kitchenDiningRoom.setLocation(xcounter+590, ycounter+250);
        add(kitchenDiningRoom);

        entertainmentLabel = new JLabel("Entertainment:");
        entertainmentLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        entertainmentLabel.setSize(180, 20);
        entertainmentLabel.setLocation(xcounter+410, ycounter+280);
        add(entertainmentLabel);

        entertainment = new JTextField("none");
        entertainment.setFont(new Font("Cambria", Font.PLAIN, 15));
        entertainment.setSize(50, 20);
        entertainment.setLocation(xcounter+590, ycounter+280);
        add(entertainment);

        //type
        type = new JLabel("Type:");
        type.setFont(new Font("Cambria", Font.BOLD, 15));
        type.setSize(50, 20);
        type.setLocation(xcounter+70, ycounter+320);
        add(type);

        apartment = new JCheckBox("Apartment");
        apartment.setFont(new Font("Cambria", Font.PLAIN, 15));
        apartment.setBackground(new Color(222, 245, 229));
        apartment.setSize(100, 20);
        apartment.setLocation(xcounter+70, ycounter+350);
        add(apartment);

        hotel = new JCheckBox("Hotel");
        hotel.setFont(new Font("Cambria", Font.PLAIN, 15));
        hotel.setBackground(new Color(222, 245, 229));
        hotel.setSize(100, 20);
        hotel.setLocation(xcounter+265, ycounter+350);
        add(hotel);

        villa = new JCheckBox("Villa");
        villa.setFont(new Font("Cambria", Font.PLAIN, 15));
        villa.setBackground(new Color(222, 245, 229));
        villa.setSize(100, 20);
        villa.setLocation(xcounter+465, ycounter+350);
        add(villa);

        group = new ButtonGroup();
        group.add(hotel);
        group.add(apartment);
        group.add(villa);

        squareSizeLabel = new JLabel("Square Size:");
        squareSizeLabel.setEnabled(false);
        squareSizeLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        squareSizeLabel.setSize(100, 20);
        squareSizeLabel.setLocation(xcounter+70, ycounter+380); //110 350
        add(squareSizeLabel);

        squareSize = new JTextField("0");
        squareSize.setEnabled(false);
        squareSize.setFont(new Font("Cambria", Font.PLAIN, 15));
        squareSize.setSize(50, 20);
        squareSize.setLocation(xcounter+195, ycounter+380);
        add(squareSize);

        floorLabel = new JLabel("Number of Floors:");
        floorLabel.setEnabled(false);
        floorLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        floorLabel.setSize(125, 20);
        floorLabel.setLocation(xcounter+70, ycounter+400);
        add(floorLabel);

        floor = new JTextField("0");
        floor.setEnabled(false);
        floor.setFont(new Font("Cambria", Font.PLAIN, 15));
        floor.setSize(50, 20);
        floor.setLocation(xcounter+195, ycounter+400);
        add(floor);

        constructionYearLabel = new JLabel("Construction Year:");
        constructionYearLabel.setEnabled(false);
        constructionYearLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        constructionYearLabel.setSize(125, 20);
        constructionYearLabel.setLocation(xcounter+70, ycounter+420);
        add(constructionYearLabel);

        constructionYear = new JTextField("0000");
        constructionYear.setEnabled(false);
        constructionYear.setFont(new Font("Cambria", Font.PLAIN, 15));
        constructionYear.setSize(50, 20);
        constructionYear.setLocation(xcounter+195, ycounter+420);
        add(constructionYear);

        roomLabel = new JLabel("Number of Rooms:");
        roomLabel.setEnabled(false);
        roomLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        roomLabel.setSize(125, 20);
        roomLabel.setLocation(xcounter+265, ycounter+380);
        add(roomLabel);

        room = new JTextField("0");
        room.setEnabled(false);
        room.setFont(new Font("Cambria", Font.PLAIN, 15));
        room.setSize(50, 20);
        room.setLocation(xcounter+390, ycounter+380);
        add(room);

        suiteLabel = new JLabel("Number of Suites:");
        suiteLabel.setEnabled(false);
        suiteLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        suiteLabel.setSize(125, 20);
        suiteLabel.setLocation(xcounter+265, ycounter+400);
        add(suiteLabel);

        suite = new JTextField("0");
        suite.setEnabled(false);
        suite.setFont(new Font("Cambria", Font.PLAIN, 15));
        suite.setSize(50, 20);
        suite.setLocation(xcounter+390, ycounter+400);
        add(suite);

        breakfastLabel = new JLabel("Breakfast:");
        breakfastLabel.setEnabled(false);
        breakfastLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        breakfastLabel.setSize(125, 20);
        breakfastLabel.setLocation(xcounter+265, ycounter+420);
        add(breakfastLabel);

        breakfast = new JCheckBox();
        breakfast.setSelected(false);
        breakfast.setEnabled(false);
        breakfast.setFont(new Font("Cambria", Font.PLAIN, 15));
        breakfast.setBackground(new Color(222, 245, 229));
        breakfast.setSize(50, 20);
        breakfast.setLocation(xcounter+390, ycounter+420);
        add(breakfast);

        poolLabel = new JLabel("Pool:");
        poolLabel.setEnabled(false);
        poolLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        poolLabel.setSize(125, 20);
        poolLabel.setLocation(xcounter+265, ycounter+440);
        add(poolLabel);

        pool = new JCheckBox();
        pool.setSelected(false);
        pool.setEnabled(false);
        pool.setFont(new Font("Cambria", Font.PLAIN, 15));
        pool.setBackground(new Color(222, 245, 229));
        pool.setSize(50, 20);
        pool.setLocation(xcounter+390, ycounter+440);
        add(pool);

        cinemaRoomLabel = new JLabel("Cinema Room:");
        cinemaRoomLabel.setEnabled(false);
        cinemaRoomLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        cinemaRoomLabel.setSize(125, 20);
        cinemaRoomLabel.setLocation(xcounter+465, ycounter+380);
        add(cinemaRoomLabel);

        cinemaRoom = new JCheckBox();
        cinemaRoom.setSelected(false);
        cinemaRoom.setEnabled(false);
        cinemaRoom.setFont(new Font("Cambria", Font.PLAIN, 15));
        cinemaRoom.setBackground(new Color(222, 245, 229));
        cinemaRoom.setSize(50, 20);
        cinemaRoom.setLocation(xcounter+590, ycounter+380);
        add(cinemaRoom);

        gymLabel = new JLabel("Gym:");
        gymLabel.setEnabled(false);
        gymLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        gymLabel.setSize(125, 20);
        gymLabel.setLocation(xcounter+465, ycounter+400);
        add(gymLabel);

        gym = new JCheckBox();
        gym.setSelected(false);
        gym.setEnabled(false);
        gym.setFont(new Font("Cambria", Font.PLAIN, 15));
        gym.setBackground(new Color(222, 245, 229));
        gym.setSize(50, 20);
        gym.setLocation(xcounter+590, ycounter+400);
        add(gym);

        privatePoolLabel = new JLabel("Private Pool:");
        privatePoolLabel.setEnabled(false);
        privatePoolLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        privatePoolLabel.setSize(125, 20);
        privatePoolLabel.setLocation(xcounter+465, ycounter+420);
        add(privatePoolLabel);

        privatePool = new JCheckBox();
        privatePool.setSelected(false);
        privatePool.setEnabled(false);
        privatePool.setFont(new Font("Cambria", Font.PLAIN, 15));
        privatePool.setBackground(new Color(222, 245, 229));
        privatePool.setSize(50, 20);
        privatePool.setLocation(xcounter+590, ycounter+420);
        add(privatePool);

        //Buttons
        addAcc = new JButton("Add Accommodation");
        addAcc.setFont(new Font("Cambria", Font.BOLD, 15));
        addAcc.setBackground(new Color(158, 213, 197));
        addAcc.setSize(180, 30);
        addAcc.setLocation(xcounter+535, ycounter+650);
        add(addAcc);

        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Cambria", Font.BOLD, 15));
        cancel.setBackground(new Color(158, 213, 197));
        cancel.setSize(180, 30);
        cancel.setLocation(xcounter+45, ycounter+650);
        add(cancel);

        clear = new JButton("Clear Form");
        clear.setFont(new Font("Cambria", Font.BOLD, 15));
        clear.setBackground(new Color(158, 213, 197));
        clear.setSize(180, 30);
        clear.setLocation(xcounter+290, ycounter+650);
        add(clear);

        //message
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setForeground(Color.red);
        message.setFont(new Font("Cambria", Font.BOLD, 20));
        message.setSize(500, 20);
        message.setLocation(xcounter+130, ycounter+550);

        add(message);

        setTitle("Add Accommodation");
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
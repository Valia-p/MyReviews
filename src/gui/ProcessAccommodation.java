package gui;

import api.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProcessAccommodation extends JFrame {
    JButton apply, showPrev, clear , cancel;
    JLabel features,image,title,nameLabel,descriptionLabel,location,luxuries,viewLabel,bathroomLabel,
            launderingLabel,entertainmentLabel,heatingAirConditioningLabel,internetLabel,kitchenDiningRoomLabel,
            outdoorLabel,parkingLabel, gymLabel,
            cinemaRoomL, privatePoolL;
    JTextField view, bathroom, laundering, entertainment, heatingAirConditioning, internet, kitchenDiningRoom, outdoor, parking;
    JTextArea description;
    JTextField name, address, city, postcode;
    JLabel SqS,Fl,Cy,roomsLabel,suitesLabel,breakfastL,poolL;
    JTextField squareSize,floor,constructionYear,rooms,suites;
    JCheckBox breakfast,pool,cinemaRoom,privatePool,gym;
    private final JLabel message = new JLabel("");
    int xcounter = 20, ycounter = 10;

    public ProcessAccommodation(Provider provider , Accommodation accommodation , AccommodationsInitialization accommodations , UsersInitialization users, ReviewsInitialization reviews){
        BuildUI(accommodation);

        cancel.addActionListener(e -> {
            new ProviderWindow(provider,users,accommodations,reviews);
            dispose();
        });

        apply.addActionListener(e -> {
            if(applyBtn(provider,accommodation,accommodations,reviews)) {
                new ProviderWindow(provider, users, accommodations, reviews);
                dispose();
            }
            message.setText("Non Valid Information");

        });


        showPrev.addActionListener(e -> {
            name.setText(accommodation.getName());
            address.setText(accommodation.getLocation().getAddress());
            city.setText(accommodation.getLocation().getCity());
            postcode.setText(accommodation.getLocation().getPostcode());
            description.setText(accommodation.getDescription());
            String[] lux = accommodation.getLuxuries().getLuxs();
            view.setText(lux[0]);
            bathroom.setText(lux[1]);
            laundering.setText(lux[2]);
            entertainment.setText(lux[3]);
            heatingAirConditioning.setText(lux[4]);
            internet.setText(lux[5]);
            kitchenDiningRoom.setText(lux[6]);
            outdoor.setText(lux[7]);
            parking.setText(lux[8]);
            if(accommodation instanceof Apartment){
                squareSize.setText(String.valueOf(((Apartment) accommodation).getSquareSize()));
                floor.setText(String.valueOf(((Apartment) accommodation).getFloor()));
                constructionYear.setText(String.valueOf(((Apartment) accommodation).getConstructionYear()));
            }
            else if(accommodation instanceof Hotel){
                rooms.setText(String.valueOf(((Hotel) accommodation).getRooms()));
                suites.setText(String.valueOf(((Hotel) accommodation).getSuites()));
                breakfast.setSelected(((Hotel) accommodation).getBreakfast());
                pool.setSelected(((Hotel) accommodation).getPool());
            }
            else if(accommodation instanceof Villa){
                cinemaRoom.setSelected(((Villa) accommodation).getCinemaRoom());
                gym.setSelected(((Villa) accommodation).getGym());
                privatePool.setSelected(((Villa) accommodation).getPrivatePool());
            }
        });

        clear.addActionListener(e -> {
            name.setText("");
            address.setText("");
            city.setText("");
            postcode.setText("");
            description.setText("");
            view.setText("none");
            laundering.setText("none");
            kitchenDiningRoom.setText("none");
            internet.setText("none");
            parking.setText("none");
            outdoor.setText("none");
            bathroom.setText("none");
            heatingAirConditioning.setText("none");
            entertainment.setText("none");
            if(accommodation instanceof Apartment){
                squareSize.setText("0");
                floor.setText("0");
                constructionYear.setText("0000");
            }
            else if(accommodation instanceof Hotel){
                rooms.setText("0");
                suites.setText("0");
                breakfast.setSelected(false);
                pool.setSelected(false);
            }
            else if(accommodation instanceof Villa){
                cinemaRoom.setSelected(false);
                gym.setSelected(false);
                privatePool.setSelected(false);
            }
        });
    }

    private boolean applyBtn(Provider provider, Accommodation accommodation ,AccommodationsInitialization accommodations ,ReviewsInitialization reviews){
        if(checkIfValid(accommodation,accommodations)) {
            message.setVisible(false);
            Location l = new Location(address.getText(), city.getText(), postcode.getText());
            Luxuries lux = new Luxuries(Luxs());
            Accommodation a = new Accommodation();
            if(accommodation instanceof Apartment){
                a = new Apartment(name.getText(), description.getText(), l, lux, provider, accommodation.getStars(), accommodation.getReviewsNum(),Integer.parseInt(squareSize.getText()),Integer.parseInt(floor.getText()),Integer.parseInt(constructionYear.getText()));
            }
            else if(accommodation instanceof Hotel){
                a = new Hotel(name.getText(), description.getText(), l, lux, provider, accommodation.getStars(), accommodation.getReviewsNum(),Integer.parseInt(rooms.getText()),Integer.parseInt(suites.getText()),breakfast.isSelected(),pool.isSelected());
            }
            else if(accommodation instanceof Villa){
                a = new Villa(name.getText(), description.getText(), l, lux, provider, accommodation.getStars(), accommodation.getReviewsNum(),cinemaRoom.isSelected(),gym.isSelected(),privatePool.isSelected());
            }
            try {
                reviews.UpdateReviewsAfterAccommodationProcess(accommodation,a);
                accommodations.processAccommodation(accommodation, a);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return true;
        }
        else{
            return false;
        }
    }

    private boolean checkIfValid(Accommodation accommodation,AccommodationsInitialization accommodations){
        if(!name.getText().equals("") && !address.getText().equals("") && !city.getText().equals("") && !postcode.getText().equals("")) {
            if (!name.getText().equals(accommodation.getName()) && !address.getText().equals(accommodation.getLocation().getAddress()))
                return accommodations.ApproveNameLocation(name.getText(), address.getText());
            else if (!name.getText().equals(accommodation.getName()))
                return accommodations.ApproveName(name.getText(), accommodation.getLocation().getAddress());
            else if (!address.getText().equals(accommodation.getLocation().getAddress()))
                return accommodations.ApproveAddress(accommodation.getName(), address.getText());
            return true;
        }
        return false;
    }

    private String[] Luxs(){
        return new String[] {view.getText(), bathroom.getText(), laundering.getText(), entertainment.getText(), heatingAirConditioning.getText() , internet.getText() , kitchenDiningRoom.getText(),outdoor.getText(),parking.getText() };
    }

    private void BuildUI(Accommodation accommodation){
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

        title = new JLabel("Process Accommodation");
        title.setFont(new Font("Cambria", Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        title.setSize(350, 35);
        title.setLocation(xcounter+230, ycounter+10); //250, 20
        add(title);

        nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Cambria", Font.BOLD, 15));
        nameLabel.setSize(100, 20);
        nameLabel.setLocation(xcounter+50, ycounter+70);
        add(nameLabel);

        name = new JTextField(accommodation.getName());
        name.setFont(new Font("Cambria", Font.PLAIN, 15));
        name.setSize(150, 20);
        name.setLocation(xcounter+150, ycounter+70);
        add(name);

        //description
        descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(new Font("Cambria", Font.BOLD, 15));
        descriptionLabel.setSize(100, 20);
        descriptionLabel.setLocation(xcounter+50, ycounter+100);
        add(descriptionLabel);

        description = new JTextArea(accommodation.getDescription());
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setFont(new Font("Cambria", Font.PLAIN, 15));
        description.setSize(470, 40);
        description.setLocation(xcounter+150, ycounter+100);
        add(description);

        //location
        location = new JLabel("Location:");
        location.setFont(new Font("Cambria", Font.BOLD, 15));
        location.setSize(100, 20);
        location.setLocation(xcounter+50, ycounter+160);
        add(location);

        address = new JTextField(accommodation.getLocation().getAddress());
        address.setFont(new Font("Cambria", Font.PLAIN, 15));
        address.setSize(150, 20);
        address.setLocation(xcounter+150, ycounter+160);
        add(address);

        city = new JTextField(accommodation.getLocation().getCity());
        city.setFont(new Font("Cambria", Font.PLAIN, 15));
        city.setSize(150, 20);
        city.setLocation(xcounter+310, ycounter+160);
        add(city);

        postcode = new JTextField(accommodation.getLocation().getPostcode());
        postcode.setFont(new Font("Cambria", Font.PLAIN, 15));
        postcode.setSize(150, 20);
        postcode.setLocation(xcounter+470, ycounter+160);
        add(postcode);

        //Luxuries
        luxuries = new JLabel("Luxuries:");
        luxuries.setFont(new Font("Cambria", Font.BOLD, 15));
        luxuries.setSize(100, 20);
        luxuries.setLocation(xcounter+50, ycounter+190);
        add(luxuries);

        String[] lux = accommodation.getLuxuries().getLuxs();
        viewLabel = new JLabel("View:");
        viewLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        viewLabel.setSize(70, 20);
        viewLabel.setLocation(xcounter+50, ycounter+220);
        add(viewLabel);

        view = new JTextField(lux[0]);
        view.setFont(new Font("Cambria", Font.PLAIN, 15));
        view.setSize(50, 20);
        view.setLocation(xcounter+120, ycounter+220);
        add(view);

        outdoorLabel = new JLabel("Outdoor:");
        outdoorLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        outdoorLabel.setSize(70, 20);
        outdoorLabel.setLocation(xcounter+50, ycounter+250);
        add(outdoorLabel);

        outdoor = new JTextField(lux[7]);
        outdoor.setFont(new Font("Cambria", Font.PLAIN, 15));
        outdoor.setSize(50, 20);
        outdoor.setLocation(xcounter+120, ycounter+250);
        add(outdoor);

        parkingLabel = new JLabel("Parking:");
        parkingLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        parkingLabel.setSize(70, 20);
        parkingLabel.setLocation(xcounter+50, ycounter+280);
        add(parkingLabel);

        parking = new JTextField(lux[8]);
        parking.setFont(new Font("Cambria", Font.PLAIN, 15));
        parking.setSize(50, 20);
        parking.setLocation(xcounter+120, ycounter+280);
        add(parking);

        launderingLabel = new JLabel("Laundering:");
        launderingLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        launderingLabel.setSize(90, 20);
        launderingLabel.setLocation(xcounter+210, ycounter+220);
        add(launderingLabel);

        laundering = new JTextField(lux[2]);
        laundering.setFont(new Font("Cambria", Font.PLAIN, 15));
        laundering.setSize(50, 20);
        laundering.setLocation(xcounter+300, ycounter+220);
        add(laundering);

        bathroomLabel = new JLabel("Bathroom:");
        bathroomLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        bathroomLabel.setSize(90, 20);
        bathroomLabel.setLocation(xcounter+210, ycounter+250);
        add(bathroomLabel);

        bathroom = new JTextField(lux[1]);
        bathroom.setFont(new Font("Cambria", Font.PLAIN, 15));
        bathroom.setSize(50, 20);
        bathroom.setLocation(xcounter+300, ycounter+250);
        add(bathroom);

        internetLabel = new JLabel("Internet:");
        internetLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        internetLabel.setSize(90, 20);
        internetLabel.setLocation(xcounter+210, ycounter+280);
        add(internetLabel);

        internet = new JTextField(lux[5]);
        internet.setFont(new Font("Cambria", Font.PLAIN, 15));
        internet.setSize(50, 20);
        internet.setLocation(xcounter+300, ycounter+280);
        add(internet);

        heatingAirConditioningLabel = new JLabel("Heating - Air Conditioning:");
        heatingAirConditioningLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        heatingAirConditioningLabel.setSize(180, 20);
        heatingAirConditioningLabel.setLocation(xcounter+390, ycounter+220);
        add(heatingAirConditioningLabel);

        heatingAirConditioning = new JTextField(lux[4]);
        heatingAirConditioning.setFont(new Font("Cambria", Font.PLAIN, 15));
        heatingAirConditioning.setSize(50, 20);
        heatingAirConditioning.setLocation(xcounter+570, ycounter+220);
        add(heatingAirConditioning);

        kitchenDiningRoomLabel = new JLabel("Kitchen - Dining Room:");
        kitchenDiningRoomLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        kitchenDiningRoomLabel.setSize(180, 20);
        kitchenDiningRoomLabel.setLocation(xcounter+390, ycounter+250);
        add(kitchenDiningRoomLabel);

        kitchenDiningRoom = new JTextField(lux[6]);
        kitchenDiningRoom.setFont(new Font("Cambria", Font.PLAIN, 15));
        kitchenDiningRoom.setSize(50, 20);
        kitchenDiningRoom.setLocation(xcounter+570, ycounter+250);
        add(kitchenDiningRoom);

        entertainmentLabel = new JLabel("Entertainment:");
        entertainmentLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
        entertainmentLabel.setSize(180, 20);
        entertainmentLabel.setLocation(xcounter+390, ycounter+280);
        add(entertainmentLabel);

        entertainment = new JTextField(lux[3]);
        entertainment.setFont(new Font("Cambria", Font.PLAIN, 15));
        entertainment.setSize(50, 20);
        entertainment.setLocation(xcounter+570, ycounter+280);
        add(entertainment);

        features = new JLabel("Features:");
        features.setFont(new Font("Cambria", Font.BOLD, 15));
        features.setSize(150, 20);
        features.setLocation(xcounter+50, ycounter+320);
        add(features);

        //Apartment
        if(accommodation instanceof Apartment){
            Fl = new JLabel("Floors:");
            Fl.setFont(new Font("Cambria", Font.PLAIN, 15));
            Fl.setSize(70, 20);
            Fl.setLocation(xcounter+50, ycounter+350);
            add(Fl);

            floor = new JTextField(String.valueOf(((Apartment) accommodation).getFloor()));
            floor.setFont(new Font("Cambria", Font.PLAIN, 15));
            floor.setSize(50, 20);
            floor.setLocation(xcounter+120, ycounter+350);
            add(floor);

            SqS = new JLabel("Square Size:");
            SqS.setFont(new Font("Cambria", Font.PLAIN, 15));
            SqS.setSize(90, 20);
            SqS.setLocation(xcounter+210, ycounter+350);
            add(SqS);

            squareSize = new JTextField(String.valueOf(((Apartment) accommodation).getSquareSize()));
            squareSize.setFont(new Font("Cambria", Font.PLAIN, 15));
            squareSize.setSize(50, 20);
            squareSize.setLocation(xcounter+300, ycounter+350);
            add(squareSize);

            Cy = new JLabel("Construction Year:");
            Cy.setFont(new Font("Cambria", Font.PLAIN, 15));
            Cy.setSize(180, 20);
            Cy.setLocation(xcounter+390, ycounter+350);
            add(Cy);

            constructionYear = new JTextField(String.valueOf(((Apartment) accommodation).getConstructionYear()));
            constructionYear.setFont(new Font("Cambria", Font.PLAIN, 15));
            constructionYear.setSize(50, 20);
            constructionYear.setLocation(xcounter+570, ycounter+350);
            add(constructionYear);

        }
        //Hotel
        else if(accommodation instanceof Hotel){
            roomsLabel = new JLabel("Rooms:");
            roomsLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
            roomsLabel.setSize(70, 20);
            roomsLabel.setLocation(xcounter+50, ycounter+350);
            add(roomsLabel);

            rooms = new JTextField(String.valueOf(((Hotel) accommodation).getRooms()));
            rooms.setFont(new Font("Cambria", Font.PLAIN, 15));
            rooms.setSize(50, 20);
            rooms.setLocation(xcounter+120, ycounter+350);
            add(rooms);

            suitesLabel = new JLabel("Suites:");
            suitesLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
            suitesLabel.setSize(90, 20);
            suitesLabel.setLocation(xcounter+210, ycounter+350);
            add(suitesLabel);

            suites = new JTextField(String.valueOf(((Hotel) accommodation).getSuites()));
            suites.setFont(new Font("Cambria", Font.PLAIN, 15));
            suites.setSize(50, 20);
            suites.setLocation(xcounter+300, ycounter+350);
            add(suites);

            breakfastL = new JLabel("Breakfast:");
            breakfastL.setFont(new Font("Cambria", Font.PLAIN, 15));
            breakfastL.setSize(180, 20);
            breakfastL.setLocation(xcounter+390, ycounter+350);
            add(breakfastL);

            breakfast = new JCheckBox();
            breakfast.setSelected(((Hotel) accommodation).getBreakfast());
            breakfast.setBackground(new Color(222, 245, 229));
            breakfast.setSize(40, 20);
            breakfast.setLocation(xcounter+570, ycounter+350);
            add(breakfast);

            poolL = new JLabel("Pool:");
            poolL.setFont(new Font("Cambria", Font.PLAIN, 15));
            poolL.setSize(70, 20);
            poolL.setLocation(xcounter+50, ycounter+380);
            add(poolL);

            pool = new JCheckBox();
            pool.setSelected(((Hotel) accommodation).getPool());
            pool.setBackground(new Color(222, 245, 229));
            pool.setSize(40, 20);
            pool.setLocation(xcounter+120, ycounter+380);
            add(pool);
        }
        //Villa
        else if (accommodation instanceof Villa){
            gymLabel = new JLabel("Gym:");
            gymLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
            gymLabel.setSize(70, 20);
            gymLabel.setLocation(xcounter+50, ycounter+350);
            add(gymLabel);

            gym = new JCheckBox();
            gym.setSelected(((Villa) accommodation).getGym());
            gym.setFont(new Font("Cambria", Font.PLAIN, 15));
            gym.setBackground(new Color(222, 245, 229));
            gym.setSize(40, 20);
            gym.setLocation(xcounter+120, ycounter+350);
            add(gym);

            cinemaRoomL = new JLabel("Cinema Room:");
            cinemaRoomL.setFont(new Font("Cambria", Font.PLAIN, 15));
            cinemaRoomL.setSize(110, 20);
            cinemaRoomL.setLocation(xcounter+210, ycounter+350);
            add(cinemaRoomL);

            cinemaRoom = new JCheckBox();
            cinemaRoom.setSelected(((Villa) accommodation).getCinemaRoom());
            cinemaRoom.setFont(new Font("Cambria", Font.PLAIN, 15));
            cinemaRoom.setBackground(new Color(222, 245, 229));
            cinemaRoom.setSize(40, 20);
            cinemaRoom.setLocation(xcounter+330, ycounter+350);
            add(cinemaRoom);

            privatePoolL = new JLabel("Private Pool:");
            privatePoolL.setFont(new Font("Cambria", Font.PLAIN, 15));
            privatePoolL.setSize(180, 20);
            privatePoolL.setLocation(xcounter+390, ycounter+350);
            add(privatePoolL);

            privatePool = new JCheckBox();
            privatePool.setSelected(((Villa) accommodation).getPrivatePool());
            privatePool.setFont(new Font("Cambria", Font.PLAIN, 15));
            privatePool.setBackground(new Color(222, 245, 229));
            privatePool.setSize(40, 20);
            privatePool.setLocation(xcounter+570, ycounter+350);
            add(privatePool);
        }

        //Buttons
        showPrev = new JButton("Show Previous");
        showPrev.setFont(new Font("Cambria", Font.BOLD, 15));
        showPrev.setBackground(new Color(158, 213, 197));
        showPrev.setSize(180, 30);
        showPrev.setLocation(xcounter+45, ycounter+580);
        add(showPrev);

        clear = new JButton("Clear Form");
        clear.setFont(new Font("Cambria", Font.BOLD, 15));
        clear.setBackground(new Color(158, 213, 197));
        clear.setSize(180, 30);
        clear.setLocation(xcounter+290, ycounter+580);
        add(clear);

        apply = new JButton("Apply Changes");
        apply.setFont(new Font("Cambria", Font.BOLD, 15));
        apply.setBackground(new Color(158, 213, 197));
        apply.setSize(180, 30);
        apply.setLocation(xcounter+535, ycounter+580);
        add(apply);

        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Cambria", Font.BOLD, 15));
        cancel.setBackground(new Color(158, 213, 197));
        cancel.setSize(100, 30);
        cancel.setLocation(xcounter+330, ycounter+650);
        add(cancel);

        //message
        message.setForeground(Color.red);
        message.setFont(new Font("Cambria", Font.BOLD, 20));
        message.setSize(220, 20);
        message.setLocation(xcounter+280, ycounter+450);
        add(message);

        setTitle("Process Accommodation");
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

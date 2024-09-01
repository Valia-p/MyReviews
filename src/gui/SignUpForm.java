package gui;

import api.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class represents the "Sign Up" window for the user to make an account in the platform.
 */
public class SignUpForm extends JFrame{
    JLabel name_label, surname_label,username_label, password_label, title, image1;
    JTextField name_text,surname_text,userName_text;
    JPasswordField password_text;
    JButton signup,cancel,clear, termsButton;
    JCheckBox acceptBox;
    String tac="This License Agreement is governed by the laws of Greece excluding its conflicts of law rules.";
    int xcounter=115, ycounter=30;

    private final JLabel error = new JLabel("");
    CheckboxGroup group;
    Checkbox provider,customer;

    /**
     * This method calls the method BuildUI() for the shaping of the window and contains all the listeners of the items,
     * such as the clear button, which is used to clear the form.
     * @param users contains all the information of the users and lets us use the methods of its class.
     * @param accommodations contains all the information of the accommodations and lets us use the methods of its class.
     * @param reviews contains all the information of the reviews and lets us use the methods of the class.
     * @throws FileNotFoundException if the user does not fill all the fields or if they give information already in use.
     */
    public SignUpForm(UsersInitialization users, AccommodationsInitialization accommodations, ReviewsInitialization reviews) throws FileNotFoundException {
        BuildUI();

        cancel.addActionListener(e -> {
            new LoginForm(users,accommodations,reviews);
            dispose();
        });

        clear.addActionListener(e -> {
            userName_text.setText("");
            password_text.setText("");
            name_text.setText("");
            surname_text.setText("");
            group.setSelectedCheckbox(null);
            acceptBox.setSelected(false);
        });

        signup.addActionListener(e -> {
            signupBtn(users,accommodations,reviews);
        });

        termsButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, tac);
        });

        acceptBox.addActionListener(e -> {
            if (acceptBox.isSelected()) {
                signup.setEnabled(true);
            }
            else {
                signup.setEnabled(false);
            }
        });
    }

    /**
     * This method defines the actions of the signup button, based on the information the user gave. It's going to
     * show certain messages if something goes wrong, or determine if the next window, to be called, is going to be
     * the provider's or the customer's one. If everything is correct, then the user is going to be signed
     * up in the platform.
     * @param users contains all the information of the users and lets us use the methods of its class.
     * @param accommodations accommodations contains all the information of the accommodations and lets us use the methods of its class.
     * @param reviews contains all the information of the reviews and lets us use the methods of the class.
     */
    private void signupBtn(UsersInitialization users,AccommodationsInitialization accommodations,ReviewsInitialization reviews) {
        String name=name_text.getText();
        String surname=surname_text.getText();
        String userName = userName_text.getText();
        String type;
        String SIGN_UP_ERROR_MESSAGE;
        String password=String.valueOf(this.password_text.getPassword());
        if(name.isEmpty()||surname.isEmpty()||userName.isEmpty()||password.isEmpty()) {
            SIGN_UP_ERROR_MESSAGE = "All fields are required!";
            error.setText(SIGN_UP_ERROR_MESSAGE);
        }
        else{
            User user=new User(name,surname,userName,password);
            try {
                if(users.signUp(user)==null){
                    SIGN_UP_ERROR_MESSAGE="Username or Password already in use!";
                    error.setText(SIGN_UP_ERROR_MESSAGE);
                }
                else{
                    if(group.getSelectedCheckbox()==null){
                        SIGN_UP_ERROR_MESSAGE="Please select your user type to continue";
                        error.setText(SIGN_UP_ERROR_MESSAGE);
                    }
                    else{
                        type = group.getSelectedCheckbox().getLabel();
                        error.setVisible(false);
                        if(type.equals("Customer")){
                            Customer customer=new Customer(name,surname,userName,password);
                            users.addToCsv(customer);
                            new CustomerWindow(customer,users,accommodations,reviews);
                        }
                        else{
                            Provider provider=new Provider(name,surname,userName,password);
                            users.addToCsv(provider);
                            new ProviderWindow(provider,users,accommodations,reviews);
                        }
                        dispose();
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method is used to build the graphical user interface (GUI) for the window, which should contain
     * a form for the user to sign up in the platform / to create an account.
     */
    private void BuildUI(){
        ImageIcon icon;
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/Logo.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        /*double dim = (0.25 * GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight());
        Image img2 = null;
        if (img!=null) {
            img2 = img.getScaledInstance((int) dim, (int) dim, Image.SCALE_SMOOTH);
        }*/
        icon = new ImageIcon(img);

        ImageIcon icon2;
        BufferedImage img3 = null;
        try {
            img3 = ImageIO.read(new File("src/blank-profile-picture.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        double dim2 = (0.2 * GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight());
        Image img4 = null;
        if (img3!=null) {
            img4 = img3.getScaledInstance((int) dim2, (int) dim2, Image.SCALE_SMOOTH);
        }
        icon2 = new ImageIcon(img4);
        image1 = new JLabel();
        image1.setIcon(icon2);
        image1.setBounds(xcounter+410, ycounter+78, 200, 200);
        add(image1);

        //title
        title = new JLabel("Registration");
        title.setFont(new Font("Cambria", Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        title.setSize(250, 35);
        title.setLocation(xcounter+195, ycounter); //290, 30
        add(title);

        // Name Label
        name_label = new JLabel("Name:");
        name_label.setFont(new Font("Cambria", Font.BOLD, 15));
        name_label.setSize(100, 20);
        name_label.setLocation(xcounter, ycounter+100); //115, 120
        add(name_label);

        name_text = new JTextField();
        name_text.setFont(new Font("Cambria", Font.PLAIN, 15));
        name_text.setSize(150, 20);
        name_text.setLocation(xcounter+100, ycounter+100);//215, 120
        add(name_text);

        //Surname
        surname_label=new JLabel("Surname:");
        surname_label.setFont(new Font("Cambria", Font.BOLD, 15));
        surname_label.setSize(100, 20);
        surname_label.setLocation(xcounter, ycounter+140); //435, 120  320 90
        add(surname_label);

        surname_text=new JTextField();
        surname_text.setFont(new Font("Cambria", Font.PLAIN, 15));
        surname_text.setSize(150, 20);
        surname_text.setLocation(xcounter+100, ycounter+140); //535, 120  420 90
        add(surname_text);

        //Username
        username_label=new JLabel("Username:");
        username_label.setFont(new Font("Cambria", Font.BOLD, 15));
        username_label.setSize(100, 20);
        username_label.setLocation(xcounter, ycounter+180); //115, 150 130
        add(username_label);

        userName_text=new JTextField();
        userName_text.setFont(new Font("Cambria", Font.PLAIN, 15));
        userName_text.setSize(150, 20);
        userName_text.setLocation(xcounter+100, ycounter+180); //215, 150
        add(userName_text);

        // Password Label
        password_label = new JLabel("Password:");
        password_label.setFont(new Font("Cambria", Font.BOLD, 15));
        password_label.setSize(100, 20);
        password_label.setLocation(xcounter, ycounter+220); //435, 150
        add(password_label);

        password_text = new JPasswordField();
        password_text.setFont(new Font("Cambria", Font.PLAIN, 15));
        password_text.setSize(150, 20);
        password_text.setLocation(xcounter+100, ycounter+220); //535, 150
        add(password_text);

        //Type
        group = new CheckboxGroup();
        provider = new Checkbox("Provider",false,group);
        provider.setFont(new Font("Cambria", Font.BOLD, 15));
        provider.setSize(100, 20);
        provider.setLocation(xcounter+75, ycounter+300); //190, 230
        add(provider);

        customer = new Checkbox("Customer",false, group);
        customer.setFont(new Font("Cambria", Font.BOLD, 15));
        customer.setSize(100, 20);
        customer.setLocation(xcounter+395, ycounter+300); //510, 230
        add(customer);

        //error
        error.setHorizontalAlignment(SwingConstants.CENTER);
        error.setForeground(Color.red);
        error.setFont(new Font("Cambria", Font.BOLD, 20));
        error.setSize(500, 20);
        error.setLocation(xcounter+15, ycounter+400); //130, 260
        add(error);

        //Box
        acceptBox = new JCheckBox("I accept the");
        acceptBox.setOpaque(false);
        acceptBox.setFont(new Font("Cambria", Font.PLAIN, 15));
        acceptBox.setSize(100, 20);
        acceptBox.setLocation(xcounter+140, ycounter+500); //255, 310
        add(acceptBox);

        termsButton = new JButton("Terms and Conditions");
        termsButton.setFont(new Font("Cambria", Font.PLAIN, 15));
        termsButton.setSize(190, 20);
        termsButton.setBackground(new Color(158, 213, 197));
        termsButton.setLocation(xcounter+240, ycounter+500); //495, 310
        add(termsButton);

        //Buttons
        signup = new JButton("Sign up");
        signup.setEnabled(false);
        signup.setFont(new Font("Cambria", Font.BOLD, 15));
        signup.setBackground(new Color(158, 213, 197));
        signup.setSize(100, 30);
        signup.setLocation(xcounter+235, ycounter+560); //350, 530
        add(signup);

        cancel= new JButton("Cancel");
        cancel.setFont(new Font("Cambria", Font.BOLD, 15));
        cancel.setBackground(new Color(158, 213, 197));
        cancel.setSize(100, 30);
        cancel.setLocation(xcounter+75, ycounter+630); //190, 660
        add(cancel);

        clear=new JButton("Clear Form");
        clear.setFont(new Font("Cambria", Font.BOLD, 15));
        clear.setBackground(new Color(158, 213, 197));
        clear.setSize(115, 30);
        clear.setLocation(xcounter+390, ycounter+630); //505, 660
        add(clear);


        setTitle("Sign Up Form");
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
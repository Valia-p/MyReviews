package gui;

import api.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class represents the first window the user will see, the "Sign In" window, in which they sign in the platform.
 */
public class LoginForm extends JFrame  {
    JLabel user_label, password_label, message, or_label, image;
    JTextField userName_text;
    JPasswordField password_text;
    JButton submit, signUp;
    int xcounter = 275, ycounter = 200;
    private final JLabel error = new JLabel("");

    /**
     * This method calls the method BuildUI() for the shaping of the window and contains all the listeners of the items,
     * such as the login button or the signUp button, which calls the "SignUpForm" class for the users who do not have
     * an account in the platform.
     * @param users contains all the information of the users and lets us use the methods of its class.
     * @param accommodations contains all the information of the accommodations and lets us use the methods of its class.
     * @param reviews contains all the information of the reviews and lets us use the methods of the class.
     */
    public LoginForm(UsersInitialization users, AccommodationsInitialization accommodations, ReviewsInitialization reviews) {
        BuildUI();
        submit.addActionListener(e -> {
            loginBtn(users,accommodations,reviews);
        });

        signUp.addActionListener(e -> {
            try {
                new SignUpForm(users,accommodations,reviews);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            dispose();
        });

        //To identify EnterKey
        userName_text.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    loginBtn(users,accommodations,reviews);
            }
        });

        password_text.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    loginBtn(users,accommodations,reviews);
            }
        });


    }

    /**
     * This method defines the actions of the login button, based on the information the user gave. It's going to
     * show certain messages if something goes wrong, or determine if the next window, to be called, is going to be
     * the provider's or the customer's one. If the user already has an account then they sign in the platform.
     * @param users contains all the information of the users and lets us use the methods of its class.
     * @param accommodations accommodations contains all the information of the accommodations and lets us use the methods of its class.
     * @param reviews contains all the information of the reviews and lets us use the methods of the class.
     */
    private void loginBtn(UsersInitialization users,AccommodationsInitialization accommodations,ReviewsInitialization reviews){
        String usernameTxt=userName_text.getText();
        String password=String.valueOf(this.password_text.getPassword());
        User user = users.signIn(usernameTxt,password);
        String LOGIN_ERROR_MESSAGE;

        if(usernameTxt.isEmpty() || password.isEmpty()){
            LOGIN_ERROR_MESSAGE="Required fields!";
            error.setText(LOGIN_ERROR_MESSAGE);
        }
        else if(user == null) {
            LOGIN_ERROR_MESSAGE = "Please check username or password!";
            error.setText(LOGIN_ERROR_MESSAGE);
        }
        else{
            if(user.getUserType().equals("Customer")){
                Customer customer = new Customer(user.getName(),user.getSurname(),usernameTxt,password);
                new CustomerWindow(customer,users,accommodations,reviews);
            }
            else{
                Provider provider=new Provider(user.getName(),user.getSurname(),usernameTxt,password);
                new ProviderWindow(provider,users,accommodations,reviews);
            }
            dispose();
        }
    }

    /**
     * This method is used to build the graphical user interface (GUI) for the window, which should contain
     * a form for the user to sign in the platform.
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
        icon = new ImageIcon(img);
        image = new JLabel();
        image.setIcon(icon);
        image.setBounds(300, 0, 200, 200);
        add(image);

        user_label = new JLabel("Username:");
        user_label.setFont(new Font("Cambria", Font.BOLD, 15));
        user_label.setSize(100, 20);
        user_label.setLocation(xcounter, ycounter+20); //275, 220
        add(user_label);

        userName_text = new JTextField();
        userName_text.setFont(new Font("Cambria", Font.PLAIN, 15));
        userName_text.setSize(150, 20);
        userName_text.setLocation(xcounter+100, ycounter+20); //375, 220
        add(userName_text);

        password_label = new JLabel("Password:");
        password_label.setFont(new Font("Cambria", Font.BOLD, 15));
        password_label.setSize(100, 20);
        password_label.setLocation(xcounter, ycounter+60); //275, 260
        add(password_label);

        password_text = new JPasswordField();
        password_text.setFont(new Font("Cambria", Font.PLAIN, 15));
        password_text.setSize(150, 20);
        password_text.setLocation(xcounter+100, ycounter+60); // 375, 260
        add(password_text);

        submit = new JButton("Sign In");
        submit.setFont(new Font("Cambria", Font.BOLD, 15));
        submit.setBackground(new Color(158, 213, 197));
        submit.setSize(100, 30);
        submit.setLocation(xcounter+75, ycounter+120); //350, 320
        add(submit);

        or_label = new JLabel("or");
        or_label.setFont(new Font("Cambria", Font.BOLD, 20));
        or_label.setForeground(new Color(98, 182, 183));
        or_label.setSize(100, 30);
        or_label.setLocation(xcounter+115, ycounter+160); //390, 360
        add(or_label);

        signUp = new JButton("Sign Up");
        signUp.setFont(new Font("Cambria", Font.BOLD, 15));
        signUp.setBackground(new Color(158, 213, 197));
        signUp.setSize(100, 30);
        signUp.setLocation(xcounter+75, ycounter+200); //350, 400
        add(signUp);

        error.setHorizontalAlignment(SwingConstants.CENTER);
        error.setForeground(Color.red);
        error.setFont(new Font("Cambria", Font.BOLD, 20));
        error.setSize(400, 20);
        error.setLocation(xcounter-75, ycounter+340); //275, 540
        add(error);

        //------------------------------------------------------------

        setTitle("Sign In Form");
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
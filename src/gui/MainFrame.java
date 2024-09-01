package gui;

import api.AccommodationsInitialization;
import api.ReviewsInitialization;
import api.UsersInitialization;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(UsersInitialization users, AccommodationsInitialization accommodations, ReviewsInitialization reviews){
        new LoginForm(users, accommodations, reviews);
    }

}
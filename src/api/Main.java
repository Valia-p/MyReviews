package api;

import gui.MainFrame;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        UsersInitialization users =new UsersInitialization();
        AccommodationsInitialization accommodations = new AccommodationsInitialization();
        ReviewsInitialization reviews = new ReviewsInitialization();
        new MainFrame(users, accommodations, reviews);
    }
}
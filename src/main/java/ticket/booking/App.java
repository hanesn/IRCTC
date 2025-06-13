package ticket.booking;


import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.services.UserBookingService;
import ticket.booking.services.UserServiceUtil;

import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) {
        System.out.println("Running Train Booking System");
        Scanner scanner = new Scanner(System.in);
        UserBookingService userBookingService;

        int option = 0;
        Train trainSelectedForBooking=new Train();

        try {
            userBookingService = new UserBookingService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (option != 7) {
            System.out.println("Choose option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch bookings");
            System.out.println("4. Search trains");
            System.out.println("5. Book a seat");
            System.out.println("6. Cancel my booking");
            System.out.println("7. Exit the app");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    try{
                        System.out.println("Enter the username to signup");
                        String usernameToSignUp = scanner.next();
                        System.out.println("Enter the password to signup");
                        String passwordToSignUp = scanner.next();
                        userBookingService.signUp(usernameToSignUp, passwordToSignUp);
                        System.out.println("Account created successfully");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 2:
                    System.out.println("Enter username to login");
                    String username = scanner.next();
                    System.out.println("Enter password to login");
                    String plainPassword = scanner.next();
                    User userToLogin = new User(UUID.randomUUID().toString(), username, plainPassword, UserServiceUtil.hashPassword(plainPassword), new ArrayList<>());
                    try {
                        userBookingService = new UserBookingService(userToLogin);
                        userBookingService.loginUser();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 3:
                    if(userBookingService.isLoggedIn()) {
                        System.out.println("Fetching your bookings...");
                        userBookingService.fetchBooking();
                    }else{
                        System.out.println("You must login first");
                    }
                    break;

                case 4:
                    System.out.println("Type your source station");
                    String source = scanner.next();
                    System.out.println("Type your destination station");
                    String destination = scanner.next();
                    List<Train> trains = userBookingService.getTrains(source, destination);
                    for (Train train : trains) {
                        System.out.println(train);
                        for (Map.Entry<String, String> entry : train.getStationTime().entrySet()) {
                            System.out.println("Station: " + entry.getKey() + ", time: " + entry.getValue());
                        }
                    }
                    if(trains.size()>0) {
                        System.out.println("Select a train by typing 1,2,3...");
                        trainSelectedForBooking = trains.get(scanner.nextInt());
                        System.out.println("Train selected. Continue with seat booking");
                    }
                    break;

                case 5:
                    System.out.println("Select a seat out of these seats");
                    List<List<Integer>> seats=userBookingService.fetchSeats(trainSelectedForBooking);
                    for(List<Integer> row:seats){
                        for(Integer val: row){
                            if(val.equals(1)){
                                System.out.print("*");
                            }else{
                                System.out.print("_");
                            }
                        }
                        System.out.println();
                    }
                    System.out.println("Select the seat by typing row and column");
                    System.out.println("Enter the row");
                    int row=scanner.nextInt();
                    System.out.println("Enter the column");
                    int column=scanner.nextInt();
                    Boolean booked=userBookingService.bookTrainSeat(trainSelectedForBooking,row,column);
                    if(booked.equals(Boolean.TRUE)){
                        System.out.println("Booked! Enjoy your journey");
                    }else{
                        System.out.println("Can't book this seat");
                    }
                    trainSelectedForBooking=null;
                    break;

                case 6:
                    System.out.println("Enter the ticket id to cancel");
                    String ticketId=scanner.next();
                    if(userBookingService.cancelBooking(ticketId)){
                        System.out.println("Ticket with ID "+ticketId+" has been cancelled");
                    }else{
                        System.out.println("No ticket found with ID "+ticketId);
                    }
                    break;

                default:
                    break;
            }
        }
    }
}

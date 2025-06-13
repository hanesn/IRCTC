package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserBookingService {
    private User user;
    private List<User> userList;
    private static final String USERS_PATH = "src/main/java/ticket/booking/localDb/users.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }

    public UserBookingService() throws IOException {
        loadUsers();
    }

    public List<User> loadUsers() throws IOException {
        File file = new File(USERS_PATH);
        userList = objectMapper.readValue(file, new TypeReference<List<User>>() {
        });
        return userList;
    }

    public Boolean loginUser() {
        Optional<User> foundUsers = userList.stream().filter(userInList -> {
            return user.getName().equalsIgnoreCase(userInList.getName()) &&
                    UserServiceUtil.checkPassword(user.getPassword(), userInList.getHashedPassword());
        }).findFirst();
        return foundUsers.isPresent();
    }

    public Boolean signUp(String username, String plainPassword) {
        try {
            User user1 = new User(UUID.randomUUID().toString(), username, plainPassword, UserServiceUtil.hashPassword(plainPassword), new ArrayList<Ticket>());
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersListFile = new File(USERS_PATH);
        objectMapper.writeValue(usersListFile, userList);
    }

    public void fetchBooking() {
        user.printTickets();
    }

    public List<Train> getTrains(String source, String destination) {
        try {
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<List<Integer>> fetchSeats(Train train) {
        return train.getSeats();
    }

    public boolean bookTrainSeat(Train train, int row, int col) {
        try {
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = trainService.getSeats(train);
            if (row >= 0 && row < seats.size() && col >= 0 && col < seats.get(0).size()) {
                if (seats.get(row).get(col) == 0) {
                    seats.get(row).set(col, 1);
                    train.setSeats(seats);
                    trainService.addTrain(train);
                    Ticket ticket=new Ticket(UUID.randomUUID().toString(),user.getUserId(),"Bangalore","Delhi","2023-12-08T18:30:00Z",train);
                    user.addTicketBooked(ticket);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean cancelBooking(String ticketId) {
        for (int i = 0; i < user.getTicketsBooked().size(); i++) {
            if (user.getTicketsBooked().get(i).getTicketId().equalsIgnoreCase(ticketId)) {
                user.getTicketsBooked().remove(i);
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public boolean isLoggedIn() {
        return user != null;
    }
}

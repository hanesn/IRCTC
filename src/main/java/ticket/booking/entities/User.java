package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("name")
    private String name;
    private String password;
    @JsonProperty("hashed_password")
    private String hashedPassword;
    @JsonProperty("tickets_booked")
    List<Ticket> ticketsBooked;

    public User(String userId, String name, String password, String hashedPassword, List<Ticket> ticketsBooked) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.ticketsBooked = ticketsBooked;
    }

    public User(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;
    }

    public void setTicketsBooked(List<Ticket> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }

    public void addTicketBooked(Ticket ticket){
        ticketsBooked.add(ticket);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", ticketsBooked=" + ticketsBooked +
                '}';
    }

    public void printTickets() {
        for (int i = 0; i < ticketsBooked.size(); i++) {
            System.out.println(ticketsBooked.get(i));
        }
    }

}

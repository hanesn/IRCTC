package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Train {
    @JsonProperty("train_id")
    private String trainId;
    @JsonProperty("train_no")
    private int trainNo;
    private List<List<Integer>> seats;
    @JsonProperty("station_time")
    private Map<String, String> stationTime;
    private List<String> stations;

    public Train(String trainId, int trainNo, List<List<Integer>> seats, Map<String, String> stationTime, List<String> stations) {
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seats = seats;
        this.stationTime = stationTime;
        this.stations = stations;
    }

    public Train(){}

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public int getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(int trainNo) {
        this.trainNo = trainNo;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Integer>> seats) {
        this.seats = seats;
    }

    public Map<String, String> getStationTime() {
        return stationTime;
    }

    public void setStationTime(Map<String, String> stationTime) {
        this.stationTime = stationTime;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    @Override
    public String toString() {
        return "Train{" +
                "trainId='" + trainId + '\'' +
                ", trainNo='" + trainNo + '\'' +
                '}';
    }
}

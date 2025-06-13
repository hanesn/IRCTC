package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainService {
    private static final String TRAINS_PATH = "src/main/java/ticket/booking/localDb/trains.json";
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<Train> trainList;

    public TrainService() throws IOException {
        File trainFile = new File(TRAINS_PATH);
        trainList = objectMapper.readValue(trainFile, new TypeReference<List<Train>>() {
        });
    }

    public List<Train> searchTrains(String source, String destination) {
        return trainList.stream()
                .filter(train -> validTrain(train, source, destination))
                .collect(Collectors.toUnmodifiableList());
    }

    private boolean validTrain(Train train, String source, String destination) {
        List<String> stationOrder = train.getStations();
        int sourceIdx = stationOrder.indexOf(source);
        int destinationIdx = stationOrder.indexOf(destination);
        return sourceIdx != -1 && destinationIdx != -1 && sourceIdx < destinationIdx;
    }

    public List<List<Integer>> getSeats(Train train) {
        return train.getSeats();
    }

    public void addTrain(Train train) {
        Optional<Train> existingTrain = trainList.stream()
                .filter(trainInList -> {
                    return train.getTrainId().equalsIgnoreCase(trainInList.getTrainId());
                }).findFirst();
        if (existingTrain.isPresent()) {
            updateTrain(train);
        } else {
            trainList.add(train);
            saveTrainListToFile();
        }
    }

    public void updateTrain(Train updatedTrain) {
        OptionalInt index = IntStream.range(0,trainList.size())
                .filter(i -> {
                    return trainList.get(i).getTrainId().equalsIgnoreCase(updatedTrain.getTrainId());
                }).findFirst();
        if(index.isPresent()){
            trainList.set(index.getAsInt(), updatedTrain);
            saveTrainListToFile();
        }else{
            addTrain(updatedTrain);
        }
    }

    private void saveTrainListToFile(){
        try{
            objectMapper.writeValue(new File(TRAINS_PATH),trainList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

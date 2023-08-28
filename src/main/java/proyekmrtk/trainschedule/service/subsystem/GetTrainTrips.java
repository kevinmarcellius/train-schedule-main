package proyekmrtk.trainschedule.service.subsystem;

import jakarta.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import proyekmrtk.trainschedule.model.TrainTrip;
import proyekmrtk.trainschedule.repository.TrainTripRepository;

@Component
public class GetTrainTrips {
    private final TrainTripRepository repository;

    public GetTrainTrips(TrainTripRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<List<TrainTrip>> buildResponse(@Nullable String line, @Nullable String trainType) {
        List<TrainTrip> listOfTrainTrips = accessRepository(line, trainType);

        if (!listOfTrainTrips.isEmpty())
            return ResponseEntity.ok().body(listOfTrainTrips);
        else
            return ResponseEntity.badRequest().body(Collections.emptyList());
    }

    public ResponseEntity<List<TrainTrip>> buildResponse() {
        return buildResponse(null, null);
    }

    public List<TrainTrip> accessRepository(@Nullable String line, @Nullable String trainType) {
        if (line == null || trainType == null) {
            try {
                return this.repository.findAll();
            } catch (NullPointerException e) {
                return Collections.emptyList();
            }
        } else {
            if ((line.equalsIgnoreCase("gehenna") || line.equalsIgnoreCase("millennium")) &&
                    (trainType.equalsIgnoreCase("local") || trainType.equalsIgnoreCase("limited express"))) {
                Optional<List<TrainTrip>> optionalTrainTrips = this.repository.findByLineAndTrainType(
                        convertToTitleCase(line), convertToTitleCase(trainType)
                );

                return optionalTrainTrips.orElse(Collections.emptyList());
            } else {
                return Collections.emptyList();
            }
        }
    }

    public List<TrainTrip> accessRepository() {
        return accessRepository(null, null);
    }

    private String convertToTitleCase(String sentence) {
        String[] words = sentence.split("\\s+");
        var stringBuilder = new StringBuilder();

        for (String word : words) {
            String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1);
            stringBuilder.append(capitalizedWord).append(" ");
        }

        return stringBuilder.toString().trim();
    }
}

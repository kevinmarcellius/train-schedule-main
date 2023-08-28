package proyekmrtk.trainschedule.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import proyekmrtk.trainschedule.model.primary.keys.TrainTripPrimaryKeys;

@Getter
@Setter
@Entity
@Table(name = "train_trips")
@IdClass(TrainTripPrimaryKeys.class)
@NoArgsConstructor
public class TrainTrip {
    @Id
    private String line;

    @Id
    private String station;

    @Id
    private String trainType;

    private Integer distance;

    @ManyToMany
    @JoinTable(name = "train_schedule", joinColumns = {
            @JoinColumn(name = "referencedTrainTripLine", referencedColumnName = "line", table = "train_trips"),
            @JoinColumn(name = "referencedTrainTripStation", referencedColumnName = "station", table = "train_trips"),
            @JoinColumn(name = "referencedTrainTripTrainType", referencedColumnName = "trainType", table = "train_trips"),
            }, inverseJoinColumns = @JoinColumn(name = "referencedTimestampId", referencedColumnName = "id", table = "timestamps"))
    private List<Timestamp> instants = new ArrayList<>();

    public TrainTrip(String line,
                     String station,
                     String trainType,
                     Integer distance) {
        this.line = line;
        this.station = station;
        this.trainType = trainType;
        this.distance = distance;
    }
    public void filterInstants(String startTime, String endTime){
        List<Timestamp> filteredInstants = new ArrayList<>();
        for(Timestamp instant : this.instants){
            if(instant.isInRange(startTime, endTime)){
                filteredInstants.add(instant);
            }
        }
        this.instants = filteredInstants;
    }
}

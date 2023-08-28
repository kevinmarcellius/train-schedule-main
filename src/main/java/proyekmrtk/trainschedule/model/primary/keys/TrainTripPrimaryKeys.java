package proyekmrtk.trainschedule.model.primary.keys;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class TrainTripPrimaryKeys implements Serializable {
    private String line;
    private String station;
    private String trainType;

    public TrainTripPrimaryKeys(String line,
                                String station,
                                String trainType) {
        this.line = line;
        this.station = station;
        this.trainType = trainType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainTripPrimaryKeys that)) return false;

        return
                Objects.equals(this.getLine(), that.getLine()) &&
                Objects.equals(this.getStation(), that.getStation()) &&
                Objects.equals(this.getTrainType(), that.getTrainType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getLine(), this.getStation(), this.getTrainType());
    }
}

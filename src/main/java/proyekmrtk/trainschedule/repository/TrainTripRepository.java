package proyekmrtk.trainschedule.repository;

import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proyekmrtk.trainschedule.model.TrainTrip;
import proyekmrtk.trainschedule.model.primary.keys.TrainTripPrimaryKeys;

@Repository
public interface TrainTripRepository extends JpaRepository<TrainTrip, TrainTripPrimaryKeys> {
    @NonNull List<TrainTrip> findAll();
    Optional<List<TrainTrip>> findByLineAndTrainType(String line, String trainType);

    @Query(value = "SELECT DISTINCT station FROM train_trips", nativeQuery = true)
    Optional<List<String>> findAllStation();

    // find by station
    Optional<List<TrainTrip>> findByStation(String station);
}

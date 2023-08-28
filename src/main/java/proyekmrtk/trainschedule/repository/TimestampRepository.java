package proyekmrtk.trainschedule.repository;

import java.util.List;
import java.util.Optional;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyekmrtk.trainschedule.model.Timestamp;

@Repository
public interface TimestampRepository extends JpaRepository<Timestamp, String> {
    @NonNull List<Timestamp> findAll();
    Optional<Timestamp> findById(@NonNull String id);
}

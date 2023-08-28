package proyekmrtk.trainschedule.service.subsystem;

import java.util.Optional;
import org.springframework.stereotype.Component;
import proyekmrtk.trainschedule.model.Timestamp;
import proyekmrtk.trainschedule.repository.TimestampRepository;

@Component
public class GetTimestamps {
    private final TimestampRepository repository;

    public GetTimestamps(TimestampRepository repository) {
        this.repository = repository;
    }

    public Timestamp accessRepository(String id) {
        Optional<Timestamp> optionalTimestamp = this.repository.findById(id);
        return optionalTimestamp.orElse(this.repository.save(new Timestamp(id)));
    }
}

package proyekmrtk.trainschedule;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proyekmrtk.trainschedule.model.TrainTrip;
import proyekmrtk.trainschedule.repository.TrainTripRepository;
import proyekmrtk.trainschedule.service.facade.TrainScheduleFacade;

@Component
public class TrainScheduleInitializer {
    private static final String GEHENNA = "Gehenna";
    private static final String MILLENNIUM = "Millennium";

    private static final String LOCAL = "Local";
    private static final String LIMITED_EXPRESS = "Limited Express";

    // busiest stations
    private static final String TRINITY = "Trinity";
    private static final String SHANHAIJING = "Shanhaijing";
    private static final String ARIUS = "Arius";

    // busiest minutes
    private static final String MINUTE15 = ":15";
    private static final String MINUTE30 = ":30";
    private static final String MINUTE38 = ":38";

    private final TrainScheduleFacade service;
    private final TrainTripRepository varTrainTripRepository;

    @Autowired
    public TrainScheduleInitializer(TrainScheduleFacade service,
                                    TrainTripRepository varTrainTripRepository) {
        this.service = service;
        this.varTrainTripRepository = varTrainTripRepository;
    }

    @PostConstruct
    public void init() {
        TrainTrip holder;

        // Gehenna Line - Local
        holder = new TrainTrip(GEHENNA, GEHENNA, LOCAL, 29);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:00"),
                this.service.getTimestamps("06" + MINUTE30),
                this.service.getTimestamps("07:00"),
                this.service.getTimestamps("07" + MINUTE30)));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(GEHENNA, "SRT", LOCAL, 26);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:02"),
                this.service.getTimestamps("06:32"),
                this.service.getTimestamps("07:02"),
                this.service.getTimestamps("07:32")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(GEHENNA, "Abydos", LOCAL, 21);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:06"),
                this.service.getTimestamps("06:36"),
                this.service.getTimestamps("07:06"),
                this.service.getTimestamps("07:36")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(GEHENNA, "Valkyrie", LOCAL, 18);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:08"),
                this.service.getTimestamps("06" + MINUTE38),
                this.service.getTimestamps("07:08"),
                this.service.getTimestamps("07" + MINUTE38)));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(GEHENNA, TRINITY, LOCAL, 16);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:10"),
                this.service.getTimestamps("06:40"),
                this.service.getTimestamps("07:10"),
                this.service.getTimestamps("07:40")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(GEHENNA, SHANHAIJING, LOCAL, 12);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:13"),
                this.service.getTimestamps("06:43"),
                this.service.getTimestamps("07:13"),
                this.service.getTimestamps("07:43")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(GEHENNA, "Odyssey", LOCAL, 10);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06" + MINUTE15),
                this.service.getTimestamps("06:45"),
                this.service.getTimestamps("07" + MINUTE15),
                this.service.getTimestamps("07:45")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(GEHENNA, ARIUS, LOCAL, 0);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:23"),
                this.service.getTimestamps("06:53"),
                this.service.getTimestamps("07:23"),
                this.service.getTimestamps("07:53")));
        this.varTrainTripRepository.save(holder);

        // Gehenna Line - Limited Express
        holder = new TrainTrip(GEHENNA, GEHENNA, LIMITED_EXPRESS, 29);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06" + MINUTE15),
                this.service.getTimestamps("07" + MINUTE15)));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(GEHENNA, "Abydos", LIMITED_EXPRESS, 21);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:21"),
                this.service.getTimestamps("07:21")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(GEHENNA, TRINITY, LIMITED_EXPRESS, 16);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:25"),
                this.service.getTimestamps("07:25")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(GEHENNA, SHANHAIJING, LIMITED_EXPRESS, 12);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:28"),
                this.service.getTimestamps("07:28")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(GEHENNA, ARIUS, LIMITED_EXPRESS, 0);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06" + MINUTE38),
                this.service.getTimestamps("07" + MINUTE38)));
        this.varTrainTripRepository.save(holder);

        // Millennium Line - Local
        holder = new TrainTrip(MILLENNIUM, MILLENNIUM, LOCAL, 34);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:00"),
                this.service.getTimestamps("06" + MINUTE30),
                this.service.getTimestamps("07:00"),
                this.service.getTimestamps("07" + MINUTE30)));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(MILLENNIUM, "Kronos", LOCAL, 32);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:02"),
                this.service.getTimestamps("06:32"),
                this.service.getTimestamps("07:02"),
                this.service.getTimestamps("07:32")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(MILLENNIUM, "Wild Hunt", LOCAL, 31);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:03"),
                this.service.getTimestamps("06:33"),
                this.service.getTimestamps("07:03"),
                this.service.getTimestamps("07:33")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(MILLENNIUM, "Red Winter", LOCAL, 25);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:08"),
                this.service.getTimestamps("06" + MINUTE38),
                this.service.getTimestamps("07:08"),
                this.service.getTimestamps("07" + MINUTE38)));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(MILLENNIUM, "Hyakkiyako", LOCAL, 17);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:14"),
                this.service.getTimestamps("06:44"),
                this.service.getTimestamps("07:14"),
                this.service.getTimestamps("07:44")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(MILLENNIUM, TRINITY, LOCAL, 16);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06" + MINUTE15),
                this.service.getTimestamps("06:45"),
                this.service.getTimestamps("07" + MINUTE15),
                this.service.getTimestamps("07:45")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(MILLENNIUM, SHANHAIJING, LOCAL, 12);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:18"),
                this.service.getTimestamps("06:48"),
                this.service.getTimestamps("07:18"),
                this.service.getTimestamps("07:48")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(MILLENNIUM, "Odyssey", LOCAL, 10);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:20"),
                this.service.getTimestamps("06:50"),
                this.service.getTimestamps("07:20"),
                this.service.getTimestamps("07:50")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(MILLENNIUM, ARIUS, LOCAL, 0);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:28"),
                this.service.getTimestamps("06:58"),
                this.service.getTimestamps("07:28"),
                this.service.getTimestamps("07:58")));
        this.varTrainTripRepository.save(holder);

        // Millennium Line - Limited Express
        holder = new TrainTrip(MILLENNIUM, MILLENNIUM, LIMITED_EXPRESS, 34);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06" + MINUTE15),
                this.service.getTimestamps("07" + MINUTE15)));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(MILLENNIUM, "Red Winter", LIMITED_EXPRESS, 25);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:23"),
                this.service.getTimestamps("07:23")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(MILLENNIUM, TRINITY, LIMITED_EXPRESS, 16);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06" + MINUTE30),
                this.service.getTimestamps("07" + MINUTE30)));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(MILLENNIUM, SHANHAIJING, LIMITED_EXPRESS, 12);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:33"),
                this.service.getTimestamps("07:33")));
        this.varTrainTripRepository.save(holder);

        holder = new TrainTrip(MILLENNIUM, ARIUS, LIMITED_EXPRESS, 0);
        holder.setInstants(Arrays.asList(
                this.service.getTimestamps("06:43"),
                this.service.getTimestamps("07:43")));
        this.varTrainTripRepository.save(holder);
    }
}

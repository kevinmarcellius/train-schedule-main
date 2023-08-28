package proyekmrtk.trainschedule.model;

import org.junit.jupiter.api.Test;
import proyekmrtk.trainschedule.model.primary.keys.TrainTripPrimaryKeys;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AllModelsTest {
    private static final String LINE = "Gehenna";
    private static final String STATION = "Odyssey";
    private static final String TRAIN_TYPE = "Local";
    private static final String SIX_AM = "06:00";
    private static final String USERNAME = "username";

    @Test
    void testTrainTripPrimaryKeysConstructor() {
        TrainTripPrimaryKeys theKeys = new TrainTripPrimaryKeys(LINE, STATION, TRAIN_TYPE);

        assertEquals(LINE, theKeys.getLine());
        assertEquals(STATION, theKeys.getStation());
        assertEquals(TRAIN_TYPE, theKeys.getTrainType());
    }

    @Test
    void testTrainTripPrimaryKeysEqualsAndHashCode() {
        TrainTripPrimaryKeys keysA = new TrainTripPrimaryKeys(LINE, STATION, TRAIN_TYPE);
        TrainTripPrimaryKeys keysB = new TrainTripPrimaryKeys(LINE, STATION, TRAIN_TYPE);

        assertEquals(keysA, keysB);
        assertEquals(keysA.hashCode(), keysB.hashCode());

        assertEquals(keysA, keysA);
    }

    @Test
    void testTrainTripPrimaryKeysNotEqualsAndHashCode() {
        TrainTripPrimaryKeys keysA = new TrainTripPrimaryKeys(LINE, STATION, TRAIN_TYPE);
        TrainTripPrimaryKeys keysB = new TrainTripPrimaryKeys(LINE, "Valkyrie", TRAIN_TYPE);

        assertNotEquals(keysA, keysB);
        assertNotEquals(keysA.hashCode(), keysB.hashCode());

        assertNotEquals("keysA", keysA);
    }

    @Test
    void testLineAndTrainType() {
        var lineAndTrainType = new LineAndTrainType(LINE, TRAIN_TYPE);
        assertEquals(LINE, lineAndTrainType.getLine());
        assertEquals(TRAIN_TYPE, lineAndTrainType.getTrainType());
    }

    @Test
    void testTimestamp() {
        var timestamp = new Timestamp(SIX_AM);
        assertEquals(SIX_AM, timestamp.getId());
    }

    @Test
    void testTrainTripParameterizedConstructor() {
        var trainTrip = new TrainTrip(LINE, STATION, TRAIN_TYPE, 200);

        assertEquals(LINE, trainTrip.getLine());
        assertEquals(STATION, trainTrip.getStation());
        assertEquals(TRAIN_TYPE, trainTrip.getTrainType());
        assertEquals(200, trainTrip.getDistance());
        assertTrue(trainTrip.getInstants().isEmpty());
    }

    @Test
    void testTrainTripEmptyConstructor() {
        var trainTrip = new TrainTrip();

        // Verify that the fields are initialized with default values
        assertNull(trainTrip.getLine());
        assertNull(trainTrip.getStation());
        assertNull(trainTrip.getTrainType());
        assertNull(trainTrip.getDistance());
        assertTrue(trainTrip.getInstants().isEmpty());
    }

    @Test
    void testTrainTripGettersAndSetters() {
        var trainTrip = new TrainTrip();

        List<Timestamp> instants = new ArrayList<>();

        trainTrip.setLine(LINE);
        trainTrip.setStation(STATION);
        trainTrip.setTrainType(TRAIN_TYPE);
        trainTrip.setDistance(200);
        trainTrip.setInstants(instants);

        assertEquals(LINE, trainTrip.getLine());
        assertEquals(STATION, trainTrip.getStation());
        assertEquals(TRAIN_TYPE, trainTrip.getTrainType());
        assertEquals(200, trainTrip.getDistance());
        assertEquals(instants, trainTrip.getInstants());
    }

    @Test
    void testUserDetails() {
        var userDetails = new UserDetails(USERNAME, 10.0);
        assertEquals(USERNAME, userDetails.getUsername());
        assertEquals(10.0, userDetails.getBalance());
    }
}

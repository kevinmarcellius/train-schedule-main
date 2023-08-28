package proyekmrtk.trainschedule.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import proyekmrtk.trainschedule.model.Timestamp;
import proyekmrtk.trainschedule.model.TrainTrip;
import proyekmrtk.trainschedule.model.UserDetails;
import proyekmrtk.trainschedule.repository.TimestampRepository;
import proyekmrtk.trainschedule.repository.TrainTripRepository;
import proyekmrtk.trainschedule.service.subsystem.GetJWT;
import proyekmrtk.trainschedule.service.subsystem.GetTimestamps;
import proyekmrtk.trainschedule.service.subsystem.GetTrainTrips;
import proyekmrtk.trainschedule.service.subsystem.GetUserDetails;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AllSubSystemsTest {
    private static final String KEY = "mrt-k-session";
    private static final String JWT = "66260701531415TheQuickBrownFoxJumpsOverTheLazyDog";

    @Value("${app.main-domain}")
    private String root;

    @Autowired
    private GetJWT varGetJWT;

    @Autowired
    private GetTimestamps varGetTimestamps;

    @Autowired
    private GetTrainTrips varGetTrainTrips;

    @Autowired
    private GetUserDetails varGetUserDetails;

    @Mock
    private TimestampRepository varTimestampRepository;

    @Mock
    private TrainTripRepository varTrainTripRepository;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(this.varGetTimestamps, "repository", this.varTimestampRepository);
        ReflectionTestUtils.setField(this.varGetTrainTrips, "repository", this.varTrainTripRepository);
    }

    private void createDummyUser() {
        var httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        String requestBody = String.format("{ \"username\": \"username\", \"password\": \"/%s\" }", JWT);

        // create the HTTP request
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(this.root + "register"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8)).build();

        // send the HTTP request asynchronously
        httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void testInspectCookiesForJWTWithExistingCookie() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie token = new Cookie(KEY, JWT);
        when(request.getCookies()).thenReturn(new Cookie[]{token});

        String result = this.varGetJWT.inspectCookiesForJWT(request);

        assertEquals(JWT, result);

        verify(request, atMostOnce()).getCookies();
        verifyNoMoreInteractions(request);
    }

    @Test
    void testInspectCookiesForJWTWithNonExistingCookie() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getCookies()).thenReturn(null);

        String result = this.varGetJWT.inspectCookiesForJWT(request);

        assertNull(result);

        verify(request, atMostOnce()).getCookies();
        verifyNoMoreInteractions(request);
    }

    @Test
    void testAccessRepositoryWithExistingTimestamp() {
        String id = "06:00";
        Timestamp expectedTimestamp = new Timestamp(id);
        when(this.varTimestampRepository.findById(id)).thenReturn(Optional.of(expectedTimestamp));

        Timestamp result = this.varGetTimestamps.accessRepository(id);

        assertEquals(expectedTimestamp, result);
        verify(this.varTimestampRepository, atMostOnce()).findById(id);
    }

    @Test
    void testAccessRepositoryWithNonExistingTimestamp() {
        String id = "08:00";
        Timestamp expectedTimestamp = new Timestamp(id);

        when(this.varTimestampRepository.findById(id)).thenReturn(Optional.empty());
        when(this.varTimestampRepository.save(any(Timestamp.class))).thenReturn(expectedTimestamp);

        Timestamp result = this.varGetTimestamps.accessRepository(id);

        assertEquals(expectedTimestamp, result);

        verify(this.varTimestampRepository, atMostOnce()).findById(id);
        verify(this.varTimestampRepository, atMostOnce()).save(any(Timestamp.class));
        verifyNoMoreInteractions(this.varTimestampRepository);
    }

    @Test
    void testBuildResponseWithValidLineAndWithValidTrainType() {
        String line = "Gehenna";
        String trainType = "Local";
        List<TrainTrip> expectedTrainTrips = Arrays.asList(new TrainTrip(), new TrainTrip());
        Optional<List<TrainTrip>> expectedOption = Optional.of(expectedTrainTrips);
        when(this.varTrainTripRepository.findByLineAndTrainType(line, trainType)).thenReturn(expectedOption);

        ResponseEntity<List<TrainTrip>> response = this.varGetTrainTrips.buildResponse(line, trainType);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(expectedTrainTrips, response.getBody());

        verify(this.varTrainTripRepository, atMostOnce()).findByLineAndTrainType("Gehenna", "Local");
        verifyNoMoreInteractions(this.varTrainTripRepository);
    }

    @Test
    void testBuildResponseWithInvalidLineAndWithInvalidTrainType() {
        String line = "unknown";
        String trainType = "unknown";

        ResponseEntity<List<TrainTrip>> response = this.varGetTrainTrips.buildResponse(line, trainType);

        assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());

        verifyNoInteractions(this.varTrainTripRepository);
    }

    @Test
    void testBuildResponseWithoutLineAndWithoutLineTrainType() {
        List<TrainTrip> expectedTrainTrips = Arrays.asList(new TrainTrip(), new TrainTrip());
        when(this.varTrainTripRepository.findAll()).thenReturn(expectedTrainTrips);

        ResponseEntity<List<TrainTrip>> response = this.varGetTrainTrips.buildResponse();

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(expectedTrainTrips, response.getBody());

        verify(this.varTrainTripRepository, atMostOnce()).findAll();
        verifyNoMoreInteractions(this.varTrainTripRepository);
    }

    @Test
    void testAccessRepositoryWithException() {
        when(this.varTrainTripRepository.findAll()).thenThrow(new NullPointerException("This is a test NullPointerException."));

        ResponseEntity<List<TrainTrip>> response = this.varGetTrainTrips.buildResponse();

        assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());

        verify(this.varTrainTripRepository, atMostOnce()).findAll();
        verifyNoMoreInteractions(this.varTrainTripRepository);
    }

    @Test
    void testUseHTTPWithValidJWT() {
        // arrange
        createDummyUser();

        // act
        UserDetails result = this.varGetUserDetails.useHTTP(JWT);

        // assert
        assertEquals("username", result.getUsername());
    }

    @Test
    void testUseHTTPWithInvalidJWT() {
        // arrange
        // ... nothing

        // act
        UserDetails result = this.varGetUserDetails.useHTTP("invalid_jwt");

        // assert
        assertNull(result);
    }

    @Test
    void testUseHTTPWithMissingJWT() {
        // arrange
        // ... nothing

        // act
        UserDetails actualUserDetails = this.varGetUserDetails.useHTTP(null);

        // assert
        assertNull(actualUserDetails);
    }
}

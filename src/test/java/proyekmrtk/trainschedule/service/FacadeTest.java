package proyekmrtk.trainschedule.service;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;
import proyekmrtk.trainschedule.model.Timestamp;
import proyekmrtk.trainschedule.model.TrainTrip;
import proyekmrtk.trainschedule.model.UserDetails;
import proyekmrtk.trainschedule.service.facade.TrainScheduleFacade;
import proyekmrtk.trainschedule.service.subsystem.GetJWT;
import proyekmrtk.trainschedule.service.subsystem.GetTimestamps;
import proyekmrtk.trainschedule.service.subsystem.GetTrainTrips;
import proyekmrtk.trainschedule.service.subsystem.GetUserDetails;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class FacadeTest {
    private static final String JWT = "jwt";
    @Value("${app.main-domain}")
    private String root;

    @Autowired
    private TrainScheduleFacade service;

    @Mock
    private GetJWT varGetJWT;

    @Mock
    private GetTimestamps varGetTimestamps;

    @Mock
    private GetTrainTrips varGetTrainTrips;

    @Mock
    private GetUserDetails varGetUserDetails;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(this.service, "varGetJWT", this.varGetJWT);
        ReflectionTestUtils.setField(this.service, "varGetTimestamps", this.varGetTimestamps);
        ReflectionTestUtils.setField(this.service, "varGetTrainTrips", this.varGetTrainTrips);
        ReflectionTestUtils.setField(this.service, "varGetUserDetails", this.varGetUserDetails);
    }

    @Test
    void testForwardRequestWhenUserDetailsIsNullShouldReturnRedirectToRoot() {
        // Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        var model = mock(Model.class);
        when(this.varGetJWT.inspectCookiesForJWT(request)).thenReturn(JWT);
        when(this.varGetUserDetails.useHTTP(JWT)).thenReturn(null);

        // When
        String result = this.service.forwardRequest(request, model);

        // Then
        assertEquals("redirect:" + this.root, result);
    }

    @Test
    void testForwardRequestWhenUserDetailsIsNotNullShouldReturnMRTsPaymentHistoryTemplate() {
        // Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        var userDetails = new UserDetails("username", 1000.0);
        var model = mock(Model.class);
        when(this.varGetJWT.inspectCookiesForJWT(request)).thenReturn(JWT);
        when(this.varGetUserDetails.useHTTP(JWT)).thenReturn(userDetails);

        // When
        String result = this.service.forwardRequest(request, model);

        // Then
        assertEquals("mrt-k-train-schedule", result);
        verify(model).addAttribute("username", userDetails.getUsername());
        verify(model).addAttribute("balance", userDetails.getBalance());
        verify(model).addAttribute("root", this.root);
        verify(model).addAttribute("homepage", this.root + "home");
    }

    @Test
    void testGetTrainTripsWithLineAndWithTrainType() {
        // Arrange
        String line = "Gehenna";
        String trainType = "Local";
        ResponseEntity<List<TrainTrip>> expectedResponse = ResponseEntity.ok().body(Arrays.asList(new TrainTrip(), new TrainTrip()));
        when(this.varGetTrainTrips.buildResponse(line, trainType)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<List<TrainTrip>> result = this.service.getTrainTrips(line, trainType);

        // Assert
        assertEquals(expectedResponse, result);
    }

    @Test
    void testGetTrainTripsWithoutLineAndWithTrainType() {
        // Arrange
        ResponseEntity<List<TrainTrip>> expectedResponse = ResponseEntity.ok().body(Arrays.asList(new TrainTrip(), new TrainTrip()));
        when(this.varGetTrainTrips.buildResponse()).thenReturn(expectedResponse);

        // Act
        ResponseEntity<List<TrainTrip>> result = this.service.getTrainTrips(null, "Local");

        // Assert
        assertEquals(expectedResponse, result);
    }

    @Test
    void testGetTrainTripsWithLineAndWithoutTrainType() {
        // Arrange
        ResponseEntity<List<TrainTrip>> expectedResponse = ResponseEntity.ok().body(Arrays.asList(new TrainTrip(), new TrainTrip()));
        when(this.varGetTrainTrips.buildResponse()).thenReturn(expectedResponse);

        // Act
        ResponseEntity<List<TrainTrip>> result = this.service.getTrainTrips("Gehenna", null);

        // Assert
        assertEquals(expectedResponse, result);
    }

    @Test
    void testGetTimestampsWithId() {
        // Arrange
        String id = "test";
        var expectedTimestamp = new Timestamp();
        when(this.varGetTimestamps.accessRepository(id)).thenReturn(expectedTimestamp);

        // Act
        Timestamp result = this.service.getTimestamps(id);

        // Assert
        assertEquals(expectedTimestamp, result);
    }
}

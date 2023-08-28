package proyekmrtk.trainschedule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import proyekmrtk.trainschedule.model.LineAndTrainType;
import proyekmrtk.trainschedule.model.TrainTrip;
import proyekmrtk.trainschedule.service.facade.TrainScheduleFacade;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ControllerTest {
    @Autowired
    private TrainScheduleController controller;

    @Mock
    private TrainScheduleFacade service;

    private MockMvc design;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(this.controller, "service", this.service);
        this.design = MockMvcBuilders.standaloneSetup(this.controller).build();
    }

    @Test
    void testRedirectToTrainSchedule() throws Exception {
        // Arrange
        String expectedTemplate = "mrt-k-train-schedule";
        when(this.service.forwardRequest(any(MockHttpServletRequest.class), any(Model.class))).thenReturn(expectedTemplate);

        MvcResult mvcResult = this.design.perform(get("/train-schedule"))
                .andExpect(request().asyncStarted())
                .andReturn();

        this.design.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedTemplate))
                .andExpect(forwardedUrl(expectedTemplate));
    }

    @Test
    void testGetTrainTripsWithValidPayload() throws Exception {
        // Arrange
        LineAndTrainType payload = new LineAndTrainType("Gehenna", "Local");
        List<TrainTrip> expectedTrainTrips = Arrays.asList(new TrainTrip(), new TrainTrip());
        when(this.service.getTrainTrips(payload.getLine(), payload.getTrainType()))
                .thenReturn(ResponseEntity.ok().body(expectedTrainTrips));

        MvcResult mvcResult = this.design.perform(post("/train-schedule/get-train-trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(payload)))
                .andExpect(request().asyncStarted())
                .andReturn();

        this.design.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().json("[{},{}]"));
    }
}

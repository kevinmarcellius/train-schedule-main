package proyekmrtk.trainschedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proyekmrtk.trainschedule.model.LineAndTrainType;
import proyekmrtk.trainschedule.model.TrainTrip;
import proyekmrtk.trainschedule.service.facade.TrainScheduleFacade;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/train-schedule")
public class TrainScheduleController {
    private final TrainScheduleFacade service;

    public TrainScheduleController(TrainScheduleFacade service) {
        this.service = service;
    }

    @Async
    @GetMapping(path = {"", "/"})
    public CompletableFuture<String> redirectToTrainSchedule(HttpServletRequest request, Model varModel) {
        return CompletableFuture.completedFuture(this.service.forwardRequest(request, varModel));
    }

    @Async
    @PostMapping(path = {"/get-train-trips", "/get-train-trips/"})
    public CompletableFuture<ResponseEntity<List<TrainTrip>>> getTrainTrips(@Valid @RequestBody LineAndTrainType payload) {
        return CompletableFuture.completedFuture(this.service.getTrainTrips(payload.getLine(), payload.getTrainType()));
    }

    // get train trips by station and a specific time range
    @Async
    @GetMapping(path = {"/get-train-trips-by-station", "/get-train-trips-by-station/"})
    public CompletableFuture<ResponseEntity<List<TrainTrip>>> getTrainTripsByStation(@RequestParam("station") String station, @RequestParam("startTime") String startTime,
                                                            @RequestParam("endTime") String endTime) {
        return CompletableFuture.completedFuture(this.service.getTrainTripsByStation(station, startTime, endTime));
    }

}

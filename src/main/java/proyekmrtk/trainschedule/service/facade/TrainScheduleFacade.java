package proyekmrtk.trainschedule.service.facade;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import proyekmrtk.trainschedule.model.Timestamp;
import proyekmrtk.trainschedule.model.TrainTrip;
import proyekmrtk.trainschedule.service.subsystem.GetJWT;
import proyekmrtk.trainschedule.service.subsystem.GetTimestamps;
import proyekmrtk.trainschedule.service.subsystem.GetTrainTrips;
import proyekmrtk.trainschedule.service.subsystem.GetUserDetails;

@Component
public class TrainScheduleFacade {
    @Value("${app.main-domain}")
    private String root;

    private final GetJWT varGetJWT;
    private final GetTrainTrips varGetTrainTrips;
    private final GetTimestamps varGetTimestamps;
    private final GetUserDetails varGetUserDetails;

    public TrainScheduleFacade(GetJWT varGetJWT,
                               GetTrainTrips varGetTrainTrips,
                               GetTimestamps varGetTimestamps,
                               GetUserDetails varGetUserDetails) {
        this.varGetJWT = varGetJWT;
        this.varGetTrainTrips = varGetTrainTrips;
        this.varGetTimestamps = varGetTimestamps;
        this.varGetUserDetails = varGetUserDetails;
    }

    public String forwardRequest(HttpServletRequest request, Model varModel) {
        String jwt = this.varGetJWT.inspectCookiesForJWT(request);
        var userDetails = this.varGetUserDetails.useHTTP(jwt);

        if (userDetails == null) {
            return "redirect:" + this.root;
        } else {
            varModel.addAttribute("username", userDetails.getUsername());
            varModel.addAttribute("balance", userDetails.getBalance());

            varModel.addAttribute("trainSchedule", this.varGetTrainTrips.accessRepository());
            varModel.addAttribute("trainStation", this.varGetTrainTrips.accessRepositoryForStation());

            varModel.addAttribute("root", this.root);
            varModel.addAttribute("homepage", this.root + "home");

            return "mrt-k-train-schedule";
        }
    }

    public ResponseEntity<List<TrainTrip>> getTrainTrips(@Nullable String line, @Nullable String trainType) {
        if (line != null && trainType != null) {
            return this.varGetTrainTrips.buildResponse(line.trim() + "", trainType.trim() + "");
        } else {
            return this.varGetTrainTrips.buildResponse();
        }
    }
    public ResponseEntity<List<TrainTrip>> getTrainTripsByStation(String station, @Nullable String startTime, @Nullable String endTime) {


        if (startTime != null && endTime != null) {
            return this.varGetTrainTrips.buildResponseByStationAndTime(station.trim() + "", startTime.trim() + "", endTime.trim() + "");
        } else {
            return this.varGetTrainTrips.buildResponse(station.trim() + "", null);
        }
    }
    public Timestamp getTimestamps(String id) {
        return this.varGetTimestamps.accessRepository(id);
    }
}

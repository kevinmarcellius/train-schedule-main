package proyekmrtk.trainschedule.model;

import jakarta.persistence.*;

import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity

@NoArgsConstructor
public class Timestamp{
    @Id
    private String id;

    public Timestamp(String id) {
        this.id = id;
    }


  //create method boolean isInRange(String startTime, String endTime)
    //return true if current time is between startTime and endTime
    //return false if current time is not between startTime and endTime
    public boolean isInRange(String startTime, String endTime){
        LocalTime currentTime = LocalTime.parse(this.id);
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        return (currentTime.isAfter(start) && currentTime.isBefore(end)) || currentTime.equals(start) || currentTime.equals(end);
    }
}

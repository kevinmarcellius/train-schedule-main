package proyekmrtk.trainschedule.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LineAndTrainType {
    private String line;
    private String trainType;

    public LineAndTrainType(String line,
                            String trainType) {
        this.line = line;
        this.trainType = trainType;
    }
}

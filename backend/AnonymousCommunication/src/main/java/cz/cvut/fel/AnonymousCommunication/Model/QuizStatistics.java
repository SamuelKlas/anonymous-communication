package cz.cvut.fel.AnonymousCommunication.Model;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.List;

@Data
@Embeddable
public class QuizStatistics {
    private long maxPoints;
    private long entries;
    private double participation;
    private double averagePoints;
    @ElementCollection
    private List<Double> averagePointsPerQuestion;
}

package cz.cvut.fel.AnonymousCommunication.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.cvut.fel.AnonymousCommunication.DTO.QuizDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Quiz extends AbstractEntity{


    @JsonIgnore
    @ManyToOne
    private Subject subject;

    private String title;

    private boolean enabled;

    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();


    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL)
    private List<QuizEntry> quizEntries = new ArrayList<>();

    @Embedded
    private QuizStatistics quizStatistics = new QuizStatistics();


    public void generateStatistics(){
        long maxPoints = questions.stream().map(q -> q.getPoints())
                .reduce(0, (a, b) -> a + b);
        quizStatistics.setMaxPoints(maxPoints);

        quizStatistics.setEntries(quizEntries.size());

        int numStudents = subject.getStudents().size();
        System.out.println(numStudents);
        System.out.println(quizEntries.size());
        double participation = numStudents == 0 ? 0 : ((1.0*quizEntries.size())
                / numStudents * 100.0);
        quizStatistics.setParticipation(participation);

        double averagePoints = 1.0 * quizEntries.stream()
                .map(quizEntry -> quizEntry.getAchievedPoints())
                .reduce(0, (a, b) -> a + b)
                / quizEntries.size();
        quizStatistics.setAveragePoints(averagePoints);

    }

    public Quiz(QuizDTO quizDTO) {
        this.title = quizDTO.getTitle();
        this.questions = quizDTO.getQuestions()
                .stream().map(question -> new Question(this,question))
                .collect(Collectors.toList());
        for (int i = 0; i < this.questions.size(); i++) {
            int finalI = i;
            this.questions.get(i).setChoices(
                    quizDTO.getQuestions().get(i).getChoices()
                    .stream().map(choice -> new Choice(this.questions.get(finalI),choice))
                    .collect(Collectors.toList())
            );
        }
    }
}

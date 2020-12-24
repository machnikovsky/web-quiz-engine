package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Entity
public class QuizQuestion {


    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    @Column
    private String title;

    @Column
    private String text;

    @ElementCollection
    @NotNull
    private List<String> options;

    @ElementCollection
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Account author;




    public QuizQuestion() {
    }

    public QuizQuestion(@JsonProperty("id") int id,
                        @JsonProperty("title") String title,
                        @JsonProperty("text") String text,
                        @JsonProperty("options") List<String> options,
                        @JsonProperty("answer") List<Integer> answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    @JsonIgnore
    public int getid() { return id; }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<String>getOptions() {
        return options;
    }

    @JsonIgnore
    public List<Integer> getAnswers() {
        return answer;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }


}

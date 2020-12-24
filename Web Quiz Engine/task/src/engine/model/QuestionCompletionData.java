package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class QuestionCompletionData {

    @Id
    @JsonIgnore
    @GeneratedValue
    public int EntityID;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    public Account author;

    @Column
    public int id;

    @Column
    public Date completedAt;



    public QuestionCompletionData() {
    }


    public QuestionCompletionData(Account author, int id) {
        this.author = author;
        this.id = id;
        this.completedAt = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "QuestionCompletionData{" +
                "id=" + id +
                ", completedAt=" + completedAt +
                '}';
    }
}

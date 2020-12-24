package engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class StringResponse {

    private List<Integer> userAnswers;

    public StringResponse(@JsonProperty("answer") List<Integer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public List<Integer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<Integer> answers) {
        this.userAnswers = answers;
    }
}
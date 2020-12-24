package engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ans {
    private final String answer;

    public Ans(@JsonProperty("answer") String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }
}

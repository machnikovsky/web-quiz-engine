package engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.ResponseEntity;

public class Answer {
    private final boolean succes;
    private final String feedback;

    public Answer(@JsonProperty("success") boolean succes,
                  @JsonProperty("feedback") String feedback) {
        this.succes = succes;
        this.feedback = feedback;
    }

    public boolean getSucces() {
        return succes;
    }

    public String getFeedback() {
        return feedback;
    }
}

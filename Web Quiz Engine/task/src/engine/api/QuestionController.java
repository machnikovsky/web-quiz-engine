package engine.api;

import engine.model.*;
import engine.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import javax.validation.constraints.Min;


@RestController
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping(value = "api/quizzes", consumes = "application/json")
    @ResponseBody
    public QuizQuestion addQuestion(@RequestBody @Valid QuizQuestion quizQuestion) throws ChangeSetPersister.NotFoundException {
        if (quizQuestion.getTitle() == null || quizQuestion.getTitle().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (quizQuestion.getText() == null || quizQuestion.getText().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (quizQuestion.getOptions() == null || quizQuestion.getOptions().size() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else  {
            questionService.addQuestion(quizQuestion);
        }
        return quizQuestion;
    }

    @GetMapping(value = "api/quizzes/{ID}")
    public QuizQuestion getQuestionByID(@PathVariable int ID) {

        return questionService.getQuestionByID(ID);
    }

    @GetMapping(value = "api/quizzes")
    public Page<QuizQuestion> getAllQuestions(
            @RequestParam(defaultValue = "0", name="page") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        System.out.println("size: " + questionService.getAllQuestions(pageNo, pageSize, sortBy).getSize());
        return questionService.getAllQuestions(pageNo, pageSize, sortBy);
    }

    @PostMapping(value = "/api/quizzes/{ID}/solve")
    @ResponseBody
    public Answer answerQuestion(@PathVariable int ID, @RequestBody StringResponse answer) {
        return questionService.answerQuestion(ID, answer);
    }

    @DeleteMapping(value = "/api/quizzes/{ID}")
    public ResponseEntity deleteQuizQuestion(@PathVariable int ID) {
        return questionService.deleteQuestion(ID);
    }

    @GetMapping(value = "/api/quizzes/completed")
    public Page<QuestionCompletionData> getCompleted(
            @RequestParam(required = false, defaultValue = "0", name = "page") @Min(0) Integer  page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy
    ) {
        return questionService.getCompleted(page, pageSize, sortBy);
    }

}

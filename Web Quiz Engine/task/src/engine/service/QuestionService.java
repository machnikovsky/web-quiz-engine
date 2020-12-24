package engine.service;

import engine.model.*;
import engine.repository.AccountRepository;
import engine.repository.QuestionCompletionDataRepository;
import engine.repository.questionRepository;
import engine.security.MyAccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;
import javax.transaction.Transactional;
import java.util.*;


@Service
public class QuestionService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    questionRepository questionRepository;

    @Autowired
    QuestionCompletionDataRepository questionCompletionDataRepository;



    public void addQuestion(QuizQuestion question) throws ChangeSetPersister.NotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyAccountDetails actualUser = (MyAccountDetails)principal;
        String email = actualUser.getUsername();
        question.setAuthor(accountRepository.findAccountByEmail(email)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException()));
        questionRepository.save(question);
    }

    public QuizQuestion getQuestionByID(int ID) {
        return questionRepository.findById(ID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Page<QuizQuestion> getAllQuestions(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<QuizQuestion> page = questionRepository.findAll(paging);

        return page;
    }

    public Answer answerQuestion(int ID, StringResponse answers) {

        QuizQuestion quizQuestion = questionRepository.findById(ID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        System.out.println("DB: " + quizQuestion.getAnswers());
        System.out.println("User: " + answers.getUserAnswers());



        if (Arrays.equals(quizQuestion.getAnswers().toArray(), answers.getUserAnswers().toArray())) {

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Account account = accountRepository.findAccountByEmail(username).get();

            QuestionCompletionData questionCompletionData = new QuestionCompletionData(account, quizQuestion.getid());
            questionCompletionDataRepository.save(questionCompletionData);

            return new Answer(true, "Congratulations, you\'re right!");
        } else {
            return new Answer(false, "Wrong answer! Please, try again.");
        }

    }

    public ResponseEntity deleteQuestion(int IDnr) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findAccountByEmail(username).get();
        Optional<QuizQuestion> quizOptional = questionRepository.findById(IDnr);
        QuizQuestion quiz;
        if (quizOptional.isPresent()) {
            quiz = quizOptional.get();
            if (quiz.getAuthor().equals(account)) {
                questionRepository.delete(quiz);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    public Page<QuestionCompletionData> getCompleted(Integer pageNo, Integer pageSize, String sortBy) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findAccountByEmail(username).get();

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<QuestionCompletionData> page = questionCompletionDataRepository.findAllCompletedQuestions(paging, account.getEmail());
        return page;
    }
}

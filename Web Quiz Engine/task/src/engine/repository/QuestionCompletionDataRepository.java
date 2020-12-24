package engine.repository;

import engine.model.QuestionCompletionData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionCompletionDataRepository extends JpaRepository<QuestionCompletionData, Integer> {

    @Query("SELECT u FROM QuestionCompletionData u WHERE u.author.email = :email ORDER BY u.completedAt DESC")
    Page<QuestionCompletionData> findAllCompletedQuestions(Pageable pageable, @Param("email") String email);

}

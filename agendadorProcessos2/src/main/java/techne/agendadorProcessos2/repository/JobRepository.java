package techne.agendadorProcessos2.repository;

import org.springframework.stereotype.Repository;
import techne.agendadorProcessos2.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import techne.agendadorProcessos2.model.Job;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    Optional<Job> findByCronExpression(String cronExpression);
}

package techne.agendadorProcessos2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import techne.agendadorProcessos2.model.JobStatus;

@Repository
public interface JobStatusRepository extends JpaRepository<JobStatus, Long> {
}

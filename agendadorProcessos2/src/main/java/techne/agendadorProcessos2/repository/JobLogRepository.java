package techne.agendadorProcessos2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techne.agendadorProcessos2.model.JobLog;

public interface JobLogRepository extends JpaRepository<JobLog, Long> {
}

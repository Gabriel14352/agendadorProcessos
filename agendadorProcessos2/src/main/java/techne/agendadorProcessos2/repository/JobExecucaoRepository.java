package techne.agendadorProcessos2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import techne.agendadorProcessos2.model.JobExecucao;

import java.util.List;

@Repository
public interface JobExecucaoRepository extends JpaRepository<JobExecucao, Long> {
    List<JobExecucao> findByJob_Id(Long jobId);
}

package techne.agendadorProcessos2.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import techne.agendadorProcessos2.model.Job;
import techne.agendadorProcessos2.repository.JobRepository;
import techne.agendadorProcessos2.service.SchedulerService;

@Component
public class JobInitializer {

    private final JobRepository jobRepository;
    private final SchedulerService schedulerService;

    public JobInitializer(JobRepository jobRepository, SchedulerService schedulerService) {
        this.jobRepository = jobRepository;
        this.schedulerService = schedulerService;
    }

    @PostConstruct
    public void init() {
        System.out.println(" Recarregando jobs do banco...");
        jobRepository.findAll().forEach(job -> {
            try {
                schedulerService.agendarJob(job);
                System.out.println(" Job re-agendado: " + job.getNome() + " (ID: " + job.getId() + ")");
            } catch (Exception e) {
                System.err.println(" Erro ao re-agendar job " + job.getNome() + ": " + e.getMessage());
            }
        });
    }
}

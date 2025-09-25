package techne.agendadorProcessos2.controller;

import org.springframework.web.bind.annotation.*;
import techne.agendadorProcessos2.model.JobLog;
import techne.agendadorProcessos2.repository.JobLogRepository;

import java.util.List;

@RestController
@RequestMapping("/api/joblogs")

public class JobLogController {
    private final JobLogRepository jobLogRepository;

    public JobLogController(JobLogRepository jobLogRepository){
        this.jobLogRepository = jobLogRepository;
    }

    @GetMapping
    public List<JobLog> listarTodos(){
        return jobLogRepository.findAll();
    }
}

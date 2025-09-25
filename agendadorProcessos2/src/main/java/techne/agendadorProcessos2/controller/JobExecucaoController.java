package techne.agendadorProcessos2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techne.agendadorProcessos2.model.JobExecucao;
import techne.agendadorProcessos2.repository.JobExecucaoRepository;

import java.util.List;

@RestController
@RequestMapping("/api/execucoes")
public class JobExecucaoController {

    private final JobExecucaoRepository jobExecucaoRepository;

    public JobExecucaoController(JobExecucaoRepository jobExecucaoRepository){
        this.jobExecucaoRepository = jobExecucaoRepository;
    }

    @GetMapping
    public List<JobExecucao> listarTodas(){
        return jobExecucaoRepository.findAll();
    }

    @GetMapping("/job/{jobId}")
    public List<JobExecucao> listarPorJob(@PathVariable Long jobId){
        return jobExecucaoRepository.findByJob_Id(jobId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobExecucao> buscarPorId(@PathVariable Long id){
        return jobExecucaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}

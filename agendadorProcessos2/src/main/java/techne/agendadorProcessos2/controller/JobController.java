package techne.agendadorProcessos2.controller;

import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techne.agendadorProcessos2.model.Job;
import techne.agendadorProcessos2.service.JobService;
import techne.agendadorProcessos2.service.SchedulerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;
    private final SchedulerService schedulerService;

    public JobController(JobService jobService, SchedulerService schedulerService) {
        this.jobService = jobService;
        this.schedulerService = schedulerService;
    }

    // Criar job
    @PostMapping
    public ResponseEntity<Job> criarJob(@RequestBody Job job) {
        if (job.getNome() == null || job.getCronExpression() == null) {
            return ResponseEntity.badRequest().build();
        }

        Job novoJob = jobService.criar(job);
        return ResponseEntity.status(201).body(novoJob);
    }

    // Listar jobs
    @GetMapping
    public List<Job> listarJobs() {
        return jobService.listarTodos();
    }

    // buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Job> buscarJobPorId(@PathVariable Long id) {
        Optional<Job> job = jobService.buscarPorId(id);
        return job.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // editar job
    @PutMapping("/{id}")
    public ResponseEntity<Job> editarJob(@PathVariable Long id, @RequestBody Job jobAtualizado) {
        try {
            Job jobEditado = jobService.editar(id, jobAtualizado);
            return ResponseEntity.ok(jobEditado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // deletar job
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarJob(@PathVariable Long id) {
        try {
            jobService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

   

    //executar manualmente sem Quartz (processa arquivos direto)
    @PostMapping("/{id}/run-now")
    public ResponseEntity<String> rodarJobDireto(@PathVariable Long id) {
        try {
            jobService.executarDireto(id);
            return ResponseEntity.ok("Job " + id + " executado direto com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao executar direto: " + e.getMessage());
        }
    }
}

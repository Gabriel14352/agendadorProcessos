package techne.agendadorProcessos2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techne.agendadorProcessos2.model.ArquivoRetorno;
import techne.agendadorProcessos2.model.JobExecucao;
import techne.agendadorProcessos2.repository.ArquivoRetornoRepository;
import techne.agendadorProcessos2.repository.JobExecucaoRepository;

import java.util.List;

@RestController
@RequestMapping("/api/jobs/{jobId}")
public class JobDetailsController {

    private final JobExecucaoRepository jobExecucaoRepository;
    private final ArquivoRetornoRepository arquivoRetornoRepository;

    public JobDetailsController(JobExecucaoRepository jobExecucaoRepository,
                                ArquivoRetornoRepository arquivoRetornoRepository) {
        this.jobExecucaoRepository = jobExecucaoRepository;
        this.arquivoRetornoRepository = arquivoRetornoRepository;
    }

    // Lista execuções do job
    @GetMapping("/execucoes")
    public List<JobExecucao> listarExecucoes(@PathVariable Long jobId) {
        return jobExecucaoRepository.findByJob_Id(jobId);
    }

    // Lista arquivos associados ao job (pendentes, processados, erro)
    @GetMapping("/arquivos")
    public List<ArquivoRetorno> listarArquivos(@PathVariable Long jobId) {
        return arquivoRetornoRepository.findByJob_Id(jobId);
    }

    // (opcional) Lista arquivos de uma execução específica
    @GetMapping("/execucoes/{execucaoId}/arquivos")
    public ResponseEntity<List<ArquivoRetorno>> listarArquivosPorExecucao(@PathVariable Long jobId,
                                                                          @PathVariable Long execucaoId) {
        // (poderia validar se a execução pertence ao jobId)
        return ResponseEntity.ok(arquivoRetornoRepository.findByExecucaoId(execucaoId));
    }
}

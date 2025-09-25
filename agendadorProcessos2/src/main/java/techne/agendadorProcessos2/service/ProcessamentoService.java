package techne.agendadorProcessos2.service;

import org.springframework.stereotype.Service;
import techne.agendadorProcessos2.model.Job;
import techne.agendadorProcessos2.model.JobStatus;
import techne.agendadorProcessos2.model.Status;
import techne.agendadorProcessos2.repository.JobStatusRepository;
import techne.agendadorProcessos2.repository.JobRepository;

import java.io.File;
import java.time.LocalDateTime;

@Service
public class ProcessamentoService {

    private final JobStatusRepository jobStatusRepository;
    private final JobRepository jobRepository;

    private static final String PASTA_RETORNOS = "C:/retornos_banco/";

    public ProcessamentoService(JobStatusRepository jobStatusRepository, JobRepository jobRepository) {
        this.jobStatusRepository = jobStatusRepository;
        this.jobRepository = jobRepository;
    }

    public Long iniciarJob(Long jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow();
        JobStatus status = new JobStatus(job, Status.PROCESSANDO, "Job iniciado");
        status.setDataRegistro(LocalDateTime.now());
        jobStatusRepository.save(status);
        return status.getId();
    }

    public void processarArquivos(Long jobId) {
        File pasta = new File(PASTA_RETORNOS);
        File[] arquivos = pasta.listFiles();

        Job job = jobRepository.findById(jobId).orElseThrow();

        if (arquivos == null || arquivos.length == 0) {
            JobStatus vazio = new JobStatus(job, Status.CONCLUIDO, "Nenhum arquivo encontrado.");
            jobStatusRepository.save(vazio);
            return;
        }

        for (File arquivo : arquivos) {
            try {
                System.out.println("📂 Processando arquivo: " + arquivo.getName());
                Thread.sleep(1000); // simulação
                JobStatus ok = new JobStatus(job, Status.CONCLUIDO,
                        "Arquivo " + arquivo.getName() + " processado com sucesso.");
                jobStatusRepository.save(ok);
            } catch (Exception e) {
                JobStatus erro = new JobStatus(job, Status.ERRO,
                        "Erro ao processar arquivo " + arquivo.getName() + ": " + e.getMessage());
                jobStatusRepository.save(erro);
            }
        }
    }

    public void finalizarJob(Long jobId, Status status, String mensagem) {
        Job job = jobRepository.findById(jobId).orElseThrow();
        JobStatus finalizado = new JobStatus(job, status, mensagem);
        finalizado.setDataRegistro(LocalDateTime.now());
        jobStatusRepository.save(finalizado);
    }
}

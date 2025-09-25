package techne.agendadorProcessos2.scheduler;

import org.quartz.Job; // Quartz Job
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import techne.agendadorProcessos2.model.JobExecucao;
import techne.agendadorProcessos2.model.Status;
import techne.agendadorProcessos2.repository.JobExecucaoRepository;
import techne.agendadorProcessos2.repository.JobRepository;
import techne.agendadorProcessos2.service.ArquivoService;

import java.io.File;
import java.time.LocalDateTime;

@Component
public class ProcessarArquivosJob implements Job {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobExecucaoRepository jobExecucaoRepository;

    @Autowired
    private ArquivoService arquivoService;

    private static final String PASTA_PENDENTES = "C:\\retornos_banco\\pendentes";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Long jobId = context.getJobDetail().getJobDataMap().getLong("jobId");
        System.out.println("🔔 Executando jobId recebido do Quartz: " + jobId);

        // 👉 Aqui usamos o nome COMPLETO da entidade para não confundir
        techne.agendadorProcessos2.model.Job jobEntity = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobExecutionException("Job não encontrado: " + jobId));

        JobExecucao execucao = new JobExecucao();
        execucao.setJob(jobEntity);
        execucao.setInicio(LocalDateTime.now());
        execucao.setStatus(Status.PROCESSANDO);
        execucao.setMensagem("Iniciando processamento...");
        execucao = jobExecucaoRepository.save(execucao);

        System.out.println("✅ Execução salva com ID: " + execucao.getId());

        try {
            File pastaPendentes = new File(PASTA_PENDENTES);
            File[] arquivos = pastaPendentes.listFiles();

            if (arquivos == null || arquivos.length == 0) {
                execucao.setFim(LocalDateTime.now());
                execucao.setStatus(Status.CONCLUIDO);
                execucao.setMensagem("Nenhum arquivo pendente encontrado.");
                jobExecucaoRepository.save(execucao);
                return;
            }

            for (File arquivo : arquivos) {
                if (arquivo.isFile()) {
                    System.out.println("📂 Processando arquivo pendente: " + arquivo.getName());
                    try {
                        // ✅ Aqui também chamamos passando o model.Job
                        arquivoService.processarArquivo(arquivo, jobEntity, execucao);
                    } catch (Exception eArquivo) {
                        System.err.println("❌ Erro ao processar arquivo " + arquivo.getName() + ": " + eArquivo.getMessage());
                    }
                }
            }

            execucao.setFim(LocalDateTime.now());
            execucao.setStatus(Status.CONCLUIDO);
            execucao.setMensagem("Processamento finalizado.");
            jobExecucaoRepository.save(execucao);

        } catch (Exception e) {
            execucao.setFim(LocalDateTime.now());
            execucao.setStatus(Status.ERRO);
            execucao.setMensagem("Erro geral no processamento: " + e.getMessage());
            jobExecucaoRepository.save(execucao);

            throw new JobExecutionException(e);
        }
    }
}

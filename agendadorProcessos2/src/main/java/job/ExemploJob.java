package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import techne.agendadorProcessos2.model.JobLog;
import techne.agendadorProcessos2.repository.JobLogRepository;

@Component
public class ExemploJob implements Job {

    @Autowired
    private JobLogRepository jobLogRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String jobName = context.getJobDetail().getKey().toString();

        // Log de início
        JobLog logInicio = new JobLog(jobName, JobLog.Status.PROCESSANDO, "Job iniciado");
        jobLogRepository.save(logInicio);

        try {
            // ⚡ Aqui vai a lógica real do job
            System.out.println("Executando job de exemplo: " + jobName);

            // Simulação de trabalho
            Thread.sleep(2000);

            // Log de sucesso
            JobLog logSucesso = new JobLog(jobName, JobLog.Status.SUCESSO, "Job finalizado com sucesso");
            jobLogRepository.save(logSucesso);

        } catch (Exception e) {
            // Log de erro
            JobLog logErro = new JobLog(jobName, JobLog.Status.ERRO, "Erro: " + e.getMessage());
            jobLogRepository.save(logErro);

            throw new JobExecutionException(e);
        }
    }
}

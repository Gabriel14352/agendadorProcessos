package techne.agendadorProcessos2.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import techne.agendadorProcessos2.model.Status;
import techne.agendadorProcessos2.service.ProcessamentoService;

@Component
public class ProcessarRetornoBancarioJob implements Job {

    @Autowired
    private ProcessamentoService processamentoService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Long jobId = null;
        try {

            jobId = (Long) context.getJobDetail().getJobDataMap().get("jobId");

            if (jobId == null) {
                throw new JobExecutionException("jobId não foi informado no JobDataMap do Quartz.");
            }


            processamentoService.iniciarJob(jobId);


            processamentoService.processarArquivos(jobId);


            processamentoService.finalizarJob(jobId, Status.CONCLUIDO, "Job finalizado com sucesso ✅");

        } catch (Exception e) {
            // 4. Em caso de erro, marca como ERRO
            if (jobId != null) {
                processamentoService.finalizarJob(jobId, Status.ERRO, "Falha no processamento ❌: " + e.getMessage());
            }
            throw new JobExecutionException("Erro ao executar job de retorno bancário", e);
        }
    }
}

package techne.agendadorProcessos2.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class ProcessarJobQuartz implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException{
        Long jobId = context.getJobDetail().getJobDataMap().getLong("jobId");

        System.out.println("Executando Job ID:" + jobId + " - " + context.getFireTime());


        // Fazer depois para simular o processar retorno bancario ou outra logica
    }
}

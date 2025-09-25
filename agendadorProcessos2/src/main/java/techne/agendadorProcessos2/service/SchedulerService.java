package techne.agendadorProcessos2.service;

import org.quartz.*;
import org.springframework.stereotype.Service;
import techne.agendadorProcessos2.model.Job;
import techne.agendadorProcessos2.scheduler.ProcessarArquivosJob;

@Service
public class SchedulerService {

    private final Scheduler scheduler;

    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void agendarJob(Job job) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ProcessarArquivosJob.class)
                .withIdentity("job_" + job.getId(), "jobs")
                .usingJobData("jobId", job.getId()) // 👈 Garantindo que salva no JobDataMap
                .build();

        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(job.getCronExpression());

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger_" + job.getId(), "jobs")
                .withSchedule(cronSchedule)
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    public void removerJob(Long jobId) throws SchedulerException {
        JobKey jobKey = new JobKey("job_" + jobId, "jobs");
        scheduler.deleteJob(jobKey);
    }

    public void rodarJobAgora(Long jobId) throws SchedulerException {
        JobKey jobKey = new JobKey("job_" + jobId, "jobs");
        if (scheduler.checkExists(jobKey)) {
            scheduler.triggerJob(jobKey); // 👈 dispara manualmente
        } else {
            throw new SchedulerException("O job com ID " + jobId + " não está agendado no Quartz.");
        }
    }
}

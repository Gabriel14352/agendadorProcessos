package techne.agendadorProcessos2.scheduler;

import org.quartz.*;
import org.springframework.stereotype.Service;
import techne.agendadorProcessos2.model.Job;

@Service
public class QuartzSchedulerService {

    private final Scheduler scheduler;

    public QuartzSchedulerService(Scheduler scheduler){
        this.scheduler = scheduler;
    }

    public void agendarJob(Job job) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ProcessarJobQuartz.class)
                .withIdentity("job-" + job.getId(), "jobs")
                .usingJobData("jobId", job.getId())
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger-" + job.getId(), "jobs")
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }
}

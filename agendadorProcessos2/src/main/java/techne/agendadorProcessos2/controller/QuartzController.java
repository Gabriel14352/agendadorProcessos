package techne.agendadorProcessos2.controller;

import job.ExemploJob;
import org.quartz.*;
import org.springframework.web.bind.annotation.*;
import org.quartz.impl.matchers.GroupMatcher;



@RestController
@RequestMapping("/api/quartz")
public class QuartzController {

    private final Scheduler scheduler;

    public QuartzController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    // Inicia/agenda um job
    @PostMapping("/start-job")
    public String startJob(@RequestParam String jobName, @RequestParam String cron) throws Exception {
        JobDetail jobDetail = JobBuilder.newJob(ExemploJob.class) // você pode trocar por outro Job futuramente
                .withIdentity(jobName)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "Trigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);

        return "Job " + jobName + " agendado com cron " + cron;
    }

    // Lista todos os jobs ativos
    @GetMapping("/jobs")
    public String listarJobs() throws Exception {
        StringBuilder builder = new StringBuilder("Jobs agendados:\n");

        for (String groupName : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                builder.append("- ").append(jobKey.getName()).append("\n");
            }
        }
        return builder.toString();
    }

    // Deleta/cancela um job
    @DeleteMapping("/delete-job/{jobName}")
    public String deleteJob(@PathVariable String jobName) throws Exception {
        JobKey jobKey = new JobKey(jobName);
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
            return "Job " + jobName + " removido!";
        }
        return "Job " + jobName + " não encontrado.";
    }

    // Executa um job manualmente
    @PostMapping("/trigger-now/{jobName}")
    public String triggerNow(@PathVariable String jobName) throws Exception {
        JobKey jobKey = new JobKey(jobName);
        if (scheduler.checkExists(jobKey)) {
            scheduler.triggerJob(jobKey);
            return "Job " + jobName + " executado manualmente!";
        }
        return "Job " + jobName + " não encontrado.";
    }
}

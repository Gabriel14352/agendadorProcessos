package techne.agendadorProcessos2.scheduler;

import jakarta.annotation.PostConstruct;
import org.quartz.Scheduler;
import org.springframework.stereotype.Component;

@Component
public class QuartzStarter {

    private final Scheduler scheduler;

    public QuartzStarter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void start() throws Exception {
        if (!scheduler.isStarted()) {
            scheduler.start();
            System.out.println("✅ Quartz Scheduler iniciado com sucesso!");
        }
    }
}

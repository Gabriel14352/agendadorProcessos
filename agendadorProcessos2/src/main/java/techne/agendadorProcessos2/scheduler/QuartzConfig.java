package techne.agendadorProcessos2.scheduler;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer() {
        return factory -> {
            factory.setAutoStartup(true); // ✅ Inicia automaticamente
        };
    }
}

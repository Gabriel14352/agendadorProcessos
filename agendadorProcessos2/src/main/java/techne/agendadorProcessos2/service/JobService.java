package techne.agendadorProcessos2.service;

import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import techne.agendadorProcessos2.model.Job;
import techne.agendadorProcessos2.repository.JobRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final SchedulerService schedulerService;

    public JobService(JobRepository jobRepository, SchedulerService schedulerService) {
        this.jobRepository = jobRepository;
        this.schedulerService = schedulerService;
    }

    // criar job no quartz e no banco
    public Job criar(Job job){
        jobRepository.findByCronExpression(job.getCronExpression()).ifPresent(existing -> {
            throw new RuntimeException("Ja existe um job com o mesmo horario (cronExpression):" + job.getCronExpression());
        });

        Job novoJob = jobRepository.save(job);

        try{
            schedulerService.agendarJob(novoJob);
        }catch(SchedulerException e){
            throw new RuntimeException("Erro ao agendar job no quartz");
        }

        return novoJob;
    }

    // listar todos
    public List<Job> listarTodos() {
        return jobRepository.findAll();
    }

    // buscar por id
    public Optional<Job> buscarPorId(Long id) {
        return jobRepository.findById(id);
    }

    // editar job
    public Job editar(Long id, Job jobAtualizado) {
       return jobRepository.findById(id).map(job -> {
           jobRepository.findByCronExpression(jobAtualizado.getCronExpression()).ifPresent(existing -> {
               if(!existing.getId().equals(id)){
                   throw new RuntimeException("ja existe outro job com esse horario (cronExpression):" + jobAtualizado.getCronExpression());
               }
           });

           job.setNome(jobAtualizado.getNome());
           job.setCronExpression(jobAtualizado.getCronExpression());
           job.setStatus(jobAtualizado.getStatus());

           Job atualizado = jobRepository.save(job);

           try{
               schedulerService.removerJob(atualizado.getId());
               schedulerService.agendarJob(atualizado);
           }catch(SchedulerException e){
               throw new RuntimeException("Erro ao re-agendar job no quartz", e);
           }
           return atualizado;
       }).orElseThrow(() -> new RuntimeException("job nao encontrado:" + id));
    }

    // deletar job
    public void deletar(Long id) {
        jobRepository.findById(id).ifPresent(job -> {
            try {
                schedulerService.removerJob(job.getId());
            } catch (SchedulerException e) {
                throw new RuntimeException("Erro ao remover job no quartz", e);
            }
            jobRepository.delete(job);
        });
    }

    // rodar manualmente direto
    public void executarDireto(Long id) {
        jobRepository.findById(id).ifPresent(job -> {
            try {
                schedulerService.rodarJobAgora(job.getId());
            } catch (Exception e) {
                throw new RuntimeException("Erro ao executar job direto", e);
            }
        });
    }
}

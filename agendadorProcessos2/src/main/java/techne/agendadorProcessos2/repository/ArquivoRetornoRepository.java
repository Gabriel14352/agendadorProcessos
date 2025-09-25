package techne.agendadorProcessos2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import techne.agendadorProcessos2.model.ArquivoRetorno;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArquivoRetornoRepository extends JpaRepository<ArquivoRetorno, Long> {
    List<ArquivoRetorno> findByJob_Id(Long jobId);
    List<ArquivoRetorno> findByExecucaoId(Long execucaoId);
    Optional<ArquivoRetorno> findByNomeArquivo(String nomeArquivo);
}

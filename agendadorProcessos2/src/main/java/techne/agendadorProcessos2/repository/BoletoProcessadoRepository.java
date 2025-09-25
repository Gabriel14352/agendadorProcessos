package techne.agendadorProcessos2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techne.agendadorProcessos2.model.BoletoProcessado;

public interface BoletoProcessadoRepository extends JpaRepository<BoletoProcessado, Long> {
}

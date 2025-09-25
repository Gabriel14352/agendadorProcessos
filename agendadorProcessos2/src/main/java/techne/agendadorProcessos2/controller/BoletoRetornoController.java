package techne.agendadorProcessos2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techne.agendadorProcessos2.model.BoletoRetorno;
import techne.agendadorProcessos2.repository.BoletoRetornoRepository;

import java.util.List;

@RestController
@RequestMapping("/api/boletos")
public class BoletoRetornoController {

    private final BoletoRetornoRepository boletoRetornoRepository;

    public BoletoRetornoController(BoletoRetornoRepository boletoRetornoRepository) {
        this.boletoRetornoRepository = boletoRetornoRepository;
    }

    // Listar todos os boletos
    @GetMapping
    public List<BoletoRetorno> listarTodos() {
        return boletoRetornoRepository.findAll();
    }

    // Buscar boleto por ID
    @GetMapping("/{id}")
    public ResponseEntity<BoletoRetorno> buscarPorId(@PathVariable Long id) {
        return boletoRetornoRepository.findById(id)
                .map(boleto -> ResponseEntity.ok(boleto))
                .orElse(ResponseEntity.notFound().build());
    }

    // (Opcional) Deletar boleto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return boletoRetornoRepository.findById(id)
                .map(boleto -> {
                    boletoRetornoRepository.delete(boleto);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

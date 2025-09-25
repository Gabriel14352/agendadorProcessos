package techne.agendadorProcessos2.service;

import org.springframework.stereotype.Service;
import techne.agendadorProcessos2.model.ArquivoRetorno;
import techne.agendadorProcessos2.model.BoletoRetorno;
import techne.agendadorProcessos2.model.Job;
import techne.agendadorProcessos2.model.JobExecucao;
import techne.agendadorProcessos2.repository.ArquivoRetornoRepository;
import techne.agendadorProcessos2.repository.BoletoRetornoRepository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ArquivoService {

    private final BoletoRetornoRepository boletoRetornoRepository;
    private final ArquivoRetornoRepository arquivoRetornoRepository;

    public ArquivoService(BoletoRetornoRepository boletoRetornoRepository,
                          ArquivoRetornoRepository arquivoRetornoRepository) {
        this.boletoRetornoRepository = boletoRetornoRepository;
        this.arquivoRetornoRepository = arquivoRetornoRepository;
    }

    public void processarArquivo(File arquivo, Job job, JobExecucao execucao) {
        ArquivoRetorno arquivoRetorno = new ArquivoRetorno();
        arquivoRetorno.setJob(job);
        arquivoRetorno.setExecucao(execucao);
        arquivoRetorno.setNomeArquivo(arquivo.getName());
        arquivoRetorno.setCaminhoArquivo(arquivo.getAbsolutePath());
        arquivoRetorno.setStatus("PENDENTE");
        arquivoRetorno.setCriadoEm(LocalDateTime.now());
        arquivoRetorno.setTamanhoBytes(arquivo.length());
        arquivoRetorno = arquivoRetornoRepository.save(arquivoRetorno);

        boolean sucesso = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                char tipo = linha.charAt(0);

                if (tipo == 'H') {
                    System.out.println("📌 Header encontrado: " + linha);
                    continue;
                }

                if (tipo == 'P' || tipo == 'R') {
                    try {
                        String valorStr = linha.substring(1, 12).trim();
                        BigDecimal valor = new BigDecimal(valorStr).movePointLeft(2);

                        LocalDate dataPagamento = LocalDate.parse(linha.substring(12, 20), formatter);

                        String descricao = linha.substring(20, Math.min(linha.length(), 50)).trim();

                        BoletoRetorno boleto = new BoletoRetorno();
                        boleto.setNumeroBoleto("N/A");
                        boleto.setValor(valor);
                        boleto.setDataPagamento(dataPagamento);
                        boleto.setDescricao(descricao);
                        boleto.setStatus(tipo == 'P' ? "PAGAMENTO" : "RECEBIMENTO");

                        boletoRetornoRepository.save(boleto);

                        System.out.println("✅ Boleto salvo: " + descricao + " | Valor: " + valor + " | Data: " + dataPagamento);

                    } catch (Exception e) {
                        sucesso = false;
                        System.err.println("❌ Erro ao processar linha: " + linha);
                        System.err.println("Motivo: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            sucesso = false;
            System.err.println("❌ Erro ao ler arquivo: " + e.getMessage());
        }

        // Tentar mover o arquivo para a pasta correta
        try {
            Thread.sleep(200); // garante que o Windows libere o arquivo

            Path origem = arquivo.toPath();
            Path destino;

            if (sucesso) {
                destino = Paths.get("C:\\retornos_banco\\processados", arquivo.getName());
                arquivoRetorno.setStatus("PROCESSADO");
                arquivoRetorno.setMensagem("Arquivo processado com sucesso");
            } else {
                destino = Paths.get("C:\\retornos_banco\\erros", arquivo.getName());
                arquivoRetorno.setStatus("ERRO");
                arquivoRetorno.setMensagem("Falha no processamento");
            }

            Files.move(origem, destino, StandardCopyOption.REPLACE_EXISTING);
            arquivoRetorno.setProcessadoEm(LocalDateTime.now());
            arquivoRetorno.setCaminhoArquivo(destino.toString());
            arquivoRetornoRepository.save(arquivoRetorno);

            System.out.println("📂 Arquivo movido para: " + destino);

        } catch (Exception moveEx) {
            System.err.println("⚠️ Falha ao mover arquivo " + arquivo.getName() + ": " + moveEx.getMessage());
            arquivoRetorno.setStatus("ERRO");
            arquivoRetorno.setMensagem("Falha ao mover: " + moveEx.getMessage());
            arquivoRetorno.setProcessadoEm(LocalDateTime.now());
            arquivoRetornoRepository.save(arquivoRetorno);
        }
    }
}

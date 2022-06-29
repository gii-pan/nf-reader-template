package processador;

import dto.NotaFiscalItem;
import io.LeitorCSV;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class LeituraDeArquivos implements Callable<Map<String,BigDecimal>> {
    private final File arquivo;
    private final BarraDeProgresso barraDeProgresso;
    private final Map<String, BigDecimal> totaisPorDestinatario = new ConcurrentHashMap<>();
    private final LeitorCSV<NotaFiscalItem> leitor = new LeitorCSV<>();


    public LeituraDeArquivos(File arquivo, BarraDeProgresso barraDeProgresso) {
        this.arquivo = arquivo;
        this.barraDeProgresso = barraDeProgresso;
    }

    @Override
    public Map<String, BigDecimal> call() {
        checaSeEhCSV(arquivo);

        List<NotaFiscalItem> notaFiscalItems = leitor.leia(arquivo, NotaFiscalItem.class);

        agrupaTotal(notaFiscalItems, totaisPorDestinatario);

        barraDeProgresso.incrementa();

        return totaisPorDestinatario;
    }

    private void agrupaTotal(List<NotaFiscalItem> notaFiscalItems, Map<String, BigDecimal> totaisPorDestinatario) {

        notaFiscalItems.forEach(nf -> {

            BigDecimal valorAnterior = totaisPorDestinatario.putIfAbsent(nf.getNomeDestinatario(), nf.getValorTotal());

            if (Objects.nonNull(valorAnterior)) {
                totaisPorDestinatario.put(nf.getNomeDestinatario(), valorAnterior.add(nf.getValorTotal()));
            }
        });
    }

    private void checaSeEhCSV(File arquivo) {

        var nomeDoArquivo = arquivo.getName();
        if (!nomeDoArquivo.endsWith(".csv")) {
            throw new IllegalArgumentException("Formato inválido do arquivo: " + nomeDoArquivo);
        }
    }
}

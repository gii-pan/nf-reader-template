package processador;

import dto.RelatorioNF;
import model.Relatorio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class RelatorioNFConversor {

    List<Relatorio> converte(Map<String, BigDecimal> totaisPorDestinatario) {

        return totaisPorDestinatario.entrySet()
                .stream()
                .map((entry) -> new Relatorio(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}

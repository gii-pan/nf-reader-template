package processador;

import dto.RelatorioNF;
import io.EscritorCSV;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class ProcessadorDeArquivos {

    private final EscritorCSV escritor = new EscritorCSV();
    private final RelatorioNFConversor conversor = new RelatorioNFConversor();

    public void processaArquivosDo(String diretorio) {

        Map<String, BigDecimal> totaisPorDestinatario = new ConcurrentHashMap<>();

        Set<File> arquivos = listFilesFrom(diretorio);

        BarraDeProgresso barraDeProgresso = new BarraDeProgresso(arquivos.size());

        ExecutorService threadPool = Executors.newCachedThreadPool();

        for (File arquivo : arquivos) {

            threadPool.execute(new LeituraDeArquivos(arquivo, totaisPorDestinatario, barraDeProgresso));
//            System.out.println(threadPool.getClass().getName());
        }

        List<RelatorioNF> relatorioNFs = conversor.converte(totaisPorDestinatario);

        escritor.escreve(relatorioNFs, Path.of("src/main/resources/relatorio/relatorio.csv"));
    }

    private Set<File> listFilesFrom(String diretorio) {
        return Stream.of(requireNonNull(new File(diretorio).listFiles()))
                .filter(file -> !file.isDirectory())
                .collect(Collectors.toSet());
    }
}

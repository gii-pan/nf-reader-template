package processador;

import dto.RelatorioNF;
import io.EscritorCSV;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class ProcessadorDeArquivos  {

    private final EscritorCSV escritor = new EscritorCSV();
    private final RelatorioNFConversor conversor = new RelatorioNFConversor();
    private final List<Future<Map<String, BigDecimal>>> futureTotal = new ArrayList<>();

    public void processaArquivosDo(String diretorio) throws ExecutionException, InterruptedException {

        Map<String,BigDecimal> futureUnido = new HashMap<>();

        Set<File> arquivos = listFilesFrom(diretorio);

        BarraDeProgresso barraDeProgresso = new BarraDeProgresso(arquivos.size());

        ExecutorService threadPool = Executors.newFixedThreadPool(2, new FabricaDeThreads());

        for (File arquivo : arquivos) {
            Future<Map<String, BigDecimal>> futures = threadPool.submit(new LeituraDeArquivos(arquivo, barraDeProgresso));
            futureTotal.add(futures);
        }

        for (Future<Map<String, BigDecimal>> future: futureTotal){
            futureUnido = future.get();
        }

        List<RelatorioNF> relatorioNFs = conversor.converte(futureUnido);

        escritor.escreve(relatorioNFs, Path.of("src/main/resources/relatorio/relatorio.csv"));
    }

    private Set<File> listFilesFrom(String diretorio) {
        return Stream.of(requireNonNull(new File(diretorio).listFiles()))
                .filter(file -> !file.isDirectory())
                .collect(Collectors.toSet());
    }
}

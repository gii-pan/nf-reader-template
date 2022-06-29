import processador.ProcessadorDeArquivos;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;

public class Main {

    private static final String DIRETORIO = "src/main/resources/nfe";

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Instant inicio = Instant.now();

        ProcessadorDeArquivos processador = new ProcessadorDeArquivos();

        processador.processaArquivosDo(DIRETORIO);

        Instant fim = Instant.now();

        System.out.println("\nTempo total em segundos: "+ Duration.between(inicio, fim).toSeconds());

    }
}

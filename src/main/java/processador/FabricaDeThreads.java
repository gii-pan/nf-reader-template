package processador;

import java.util.concurrent.ThreadFactory;

public class FabricaDeThreads implements ThreadFactory {

    @Override
    public Thread newThread(Runnable tarefa) {
        Thread thread = new Thread(tarefa);
        thread.setUncaughtExceptionHandler(new ThreadErroHandler());
        thread.setDaemon(true);
        return thread;
    }
}

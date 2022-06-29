package processador;

public class ThreadErroHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable e) {
        System.out.println("Exceção na thread " +
                thread.getName() + ", " + e.getMessage());
    }
}

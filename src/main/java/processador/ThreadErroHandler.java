package processador;

public class ThreadErroHandler implements Thread.UncaughtExceptionHandler {
    private final EnvioDeEmail errorEmail = new EnvioDeEmail();
    @Override
    public void uncaughtException(Thread thread, Throwable e) {
        System.out.println("Exceção na thread " +
                thread.getName() + ", " + e.getMessage());
        errorEmail.enviaEmail();
    }
}

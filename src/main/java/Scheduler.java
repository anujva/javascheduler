public interface Scheduler {

    void schedule(Runnable runnable, long millis);

    void exit();
}

public class Application {

    public static void main(String[] args) throws InterruptedException {
        Scheduler scheduler = new FixedDelayScheduler();
        Runnable runnable1 = () -> System.out.println("Hello from first");
        Runnable runnable2 = () -> System.out.println("Hello from second");
        scheduler.schedule(runnable1, 10000);
        scheduler.schedule(runnable2, 15000);
        Thread.sleep(15000);
        scheduler.exit();
    }
}

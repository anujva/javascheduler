import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class FixedDelayScheduler implements Scheduler {

    boolean shouldRun;
    private Map<Runnable, Long> schedules;

    public FixedDelayScheduler() {
        schedules = new HashMap<>();
        shouldRun = true;
        Thread t1 = new Thread(() -> {
            while (shouldRun) {
                poll();
            }
        });
        t1.start();
    }

    public void schedule(Runnable runnable, long millis) {
        System.out.println("Scheduling the system at : " + System.currentTimeMillis());
        synchronized (schedules) {
            schedules.put(runnable, System.currentTimeMillis() + millis);
        }
    }

    @Override
    public void exit() {
        shouldRun = false;
    }

    private void poll() {
        synchronized (schedules) {
            Set<Runnable> removeThese = new LinkedHashSet<>();
            for (Map.Entry<Runnable, Long> schedule : schedules.entrySet()) {
                if (schedule.getValue() < System.currentTimeMillis()) {
                    System.out.println("Running the runnable at: " + System.currentTimeMillis());
                    new Thread(schedule.getKey()).start();
                    removeThese.add(schedule.getKey());
                }
            }
            for (Runnable runnable : removeThese) {
                schedules.remove(runnable);
            }
        }
    }
}

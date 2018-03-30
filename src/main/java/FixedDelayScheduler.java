import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
        schedules.put(runnable, System.currentTimeMillis() + millis);
    }

    @Override
    public void exit() {
        shouldRun = false;
    }

    private void poll() {
        for (Iterator<Entry<Runnable, Long>> iterator = schedules.entrySet().iterator();
            iterator.hasNext(); ) {
            Entry<Runnable, Long> schedule = iterator.next();
            if (schedule.getValue() < System.currentTimeMillis()) {
                System.out.println("Running the runnable at: " + System.currentTimeMillis());
                new Thread(schedule.getKey()).start();
                iterator.remove();
            }
        }
    }
}
